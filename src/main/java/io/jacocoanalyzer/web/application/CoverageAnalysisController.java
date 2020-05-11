package io.jacocoanalyzer.web.application;

import io.jacocoanalyzer.entity.CoverageRepositoryCustom;
import io.jacocoanalyzer.entity.PackageSumCoverage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/coverageanalysis")
public class CoverageAnalysisController {
  @Autowired CoverageRepositoryCustom coverageRepositoryCustom;

  @RequestMapping(method = RequestMethod.GET) public String getCoverageAnalysis(Model model) {
    List<PackageSumCoverage> coverages = coverageRepositoryCustom.sumByPackage();
    model.addAttribute("coverages", coverages);
    return "coverageanalysis";
  }
}
