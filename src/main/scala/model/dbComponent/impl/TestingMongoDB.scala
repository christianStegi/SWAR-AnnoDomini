package model.dbComponent.impl

import model.dbComponent.DAOInterface

import org.mongodb.scala.*
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}

// import org.mongodb.scala._
// import scala.collection.JavaConverters._

class TestingMongoDB() extends DAOInterface:


  /** Init */
  val db_pw = sys.env.getOrElse("MONGO_INITDB_ROOT_PASSWORD", "1234").toString
  val db_user = sys.env.getOrElse("MONGO_INITDB_ROOT_USERNAME", "admin").toString
  val port = 27017

  val uri: String = s"mongodb://$db_user:$db_pw@localhost:$port/?authSource=admin"
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("TestingDB")
  val firstColl: MongoCollection[Document] = db.getCollection("firstCollection")


    override
    def create: Unit =
        val firstDoc: Document = Document("_id" -> "firstDoc", "game" -> "")
        // observerInsertion(firstColl.insertOne(firstDoc))

        
    override
    def read: String =
        ???


    override
    def update(input: String): Unit =
        ???


    override
    def delete: Unit =
        ???

