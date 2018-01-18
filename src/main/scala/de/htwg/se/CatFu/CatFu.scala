package de.htwg.se.CatFu

import de.htwg.se.CatFu.model._

import scala.io.StdIn.readLine

object CatFu {
  def main(args: Array[String]): Unit = {
    menu()
    val student: Player = new Mage("Whiskers")
    userPrint(student.fullDescription)
    val enemy: Player = new Warrior("Lucifurr")
    enemy.setLvl(100) // scalastyle:ignore
    userPrint(enemy.fullDescription)

    for (_ <- 0 until 10) {
      val result = student.attack(enemy)
      if (result == -1) {
        userPrint(enemy.name + " is already dead!")
      } else if (result != 0) {
        userPrint(student.name + " attacked " + enemy.name + " for " + result + " damage!")
        userPrint(enemy.currentHP)
      } else {
        userPrint("Miss")
      }
    }
    userPrint(student.fullDescription)
    val obs1 = Obstacle()
    val list: List[Thing] = List(student, enemy, obs1)
    for (x: Thing <- list) {
      x match {
        case p: Player => userPrint(p)
        case o: Obstacle => userPrint(o)
      }
    }
    userPrint(student.hitrate(enemy))
    userPrint(enemy.hitrate(student))
    student.setLvl(100) // scalastyle:ignore
    userPrint(student.fullDescription)
    userPrint(enemy.fullDescription)
    userPrint(student.hitrate(enemy))
    userPrint(enemy.hitrate(student))
  }

  def menu(): Unit = {
    userPrint("\u001b[H\u001b[J")
    var accepted = false
    while (!accepted) {
      userPrint(Console.RED + "Welcome to CatFu!" + Console.RESET)
      userPrint("What do you want to do?")
      userPrint(Console.RED + "S" + Console.RESET + "tart the Game")
      userPrint(Console.RED + "H" + Console.RESET + "ow To Play")
      userPrint(Console.RED + "E" + Console.RESET + "xit")
      readLine(">") match {
        case "S" | "s" =>
          userPrint("Not yet implemented")
          userPrint("\u001b[H\u001b[J")
          accepted = true
          start() // start()
        case "H" | "h" => help() // help()
        case "E" | "e" => System.exit(0)
        case _ => userPrint("What?")
      }
    }
  }

  def userPrint(msg: Any): Unit = {
    println(msg)
  }

  def userPrint(): Unit = {
    println()
  }

  def start(): Unit = {
    // Player management up to 4 ()
    // Load Players
    // Create Players
    // remove Players
    // Start Game
    // Generate 3-4 Random Enemies split playergroup total level among them
    // prepare Board
    var board = new Field()
    board.clearField()
    board.fillrandomField()     // set Obstacles
    userPrint(board)


    // spawn it in

    // set Enemies to random upper locations
    // let Players decide where to place their pawns?
    // show Board and Controls on Screen
    // randomize which turn it is
    // start appropriate turn
  }

  def playerTurn(): Unit = {
    // map Actor to (bool, bool) ("available" and "movable")
    // for (_ <- 0 until actorlist.length) {
    // Show Options to Select available Actor or End Turn
    // Handle Input
    // if End Turn enemyTurn()
    // else Show Actor Action Menu
    // Handle Input
    // move (grey out if movable == false)
    // attack
    // legality check
    // pass:
    // attack(field(x)(y): Player)
    // failed:
    // return to previous menu
    // after that set available to false
    // wait (immediately set available to false and continue)
    // cancel to previous menu
    // End Turn
  }

  def enemyTurn(): Unit = {
    // see User Turn, but with AI
  }

  def help(): Unit = {
    userPrint("\u001b[H\u001b[J")
    userPrint("CatFu is a so called " + Console.RED + "S" + Console.RESET + "trategic "
      + Console.RED + "R" + Console.RESET + "ole-" + Console.RED + "P" + Console.RESET +
      "laying " + Console.RED + "G" + Console.RESET + "ame, or " + Console.RED + "SRPG" + Console.RESET + " for short.")
    userPrint("======================================================================")
    userPrint("Goal of the game is to defeat all of your enemies kittens.")
    userPrint("To achieve this, you must use your own Pawriors and Fleazards to attack and damage your enemy.")
    userPrint("To attack, you need to navigate your cats on the field to reach the Opurrnents with your attacks.")
    userPrint()
    userPrint("CLASSES\n==============================")
    userPrint(Console.YELLOW + "Pawriors" + Console.RESET + " use their claws as their favourite weapons, so they naturally have a range of only 1.")
    userPrint(Console.CYAN + "Fleazards" + Console.RESET + " have magical staffs. They can chant Spells of epical Purrportions, to reach far away enemies.")
    userPrint()
    userPrint("You lost when all your kittens are defeated. Hopefully they haven't used up all their 9 lives.")
    userPrint()
    readLine("Press " + Console.UNDERLINED + "<Enter>" + Console.RESET + " to continue.")
    userPrint("\u001b[H\u001b[J")
  }
}

