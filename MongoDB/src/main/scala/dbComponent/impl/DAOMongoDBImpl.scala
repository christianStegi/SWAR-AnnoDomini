package dbComponent.impl

import org.mongodb.scala._

import dbComponent.DAOInterface


import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}
import play.api.libs.json.JsValue
import play.api.libs.json.Json

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try
import scala.util.Failure
import scala.util.Success


class DAOMongoDBImpl() extends DAOInterface:

    /* INITIALIZATION */
    val db_pw = sys.env.getOrElse("MONGO_INITDB_ROOT_PASSWORD", "1234").toString
    val db_user = sys.env.getOrElse("MONGO_INITDB_ROOT_USERNAME", "admin").toString
    val port = 27017
    val mongoURI: String = s"mongodb://localhost:$port/?authSource=admin"

    /* CREATE DATABASE OBJECTS */
    val client = MongoClient(mongoURI)
    val db_anno: MongoDatabase = client.getDatabase("AnnoDomini")
    /* getDatabase gets collection if already existing, else creates it. */
    val coll_game: MongoCollection[Document] = db_anno.getCollection("game") 

  
    override def create: Unit =
        val firstDoc: Document = Document("_id" -> "gameDoc", "table" -> "nothingInYet")
        insertOneObserver(coll_game.insertOne(firstDoc))


    override def read: String =
        val docFromDB: Document = Await.result(coll_game.find(equal("_id", "gameDoc")).first().head(), Duration.Inf)
        val table = docFromDB("table").asString().getValue.toString
        table.toString


    override def update(input: String): Unit =
        println("DAOMongoDBImpl.update was called")

        val tableAsJson: JsValue = Json.parse(input)

        val tableContent = (tableAsJson \ "table").get.toString
        println("tableContent:")
        println(tableContent)
        
        Try({
            updateOneObserver(coll_game.updateOne(equal("_id", "gameDoc"), set("table", tableContent)))
        }) match {
            case Success(_) =>
            case Failure(exception) => println(exception);
        }


    override def delete: Unit =
        Await.result(deleteSomeDocuments(), Duration.Inf)
        

    def deleteSomeDocuments(): Future[String] =
        coll_game.deleteMany(equal("_id", "gameDoc")).subscribe(
            (dr: DeleteResult) => println(s"Deleted document gameDoc"),
            (e: Throwable) => println(s"Error while trying to delete gridDocument: $e")
        )
        // coll_game.deleteMany(equal("_id", "table")).subscribe(
        //     (dr: DeleteResult) => println(s"Deleted document table"),
        //     (e: Throwable) => println(s"Error while trying to delete table: $e")
        // )
        
        Future { 
            "Finished deleting!"
        }


    /* method just for testing if service is available and responding */
    def show(tableAsString: String): Unit =
        println("tableAsString:")
        println(tableAsString)


    /*  the FOLLOWING OBSERVER-METHODS are needed for being able to insert values into a mongodb. If an document gets inserted, it 
        also has to be registered (=subscribed), else insertion is not completed and it will not show up in the database */

    /* Observer Methods for Handling Insertions, Updates and so on */
    private def insertOneObserver(insertObservable: SingleObservable[InsertOneResult]): Unit = {
        insertObservable.subscribe(new Observer[InsertOneResult] {
            override def onNext(result: InsertOneResult): Unit = println(s"inserted: $result")

            override def onError(e: Throwable): Unit = println(s"insert onError: $e")

            override def onComplete(): Unit = println("completed insert")
        })
    }

    private def updateOneObserver(insertObservable: SingleObservable[UpdateResult]): Unit = {
        insertObservable.subscribe(new Observer[UpdateResult] {
            override def onNext(result: UpdateResult): Unit = println(s"updated: $result")

            override def onError(e: Throwable): Unit = println(s"update onError: $e")

            override def onComplete(): Unit = println("completed update")
        })
    }