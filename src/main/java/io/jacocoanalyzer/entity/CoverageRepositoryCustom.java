package io.jacocoanalyzer.entity;

import java.util.List;

public interface CoverageRepositoryCustom {
  List<ReportSumCoverage> sumByPackage();
  void deleteCoverageRun(String coverageRunName);
}
