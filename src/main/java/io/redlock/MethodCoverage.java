package io.redlock;

public class MethodCoverage {
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


  public MethodCoverage() {
  }

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

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getCoverageRunName() {
    return coverageRunName;
  }

  public void setCoverageRunName(String coverageRunName) {
    this.coverageRunName = coverageRunName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public int getInstructionsCovered() {
    return instructionsCovered;
  }

  public void setInstructionsCovered(int instructionsCovered) {
    this.instructionsCovered = instructionsCovered;
  }

  public int getInstructionsMissed() {
    return instructionsMissed;
  }

  public void setInstructionsMissed(int instructionsMissed) {
    this.instructionsMissed = instructionsMissed;
  }

  public int getMethodCovered() {
    return methodCovered;
  }

  public void setMethodCovered(int methodCovered) {
    this.methodCovered = methodCovered;
  }

  public int getMethodMissed() {
    return methodMissed;
  }

  public void setMethodMissed(int methodMissed) {
    this.methodMissed = methodMissed;
  }

  public int getLinesCovered() {
    return linesCovered;
  }

  public void setLinesCovered(int linesCovered) {
    this.linesCovered = linesCovered;
  }

  public int getLinesMissed() {
    return linesMissed;
  }

  public void setLinesMissed(int linesMissed) {
    this.linesMissed = linesMissed;
  }

  public int getComplexityCovered() {
    return complexityCovered;
  }

  public void setComplexityCovered(int complexityCovered) {
    this.complexityCovered = complexityCovered;
  }

  public int getComplexityMissed() {
    return complexityMissed;
  }

  public void setComplexityMissed(int complexityMissed) {
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
