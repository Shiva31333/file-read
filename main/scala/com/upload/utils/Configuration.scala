package com.upload.utils

import com.typesafe.config.ConfigFactory

object Configuration {

  private val config = ConfigFactory.load

  config.checkValid(ConfigFactory.defaultReference)

  val httpInterface = config.getString("http.interface")
  val httpPort = config.getInt("http.port")

  val driver = config.getString("db.default.driver")
  val url = config.getString("db.default.url")
  val user = config.getString("db.default.user")
  val password = config.getString("db.default.password")
  val filePath = config.getString("filePath")

}