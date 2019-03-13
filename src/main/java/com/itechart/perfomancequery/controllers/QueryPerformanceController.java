package com.itechart.perfomancequery.controllers;

import com.itechart.perfomancequery.model.QueryPerformanceRequest;
import com.itechart.perfomancequery.model.report.ReportDto;
import com.itechart.perfomancequery.services.PerformanceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
public class QueryPerformanceController {


  @Autowired
  private PerformanceQueryService performanceQueryService;

  @PostMapping(Constants.PATH_PERFORMANCE_QUERY)
  public List<ReportDto> getQueryPerformance(@RequestBody @Valid QueryPerformanceRequest content) {
    if(content.getDatabase() == null || content.getDatabase().isEmpty()) {
      return performanceQueryService.testPerformance(content.getQueries());
    }
    else
      return performanceQueryService.testPerformance(content.getQueries(), content.getDatabase());
  }

}
