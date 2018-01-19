package de.htwg.se.CatFu.logic

import java.io.{File, FileWriter, BufferedWriter}
import de.htwg.se.CatFu.model.{Mage, Player, Warrior}
import play.api.libs.json._

import scala.reflect.io.File

object PlayerManagement {

  def createEnemy(name: String, level: Int) : Player = {
    val random = new scala.util.Random()
    if (random.nextBoolean()) {
      val mage : Mage = new Mage(name, Console.RED)
      mage.setLvl(level)
      mage
    } else {
      val warrior : Warrior = new Warrior(name, Console.RED)
      warrior.setLvl(level)
      warrior
    }
  }

  def createWarrior(name: String, level: Int) : Warrior = {
    val x = new Warrior(name, Console.GREEN)
    x.setLvl(level)
    x
  }

  def createMage(name: String, level: Int) : Mage = {
    val x = new Mage(name, Console.GREEN)
    x.setLvl(level)
    x
  }

  def saveCharacter(char: Player) : Boolean = {
    val name = char.name
    var role: String = ""
    if (char.isInstanceOf[Mage]) {
      role = "Mage"
    } else {
      role = "Warrior"
    }
    val lvl = char.lvl
    val jsonString = Json.obj(
      name -> Json.arr(
        Json.obj(
          "role" -> role,
          "level" -> lvl
        )
      )
    )
    println(jsonString)
    val file = new File(name + ".json")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(jsonString.toString())
    true
  }

  def loadCharacter(name: String) : Player = {
    new Mage(name, Console.GREEN)
  }
}
