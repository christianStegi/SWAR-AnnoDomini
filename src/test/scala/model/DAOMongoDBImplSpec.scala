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
        "A MongoDB instance" when {
            "started" should {

                "be reachable" in {
                    val mongoDbClient = mongoDBImpl.initializeDB()
                }

                "be able to return it's databases" in {}

                "be able to select a database" in {}

                "be able to insert a value" in {}

                "be able to get a demanded value" in {}

            }

        }
    
    
    doFirstTests
