package com.itechart.perfomancequery.controllers;

import com.itechart.perfomancequery.model.QueryPerformanceRequest;
import com.itechart.perfomancequery.model.report.ReportDto;
import com.itechart.perfomancequery.services.PerformanceQueryService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QueryPerformanceController {


  @Autowired
  private PerformanceQueryService performanceQueryService;

  /**
   *Methode recive request with list of queries and parameters(optional)
   * and fair service for check performance.
   * @param content with queries
   * @return List of reports
   */
  @PostMapping(Constants.PATH_PERFORMANCE_QUERY)
  public List<ReportDto> getQueryPerformance(@RequestBody @Valid QueryPerformanceRequest content) {
    if (content.getDatabase() == null || content.getDatabase().isEmpty()) {
      return performanceQueryService.testPerformance(content.getQueries());
    } else {
      return performanceQueryService.testPerformance(content.getQueries(), content.getDatabase());
    }
  }

}
