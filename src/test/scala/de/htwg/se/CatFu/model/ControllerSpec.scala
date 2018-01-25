package de.htwg.se.CatFu.model

import de.htwg.se.CatFu.logic._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  var controller = Controller
  var state: State.Value = State.Init
  val obs1 = Obstacle()
  var board = new Field()
  var playerList: List[Player] = List()
  var startingTeam: List[Player] = List()
  var enemyList: List[Player] = List()
  val player = new Mage("Your Name", Console.GREEN)
  val player2 = new Warrior("Name", Console.GREEN)
  val x = 5
  val y = 5
  board.setPosition(player, x, y)

  // No Tests needed:
  // userinput

  "Controller " when {


    "move" should {
      "move returns remainingMoves" in {
        controller.move(player,0) should be(0)
      }
    }

    "input" should {
      "true" in {
        controller.attackMenu(player) should be(true)
      }
    }

      "Option" should {
        "Player" in {
       // controller.playerChoose() should be()
      }
    }
  } //end class
}