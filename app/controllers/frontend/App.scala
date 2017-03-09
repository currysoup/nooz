package controllers.frontend

import controllers.AssetsFinder
import play.api.mvc.{AbstractController, ControllerComponents}

class App (components: ControllerComponents, finder: AssetsFinder) extends AbstractController(components) {
  def index = Action { implicit req =>
    Ok("hello world")
  }
}
