package de.htwg.se.CatFu

import de.htwg.se.CatFu.model._
import de.htwg.se.CatFu.logic._

import scala.collection.JavaConverters._
import scala.io.StdIn.readLine

object CatFu {

  var counter: Int = 0

  var board = new Field()
  val obs1 = Obstacle()
  var playerList: List[Player] = List()
  var enemyList: List[Player] = List()

  def main(args: Array[String]): Unit = {
    menu()
  }

  def userinput(): String = {
    val userinput = readLine(">")
    userinput
  }

  def gameProcess(): Unit = {
    val p: Player = playerChoose()
    actionmenu(p)

    var input = userinput()

    userPrint(board)
  }

  def move(p: Player): Unit = {
    userPrint(board.highlight(board.dijkstra(p)))
    userPrint(Console.RED + "Please enter your way/ catjump" + Console.RESET + " compuurrrende?!")
    val intsteps = board.isvalid(p, userinput(), p.getSpeed)
    if (intsteps > 0) {
      actionmenu(p)
    }
  }

  def playerChoose(): Player = {
    //if(liste von charakter == 0) -> nehme den einen und geh gleich zu actionmenu
    // ansonsten fragen, welchen er benutzen möchte:

    var accepted = false
    var index = -1
    while (!accepted) {
      userPrint(Console.RED + "Now it´s your turn." + Console.RESET)
      userPrint(Console.RED + "Which Pokemon" + Console.RESET +
        "... I mean Kitty " + Console.RED + "do you wanna choose?" + Console.RESET)
      //Liste von Charakter, die der User noch hat.
      //Listen index dann eingeben???????? korrekt.
      for (x: Int <- playerList.indices) {
        userPrint(Console.RED + (x + 1) + ":" + Console.RESET + playerList(x).name + " @ (" + playerList(x).posy + "," + playerList(x).posx + ")")
      }
      userPrint(Console.RED + "C" + Console.RESET + "ancel")
      userinput() match {
        case x if playerList.indices contains (x.toInt - 1) =>
          index = x.toInt - 1
          accepted = true
        case "C" | "c" => ()
        case _ => userPrint("What? Please try it again and get your Cat away from your keys." +
          "It´s not a Game for cats. It´s a game about cats, buddy.")
      }
    }
    playerList(index)
  }

  def actionmenu(player: Player): Unit = {
    clearScreen()
    var accepted = false
    while (!accepted) {
      userPrint(board.highlight(player))
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
          clearScreen()
          accepted = true
          move(player)
        case "A" | "a" =>
          userPrint("Not yet implemented")
          clearScreen()
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
    clearScreen()
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
          clearScreen()
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

  def clearScreen(): Unit = {
    println("\u001b[H\u001b[J")
  }

  def printCharacterMenu(list: List[Player]): Unit = {
    if (list.length > 4) {
      userPrint(Console.RED + "TOO MANY KITTENS" + Console.RESET)
    }
    userPrint(Console.GREEN + "Selected Character:")
    for (x <- list) {
      userPrint(x)
    }
    userPrint(Console.RESET)
    userPrint("Do you want to")
    userPrint(Console.RED + "L" + Console.RESET + "oad a Character?")
    userPrint(Console.RED + "C" + Console.RESET + "reate a new Kitten?")
    userPrint(Console.RED + "G" + Console.RESET + "enerate a random Team?")
    userPrint(Console.RED + "S" + Console.RESET + "tart the game or")
    userPrint(Console.RED + "E" + Console.RESET + "xit while you still can?")
  }

  def loadMenu(): List[Player] = {
    userPrint("Please tell me the kitten you want to load.")
    PlayerManagement.loadCharacter(userinput()) match {
      case Some(x) => List(x)
      case None =>
        userPrint("Kitten not found.")
        List()
    }
  }

  def createMenu(): List[Player] = {
    userPrint("What's the name of the new Kitty?")
    val name = userinput()
    userPrint("Is the new Feline a " + Console.RED + "P" + Console.RESET + "awrior or a " + Console.RED + "F" + Console.RESET + "leazard?")
    userinput() match {
      case "P" | "p" => List(PlayerManagement.createWarrior(name, 1))
      case "F" | "f" => List(PlayerManagement.createMage(name, 1))
      case _ =>
        userPrint("I couldn't understand that.")
        List()
    }
  }

  def characterMenu(): List[Player] = {
    var list: List[Player] = List()
    var accepted = false
    while (!accepted) {
      clearScreen()
      printCharacterMenu(list)
      userinput() match {
        case "L" | "l" => list = list ++ loadMenu()
        case "C" | "c" => list = list ++ createMenu()
        case "G" | "g" => list = PlayerManagement.randomTeam()
        case "S" | "s" => if (list.nonEmpty && list.length < 5) accepted = true
        case "E" | "e" => sys.exit(0)
      }
    }
    list
  }

  def start(): Unit = {
    // Player management up to 4 ()
    playerList = characterMenu()
    enemyList = PlayerManagement.enemyTeam(playerList)
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
    clearScreen()
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
    clearScreen()
  }

  // scalastyle:off
  def testDijkstra(): Unit = {
    val field = new Field
    field.clearField()
    field.setPosition(Obstacle(), 2, 4)
    field.setPosition(Obstacle(), 3, 3)
    field.setPosition(Obstacle(), 3, 6)
    field.setPosition(Obstacle(), 1, 5)
    val p = new Mage("Peter", Console.GREEN)
    field.setPosition(p, 3, 4)
    p.posx = 3
    p.posy = 4
    val list = field.dijkstra(p)
    println(field.highlight(list))
  }
  // scalastyle:on
}

//undo should get a "last called function" but then turn parameters around accordingly.
