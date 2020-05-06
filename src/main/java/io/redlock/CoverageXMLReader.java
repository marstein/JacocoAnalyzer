package io.redlock;

import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class CoverageXMLReader {

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
          break;
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
        if (elementName.equals("class")) {
          String className = readAttribute(reader, "name");
          readClass(reader, reportName, packageName, className);
        }
      } else if (eventType == XMLStreamReader.END_ELEMENT) {
        return;
      }
    }
    throw new XMLStreamException("Premature end of file");
  }


  private void readClass(XMLStreamReader reader, String reportName, String packageName, String className)
      throws XMLStreamException {
    while (reader.hasNext()) {
      int eventType = reader.next();
      if (eventType == XMLStreamReader.START_ELEMENT) {
        String elementName = reader.getLocalName();
        if (elementName.equals("method")) {
          String methodName = readAttribute(reader, "name");
          MethodCoverage methodCoverage = new MethodCoverage();
          methodCoverage.setCoverageRunName(reportName);
          methodCoverage.setPackageName(packageName);
          methodCoverage.setMethodName(methodName);
          readCounters(reader, methodCoverage);
        }
      } else if (eventType == XMLStreamReader.END_ELEMENT) {
        return;
      }
    }

    throw new XMLStreamException("Premature end of file");
  }

  private String readAttribute(XMLStreamReader reader, String attributeName) {
    for (int i = 0; i < reader.getAttributeCount(); i++) {
      if (reader.getAttributeName(i).toString().equals(attributeName)) {
        return reader.getAttributeValue(i);
      }
    }
    throw new IllegalStateException("attribute " + attributeName + " not found!");
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
          return;
      }
    }
    throw new XMLStreamException("Premature end of file");
  }

  private void readCounter(XMLStreamReader reader, MethodCoverage methodCoverage) throws XMLStreamException {
    String counterType = reader.getAttributeValue(0);
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
}
