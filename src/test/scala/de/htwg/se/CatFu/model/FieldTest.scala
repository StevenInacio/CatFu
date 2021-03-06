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
  "Print an empty Field" should {
    val board = new Field
    board.clearField()
    "have no Chars" in {
      board.toString should be(
        "      0   1   2   3   4   5   6   7   8   9  10  11 \n" +
          "    -------------------------------------------------\n" +
          "  0 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  1 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  2 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  3 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  4 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  5 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  6 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  7 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  8 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  9 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          " 10 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          " 11 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------")
    }
    /*
      "have a nice String representation" in {
        player.toString should be("Your Name, the level 1 Fleazard")
      }
      "be level 1" in {
        player.lvl should be(1)
      }
      */
    "with Obstacle" should {
      val rock = Obstacle()
      val board = new Field
      board.clearField()
      board.setPosition(rock, 4, 2)
      "have a X at 4,2" in {
        board.toString should be(
          "      0   1   2   3   4   5   6   7   8   9  10  11 \n" +
            "    -------------------------------------------------\n" +
            "  0 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  1 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  2 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  3 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  4 |   |   | " + Console.YELLOW + "X" + Console.RESET + " |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  5 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  6 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  7 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  8 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            "  9 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            " 10 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------\n" +
            " 11 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            "    -------------------------------------------------")
      }
    }
  }
}