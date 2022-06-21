package model.dbComponent.impl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.fileIOComponent.Impl.FileIOAsJSON

class MongoDBImplSpec extends AnyWordSpec:

    val fileIO = FileIOAsJSON()

    def doFirstTests: Unit = 
        "A MongoDB instance" when {
            "started" should {

                "be reachable" in {}

                "be able to return it's databases" in {}

                "be able to select a database" in {}

                "be able to insert a value" in {}

                "be able to get a demanded value" in {}

            }

        }
    
    
    doFirstTests
