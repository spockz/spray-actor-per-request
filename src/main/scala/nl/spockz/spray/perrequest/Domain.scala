package nl.spockz.spray.perrequest

import spray.http.StatusCode
import spray.http.StatusCodes._
/**
 * Created by alessandro on 08/02/15.
 */
object Domain {
  trait RestRequest
  trait RestResponse {
    def status: StatusCode
    def entity: AnyRef
  }

  case class Success(status: StatusCode = OK, entity : AnyRef) extends RestResponse
  case class Error(status: StatusCode = InternalServerError, message: String) extends RestResponse {
    val entity = message
  }
  case class BadRequest(status: StatusCode = spray.http.StatusCodes.BadRequest, message: String) extends RestResponse {
    val entity = message
  }
  case class Timeout(status: StatusCode = BadGateway, message: String) extends RestResponse {
    val entity = message
  }
}
