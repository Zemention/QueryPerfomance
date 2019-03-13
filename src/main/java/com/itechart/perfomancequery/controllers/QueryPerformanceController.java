package com.itechart.perfomancequery.controllers;

import com.itechart.perfomancequery.model.QueryPerformanceRequest;
import com.itechart.perfomancequery.model.report.ReportDto;
import com.itechart.perfomancequery.services.PerformanceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class QueryPerformanceController {


  @Autowired
  private PerformanceQueryService performanceQueryService;

  //TODO recive parameters for query
  @PostMapping(Constants.PATH_PERFORMANCE_QUERY)
  public ReportDto getQueryPerformance(@RequestBody @Valid QueryPerformanceRequest content) {
    return performanceQueryService.testPerformance(content.getQueries());
  }

}
