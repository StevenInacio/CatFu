package de.htwg.se.CatFu.logic

import de.htwg.se.CatFu.model.{ Mage, Player, Warrior }
import play.api.libs.json._

import scala.io.Source

object PlayerManagement {

  val names = List("Lucifurr", "Catmando", "Findus", "Jenifurr", "Flease Witherspoon", "Whispurr", "Cat Damon",
    "David Meowie", "Fuzz Aldrin", "Jude Paw", "Meowses", "Oedipuss", "Catzilla", "Fuzzinator", "iCat", "Kitkat",
    "Miraclaw", "Octopuss", "Wigglebutt", "Flufferton", "Giggles", "Nacho", "Purrgatory", "Schrödinger", "Tempurra",
    "She-ra", "Puma Thurman", "Garfield", "He-Man", "Mr. Bigglesworth", "Shakespurr", "Rwarbo", "Nyancat", "Grumpy Cat")

  def randomTeam(): List[Player] = {
    val random = scala.util.Random
    var list: List[Player] = List()
    for (_ <- 0 until 4) { // scalastyle:ignore
      if (random.nextBoolean()) {
        list ++= List(createMage(names(random.nextInt(names.length)), 1))
      } else {
        list ++= List(createWarrior(names(random.nextInt(names.length)), 1))
      }
    }
    list
  }

  def enemyTeam(list: List[Player]): List[Player] = {
    var level = 0
    for (x <- list) {
      level += x.lvl
    }
    var enemy: List[Player] = List()
    val random = scala.util.Random
    val enemyAmount = random.nextInt(2) + 3
    level = level / enemyAmount
    for (_ <- 0 until enemyAmount) { // scalastyle:ignore
      enemy ++= List(createEnemy(names(random.nextInt(names.length)), level))
    }
    enemy
  }

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

  def createMage(name: String, level: Int): Mage = {
    val x = new Mage(name, Console.GREEN)
    x.setLvl(level)
    x
  }

  def saveCharacter(char: Player): Boolean = {
    import java.io.{ File, FileWriter, PrintWriter }
    val name = char.name
    var role: String = ""
    if (char.isInstanceOf[Mage]) {
      role = "Mage"
    } else {
      role = "Warrior"
    }
    val lvl = char.lvl
    val jsonString = Json.obj(
      name -> Json.obj(
        "role" -> role,
        "level" -> lvl
      )
    )
    val file = new File(name + ".json")
    val pw = new PrintWriter(new FileWriter(file))
    println(Json.prettyPrint(jsonString))
    pw.write(Json.prettyPrint(jsonString))
    pw.close()
    true
  }

  def loadCharacter(name: String): Option[Player] = {
    var s: String = ""
    for (x <- Source.fromFile(name + ".json").getLines) {
      println(x)
      s += x
    }
    if (!s.isEmpty) {
      val jsonString = Json.parse(s)
      println(jsonString)
      val role = (jsonString \ name \ "role").get.toString()
      println(role)
      val level = (jsonString \ name \ "level").get.toString().toInt
      role match {
        case "\"Warrior\"" => Some(createWarrior(name, level))
        case "\"Mage\"" => Some(createMage(name, level))
        case _ => None
      }
    } else {
      None
    }
  }
}
