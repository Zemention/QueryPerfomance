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
import java.util.function.Supplier;

@Service
public class PerformanceQueryServiceImpl implements PerformanceQueryService {

  private Logger logger = LoggerFactory.getLogger(PerformanceQueryServiceImpl.class);

  @Autowired
  private DataBaseConnectionFactory dataBaseConnectionFactory;


  @Override
  public ReportDto testPerformance(List<String> queries) {

    return testPerformance(queries, dataBaseConnectionFactory.getConnections().keySet());
  }

  @Override
  public ReportDto testPerformance(List<String> queries, Set<String> database) {
    ReportDto reportDto = new ReportDto();
    queries.forEach(query -> runTests(query, reportDto,  database));

    return reportDto;

  }

  private void runTests(String queries, ReportDto reportDto, Set<String> testForDb) {
    ExecutorService executor = Executors.newWorkStealingPool();
    List<Callable<ResultPerformanceTest>> testesTask = new ArrayList<>();
    dataBaseConnectionFactory.getConnections().entrySet().stream().filter(entry -> testForDb.contains(entry.getKey()))
            .forEach((entry -> {
      Callable<ResultPerformanceTest> task = () -> testPerformanceOneInstanceDataBase(queries,
              entry.getValue().get(), entry.getKey());
      testesTask.add(task);
    }));
    try {
      executor.invokeAll(testesTask).forEach(future -> addResultToReport(reportDto, future));
    } catch (Exception e) {
      logger.error(e.getMessage());
      reportDto.setErrorMessage(e.getMessage());
    }
  }


  private ResultPerformanceTest testPerformanceOneInstanceDataBase(String query, Connection connection, String dbName) throws SQLException {
    try (Statement statement = connection.createStatement(); connection) {
      long startTime = System.nanoTime();

      statement.execute(query);
      long finishTime = System.nanoTime() - startTime;

      connection.rollback();

      return new ResultPerformanceTest(query, finishTime, dbName);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new SQLException(e);
    }
  }

  private void addResultToReport(ReportDto reportDto, Future<ResultPerformanceTest> future) {
    try {
      ResultPerformanceTest resultTest = future.get();
      List<ResultPerformanceTest> resultsTest = reportDto.getResult().get(resultTest.getDataBase());
      if (resultsTest == null) {
        resultsTest = new ArrayList<>();
        resultsTest.add(future.get());
        reportDto.getResult().put(resultTest.getDataBase(), resultsTest);
      } else {
        reportDto.getResult().get(resultTest.getDataBase()).add(future.get());
      }
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      reportDto.setErrorMessage(e.toString());
      logger.error(e.getMessage());
    }
  }
}
