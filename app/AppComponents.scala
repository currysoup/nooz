import java.security.Security
import java.net.InetAddress

import controllers.{AssetsComponents, Assets}
import play.api.ApplicationLoader.Context
import play.api.BuiltInComponentsFromContext
import play.api.mvc.DefaultControllerComponents
import router.Routes

import controllers.api.TopicController
import controllers.frontend.App
import services._

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context) with AssetsComponents {
  val config = Config(configuration)

  // Injectable services
  val neo4jDriver = Neo4j.newDriver(config)
  Neo4j.setup(neo4jDriver).left.foreach(failure => throw new Exception(failure.msg))

  // Controllers
  val builder = DefaultControllerComponents(defaultActionBuilder, playBodyParsers, messagesApi, langs, fileMimeTypes, executionContext)

  val appController = new App(builder, assetFinder)
  val topicController = new TopicController(builder, neo4jDriver)

  // Router
  val router = new Routes(httpErrorHandler, appController, topicController, assets)
}
