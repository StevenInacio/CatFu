package de.htwg.se.CatFu.logic

import de.htwg.se.CatFu.model.{ Player, Mage, Warrior }

object PlayerManagement {

  def createEnemy(name: String, level: Int): Player = {
    val random = new scala.util.Random()
    if (random.nextBoolean()) {
      val mage: Mage = new Mage(name, Console.RED)
      mage.setLvl(level)
      mage
    } else {
      val warrior: Warrior = new Warrior(name, Console.RED)
      warrior.setLvl(level)
      warrior
    }
  }

  def createWarrior(name: String, level: Int): Warrior = {
    val x = new Warrior(name, Console.GREEN)
    x.setLvl(level)
    x
  }
}
