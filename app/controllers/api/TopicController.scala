package controllers.api

import akka.actor.ActorRef
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}

import commands._
import controllers._
import services._
import utils.Failure

class TopicController(components: ControllerComponents, neo4jDriver: Neo4jDriver)
  extends AbstractController(components) {

  def newTopic() = Action { req =>
    req.body.asJson.map { json =>
      CreateTopic(json.as[CreateTopicData], neo4jDriver).process() match {
        case Right(id) => Created(id)
        case Left(err) => HttpCodeMapper.failureToHttpCode(err)
      }
    }.getOrElse(BadRequest)
  }

  def listTopics() = Action {
    Neo4jSession(neo4jDriver).getTopics match {
      case Right(topics) => Ok(Json.toJson(topics))
      case Left(err) => HttpCodeMapper.failureToHttpCode(err)
    }
  }

  def getTopic(id: String) = Action {
    Neo4jSession(neo4jDriver).getTopic(id) match {
      case Right(topic) => Ok(Json.toJson(topic))
      case Left(err) => HttpCodeMapper.failureToHttpCode(err)
    }
  }
}
