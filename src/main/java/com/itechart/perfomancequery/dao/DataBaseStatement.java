package com.itechart.perfomancequery.dao;

import com.itechart.perfomancequery.model.database.DbConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataBaseStatement {

  private Logger logger = LoggerFactory.getLogger(DataBaseStatement.class);

  @Autowired
  private DbConfig dbConfig;

  private Map<String, Connection> connections;

  @PostConstruct
  private void initialization() {
    connections = new HashMap<>();
    dbConfig.getDataBaseInfo().forEach(config -> {
      try {
        Connection con = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
        con.setAutoCommit(false);
        connections.put(config.getName(), con);
      } catch (SQLException e) {
        logger.error(e.getMessage());
      }
    });
  }

  //TODO use poop of connection
  public Map<String, Connection> getConnections() {
    return connections;
  }

  public void setConnections(Map<String, Connection> connections) {
    this.connections = connections;
  }
}
