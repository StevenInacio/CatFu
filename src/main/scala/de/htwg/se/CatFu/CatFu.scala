package de.htwg.se.CatFu

import de.htwg.se.CatFu.model._

import scala.io.StdIn.readLine

object CatFu {
  def main(args: Array[String]): Unit = {
    val student: Player = new Mage("Whiskers")
    println("Hello, " + student) // scalastyle:ignore
    student.setLvl(5)
    println(student.fullDescription)
    student.increaseXP(500)
    println(student.fullDescription)
    val enemy: Player = new Warrior("Lucifurr")
    enemy.setLvl(14)
    println(enemy.fullDescription)

    for (_ <- 0 until 10) {
      val result = student.attack(enemy)
      if (result == -1) {
        println(enemy.name + " is already dead!")
      } else if (result != 0) {
        println(student.name + " attacked " + enemy.name + " for " + result + " damage!")
        println(enemy.currentHP)
      }
      else {
        println("Miss")
      }
    }
  }

  def menu(): Unit = {
    println(Console.RED + "Welcome to CatFu!" + Console.RESET)
    println("What do you want to do?")
    println(Console.RED + "S" + Console.RESET + "tart the Game")
    println(Console.RED + "H" + Console.RESET + "ow To Play")
    println(Console.RED + "E" + Console.RESET + "xit")
    val input = readLine(">")
  }
}
