package com.itechart.perfomancequery.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class QueryPerformanceRequest {

  @NotNull
  @NotEmpty
  private List<String> queries;

  public List<String> getQueries() {
    return queries;
  }

  public void setQueries(List<String> queries) {
    this.queries = queries;
  }

}
