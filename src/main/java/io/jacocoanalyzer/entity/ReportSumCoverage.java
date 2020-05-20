package io.jacocoanalyzer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

/**
 * Sum up counters by package and run.
 */
@Entity
@Data
public class ReportSumCoverage {
  @Id private int id;
  private String coverageRunName;
  private String reportName;
  private String packageName;
  private String className;
  private String methodName;
  private int linesum;
  private int linesmissedsum;
  private int linescoveredsum;
  private int crapsum;
  private int compsum;
  private int covavgpercent;

  public ReportSumCoverage() {
  }
}
