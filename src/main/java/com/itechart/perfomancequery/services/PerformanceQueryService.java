package com.itechart.perfomancequery.services;

import com.itechart.perfomancequery.model.report.ReportDto;

import java.util.List;
import java.util.Set;

public interface PerformanceQueryService {
  List<ReportDto> testPerformance(List<String> queries);

  List<ReportDto> testPerformance(List<String> queries, Set<String> database);
}
