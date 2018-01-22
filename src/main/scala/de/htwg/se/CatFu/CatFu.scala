package de.htwg.se.CatFu

import de.htwg.se.CatFu.model._
import de.htwg.se.CatFu.logic._
import scala.io.StdIn.readLine

object CatFu {

  var counter : Int = 0

  var board = new Field()
  val obs1 = Obstacle()
  val list: List[Thing] = List(student, enemy, obs1)
  val playerList: List[Player] = List(student)
  val enemyList: List[Player] = List(enemy)


    val student: Player = new Mage("Whiskers", Console.GREEN)
    userPrint(student.fullDescription)
    val enemy: Player = new Warrior("Lucifurr", Console.RED)
    enemy.setLvl(100) // scalastyle:ignore
    userPrint(enemy.fullDescription)

  def main(args: Array[String]): Unit = {
    menu()

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

  def userinput(): String = {
   var userinput = readLine(">")
    userinput
  }

  def gameProcess(): Unit = {
    val p :Player = playerChoose()
    actionmenu(p)




    var input = userinput()

    userPrint(board)

//    if (intsteps != 0) {
  //    userPrint(board.isvalid(enemy, input, intsteps))
    //}

    userPrint(board)
  }

  def move(p: Player): Unit ={
    val list = board.dijkstra(p)
    println(board.highlight(list))
    userPrint(Console.RED + "Please enter your way/ catjump. Possible Moves are shown." + Console.RESET + " compuurrrende?!")
    var intsteps = board.isvalid(p,userinput(),p.getSpeed)
    if (intsteps > 0){
      actionmenu(p)
    }
  }

  def playerChoose(): Player = {
    //if(liste von charakter == 0) -> nehme den einen und geh gleich zu actionmenu
    // ansonsten fragen, welchen er benutzen möchte:

    var accepted = false
    while (!accepted) {
      userPrint(Console.RED + "Now it´s your turn." + Console.RESET)
      userPrint(Console.RED + "Which Pokemon" + Console.RESET +
        "... I mean Kitty " + Console.RED + "do you wanna choose?" + Console.RESET)
      //Liste von Charakter, die der User noch hat.
      //Listen index dann eingeben????????
      userPrint(Console.RED + "M" + Console.RESET + "age")
      userPrint(Console.RED + "P" + Console.RESET + "awior")
      userPrint(Console.RED + "H" + Console.RESET + "ow To Play")
      userPrint(Console.RED + "E" + Console.RESET + "xit")
      userinput() match {
        case "M" | "m" =>
          userPrint("Not yet implemented")
          userPrint("\u001b[H\u001b[J")
          accepted = true
          return new Mage("hmania", "BLUE")
        case "P" | "p" =>
          userPrint("Not yet implemented")
          userPrint("\u001b[H\u001b[J")
          accepted = true
          return new Warrior("hmania aha", "BLUE")
        case "H" | "h" => help()
        case "E" | "e" => System.exit(0)
        case _ => userPrint("What? Please try it again and get your Cat away from your keys." +
          "It´s not a Game for cats. It´s a game about cats, buddy.")
      }
    }
    new Mage("blablabla","WHITE")
  }

  def actionmenu(player: Player): Unit = {
    userPrint("\u001b[H\u001b[J")
    var accepted = false
    while (!accepted) {
      userPrint(Console.RED + "Now it´s your turn." + Console.RESET)
      userPrint("What do you want to do?")
      userPrint(Console.RED + "M" + Console.RESET + "ove")
      userPrint(Console.RED + "A" + Console.RESET + "ttack")
      userPrint(Console.RED + "H" + Console.RESET + "ow To Play")
      userPrint(Console.RED + "E" + Console.RESET + "xit")
      userPrint(Console.RED + "P" + Console.RESET + "et a cat.")
      readLine(">") match {
        case "M" | "m" =>
          userPrint("Not yet implemented")
          userPrint("\u001b[H\u001b[J")
          accepted = true
        move(player)
        case "A" | "a" =>
          userPrint("Not yet implemented")
          userPrint("\u001b[H\u001b[J")
          accepted = true
          //attack(player)
        case "H" | "h" => help()
        case "E" | "e" => System.exit(0)
        case _ | "P" | "p" => userPrint("What? Please try it again and get your Cat away from your keys." +
          "It´s not a Game for cats. It´s a game about cats, buddy.")
      }
    }
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
          start()
        case "H" | "h" => help()
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
    val manager = PlayerManagement
    board.clearField()
    board.fillrandomField() // set Obstacles
    board.setUpTeams(playerList, enemyList)
    userPrint(board)
    gameProcess()
    // Load Players
    // Create Players // make a Player Factory?
    // remove Players
    // Start Game

    // Generate 3-4 Random Enemies split playergroup total level among them
    // Enemy Factory. Abstract or Method Combine with Player?
    // prepare Board
    // spawn it in
    // set Obstacles
    // set Enemies to random upper locations
    // let Players decide where to place their pawns?
    // need a menu for that.
    // show Board and Controls on Screen
    /*val rand = new scala.util.Random
    rand.nextBoolean() match {
      case true => playerTurn()
      case false => enemyTurn()
    }*/
    enemyTurn()
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
    menu()
  }

  def enemyTurn(): Unit = {
    // see User Turn, but with AI
    playerTurn()
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
    userPrint("You lost when all your kitties are defeated. Hopefully they haven't used up all their 9 lives.")
    userPrint()
    readLine("Press " + Console.UNDERLINED + "<Enter>" + Console.RESET + " to continue.")
    userPrint("\u001b[H\u001b[J")
  }

}

//undo should get a "last called function" but then turn parameters around accordingly.
