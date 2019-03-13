package com.itechart.perfomancequery.dao;

import com.itechart.perfomancequery.model.database.DataBaseInfo;
import com.itechart.perfomancequery.model.database.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseConnectionFactory {

  private Logger logger = LoggerFactory.getLogger(DataBaseConnectionFactory.class);

  @Autowired
  private DbConfig dbConfig;

  private Map<String, Supplier<Connection>> connections;

  @PostConstruct
  private void initialization() {
    connections = new HashMap<>();
    dbConfig.getDataBaseInfo().forEach(config -> connections.put(config.getName(), createConnection(config)));
  }

  private Supplier<Connection> createConnection(DataBaseInfo config) {
    return () -> {
      try {
        Connection con = DriverManager.getConnection(config.getUrl(),
                config.getUser(), config.getPassword());
        con.setAutoCommit(false);
        return con;
      } catch (SQLException e) {
        logger.error(e.getMessage());
      }
      return null;
    };
  }

  public Map<String, Supplier<Connection>> getConnections() {
    return connections;
  }

  public void setConnections(Map<String, Supplier<Connection>> connections) {
    this.connections = connections;
  }
}
