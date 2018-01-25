package de.htwg.se.CatFu.model

import de.htwg.se.CatFu.logic._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FieldTest extends WordSpec with Matchers {
  "A Field" when {
    "new" should {
      val player = new Mage("Your Name", Console.WHITE)
      "have a name" in {
        player.name should be("Your Name")
      }
      "have a nice String representation" in {
        player.toString should be("Your Name, the level 1 Fleazard")
      }
      "be level 1" in {
        player.lvl should be(1)
      }
    }
  }
  /*
   "A Move" when {
     "valid" should {
       val field: Field = new Field
       "return true in isvalid method" in {

       }
     }
   }*/
  "A Way" when {
    "found" should {
      "be not empty" in {
        val board: Field = new Field()
        board.clearField()
        val player = PlayerManagement.createWarrior("Purrince", 1)
        val enemy = PlayerManagement.createEnemy("Lucifurr", 1)
        board.setPosition(player, 3, 2)
        board.setPosition(enemy, 0, 1)
        board.findWay(player, enemy) should be("www")
      }
    }
  }

}
