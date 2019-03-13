package com.itechart.perfomancequery.model;

import java.util.List;

public class QueryPerformanceRequest {

  //TODO validation
  private List<String> queries;

  public QueryPerformanceRequest(List<String> queries) {
    this.queries = queries;
  }

  public QueryPerformanceRequest() {
  }

  public List<String> getQueries() {
    return queries;
  }

  public void setQueries(List<String> queries) {
    this.queries = queries;
  }

}
