package com.itechart.perfomancequery.controllers;

import com.itechart.perfomancequery.PerfomanceQueryApplication;
import com.itechart.perfomancequery.TestUtils;
import com.itechart.perfomancequery.model.QueryPerformanceRequest;
import com.itechart.perfomancequery.model.report.ReportDto;
import com.itechart.perfomancequery.model.report.ResultPerformanceTest;
import com.itechart.perfomancequery.services.PerformanceQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes = PerfomanceQueryApplication.class)
class QueryPerformanceControllerTest {

    @MockBean
    private PerformanceQueryServiceImpl perfomanceQueryService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getQueryPerformance_oneQueryWithDefaultSettings_ReturnReportForAllDataBase() throws Exception {
        QueryPerformanceRequest content = new QueryPerformanceRequest();
        content.setQueries(List.of("Select * from Table"));
        ReportDto reportDto = new ReportDto();
        ResultPerformanceTest resultPerformanceTest = new ResultPerformanceTest("mysql", 1000L);
        reportDto.getResult().add(resultPerformanceTest);
        when(perfomanceQueryService.testPerformance(anyList())).thenReturn(List.of(reportDto));
        this.mockMvc.perform(post("/"+Constants.PATH_PERFORMANCE_QUERY)
                .content(TestUtils.convertObjectToJsonBytes(content)).contentType(TestUtils.APPLICATION_JSON_UTF8)
                .accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].result[0].resultTest").value("1000"));
    }

    @Test
    void shouldReturnStatusBadRequestAndErrorMessage() throws Exception {
        this.mockMvc.perform(post("/"+Constants.PATH_PERFORMANCE_QUERY)
                .param("query", "")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest());
    }
}
