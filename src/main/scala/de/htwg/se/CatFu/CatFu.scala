package de.htwg.se.CatFu

import de.htwg.se.CatFu.model.Player
import scala.io.StdIn._

object CatFu {
  def main(args: Array[String]): Unit = {
    menu()
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
