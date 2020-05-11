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
  private int linesum;
  private int linesmissedsum;
  private int linescoveredsum;
  private int crapsum;
  private int compsum;
  private int covavgpercent;

  public PackageSumCoverage() {
  }
}
