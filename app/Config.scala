package services

import play.api.Configuration

case class Config(config: Configuration) {
  object neo4j {
    val uri: String = config.getString("neo4j.uri").get
    val user: String = config.getString("neo4j.user").get
    val password: String = config.getString("neo4j.password").get
  }

  object user {
    val name: String = config.getString("user.name").get
    val avatar: String = config.getString("user.avatar").get
  }
}
