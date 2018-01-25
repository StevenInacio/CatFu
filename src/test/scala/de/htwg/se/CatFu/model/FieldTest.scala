package de.htwg.se.CatFu.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FieldTest extends WordSpec with Matchers {
  val board = new Field
  val player = new Mage("Your Name", Console.GREEN)
  val x = 4
  val y = 2


  /*
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
  }*/



  "Print an empty Field" should {
    "have no Chars" in {
      val board = new Field
      board.clearField()
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

    "with Obstacle" should {
      val rock = Obstacle()
      board.setPosition(rock, 4, 3)
      "have a X at 4,3" in {
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
            "  4 |   |   |   | " + Console.YELLOW + "X" + Console.RESET + " |   |   |   |   |   |   |   |   |\n" +
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


  "A Player" when {
    "get Color" should {
      "GREEN" in {
        board.getColor(player) should be(Console.GREEN)
      }
    }

    "get Display" should {
      "F" in {
        board.setPosition(player, x, y)
        board.getDisplay(x, y) should be('F')
      }
    }
  }


  "A Move" when {
    "valid" should {
      "isvalid method return 1" in {
        board.isValid(player, "wdd", 0) should be(0)
      }
    }
  }
  "IO" when {
    "valid input" should {
      "is in Field with Tuple return true" in {
        board.setPosition(player, 4, 4)
        board.matchTestIsInField((3, 4), 'w') should be(true)
      }
    }
    "valid input" should {
      "is in Field return with Player true" in {
        board.setPosition(player, 4, 4)
        board.matchTestIsInField(player, 'w') should be(true)
      }
    }
    "valid input" should {
      "valid input space return true" in {
        board.setPosition(player, 4, 4)
        board.matchTestValidInputSpace((3, 4), 'w') should be(true)
      }
    }
    "valid" should {
      "valid input space  match return 1" in {
        board.setPosition(player, 4, 4)
        board.matchTestValidInputSpace(player, 'w') should be(true)
      }
    }
    "Dijkstra" should {
      "bla" in {
        val field = new Field
        //field.clearField()
        field.setPosition(Obstacle(), 2, 4)
        field.setPosition(Obstacle(), 3, 3)
        field.setPosition(Obstacle(), 3, 6)
        field.setPosition(Obstacle(), 1, 5)
        val p = new Mage("Peter", Console.GREEN)
        field.setPosition(p, 3, 4)
        // p.posx = 3
        // p.posy = 4
        val list = field.dijkstra(p, p.getSpeed)
        println(field.highlight(list)) should be(
          "      0   1   2   3   4   5   6   7   8   9  10  11 \n" +
          "    -------------------------------------------------\n" +
          "  0 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  1 |   |   |   |   |   | "+Console.YELLOW + "X" + Console.RESET+" |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  2 |   |   |   |   | "+Console.YELLOW + "X" + Console.RESET+" |   |   |   |   |   |   |   |\n" +
          "    -------------------------------------------------\n" +
          "  3 |   |   |   | "+Console.YELLOW + "X" + Console.RESET+" |   |   | "+Console.YELLOW + "X" + Console.RESET+" |   |   |   |   |   |\n" +
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
    }

  }
}