package io.jacocoanalyzer.web.application;

import io.jacocoanalyzer.CoverageXMLReader;
import io.jacocoanalyzer.entity.CoverageRepositoryCustom;
import io.jacocoanalyzer.entity.PackageSumCoverage;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/coverageanalysis")
public class CoverageAnalysisController {
  @Autowired CoverageRepositoryCustom coverageRepositoryCustom;
  public static Logger log = LoggerFactory.getLogger(CoverageAnalysisController.class);

  @RequestMapping(method = RequestMethod.GET) public String getCoverageAnalysis(Model model) {
    log.info("Getting coverage analysis!");
    List<PackageSumCoverage> coverages = coverageRepositoryCustom.sumByPackage();
    model.addAttribute("coverages", coverages);
    log.info("created HTML page with {} coverages",coverages.size());
    return "coverageanalysis";
  }
}
