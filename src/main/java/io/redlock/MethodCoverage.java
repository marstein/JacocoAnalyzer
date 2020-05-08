package io.redlock;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data // Lombok getters and setters.
public class MethodCoverage {
  @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;

  private String reportName;
  private String packageName;
  private String coverageRunName;
  private String className;
  private String sourcefileName;
  private String methodName;
  private int instructionsCovered;
  private int instructionsMissed;

  private int methodCovered;
  private int methodMissed;

  private int linesCovered;
  private int linesMissed;

  private int complexityCovered;
  private int complexityMissed;

  public MethodCoverage() {
  }

  @Override public String toString() {
    return "MethodCoverage{" + "id=" + id + ", reportName='" + reportName + '\'' + ", packageName='" + packageName
        + '\'' + ", className='" + className + '\'' + ", sourcefileName='" + sourcefileName + '\'' + ", methodName='"
        + methodName + '\'' + ", instructionsCovered=" + instructionsCovered + ", instructionsMissed="
        + instructionsMissed + ", methodCovered=" + methodCovered + ", methodMissed=" + methodMissed + ", linesCovered="
        + linesCovered + ", linesMissed=" + linesMissed + ", complexityCovered=" + complexityCovered
        + ", complexityMissed=" + complexityMissed + '}';
  }

  public MethodCoverage(String packageName, String reportName, String coverageRunName, String className,
      String sourcefileName, String methodName, int instructionsCovered, int instructionsMissed, int methodCovered,
      int methodMissed, int linesCovered, int linesMissed, int complexityCovered, int complexityMissed) {
    this.packageName = packageName;
    this.reportName = reportName;
    this.coverageRunName = coverageRunName;
    this.className = className;
    this.sourcefileName = sourcefileName;
    this.methodName = methodName;
    this.instructionsCovered = instructionsCovered;
    this.instructionsMissed = instructionsMissed;
    this.methodCovered = methodCovered;
    this.methodMissed = methodMissed;
    this.linesCovered = linesCovered;
    this.linesMissed = linesMissed;
    this.complexityCovered = complexityCovered;
    this.complexityMissed = complexityMissed;
  }

}
