package model.dbComponent.impl

import model.dbComponent.DAOInterface

// import org.bson._
import org.mongodb.scala._
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import scala.util.Try
import scala.util.Failure
import scala.util.Success


// import org.mongodb.scala._
// import scala.collection.JavaConverters._

class DAOMongoDBImpl() extends DAOInterface:

    /* INITIALIZATION */
    val db_pw = sys.env.getOrElse("MONGO_INITDB_ROOT_PASSWORD", "1234").toString
    val db_user = sys.env.getOrElse("MONGO_INITDB_ROOT_USERNAME", "admin").toString
    val port = 27017
    // val uri: String = s"mongodb://$db_user:$db_pw@localhost:$port/?authSource=admin"
    // val uri: String = s"mongodb://localhost:$port"
    val mongoURI: String = s"mongodb://localhost:$port/?authSource=admin"

    /* CREATE DATABASE OBJECTS */
    val client = MongoClient(mongoURI)

    val db_anno: MongoDatabase = client.getDatabase("AnnoDomini")
    /* getDatabase gets collection if already existing, else creates it. */
    val coll_game: MongoCollection[Document] = db_anno.getCollection("game") 
    
    // val doc_table: Document = Document("_id" -> "table", "table" -> "nothingSavedYet")
    // insertOneObserver(coll_game.insertOne(doc_table))

  
    override def create: Unit =
        val firstDoc: Document = Document("_id" -> "gameDoc", "game" -> "nothingInYet")
        insertOneObserver(coll_game.insertOne(firstDoc))
        // observerInsertion(firstColl.insertOne(firstDoc))
        // doFirstCreationStuff(client)
        // val client = createGameCollection(client_init)
        
        // val client_init = initializeDB()
        // val client = createTableCollection(client_init)


    /* NOT USED AT THE MOMENT, MAYBE TO BE DELETED LATER (code ist jetzt am anfang der Klasse*/
    def initializeDB(): MongoClient = 
        /* INITIALIZATION */
        val db_pw = sys.env.getOrElse("MONGO_INITDB_ROOT_PASSWORD", "1234").toString
        val db_user = sys.env.getOrElse("MONGO_INITDB_ROOT_USERNAME", "admin").toString
        val port = 27017
        // val uri: String = s"mongodb://$db_user:$db_pw@localhost:$port/?authSource=admin"
        // val uri: String = s"mongodb://localhost:$port"
        val mongoURI: String = s"mongodb://localhost:$port/?authSource=admin"

        /* CREATE DATABASE OBJECTS */
        MongoClient(mongoURI)
        

    override def read: String =
        val docFromDB: Document = Await.result(coll_game.find(equal("_id", "gameDoc")).first().head(), Duration.Inf)
        val table = docFromDB("table").asString().getValue.toString
        println("table in read:")
        println(table)
        table.toString
        // val parsedAsJson: JsValue = Json.parse(readFromDB)


    override def update(input: String): Unit =
        println("DAOMongoDBImpl.update was called")

        val tableAsJson: JsValue = Json.parse(input)
        //pretty printen?
        val tableContent = (tableAsJson \ "table").get.toString
        println("tableContent:")
        println(tableContent)
        
        Try({
        // observerUpdate(coll_game.updateOne(equal("_id","gameDoc"), set("_id","gridDocument")))
        updateOneObserver(coll_game.updateOne(equal("_id", "gameDoc"), set("table", tableContent)))
        }) match {
        case Success(_) =>
        case Failure(exception) => println(exception);
        }




    override def delete: Unit =
        Await.result(deleteGameColl(), Duration.Inf)
        

    def deleteGameColl(): Future[String] =
        coll_game.deleteMany(equal("_id", "gameDoc")).subscribe(
            (dr: DeleteResult) => println(s"Deleted gridDocument"),
            (e: Throwable) => println(s"Error while trying to delete gridDocument: $e")
        )
        
        Future { 
            "Finished deleting!"
        }



    def show(tableAsString: String): Unit =
        println("tableAsString:")
        println(tableAsString)


    def init(): Unit =
        println("in methode init")
        initializeDB()


    /* NOT USED AT THE MOMENT, MAYBE TO BE DELETED LATER (code ist jetzt am anfang der Klasse*/
    /* add collection game with document table */
    def createTableCollection(client: MongoClient) : MongoClient = 
        /* getDatabase gets Database if already existing, else creates it. */
        val db_anno: MongoDatabase = client.getDatabase("AnnoDomini")
        /* getDatabase gets collection if already existing, else creates it. */
        val coll_game: MongoCollection[Document] = db_anno.getCollection("game") 
        val doc_table: Document = Document("_id" -> "table", "table" -> "nothingSavedYet")
        insertOneObserver(coll_game.insertOne(doc_table))
        client


    /* NOT USED AT THE MOMENT, MAYBE TO BE DELETED LATER (code ist jetzt am anfang der Klasse*/
    /* add collection game with documents playerList1, player2, table.  */        
    def createGameCollection(client: MongoClient) : MongoClient = 
        // getDatabase gets Database if already existing, else creates it.
        val db_anno: MongoDatabase = client.getDatabase("AnnoDomini")
        // getDatabase gets collection if already existing, else creates it.
        val coll_game: MongoCollection[Document] = db_anno.getCollection("game") 
        val doc_player1: Document = Document("_id" -> "player1", "name" -> "player1")
        val doc_player2: Document = Document("_id" -> "player2", "name" -> "player2")
        val doc_table: Document = Document("_id" -> "table", "name" -> "table")

        insertOneObserver(coll_game.insertOne(doc_player1))
        insertOneObserver(coll_game.insertOne(doc_player2))
        insertOneObserver(coll_game.insertOne(doc_table))
 
        client


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