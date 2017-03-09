package model

import play.api.libs.json._
import org.neo4j.driver.v1.Value

case class Topic(id: String, name: String)

object Topic {
  implicit val topicFormat = Json.format[Topic]

  def fromNeo4jValue(value: Value): Topic = {
    Topic(
      id = value.get("id").asString(),
      name = value.get("name").asString())
  }
}
