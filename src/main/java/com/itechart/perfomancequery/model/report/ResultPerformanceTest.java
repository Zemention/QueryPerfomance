package com.itechart.perfomancequery.model.report;

public class ResultPerformanceTest {

  private String query;
  private long resultTest;
  private String dataBase;

  public ResultPerformanceTest(String query, long resultTest) {
    this.query = query;
    this.resultTest = resultTest;
  }

  public ResultPerformanceTest(String query, long resultTest, String dataBase) {
    this.query = query;
    this.resultTest = resultTest;
    this.dataBase = dataBase;
  }

  public String getDataBase() {
    return dataBase;
  }

  public void setDataBase(String dataBase) {
    this.dataBase = dataBase;
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
