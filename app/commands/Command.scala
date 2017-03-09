package commands

import utils.Failure

trait Command[T] {
  def process(): T
}

trait CommandCanFail[T] extends Command[Either[Failure, T]]
