package com.upload.Database

import java.sql.{ Connection, DriverManager }

import com.upload.utils.Configuration
import com.typesafe.config.ConfigFactory

object MySQLDBConnection {

  var dbconnection: Connection = null

  /**
   * This method is called to get the RDS DB connection from the Host
   *
   * @return DB Connection
   */
  def getConnection(): Connection = {
    if (dbconnection == null || (dbconnection.isClosed())) {
      Class.forName(Configuration.driver)
      dbconnection = DriverManager.getConnection(Configuration.url, Configuration.user, Configuration.password)
      dbconnection
    } else {
      dbconnection
    }

  }

  /**
   * This method is called to close the RDS DB connection from the Host.
   */

  def closeConnection() = {
    if (dbconnection != null && !dbconnection.isClosed()) {
      dbconnection.close();
    }

  }

}