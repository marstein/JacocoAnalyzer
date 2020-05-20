package io.jacocoanalyzer.web.application;

import io.jacocoanalyzer.entity.CoverageRepositoryCustom;
import io.jacocoanalyzer.entity.ReportSumCoverage;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/coverageanalysis")
public class CoverageAnalysisController {
  final CoverageRepositoryCustom coverageRepositoryCustom;
  public static Logger log = LoggerFactory.getLogger(CoverageAnalysisController.class);

  public CoverageAnalysisController(CoverageRepositoryCustom coverageRepositoryCustom) {
    this.coverageRepositoryCustom = coverageRepositoryCustom;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String getCoverageAnalysis(@RequestParam(value = "run", required = false) String runName,
      @RequestParam(value = "limit", required = false) Integer limit, Model model) {
    log.info("Getting coverage analysis!");
    List<ReportSumCoverage> coverages = coverageRepositoryCustom.sumByPackage(runName);
    model.addAttribute("coverages", coverages);
    List<ReportSumCoverage> crappyMethods = coverageRepositoryCustom.crappyMethods(runName, limit);
    model.addAttribute("crap", crappyMethods);
    log.info("created HTML page with {} coverages", coverages.size());
    return "coverageanalysis";
  }
}
