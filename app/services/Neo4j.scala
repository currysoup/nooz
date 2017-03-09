
package services

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.Values.parameters
import org.neo4j.driver.v1.{AuthTokens, GraphDatabase, Record, Session, StatementResult, Transaction}

import model.Topic
import utils._

object `package` {
  type Neo4jDriver = org.neo4j.driver.v1.Driver
}

object Neo4j {
  case class ExpectationsViolatedException(message: String) extends Exception(message)

  def newDriver(config: Config): Neo4jDriver = {
    GraphDatabase.driver(config.neo4j.uri, AuthTokens.basic(config.neo4j.user, config.neo4j.password))
  }

  def setup(driver: Driver): Either[Failure, Unit] = {
    val neo = Neo4jSession(driver)
    neo.setup().map(_ => ())
  }
}

case class Neo4jSession(driver: Neo4jDriver) {
  val session = driver.session()

  // Transactions make sure we properly open and close the neo4j transaction and map from the java exception model to a functional "either" model
  def transaction[T](f: Transaction => T): Either[Failure, T] = {
    val tx = session.beginTransaction()
    try {
      val result = f(tx)
      tx.success()
      Right(result)
    } catch {
      case ex: Neo4j.ExpectationsViolatedException =>
        tx.failure()
        Left(ClientFailure(ex.getMessage))
      case ex: Exception =>
        tx.failure()
        Left(UnknownFailure(ex.getMessage))
    } finally {
      tx.close()
    }
  }

  def setup() = transaction { tx =>
    tx.run("CREATE CONSTRAINT ON (topic :Topic) ASSERT topic.id IS UNIQUE")
    //tx.run("CREATE INDEX ON :File(fullName)")
  }

  def insertTopic(id: String, name: String) = transaction { tx =>
    tx.run("CREATE (:Topic {id: {id}, name: {name}})", parameters("id", id, "name", name))
  }

  def getTopic(id: String) = transaction { tx =>
    val summary = tx.run("MATCH (topic: Topic {id: {id}}) RETURN topic", parameters("id", id))

    val list = summary.list().asScala

    expect(list, "topic", s"No topic with id '$id'")

    list.map(r => Topic.fromNeo4jValue(r.get("topic"))).head
  }

  def getTopics() = transaction { tx =>
    val summary = tx.run("MATCH (topic: Topic) RETURN topic")

    summary.list().asScala.map(r => Topic.fromNeo4jValue(r.get("topic")))
  }

  // Expect a record to be retuned as part of a statement result
  private def expect(list: Seq[Record], expectedKey: String, errorMessage: String) = {
    // This throws to allow the outer transaction handler to correctly roll back!
    if (!list.exists(x => x.containsKey(expectedKey))) {
      throw Neo4j.ExpectationsViolatedException(errorMessage)
    }
  }
}


