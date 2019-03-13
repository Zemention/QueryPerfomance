package com.itechart.perfomancequery.model.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "db")
public class DbConfig {

  private List<DataBaseInfo> dataBaseInfo = new ArrayList<>();

  public List<DataBaseInfo> getDataBaseInfo() {
    return dataBaseInfo;
  }

  public void setDataBaseInfo(List<DataBaseInfo> dataBaseInfo) {
    this.dataBaseInfo = dataBaseInfo;
  }
}
