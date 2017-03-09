package commands

import play.api.libs.json._

import services._
import utils.Failure

case class CreateTopicData(name: String)

object CreateTopicData {
  implicit val createTopicDataFormat = Json.format[CreateTopicData]
}

case class CreateTopic(data: CreateTopicData, neo4jDriver: Neo4jDriver) extends CommandCanFail[String] {
  def process(): Either[Failure, String] = {
    val id = data.name.toLowerCase.replace(" ", "-")
    Neo4jSession(neo4jDriver).insertTopic(id, data.name).map(_ => data.name)
  }
}

