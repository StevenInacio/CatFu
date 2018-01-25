package de.htwg.se.CatFu


import de.htwg.se.CatFu.logic._
import de.htwg.se.CatFu.model._
import de.htwg.se.CatFu.view.CatFuGUI

import scala.io.StdIn.readLine

object CatFu {

   val gui = new CatFuGUI()

  val obs1 = Obstacle()
  var counter: Int = 0
  var board = new Field()
  var playerList: List[Player] = List()
  var enemyList: List[Player] = List()

  def main(args: Array[String]): Unit = {
    menu()
  }

  def userinput(): String = {
    val userinput = readLine(">")
    userinput
  }

  def move(p: Player, remainingMoves: Int): Int = {
    clearScreen()
    userPrint(board.highlight(board.dijkstra(p, remainingMoves)))
    userPrint(Console.RED + "Please enter your way/ catjump" + Console.RESET + " compuurrrende?!")
    board.isValid(p, userinput(), remainingMoves)
  }

  // scalastyle:off
  def playerChoose(playerMap: Map[Int, (Boolean, Boolean)]): Option[Player] = {
    var accepted = false
    var index = -1
    var falseInput = false
    while (!accepted) {
      clearScreen()
      userPrint(board)
      userPrint(Console.RED + "Now it´s your turn." + Console.RESET)
      userPrint(Console.RED + "Which Pokemon" + Console.RESET +
        "... I mean Kitty " + Console.RED + "do you wanna choose?" + Console.RESET)
      for (x <- playerList.indices) {
        if (!playerMap(x)._1) {
          userPrint(Console.INVISIBLE + (x + 1) + ": " + playerList(x).name +
            " @ (" + playerList(x).posy + "," + playerList(x).posx + ")" + Console.RESET + "HP: " + playerList(x).currentHP + "/" + playerList(x).getMaxHP)
        } else {
          userPrint(Console.RED + (x + 1) + ": " + Console.RESET + playerList(x).name +
            " @ (" + playerList(x).posy + "," + playerList(x).posx + ")" + "HP: " + playerList(x).currentHP + "/" + playerList(x).getMaxHP)
        }
      }
      userPrint(Console.RED + "E" + Console.RESET + "nd Turn")
      if (falseInput) {
        userPrint("What? Please try it again and get your Cat away from your keyboard." +
          "\nIt´s not a Game for cats. It´s a game about cats, buddy.")
        falseInput = false
      }
      try {
        userinput() match {
          case "E" | "e" => accepted = true
          case x if playerList.indices.contains(x.toInt - 1) && playerMap(x.toInt - 1)._2 =>
            index = x.toInt - 1
            accepted = true
          case _ => falseInput = true
        }
      } catch {
        case _: NumberFormatException => falseInput = true
      }
    }
    if (index == -1) None else Some(playerList(index))
  }

  // scalastyle:on

  def attackMenu(player: Player): Boolean = {
    val enemiesInRange = board.dijkstraShowEnemiesInRange(player, enemyList)
    userPrint("Which enemy do you want to attack?")
    for (x <- enemiesInRange.indices) {
      userPrint(Console.RED + (x + 1) + ": " + Console.RESET + enemiesInRange(x).name +
        " @ (" + enemiesInRange(x).posy + "," + enemiesInRange(x).posx + ")" + " HitRate: " +
        player.hitrate(enemiesInRange(x)) + "%")
    }
    try {
      userinput() match {
        case x if enemiesInRange.indices.contains(x.toInt - 1) =>
          player.attack(enemiesInRange(x.toInt - 1)) match {
            case 0 => userPrint(player.name + " missed " + enemiesInRange(x.toInt - 1).name + "!")
              false
            case -1 => userPrint(enemiesInRange(x.toInt - 1).name + " is already dead!")
              true
            case y => userPrint(player.name + " did " + y + " damage to " + enemiesInRange(x.toInt - 1).name)
              if (enemiesInRange(x.toInt - 1).currentHP == 0) {
                board.setPosition(Empty(Console.WHITE), enemiesInRange(x.toInt - 1).posx, enemiesInRange(x.toInt - 1).posy)
                removeFromList(enemyList, x.toInt - 1)
              }
              false
          }
        case _ => true
      }
    }
    catch {
      case _: NumberFormatException => true
    }
  }

  def actionMenu(player: Player, map: Map[Int, (Boolean, Boolean)]): (Boolean, Boolean) = {
    clearScreen()
    var accepted = false
    var remainingMoves = player.getSpeed
    var movable = map(playerList.indexOf(player))._2
    var available = map(playerList.indexOf(player))._1
    while (!accepted) {
      userPrint(board.highlight(player))
      userPrint(Console.RED + "Now it´s your turn." + Console.RESET)
      userPrint("What do you want to do?")
      if (movable) {
        userPrint(Console.RED + "M" + Console.RESET + "ove")
      } else {
        userPrint(Console.INVISIBLE + "Move" + Console.RESET)
      }
      userPrint(Console.RED + "A" + Console.RESET + "ttack")
      userPrint(Console.RED + "E" + Console.RESET + "nd Turn")
      userPrint(Console.RED + "C" + Console.RESET + "ancel")
      readLine(">") match {
        case "M" | "m" if movable =>
          remainingMoves -= move(player, remainingMoves)
          if (0 == remainingMoves) movable = false
        case "A" | "a" =>
          userPrint("Not yet implemented")
          available = attackMenu(player)
          if (!available) {
            accepted = true
          }
        case "E" | "e" =>
          available = false
          accepted = true
        case "C" | "c" => accepted = true
        case _ => userPrint("What? Please try it again and get your Cat away from your keys." +
          "It´s not a Game for cats. It´s a game about cats, buddy.")
      }
    }
    available -> movable
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
    userPrint(Console.RED + "R" + Console.RESET + "emove a Kitten from your team?")
    userPrint(Console.RED + "G" + Console.RESET + "enerate a random Team?")
    userPrint(Console.RED + "S" + Console.RESET + "tart the game or")
    userPrint(Console.RED + "E" + Console.RESET + "xit while you still can?")
  }

  def loadMenu(): List[Player] = {
    clearScreen()
    userPrint("Please tell me the kitten you want to load.")
    PlayerManagement.loadCharacter(userinput()) match {
      case Some(x) => List(x)
      case None =>
        userPrint("Kitten not found.")
        List()
    }
  }

  def createMenu(): List[Player] = {
    clearScreen()
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

  def removeFromList(list: List[Player], index: Int): List[Player] = {
    val (l1, l2) = list.splitAt(index)
    l1 ::: (l2 drop 1)
  }

  def removeMenu(list: List[Player]): List[Player] = {
    clearScreen()
    userPrint("Who do you want to remove?")
    for (x: Int <- list.indices) {
      userPrint(Console.RED + (x + 1) + ": " + Console.RESET + list(x).name)
    }
    userPrint(Console.RED + "C" + Console.RESET + "ancel")
    userinput() match {
      case x if list.indices contains (x.toInt - 1) =>
        removeFromList(list, x.toInt - 1)
      case "C" | "c" => list
      case _ =>
        userPrint("wrong input, cancelling")
        list
    }
  }

  // scalastyle:off
  def characterMenu(): List[Player] = {
    var list: List[Player] = List()
    var accepted = false
    var falseInput = false
    while (!accepted) {
      clearScreen()
      printCharacterMenu(list)
      if (falseInput) {
        userPrint("What? I didn't get that.")
        falseInput = false
      }
      userinput() match {
        case "L" | "l" => list = list ++ loadMenu()
        case "C" | "c" => list = list ++ createMenu()
        case "R" | "r" => list = removeMenu(list)
        case "G" | "g" => list = PlayerManagement.randomTeam()
        case "S" | "s" => if (list.nonEmpty && list.length < 5) accepted = true
        case "E" | "e" => sys.exit(0)
        case _ => falseInput = true
      }
    }
    list
  }

  // scalastyle:on

  def start(): Unit = {
    // Player management up to 4 ()
    playerList = characterMenu()
    enemyList = PlayerManagement.enemyTeam(playerList)
    board.clearField()
    board.setRandomObstacle() // set Obstacles
    board.setUpTeams(playerList, enemyList)
    userPrint(board)
    playerTurn()

    // Generate 3-4 Random Enemies split playergroup total level among them [+] done
    // Enemy Factory. Abstract or Method Combine with Player? [+] done
    // prepare Board [+] done
    // spawn it in [+] done
    // set Obstacles [+] done
    // set Enemies to random upper locations [+] done
    // let Players decide where to place their pawns? [-] scrapped
    // show Board and Controls on Screen [+]
    /*val rand = new scala.util.Random
    rand.nextBoolean() match {
      case true => playerTurn()
      case false => enemyTurn()
    }*/
    enemyTurn()
  }

  def victory(): Unit = {
    userPrint(Console.RED + "YOU WON!" + Console.RESET)
    userPrint("\n\n\n\n\n\n\n\n\n\n\n")
    readLine("Press " + Console.UNDERLINED + "<Enter>" + Console.RESET + "to continue.")
    menu()
  }

  def playerTurn(): Unit = {
    var map: Map[Int, (Boolean, Boolean)] = Map()
    playerList.indices.foreach(p =>
      if (playerList(p).currentHP == 0) {
        map = map + (p -> (false, false))
      }
      else {
        map = map + (p -> (true, true))
      }
    )
    userPrint(board)
    while (map.exists((t) => t._2._1)) {
      val result = playerChoose(map)
      result match {
        case Some(x) => // Player was selected
          map = map.updated(playerList.indexOf(x), actionMenu(x, map))
        case None => // End Turn was pressed
          playerList.indices.foreach(p => map = map + (p -> (false, false)))
      }
    }
    if (!enemyList.exists(p => p.currentHP != 0)) {
      victory()
    }
    enemyTurn()
  }

  def defeat(): Unit = {
    userPrint(Console.RED + "You lost!")
    userPrint(Console.RESET + "Git gud.")
    userPrint("\n\n\n\n\n\n\n\n\n")
    readLine("Press " + Console.UNDERLINED + "<Enter>" + Console.RESET + "to continue.")
    menu()
  }

  def enemyTurn(): Unit = {
    for (x <- enemyList) {
      val target = board.getMinDistancetonextPlayer(x, playerList)
      val theWay: String = board.findWay(x, target).take(x.getSpeed)
      //TODO: find de wae *cluck cluck cluck*
    }
    if (!playerList.exists(p => p.currentHP != 0)) defeat()
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
    val list = field.dijkstra(p, p.getSpeed)
    println(field.highlight(list))
  }

  // scalastyle:on
}

//undo should get a "last called function" but then turn parameters around accordingly.
