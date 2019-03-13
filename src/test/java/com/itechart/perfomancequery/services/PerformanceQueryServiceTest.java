package com.itechart.perfomancequery.services;

import com.itechart.perfomancequery.dao.DataBaseConnectionFactory;
import com.itechart.perfomancequery.model.report.ReportDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceQueryServiceTest {

  @MockBean
  private DataBaseConnectionFactory dataBaseConnectionFactory;

  @Autowired
  private PerformanceQueryService performanceQueryService;

  private Connection mockConnection;

  private Statement mockStatement;

  private Map<String, Supplier<Connection>> connections;

  @Before
  public void setUpRequredData() throws SQLException {

    connections = new HashMap<>();
    mockConnection = Mockito.mock(Connection.class);
    mockStatement = Mockito.mock(Statement.class);
    when(mockStatement.execute(anyString())).thenAnswer((Answer<Boolean>) invocation -> {
      //It is simulate request to data base
      Thread.sleep(5000);
      return Boolean.TRUE;
    });
    when(mockConnection.createStatement()).thenReturn(mockStatement);
    Supplier<Connection> mockSupplierConnection = () -> mockConnection;
    connections.put("mysql", mockSupplierConnection);

    when(dataBaseConnectionFactory.getConnections()).thenReturn(connections);
  }

  @Test
  public void testPerformance_oneQueryDefaultSettingsToOneDb_returnReportForAllConnectedDb() {
    List<String> queries = List.of("query1");
    List<ReportDto> reportDto = performanceQueryService.testPerformance(queries);
    assertTrue(reportDto.get(0).getResult().stream().anyMatch((performanceResult -> performanceResult.getResultTest() >= 1000 * 100_0000)));
  }

  @Test
  public void testPerformance_manyQueryDefaultSettingsToOneDb_returnReportForAllConnectedDb() {
    List<String> queries = List.of("query1", "query2");

    List<ReportDto>  reportDto = performanceQueryService.testPerformance(queries);
    assertTrue(reportDto.get(0).getResult().stream().anyMatch((performanceResult -> performanceResult.getResultTest() >= 1000 * 100_0000)));
  }



}