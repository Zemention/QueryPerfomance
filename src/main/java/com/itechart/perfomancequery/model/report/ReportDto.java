package com.itechart.perfomancequery.model.report;

import java.util.ArrayList;
import java.util.List;

public class ReportDto {

  private List<ResultPerformanceTest> result = new ArrayList<>();
  private String errorMessage;
  private String dataBaseName;

  public ReportDto() {
  }

  public List<ResultPerformanceTest> getResult() {
    return result;
  }

  public ReportDto(List<ResultPerformanceTest> result, String dataBaseName) {
    this.result = result;
    this.dataBaseName = dataBaseName;
  }

  public String getDataBaseName() {
    return dataBaseName;
  }

  public void setDataBaseName(String dataBaseName) {
    this.dataBaseName = dataBaseName;
  }

  public void setResult(List<ResultPerformanceTest> result) {
    this.result = result;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
