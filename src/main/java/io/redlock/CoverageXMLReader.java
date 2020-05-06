package io.redlock;

import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoverageXMLReader {

  public static Logger log = LoggerFactory.getLogger(CoverageXMLReader.class);
  private final ICoverageRepository coverageRepo;

  public CoverageXMLReader(ICoverageRepository repository) {
    this.coverageRepo = repository;
  }

  public void readFromXML(InputStream is) throws XMLStreamException {
    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    XMLStreamReader reader = null;
    try {
      reader = inputFactory.createXMLStreamReader(is);
      readDocument(reader);
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }

  private void readDocument(XMLStreamReader reader) throws XMLStreamException {
    while (reader.hasNext()) {
      int eventType = reader.next();
      switch (eventType) {
        case XMLStreamReader.START_ELEMENT:
          String elementName = reader.getLocalName();
          if (elementName.equals("report")) {
            readPackage(reader, readAttribute(reader, "name"));
          }
          break;
        case XMLStreamReader.END_ELEMENT:
          return;
      }
    }
    throw new XMLStreamException("Premature end of file");
  }

  private void readPackage(XMLStreamReader reader, String reportName) throws XMLStreamException {
    while (reader.hasNext()) {
      int eventType = reader.next();
      switch (eventType) {
        case XMLStreamReader.START_ELEMENT:
          String elementName = reader.getLocalName();
          if (elementName.equals("package")) {
            readClasses(reader, reportName, readAttribute(reader, "name"));
          }
          break;
        case XMLStreamReader.END_ELEMENT:
          return;
      }
    }
    throw new XMLStreamException("Premature end of file");
  }

  private void readClasses(XMLStreamReader reader, String reportName, String packageName) throws XMLStreamException {
    while (reader.hasNext()) {
      int eventType = reader.next();
      if (eventType == XMLStreamReader.START_ELEMENT) {
        String elementName = reader.getLocalName();
        log.debug(elementName);
        if (elementName.equals("class")) {
          String className = readAttribute(reader, "name");
          readClass(reader, reportName, packageName, className);
        } else {
          return;
        }
      }
    }
    throw new XMLStreamException("Premature end of file");
  }


  private void readClass(XMLStreamReader reader, String reportName, String packageName, String className)
      throws XMLStreamException {

    log.info("reading class " + className);
    log.debug("debug class " + className);
    while (reader.hasNext()) {
      int eventType = reader.next();
      if (eventType == XMLStreamReader.START_ELEMENT) {
        String elementName = reader.getLocalName();
        if (elementName.equals("method")) {
          String methodName = readAttribute(reader, "name");
          MethodCoverage methodCoverage = new MethodCoverage();
          methodCoverage.setCoverageRunName(reportName);
          methodCoverage.setPackageName(packageName);
          methodCoverage.setClassName(className);
          methodCoverage.setMethodName(methodName);
          readCounters(reader, methodCoverage);
          coverageRepo.write(methodCoverage);
          log.info(methodCoverage.toString());
        }
      } else if (eventType == XMLStreamReader.END_ELEMENT && reader.getLocalName().equals("class")) {
        return; // keep reading methods until class end tag.
      }
    }
    throw new XMLStreamException("Premature end of file");
  }

  private void readCounters(XMLStreamReader reader, MethodCoverage methodCoverage) throws XMLStreamException {
    while (reader.hasNext()) {
      int eventType = reader.next();
      switch (eventType) {
        case XMLStreamReader.START_ELEMENT:
          String elementName = reader.getLocalName();
          if (elementName.equals("counter")) {
            readCounter(reader, methodCoverage);
          }
          break;
        case XMLStreamReader.END_ELEMENT:
          if (reader.getLocalName().equals("method")) {
            return;
          }
          break;
      }
    }
    throw new XMLStreamException("Premature end of file");
  }

  private void readCounter(XMLStreamReader reader, MethodCoverage methodCoverage) throws XMLStreamException {
    String counterType = readAttribute(reader, "type");
    if (counterType.equals("INSTRUCTION")) {
      methodCoverage.setInstructionsMissed(Integer.parseInt(readAttribute(reader, "missed")));
      methodCoverage.setInstructionsCovered(Integer.parseInt(readAttribute(reader, "covered")));
    }
    if (counterType.equals("LINE")) {
      methodCoverage.setLinesMissed(Integer.parseInt(readAttribute(reader, "missed")));
      methodCoverage.setLinesCovered(Integer.parseInt(readAttribute(reader, "covered")));
    }
    if (counterType.equals("COMPLEXITY")) {
      methodCoverage.setComplexityMissed(Integer.parseInt(readAttribute(reader, "missed")));
      methodCoverage.setComplexityCovered(Integer.parseInt(readAttribute(reader, "covered")));
    }
    if (counterType.equals("METHOD")) {
      methodCoverage.setMethodMissed(Integer.parseInt(readAttribute(reader, "missed")));
      methodCoverage.setMethodCovered(Integer.parseInt(readAttribute(reader, "covered")));
    }
  }

  private String readAttribute(XMLStreamReader reader, String attributeName) {
    for (int i = 0; i < reader.getAttributeCount(); i++) {
      if (reader.getAttributeName(i).toString().equals(attributeName)) {
        return reader.getAttributeValue(i);
      }
    }
    throw new IllegalStateException("attribute " + attributeName + " not found!");
  }
}
