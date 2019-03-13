package com.itechart.perfomancequery.services;

import com.itechart.perfomancequery.model.report.ReportDto;

import java.util.List;

public interface PerformanceQueryService {
  ReportDto testPerformance(List<String> queries);
}
