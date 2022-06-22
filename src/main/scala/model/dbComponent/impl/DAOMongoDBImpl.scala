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


    override def create: Unit =
        val firstDoc: Document = Document("_id" -> "firstDoc", "game" -> "")
        //  observerInsertion(firstColl.insertOne(firstDoc))
        val client_init = initializeDB()
        // doFirstCreationStuff(client)
        // val client = createGameCollection(client_init)
        val client = createTableCollection(client_init)
        

    override def read: String =
        ???


    override def update(input: String): Unit =
        ???


    override def delete: Unit =
        ???


    def show(tableAsString: String): Unit =
        println("tableAsString:")
        println(tableAsString)

    def initializeDB(): MongoClient = 
        /* INITIALIZATION */
        val db_pw = sys.env.getOrElse("MONGO_INITDB_ROOT_PASSWORD", "1234").toString
        val db_user = sys.env.getOrElse("MONGO_INITDB_ROOT_USERNAME", "admin").toString
        val port = 27017
        // val uri: String = s"mongodb://$db_user:$db_pw@localhost:$port/?authSource=admin"
        // val uri: String = s"mongodb://localhost:$port"
        val uri: String = s"mongodb://localhost:$port/?authSource=admin"

        /* CREATE DATABASE OBJECTS */
        MongoClient(uri)




    /* add collection game with document tabe */
    def createTableCollection(client: MongoClient) : MongoClient = 
        /* getDatabase gets Database if already existing, else creates it. */
        val db_anno: MongoDatabase = client.getDatabase("AnnoDomini")
        /* getDatabase gets collection if already existing, else creates it. */
        val coll_game: MongoCollection[Document] = db_anno.getCollection("game") 
        val doc_table: Document = Document("_id" -> "table", "name" -> "table")
        insertOneObserver(coll_game.insertOne(doc_table))
        client


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