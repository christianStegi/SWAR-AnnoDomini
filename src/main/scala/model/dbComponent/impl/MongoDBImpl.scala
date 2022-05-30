package model.dbComponent.impl

import model.dbComponent.DAOInterface

// import org.bson._
import org.mongodb.scala._
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}

// import org.mongodb.scala._
// import scala.collection.JavaConverters._

class DAOMongoDBImpl() extends DAOInterface:


  /** Init */
  val db_pw = sys.env.getOrElse("MONGO_INITDB_ROOT_PASSWORD", "1234").toString
  val db_user = sys.env.getOrElse("MONGO_INITDB_ROOT_USERNAME", "admin").toString
  val port = 27017

  val uri: String = s"mongodb://$db_user:$db_pw@localhost:$port/?authSource=admin"
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("AnnoDomini")
  val gameCollection: MongoCollection[Document] = db.getCollection("game")


    override
    def create: Unit =
        val gameDocument: Document = Document("_id" -> "gameDocument", "game" -> "")
        // observerInsertion(gameCollection.insertOne(gameDocument))

    override
    def read: String =
        ???


    override
    def update(input: String): Unit =
        ???


    override
    def delete: Unit =
        ???

