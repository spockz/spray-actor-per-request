package nl.spockz.spray.perrequest

import akka.actor._
import akka.actor.SupervisorStrategy.Stop
import nl.spockz.spray.perrequest.Domain.{RestResponse, RestRequest}
import spray.http.StatusCodes._
import spray.routing.RequestContext
import akka.actor.OneForOneStrategy
import spray.httpx.Json4sSupport
import scala.concurrent.duration._
import org.json4s.DefaultFormats
import spray.http.StatusCode

trait PerRequest extends Actor with Json4sSupport {

  import context._

  val json4sFormats = DefaultFormats
  def timeout : FiniteDuration

  def r: RequestContext
  def target: ActorRef
  def message: RestRequest

  setReceiveTimeout(timeout)
  target ! message

  def receive = {
    case res : RestResponse => complete(res.status, res.entity)
    case ReceiveTimeout     => complete(GatewayTimeout, "Request timed-out.")
  }

  def complete[T <: AnyRef](status: StatusCode, obj: T) = {
    r.complete(status, obj)
    stop(self)
  }

  override val supervisorStrategy =
    OneForOneStrategy() {
      case e => {
        complete(InternalServerError, e.getMessage)
        Stop
      }
    }
}

object PerRequest {
  case class WithActorRef(r: RequestContext, target: ActorRef, message: RestRequest, timeout: FiniteDuration) extends PerRequest

  case class WithProps(r: RequestContext, props: Props, message: RestRequest, timeout: FiniteDuration) extends PerRequest {
    lazy val target = context.actorOf(props)
  }
}

trait PerRequestCreator {
  this: Actor =>

  import PerRequest._

  def perRequest(timeout: FiniteDuration)(r: RequestContext, target: ActorRef, message: RestRequest) =
    context.actorOf(Props(new WithActorRef(r, target, message, timeout)))

  def perRequest(timeout: FiniteDuration)(r: RequestContext, props: Props, message: RestRequest) =
    context.actorOf(Props(new WithProps(r, props, message, timeout)))
}