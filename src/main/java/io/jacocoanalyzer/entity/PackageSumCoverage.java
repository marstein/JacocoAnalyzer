package io.jacocoanalyzer.entity;

import io.jacocoanalyzer.entity.MethodCoverage;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

/**
 * Sum up counters by package and run.
 */
@Entity
@Data
public class PackageSumCoverage {
  @Id private int id;
  private String packageName;
  private String coverageRunName;
  private int instructionsCovered;
  private int instructionsMissed;
  private int methodCovered;
  private int methodMissed;
  private int linesCovered;
  private int linesMissed;
  private int complexityCovered;
  private int complexityMissed;

  public PackageSumCoverage() {
  }

  public PackageSumCoverage(MethodCoverage methodCoverage) {
    id = (int) System.nanoTime();
    this.packageName = methodCoverage.getPackageName();
    this.coverageRunName = methodCoverage.getCoverageRunName();
    this.instructionsCovered = methodCoverage.getInstructionsCovered();
    this.instructionsMissed = methodCoverage.getInstructionsMissed();
    methodCovered = methodCoverage.getMethodCovered();
    methodMissed = methodCoverage.getMethodMissed();
    linesCovered = methodCoverage.getLinesCovered();
    linesMissed = methodCoverage.getLinesMissed();
    complexityCovered = methodCoverage.getComplexityCovered();
    complexityMissed = methodCoverage.getComplexityMissed();
  }
}
