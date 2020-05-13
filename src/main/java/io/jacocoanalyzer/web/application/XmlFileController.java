package io.jacocoanalyzer.web.application;

import io.jacocoanalyzer.CoverageXMLReader;
import io.jacocoanalyzer.entity.CoverageRepository;
import io.jacocoanalyzer.entity.CoverageRepositoryCustom;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class XmlFileController {
  final CoverageRepository coverageRepository;
  public static Logger log = LoggerFactory.getLogger(XmlFileController.class);
  private final CoverageRepositoryCustom repositoryCustom;

  public XmlFileController(CoverageRepository coverageRepository, CoverageRepositoryCustom repositoryCustom) {
    this.coverageRepository = coverageRepository;
    this.repositoryCustom = repositoryCustom;
  }

  /**
   * @param file            upload in the Jacoco XML results file format
   * @param coverageRunName sprint name, build name
   * @throws IOException        if comm trouble
   * @throws XMLStreamException if parsing fails
   */
  @PostMapping("/jacocofile") public void postCoverageXMLFile(@RequestParam("file") MultipartFile file,
      @RequestParam("runName") String coverageRunName) throws IOException, XMLStreamException {
    log.info("POSTing XML file {}", file.getOriginalFilename());
    CoverageXMLReader reader = new CoverageXMLReader(coverageRepository);
    reader.readFromXML(file.getInputStream(), coverageRunName);
  }

  /**
   * delete all data or selected coverage runs in the table to clean up
   *
   * @param coverageRunName * or empty drops all records; else all records with coverage-run-names matching will be
   *                        deleted.
   */
  @DeleteMapping("/coverage_run") public void deleteCoverageRun(@RequestParam("run") String coverageRunName) {
    if (coverageRunName.equals("*") || coverageRunName.isBlank()) {
      coverageRepository.deleteAll();
    } else {
      repositoryCustom.deleteCoverageRun(coverageRunName);
    }
  }
}
