package io.jacocoanalyzer.entity;

import java.util.List;

public interface CoverageRepositoryCustom {
  List<PackageSumCoverage> sumByPackage();
}
