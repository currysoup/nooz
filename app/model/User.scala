package model

import play.api.libs.json._
import org.neo4j.driver.v1.Value

case class User(name: String)

object User {
  implicit val userFormat = Json.format[User]

  def fromNeo4jValue(value: Value): User = {
    User(value.get("name").asString())
  }
}
