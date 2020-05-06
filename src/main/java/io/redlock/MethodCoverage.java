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

  private String coverageRunName;
  private String packageName;
  private String className;
  private String methodName;
  private int instructionsCovered;
  private int instructionsMissed;

  private int methodCovered;
  private int methodMissed;

  private int linesCovered;
  private int linesMissed;

  private int complexityCovered;
  private int complexityMissed;

  public MethodCoverage() {}

  public MethodCoverage(String packageName, String coverageRunName, String className, String methodName,
      int instructionsCovered, int instructionsMissed, int methodCovered, int methodMissed, int linesCovered,
      int linesMissed, int complexityCovered, int complexityMissed) {
    this.packageName = packageName;
    this.coverageRunName = coverageRunName;
    this.className = className;
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

  @Override public String toString() {
    return "MethodCoverage{" + "coverageRunName='" + coverageRunName + '\'' + ", packageName='" + packageName + '\''
        + ", className='" + className + '\'' + ", methodName='" + methodName + '\'' + ", instructionsCovered="
        + instructionsCovered + ", instructionsMissed=" + instructionsMissed + ", methodCovered=" + methodCovered
        + ", methodMissed=" + methodMissed + ", linesCovered=" + linesCovered + ", linesMissed=" + linesMissed
        + ", complexityCovered=" + complexityCovered + ", complexityMissed=" + complexityMissed + '}';
  }
}
