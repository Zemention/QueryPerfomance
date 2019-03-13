package com.itechart.perfomancequery.services;

import com.itechart.perfomancequery.dao.DataBaseConnectionFactory;
import com.itechart.perfomancequery.model.report.ReportDto;
import com.itechart.perfomancequery.model.report.ResultPerformanceTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class PerformanceQueryServiceImpl implements PerformanceQueryService {

  private Logger logger = LoggerFactory.getLogger(PerformanceQueryServiceImpl.class);

  @Autowired
  private DataBaseConnectionFactory dataBaseConnectionFactory;


  @Override
  public List<ReportDto> testPerformance(List<String> queries) {

    return testPerformance(queries, dataBaseConnectionFactory.getConnections().keySet());
  }

  @Override
  public List<ReportDto> testPerformance(List<String> queries, Set<String> database) {

    return runTests(queries, database);
  }

  private List<ReportDto> runTests(List<String> queries, Set<String> testForDb) {
    ExecutorService executor = Executors.newWorkStealingPool();

    List<Callable<ReportDto>> testesTask = getCollectOfTask(queries, testForDb);

    List<ReportDto> reportsDto = new ArrayList<>();
    try {
      reportsDto = executor.invokeAll(testesTask).stream()
              .map(this::getReport).collect(Collectors.toList());
      return reportsDto;
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return reportsDto;
  }

  private List<Callable<ReportDto>> getCollectOfTask(List<String> queries, Set<String> testForDb) {
    return dataBaseConnectionFactory.getConnections().entrySet().stream().filter(entry -> testForDb.contains(entry.getKey()))
            .map((entry ->  (Callable<ReportDto>) () -> testPerformanceOneInstanceDataBase(queries,
                      entry.getValue().get(), entry.getKey())
            )).collect(Collectors.toList());
  }


  private ReportDto testPerformanceOneInstanceDataBase(List<String> query, Connection connection, String dbName) {
    List<ResultPerformanceTest> resultPerformanceTests = query.stream().map(q -> {
      long finishTime = 0;
      try {
        Statement statement = connection.createStatement();
        finishTime = fireTest(q, connection, statement);
      } catch (SQLException e) {
        logger.error(e.getMessage());
      }
      return new ResultPerformanceTest(q, finishTime);
    }).collect(Collectors.toList());
    return new ReportDto(resultPerformanceTests, dbName);
  }

  private long fireTest(String query, Connection connection, Statement statement) throws SQLException {
    long startTime = System.nanoTime();
    statement.execute(query);
    long finishTime = System.nanoTime() - startTime;

    connection.rollback();
    return finishTime;
  }

  private ReportDto getReport(Future<ReportDto> future) {
    ReportDto reportWithError = new ReportDto();
    try {
      return future.get();
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      reportWithError.setErrorMessage(e.getMessage());
      logger.error(e.getMessage());
    }
    return reportWithError;
  }
}
