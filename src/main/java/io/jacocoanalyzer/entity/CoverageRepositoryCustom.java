package io.jacocoanalyzer.entity;

import java.util.List;

public interface CoverageRepositoryCustom {
  List<ReportSumCoverage> sumByPackage(String runName);

  List<ReportSumCoverage> crappyMethods(String runName, Integer limit);

  void deleteCoverageRun(String coverageRunName);
}
