package io.jacocoanalyzer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity // Generated DB table automatically. See hibernate.auto-ddl
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
}
