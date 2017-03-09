package controllers

import play.api.mvc.{Results, Result}

import utils._

object HttpCodeMapper {
  def failureToHttpCode(err: Failure) = {
    err match {
      case e: ClientFailure => Results.BadRequest(e.msg)
      case e: Failure => Results.InternalServerError(e.msg)
    }
  }
}
