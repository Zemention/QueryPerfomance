package com.itechart.perfomancequery.services;

import com.itechart.perfomancequery.dao.DataBaseStatement;
import com.itechart.perfomancequery.model.report.ReportDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceQueryServiceTest {

  @MockBean
  private DataBaseStatement dataBaseStatement;

  @Autowired
  private PerformanceQueryService performanceQueryService;

  @Test
  public void testPerformance_oneQueryDefaultSettingsToOneDb_returnReportForAllConnectedDb() throws Exception {
    List<String> queries = List.of("query1");
    Map<String, Connection> connections = new HashMap<>();

    Connection mockConnection = Mockito.mock(Connection.class);
    Statement mockStatement = Mockito.mock(Statement.class);
    when(mockStatement.execute(anyString())).thenAnswer((Answer<Boolean>) invocation -> {
      //It is simulate request to data base
      Thread.sleep(5000);
      return Boolean.TRUE;
    });
    when(mockConnection.createStatement()).thenReturn(mockStatement);
    connections.put("mysql", mockConnection);
    when(dataBaseStatement.getConnections()).thenReturn(connections);

    ReportDto reportDto = performanceQueryService.testPerformance(queries);
    assertTrue(reportDto.getResult().values().stream().anyMatch((performanceResult -> performanceResult.get(0).getResultTest() >= 1000 * 100_0000)));
  }

}