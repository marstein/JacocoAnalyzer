package io.redlock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileUploaderBean {
  private static final Logger log = LoggerFactory.getLogger(FileUploaderBean.class);
  @Autowired CoverageRepository repository;

  public void readAndUploadFile(File jacocoXmlFileToUpload, String coverageRunName) throws FileNotFoundException, XMLStreamException {
    log.info("uploading file {} to repo {}", jacocoXmlFileToUpload, repository);
    CoverageXMLReader reader = new CoverageXMLReader(repository);
    reader.readFromXML(new FileInputStream(jacocoXmlFileToUpload), coverageRunName);
  }
}
