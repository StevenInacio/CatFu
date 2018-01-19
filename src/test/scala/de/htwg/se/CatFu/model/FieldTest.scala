package de.htwg.se.CatFu.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FieldTest extends WordSpec with Matchers {
  "A Field" when { "new" should {
    val player = new Mage("Your Name", Console.WHITE)
    "have a name"  in {
      player.name should be("Your Name")
    }
    "have a nice String representation" in {
      player.toString should be("Your Name, the level 1 Fleazard")
    }
    "be level 1" in {
      player.lvl should be (1)
    }
  }}
  "A Move" when {
    "valid" should {
      val field: Field = new Field
      "return true in isvalid method" in {

      }
    }
  }





}
