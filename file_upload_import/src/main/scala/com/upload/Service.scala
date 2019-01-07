package com.upload

import java.io.{ File, PrintWriter }
import java.nio.file.Paths
import java.sql.{ PreparedStatement, ResultSet }

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives.{ as, complete, entity, post, _ }
import akka.stream.Materializer
import akka.stream.scaladsl.FileIO
import com.upload.Database.MySQLDBConnection
import com.upload.Database.ObjectMapperUtil.toJson
import com.upload.utils.Configuration
import org.apache.commons.io.FileDeleteStrategy
import org.apache.commons.lang3.RandomStringUtils
import org.json4s.jackson.JsonMethods.parse
import play.api.libs.json.{ JsValue, Json }

import scala.concurrent.ExecutionContextExecutor
import scala.io.Source
import scala.util.{ Failure, Success }

trait Service {
  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: Materializer

  val logger: LoggingAdapter
  val stringI = s"""{"messageid":"51ca7075-c6f8-414e-9ea4-7355f5fd8bc6", "responsetopic": "api.reply.interface", "request": {"requestid":"7293cb40-09e5-11e9-b617-ff8943b2cd94", "type":"applyLCOConfigToGroup","model":"GroupModel","action":"CAN_CREATE","user":"507ef3cc-cd2d-46d8-ae6d-7ccf430c1110","groupprops":{ "groupid":"94jef60d-25d7-4c17-8bed-1b0974c44007", "name":"testGroupName84def60d"}, "orgprops":{"orgid":"efe5bdb3-baac-5d8e-6cae57771c13"},"siteprops":{"siteid":"4a6b20f0-e325-11e8-9d8d-6f7fef272615"}, "configprops":{"configid":"7294b5a0-09e5-11e9-b617-ff8943b2cd94","name":"Default Video Node","model":"lco"},"service":"config", "transactionID":"2018-12-27T14:41:08.603Z", "instanceid":"is_api", "timestamp":"2018-12-27T14:41:08.970Z"}}"""

  lazy val servRoutes = {
    logRequestResult("1-microservice")
    path("api" / "create") {
      extractClientIP { ip =>
        headerValueByName("auth") { auth =>
          (post & extractRequestContext) {
            request =>
              extractRequestContext {
                ctx =>
                  {
                    implicit val materializer = ctx.materializer
                    implicit val ec = ctx.executionContext

                    fileUpload("file") {
                      case (fileInfo, fileStream) =>
                        println(" file upload completed ")
                        val localPath = Configuration.filePath
                        val uniqueidString = RandomStringUtils.random(5, true, true)
                        val sink = FileIO.toPath(Paths.get(localPath) resolve uniqueidString + fileInfo.fileName)
                        val writeResult = fileStream.runWith(sink)
                        println("=================")
                        onSuccess(writeResult) { result =>
                          /*result.status match {

              }*/
                          val s1 = Source.fromFile(uniqueidString + fileInfo.fileName).mkString;

                          val writer = new PrintWriter(new File("D:\\temporary\\file_upload_import\\Write.json"))

                          writer.write(s1)
                          writer.close()

                          complete {
                            val json: JsValue = Json.obj("status" -> "success", "status_code" -> 100, "status_details" -> StringContext.treatEscapes(s1))
                            HttpResponse(status = StatusCodes.OK, entity = HttpEntity(ContentType(MediaTypes.`application/json`), json.toString))
                          }
                        }
                    }
                  }
              }

          }
        }
      }
    }
  }

  def test: Unit = {

    val parsedMessage = parse(stringI)
    val cfgId = (parsedMessage \ "request" \ "configprops" \ "configid")
    val orgId = (parsedMessage \ "request" \ "orgprops" \ "orgid")
    val siteId = (parsedMessage \ "request" \ "siteprops" \ "siteid")
    val groupId = (parsedMessage \ "request" \ "groupprops" \ "groupid")
    val lcoTopic = "ms.request.lco" //todo: read topic from constants
    println(" cfgId: " + toJson(cfgId))
    println(" orgId: " + orgId)
    println(" siteId: " + siteId)
    println(" groupId: " + groupId)
    val query = "select * from testing where cfgid = ? and siteid = ?"

    val con = MySQLDBConnection.getConnection()
    val emailContentList: PreparedStatement = con.prepareStatement(query)
    emailContentList.setString(1, "7294b5a0-09e5-11e9-b617-ff8943b2cd94")
    emailContentList.setString(2, "4a6b20f0-e325-11e8-9d8d-6f7fef272615")
    val rs: ResultSet = emailContentList.executeQuery()
    println(" emailContentList " + emailContentList)

    while (rs.next()) {
      val userId = rs.getString("body")
      // println(" userId " + userId)
      println(" userId " + StringContext.treatEscapes(userId))
      val objresp = toJson(StringContext.treatEscapes(userId))
      println(" objresp " + objresp)

    }
  }

}
