package io.jacocoanalyzer.web.application;

import io.jacocoanalyzer.CoverageXMLReader;
import io.jacocoanalyzer.entity.CoverageRepository;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class XmlFileController {
  @Autowired CoverageRepository coverageRepository;

  @PostMapping("/jacocofile") public void postCoverageXMLFile(@RequestParam("file") MultipartFile file,
      @RequestParam("runName") String coverageRunName)
      throws IOException, XMLStreamException {
    CoverageXMLReader reader = new CoverageXMLReader(coverageRepository);
    reader.readFromXML(file.getInputStream(), coverageRunName);
  }
}
