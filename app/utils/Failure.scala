package utils

import scala.util.Try

sealed trait Failure {
  def msg: String
  override def toString(): String = msg
}

case class ClientFailure(msg: String) extends Failure
case class UnknownFailure(msg: String) extends Failure
