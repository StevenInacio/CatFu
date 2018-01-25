package de.htwg.se.CatFu.logic

import de.htwg.se.CatFu.model._
import de.htwg.util.Observable
import scala.io.StdIn.readLine

object Controller extends Observable {
  var state: State.Value = State.Init
  val obs1 = Obstacle()
  var board = new Field()
  var playerList: List[Player] = List()
  var startingTeam: List[Player] = List()
  var enemyList: List[Player] = List()

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

  def printPlayerChoose(playerMap: Map[Int, (Boolean, Boolean)], falseInput: Boolean): Boolean = {
    var input: Boolean = falseInput
    userPrint(board)
    userPrint(Console.RED + "Now it´s your turn." + Console.RESET)
    userPrint(Console.RED + "Which Pokemon" + Console.RESET +
      "... I mean Kitty " + Console.RED + "do you wanna choose?" + Console.RESET)
    for (x <- playerList.indices) {
      if (!playerMap(x)._1) {
        userPrint(Console.INVISIBLE + (x + 1) + ": " + playerList(x).name +
          " @ (" + playerList(x).posy + "," + playerList(x).posx + ")" + "HP: " + playerList(x).currentHP + "/" + playerList(x).getMaxHP + Console.RESET)
      } else {
        userPrint(Console.RED + (x + 1) + ": " + Console.RESET + playerList(x).name +
          " @ (" + playerList(x).posy + "," + playerList(x).posx + ")" + "HP: " + playerList(x).currentHP + "/" + playerList(x).getMaxHP)
      }
    }
    userPrint(Console.RED + "E" + Console.RESET + "nd Turn")
    if (input) {
      userPrint("What? Please try it again and get your Cat away from your keyboard." +
        "\nIt´s not a Game for cats. It´s a game about cats, buddy.")
      input = false
    }
    input
  }

  def playerChoose(playerMap: Map[Int, (Boolean, Boolean)]): Option[Player] = {
    var accepted = false
    var index = -1
    var falseInput = false
    while (!accepted) {
      clearScreen()
      falseInput = printPlayerChoose(playerMap, falseInput)
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

  def printInRange(player: Player, enemiesInRange: List[Player]): Unit = {
    for (x <- enemiesInRange.indices) {
      userPrint(Console.RED + (x + 1) + ": " + Console.RESET + enemiesInRange(x).name +
        " @ (" + enemiesInRange(x).posy + "," + enemiesInRange(x).posx + ")" + " HitRate: " +
        player.hitrate(enemiesInRange(x)) + "%" + " HP: " + enemiesInRange(x).currentHP + "/" + enemiesInRange(x).getMaxHP)
    }
  }

  def tryAttacking(player: Player, enemiesInRange: List[Player]): Boolean = {
    try {
      userinput() match {
        case x if enemiesInRange.indices.contains(x.toInt - 1) =>
          player.attack(enemiesInRange(x.toInt - 1)) match {
            case 0 =>
              userPrint(player.name + " missed " + enemiesInRange(x.toInt - 1).name + "!")
              false
            case -1 =>
              userPrint(enemiesInRange(x.toInt - 1).name + " is already dead!")
              true
            case y =>
              userPrint(player.name + " did " + y + " damage to " + enemiesInRange(x.toInt - 1).name)
              if (isPlayerDead(enemiesInRange(x.toInt - 1), true)) {
                if (enemyList.isEmpty) {
                  victory()
                }
              }
              false
          }
        case _ => true
      }
    } catch {
      case _: NumberFormatException => true
    }
  }

  def attackMenu(player: Player): Boolean = {
    val enemiesInRange = board.dijkstraShowEnemiesInRange(player, enemyList)
    userPrint("Which enemy do you want to attack?")
    printInRange(player, enemiesInRange)
    tryAttacking(player, enemiesInRange)
  }

  def printActionMenu(player: Player, map: Map[Int, (Boolean, Boolean)], movable: Boolean): Unit = {
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
  }

  def actionMenu(player: Player, map: Map[Int, (Boolean, Boolean)]): (Boolean, Boolean) = {
    var accepted = false
    var remainingMoves = player.getSpeed
    var movable = map(playerList.indexOf(player))._2
    var available = map(playerList.indexOf(player))._1
    while (!accepted) {
      clearScreen()
      printActionMenu(player, map, movable)
      userinput() match {
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

  def printCharacterMenu(list: List[Player], falseInput: Boolean): Boolean = {
    var input: Boolean = falseInput
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
    if (falseInput) {
      userPrint("What? I didn't get that.")
      input = false
    }
    input
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

  def characterMenuInput(list: List[Player]): (Boolean, Boolean, List[Player]) = {
    userinput() match {
      case "L" | "l" => (false, false, list ++ loadMenu())
      case "C" | "c" => (false, false, list ++ createMenu())
      case "R" | "r" => (false, false, removeMenu(list))
      case "G" | "g" => (true, false, PlayerManagement.randomTeam())
      case "S" | "s" => if (list.nonEmpty && list.length < 5) {
        (true, false, list)
      } else {
        (false, false, list)
      }
      case "E" | "e" => sys.exit(0)
      case _ => (false, true, list)
    }
  }

  def characterMenu(): List[Player] = {
    var list: List[Player] = List()
    var accepted = false
    var falseInput = false
    while (!accepted) {
      clearScreen()
      falseInput = printCharacterMenu(list, falseInput)
      val x = characterMenuInput(list)
      list = x._3
      falseInput = x._2
      accepted = x._1
    }
    list
  }

  def start(): Unit = {
    playerList = characterMenu()
    enemyList = PlayerManagement.enemyTeam(playerList)
    board.clearField()
    board.setRandomObstacle() // set Obstacles
    board.setUpTeams(playerList, enemyList)
    userPrint(board)
    val rand = new scala.util.Random
    rand.nextBoolean() match {
      case true => playerTurn()
      case false => enemyTurn()
    }
  }

  def victory(): Unit = {
    userPrint(Console.RED + "YOU WON!" + Console.RESET)
    userPrint("\n\n\n\n\n\n\n\n\n\n\n")
    readLine("Press " + Console.UNDERLINED + "<Enter>" + Console.RESET + "to continue.")
    for (x <- playerList) {

    }
    menu()
  }

  def playerTurn(): Unit = {
    var map: Map[Int, (Boolean, Boolean)] = Map()
    playerList.indices.foreach(p =>
      if (playerList(p).currentHP == 0) {
        map = map + (p -> (false, false))
      } else {
        map = map + (p -> (true, true))
      })
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

  def isPlayerDead(target: Player, enemy: Boolean): Boolean = {
    if (target.currentHP == 0) {
      board.setPosition(Empty(""), target.posx, target.posy)
      if (enemy) {
        enemyList = removeFromList(enemyList, enemyList.indexOf(target))
      } else {
        playerList = removeFromList(playerList, playerList.indexOf(target))
      }
      true
    } else {
      false
    }
  }

  def enemyMove(x: Player): Player = {
    val target = board.getMinDistanceToNextPlayer(x, playerList)
    val distanceToTarget = board.getDistance(x, target)
    if (distanceToTarget > x.getRange) {
      val theWay = board.findWay(x, target).take(distanceToTarget - x.getRange)
      board.isValid(x, theWay, x.getSpeed)
    }
    target
  }

  def enemyTurn(): Unit = {
    for (x <- enemyList) {
      clearScreen()
      userPrint(board.highlight(x))
      val target = enemyMove(x)
      Thread.sleep(1000) // scalastyle:ignore
      clearScreen()
      if (board.getDistance(x, target) <= x.getRange) {
        x.attack(target)
        if (isPlayerDead(target, false)) {
          if (playerList.isEmpty) defeat()
        }
        userPrint(board.highlight(List((x.posx, x.posy), (target.posx, target.posy))))
      } else {
        userPrint(board.highlight(x))
      }
      Thread.sleep(1000) // scalastyle:ignore
    }
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
