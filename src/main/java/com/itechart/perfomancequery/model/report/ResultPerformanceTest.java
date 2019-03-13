package com.itechart.perfomancequery.model.report;

public class ResultPerformanceTest {

  private String query;
  private long resultTest;


  public ResultPerformanceTest(String query, long resultTest) {
    this.query = query;
    this.resultTest = resultTest;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public long getResultTest() {
    return resultTest;
  }

  public void setResultTest(long resultTest) {
    this.resultTest = resultTest;
  }
}
