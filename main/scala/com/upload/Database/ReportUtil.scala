package com.upload.Database

import java.sql.ResultSet
import java.time.Instant
import java.util.{ Date, TimeZone, UUID }
import com.upload.Database.ObjectMapperUtil._
import org.slf4j.{ Logger, LoggerFactory }

trait Logging {
  implicit lazy val log: Logger = LoggerFactory.getLogger(this.getClass)
}

class ResponseException(
  message: String = "Unable to process Request: ",
  cause: Throwable = None.orNull
)
    extends Exception(message, cause)

case class ReportRules(
  nodeids: Set[String] = Set.empty,
  alarmtypes: Set[String] = Set.empty,
  alertstatus: Option[String] = None,
  reportcolumns: Set[String] = Set.empty
)

case class TestValue(make: String, model: String, pmin: String, pmax: String, gt: String, lt: String, steps: String, resourcePath: String)

case class Users(userName: Option[String] = None, userEmail: Option[String] = None)

case class RawReport(
  reportName: Option[String] = None,
  reportType: Option[String] = Some("Alert Summary"),
  timePeriod: Option[String] = None,
  schedule: Option[String] = None,
  active: Boolean = false,
  rules: Option[String] = None,
  recipients: Set[Users] = Set.empty
)

object RawReport {
  TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
  val validStatus = Set("all", "active", "inactive")
  val validTime = Set("1day", "2days", "7days", "30days", "60days", "90days")
  val validColumns = Set(
    "nodeid",
    "nodehw",
    "action",
    "alarmtype",
    "ufname",
    "severity",
    "active",
    "created",
    "updated",
    "updated_by"
  )

  def validateRules(str: String): TestValue = {
    val rules: TestValue = fromJson[TestValue](str)
    rules
  }

}

case class ResponseMsg(message: Option[String] = None)

object ResponseMsg {
  def errorMsg(msg: String): ResponseMsg = ResponseMsg(Some("Error: " + msg))

  def successMsg(msg: String): ResponseMsg =
    ResponseMsg(Some("Success: " + msg))
}