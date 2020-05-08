package io.redlock;

import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoverageXMLReader {

  public static Logger log = LoggerFactory.getLogger(CoverageXMLReader.class);
  private final CoverageRepository coverageRepo;

  public CoverageXMLReader(CoverageRepository repository) {
    this.coverageRepo = repository;
  }

  public class Watcher {
    private final String coverageRun;

    public Watcher(String coverageRun) {
      this.coverageRun = coverageRun;
    }

    private String reportName;

    public void acceptStartReport(XMLStreamReader reader) {
      reportName = readAttribute(reader, "name");
    }

    public void acceptEndReport(XMLStreamReader reader) {
      reportName = null;
    }

    private String packageName;

    public void acceptStartPackage(XMLStreamReader reader) {
      packageName = readAttribute(reader, "name");
    }

    public void acceptEndPackage(XMLStreamReader reader) {
      packageName = null;
    }


    private String sourceFileName;
    private String className;
    private int classCounter = 0;

    public void acceptStartClass(XMLStreamReader reader) {
      log.info("reading class {}", readAttribute(reader, "name"));
      className = readAttribute(reader, "name");
      sourceFileName = readAttribute(reader, "sourcefilename");
      classCounter++;
    }

    public void acceptEndClass() {
      log.info("reading class {}, file {} done", className, sourceFileName);
      className = null;
      sourceFileName = null;
    }

    MethodCoverage methodCoverage;
    private int methodCounter = 0;

    public void acceptStartMethod(XMLStreamReader reader) throws XMLStreamException {
      String methodName = readAttribute(reader, "name");
      methodCoverage = new MethodCoverage();
      methodCoverage.setSourcefileName(sourceFileName);
      methodCoverage.setReportName(reportName);
      methodCoverage.setCoverageRunName(coverageRun);
      methodCoverage.setPackageName(packageName);
      methodCoverage.setClassName(className);
      methodCoverage.setMethodName(methodName);
      methodCounter++;
    }

    public void acceptEndMethod() {
      coverageRepo.save(methodCoverage);
      log.info("Method done. {}", methodCoverage);
      methodCoverage = null;
    }

    public void acceptEndCounter() {
    }

    public void acceptStartCounter(XMLStreamReader reader) throws XMLStreamException {
      if (methodCoverage == null) {
        return; // do not record class or package level counters.
      }
      String counterType = readAttribute(reader, "type");
      switch (counterType) {
        case "INSTRUCTION":
          methodCoverage.setInstructionsMissed(Integer.parseInt(readAttribute(reader, "missed")));
          methodCoverage.setInstructionsCovered(Integer.parseInt(readAttribute(reader, "covered")));
          break;
        case "LINE":
          methodCoverage.setLinesMissed(Integer.parseInt(readAttribute(reader, "missed")));
          methodCoverage.setLinesCovered(Integer.parseInt(readAttribute(reader, "covered")));
          break;
        case "COMPLEXITY":
          methodCoverage.setComplexityMissed(Integer.parseInt(readAttribute(reader, "missed")));
          methodCoverage.setComplexityCovered(Integer.parseInt(readAttribute(reader, "covered")));
          break;
        case "METHOD":
          methodCoverage.setMethodMissed(Integer.parseInt(readAttribute(reader, "missed")));
          methodCoverage.setMethodCovered(Integer.parseInt(readAttribute(reader, "covered")));
          break;
      }
    }
  }

  public void visit(Watcher watcher, XMLStreamReader reader) throws XMLStreamException {
    while (reader.hasNext()) {
      int eventType = reader.next();
      switch (eventType) {
        case XMLStreamReader.START_ELEMENT:
          String elementName = reader.getLocalName();
          switch (elementName) {
            case "report":
              log.info("reading report {}", readAttribute(reader, "name"));
              watcher.acceptStartReport(reader);
              break;
            case "package":
              watcher.acceptStartPackage(reader);
              break;
            case "class":
              watcher.acceptStartClass(reader);
              break;
            case "method":
              watcher.acceptStartMethod(reader);
              break;
            case "counter":
              watcher.acceptStartCounter(reader);
              break;
          }
          break;
        case XMLStreamReader.END_ELEMENT:
          elementName = reader.getLocalName();
          switch (elementName) {
            case "report":
              watcher.acceptEndReport(reader);
              break;
            case "package":
              watcher.acceptEndPackage(reader);
              break;
            case "class":
              watcher.acceptEndClass();
              break;
            case "method":
              watcher.acceptEndMethod();
              break;
            case "counter":
              watcher.acceptEndCounter();
              break;
          }
          break;
      }
    }
    log.info("Parsing XML file done. {} classes with {} methods.", watcher.classCounter, watcher.methodCounter);
  }

  public void readFromXML(InputStream is, String coverageRun) throws XMLStreamException {
    XMLStreamReader reader = null;
    try {
      reader = XMLInputFactory.newInstance().createXMLStreamReader(is);
      visit(new Watcher(coverageRun), reader);
      //      readDocument(reader, coverageRun);
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }

  protected static String readAttribute(XMLStreamReader reader, String attributeName) {
    for (int i = 0; i < reader.getAttributeCount(); i++) {
      if (reader.getAttributeName(i).toString().equals(attributeName)) {
        return reader.getAttributeValue(i);
      }
    }
    throw new IllegalStateException("attribute " + attributeName + " not found!");
  }
}
