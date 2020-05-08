package io.jacocoanalyzer.web.application;

import io.jacocoanalyzer.entity.CoverageRepository;
import io.jacocoanalyzer.entity.MethodCoverage;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/coverageanalysis")
public class CoverageAnalysisController {
  @Autowired CoverageRepository coverageRepository;

  @RequestMapping(method = RequestMethod.GET) public String getCoverageAnalysis(Model model) {
    List<MethodCoverage> coverages=new ArrayList<>();
    coverageRepository.findAll().forEach(coverages::add);
    model.addAttribute("coverages", coverages);
    return "coverageanalysis";
  }
}
