package com.itechart.perfomancequery.model.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDto {


    private Map<String, List<ResultPerformanceTest>> result = new HashMap<>();
    private String errorMessage;

    public Map<String, List<ResultPerformanceTest>>  getResult() {
        return result;
    }

    public void setResult(Map<String, List<ResultPerformanceTest>> result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
