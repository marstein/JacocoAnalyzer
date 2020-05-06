package io.redlock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;
import org.junit.jupiter.api.Test;

class CoverageXMLReaderTest {

  @Test void readFromXML() throws FileNotFoundException, XMLStreamException {
    CoverageXMLReader c = new CoverageXMLReader();
    c.readFromXML(new FileInputStream("src/test/resource/sample.xml"));
  }
}