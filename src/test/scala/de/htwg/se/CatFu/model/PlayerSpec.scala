package de.htwg.se.CatFu.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {

  val p = new Mage("Name", Console.WHITE)
  val player = new Warrior("Your Name", Console.WHITE)

  "A Player" when {
    "new" should {

      "have a name" in {
        player.name should be("Your Name")
      }
      "have a nice String representation" in {
        player.toString should be("Your Name, the level 1 Pawrior")
      }



    }

    "Equals" should{
      "Boolean " in{
        p.equals(p) should be (true)
      }
    }
/*
    "HashCode" should{
      "Int " in{
        p.hashCode() should be (1888442711)
      }
    }
    */

    "Damage" should{
      "Int " in{
       p.damage(2) should be (2)
      }
    }

    "Heal" should{
      "Int " in{
        p.heal(2) should be (2)
      }
    }

    "SetLvl" should{
      "Int " in{
        p.setLvl(42)
        p.lvl should be (42)
      }
    }
/*
    "attack" should{
      "Int " in{
        p.attack(player)
         should be (42)
      }
    }
*/
    "increaseXP" should{
      "Int " in{
        p.increaseXP(42)
        p.currentXP should be (42)
      }
    }

    "HitRate" should{
      "Int " in{
        p.hitrate(player) should be (99)
      }
    }

    "xpToLvlUp" should{
      "Int " in{
        p.xpToLvlUp should be (222)
      }
    }

    "fullDescription" should{
      "String " in{
        p.fullDescription should be (
          "Name, the level 42 Fleazard\n" +
            "XP: 42/222\nHP: 176/176\n" +
            "Attack: 126\nDefense: 84\n" +
            "Speed: 4\nRange: 5\n")
      }
    }





  }
}