package com.itechart.perfomancequery.services;

import com.itechart.perfomancequery.model.report.ReportDto;

import java.util.List;
import java.util.Set;

public interface PerformanceQueryService {
  ReportDto testPerformance(List<String> queries);

  ReportDto testPerformance(List<String> queries, Set<String> database);
}
