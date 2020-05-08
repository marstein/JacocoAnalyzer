package io.jacocoanalyzer;

import io.jacocoanalyzer.entity.CoverageRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileUploaderBean {
  private static final Logger log = LoggerFactory.getLogger(FileUploaderBean.class);
  final CoverageRepository repository;

  public FileUploaderBean(CoverageRepository repository) {
    this.repository = repository;
  }

  public int readAndUploadFile(File jacocoXmlFileToUpload, String coverageRunName)  {
    log.info("uploading file {} to repo {}", jacocoXmlFileToUpload, repository);
    CoverageXMLReader reader = new CoverageXMLReader(repository);
    try (FileInputStream is = new FileInputStream(jacocoXmlFileToUpload)) {
      reader.readFromXML(is, coverageRunName);
    } catch (XMLStreamException | FileNotFoundException e) {
      log.error("Could not read XML file {} because of error: {}", jacocoXmlFileToUpload, e.getMessage());
      return 1;
    } catch (IOException e) {
      log.error("Problem reading XML file {}, {}", jacocoXmlFileToUpload, e.getMessage());
      return 2;
    }
    return 0;
  }
}
