package io.redlock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
      String fileName = args[1];
      CoverageXMLReader c = new CoverageXMLReader();
      c.readFromXML(new FileInputStream(fileName));
    }
}
