package model.dbComponent.Impl
// package model.fakepackage

import model.dbComponent.impl.DAOMongoDBImpl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.fileIOComponent.Impl.FileIOAsJSON

class DAOMongoDBImplSpec extends AnyWordSpec:

    val fileIO = FileIOAsJSON()
    val mongoDBImpl = DAOMongoDBImpl()

    def doFirstTests: Unit = 
        "Our MongoDB instance" when {
            "started" should {

                val mongoClient = mongoDBImpl.initializeDB()

                "be reachable" in {
                    val oneDefaultDB: String = "config"
                    val db = mongoClient.getDatabase(oneDefaultDB)
                    val dataTypeOfResult = db.getClass.toString
                    dataTypeOfResult should be ("class org.mongodb.scala.MongoDatabase")
                }

                "have a database called \"AnnoDomini\"" in {
                    val dbToSearch: String = "AnnoDomini"
                    val dbList = mongoClient.listDatabaseNames()
                    var isTestSuccessful: Boolean = false
                    
                    for (elem <- dbList) 
                        if(elem.toString().contains(dbToSearch)) 
                        then isTestSuccessful = true

                    Thread.sleep(500) //
                    if (!isTestSuccessful) {
                        fail()
                    }

                }
                
                "be able to insert a value" in {}

                "be able to get a demanded value" in {}

                "DO JUST SOME FUNCTION CALLING FOR DEVELOPMENT:" in {
                    mongoDBImpl.create

                }

            }

        }
    
    
    doFirstTests
