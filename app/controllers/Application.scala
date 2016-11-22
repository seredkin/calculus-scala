package controllers

import java.util.Base64

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import service.CalcService

/**
  * Created by Anton on 22.11.2016.
  */
object Application extends Controller {

  case class CalcResult(error: Boolean, message: String, result: String)

  implicit val calcWrites = Json.writes[CalcResult]
  implicit val calcReads = Json.reads[CalcResult]


  def calculus(query: Option[String]) = Action{ request =>
    try {
      val result: CalcResult = calcResult(query.get)
      Ok(Json.toJson(result))
    } catch {
      case e:Exception => BadRequest(Json.toJson(CalcResult(error = true, e.getLocalizedMessage, null)))
    }
  }

  def calcResult(query :String):CalcResult = {

    val ex = new String(Base64.getDecoder.decode(query))

    val calcService = new CalcService

    var result: Double = calcService.calcExpression(ex)
    CalcResult(false, null  , result.toString());
  }

}
