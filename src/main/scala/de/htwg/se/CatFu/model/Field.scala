package de.htwg.se.CatFu.model

class Field {
  var stepsTaken = 0
  val xfield: Int = 12
  val yfield: Int = 12
  var field: Array[Array[Thing]] = Array.ofDim[Thing](xfield, yfield)
  val empty = Empty("")
  val rock = Obstacle()
  clearField()

  /**
    * GetColor returns the Color of a Thing in String
    *
    * @param t Thing
    * @return String color
    */
  def getColor(t: Thing): String = {
    t.color
  }

  // scalastyle:off
  def findWay(player: Player, target: Player): String = {
    var (x, y) = {
      player.posx -> player.posy
    }
    val (x_1, y_1) = {
      target.posx -> target.posy
    }
    var s: String = ""
    var directx = x - x_1
    var directy = y - y_1
    var vert = true
    var stepCounter = 0
    var errorCount = 0
    while (!((directx.abs == 1 && directy == 0) || (directx == 0 && directy.abs == 1)) && stepCounter < player.getSpeed) {
      vert match {
        case v if errorCount > 2 =>
          if (v) {
            if (matchTestValidInputSpace(x -> y, 'w')) {
              if (s.nonEmpty) if (s.last == 's') {
                s = s.slice(0, s.length - 1)
              } else {
                s += 'w'
                errorCount -= 1
              }
              directx -= 1
              x -= 1
              stepCounter += 1
              if (errorCount <= 2) vert = !vert
            } else if (matchTestValidInputSpace(x -> y, 's')) {
              if (s.nonEmpty) if (s.last == 'w') s = s.slice(0, s.length - 1)
              else {
                s += 's'
                errorCount -= 1
              }
              directx += 1
              x += 1
              if (errorCount <= 2) vert = !vert
              stepCounter += 1
            } else {
              errorCount += 1
              vert = !vert
            }
          } else {
            if (matchTestValidInputSpace(x -> y, 'a')) {
              if (s.nonEmpty) if (s.last == 'd') s = s.slice(0, s.length - 1)
              else {
                s += 'a'
                errorCount -= 1
              }
              directy -= 1
              y -= 1
              stepCounter += 1
              if (errorCount <= 2) vert = !vert
            } else if (matchTestValidInputSpace(x -> y, 'd')) {
              if (s.nonEmpty) if (s.last == 'a') {
                s = s.slice(0, s.length - 1)

              } else {
                s += 'd'
                errorCount -= 1
              }
              directy += 1
              y += 1
              stepCounter += 1
              if (errorCount <= 2) vert = !vert
            } else {
              errorCount += 1
              vert = !vert
            }
          }
          if (errorCount > 10) {
            s = ""
            directx = 1
            directy = 0
          }
        case true if directx != 0 =>
          if (directx < 0) {
            if (matchTestValidInputSpace(x -> y, 's')) {
              s += 's'
              directx += 1
              x += 1
              errorCount = 0
              stepCounter += 1
            } else {
              errorCount += 1
              vert = !vert
            }
          } else {
            if (matchTestValidInputSpace(x -> y, 'w')) {
              s += 'w'
              directx -= 1
              x -= 1
              errorCount = 0
              stepCounter += 1
            } else {
              errorCount += 1
              vert = !vert
            }
          }
        case false if directy != 0 =>
          if (directy < 0) {
            if (matchTestValidInputSpace(x -> y, 'd')) {
              s += 'd'
              directy += 1
              y += 1
              errorCount = 0
              stepCounter += 1
            } else {
              errorCount += 1
              vert = !vert
            }
          } else {
            if (matchTestValidInputSpace(x -> y, 'a')) {
              s += 'a'
              directy -= 1
              y -= 1
              errorCount = 0
              stepCounter += 1
            } else {
              errorCount += 1
              vert = !vert
            }
          }
        case _ =>
          errorCount += 1
          vert = !vert
      }
    }
    s
  }

  // scalastyle:on

  /**
    * Fills the Field with empty Things
    */
  def clearField(): Unit = {
    val empty = Empty(Console.WHITE)
    field = Array.ofDim[Thing](xfield, yfield)
    for (i <- 0 until xfield) {
      for (j <- 0 until yfield) {
        field(i)(j) = empty
      }
    }
  }

  /**
    * Returns a Display Char
    *
    * @param x Int
    * @param y Int
    * @return the Display Char of the Thing in this field
    */
  def getDisplay(x: Int, y: Int): Char = {
    field(x)(y).display
  }

  /**
    * Returns the Thing
    *
    * @param x Int
    * @param y Int
    * @return the Thing in this field
    */
  def getInstance(x: Int, y: Int): Thing = {
    field(x)(y)
  }

  /**
    * ToString TUI <br>
    * Prints the field
    *
    * @return s a String
    */
  override def toString: String = {
    val empty = Empty(Console.WHITE)
    val rock = new Obstacle
    val reset = Console.RESET
    var s: String = ""
    var vertical = "----"
    var block = true
    vertical = (vertical * yfield) + "-"
    s += "    "
    for (i <- 0 until xfield) {
      if (block) {
        for (k <- 0 until xfield) {
          if (k > 9) {
            s += " " + k + " "
          } else {
            s += "  " + k + " "
          }
        }
        block = false
      }
      if (i > 0) {
        s += "|"
      }
      s += "\n    " + vertical + "\n"
      if (i > 9) {
        s += " " + i + " "
      } else {
        s += "  " + i + " "
      }
      for (j <- 0 until yfield) {
        val thing: Thing = field(i)(j)
        if (thing == empty) {
          s += "|   "
        } else if (thing == rock) {
          s += "| " + rock.color + rock.display + reset + " "
        } else {
          s += "| " + thing.color + thing.display + reset + " "
        }
      }
    }
    s += "|\n    " + vertical
    s
  }

  /**
    * Sets Random the Players<br>
    * At the beginning the game.
    *
    * @param enemy  Team at the top of the field.
    * @param player Team at the bottom of the field.
    */
  def setUpTeams(player: List[Player], enemy: List[Player]): Unit = {
    val random = new scala.util.Random
    for (s <- player) {
      val x = random.nextInt(xfield / 3) + (2 * xfield) / 3
      val y = random.nextInt(yfield)
      field(x)(y) = s
      s.posx = x
      s.posy = y
    }
    for (s <- enemy) {
      val x = random.nextInt(xfield / 3)
      val y = random.nextInt(yfield)
      field(x)(y) = s
      s.posx = x
      s.posy = y
    }
  }

  /**
    * Sets Obstacles on random Positions<br>
    * in the Field
    */
  def setRandomObstacle(): Unit = {
    val random = new scala.util.Random
    var p = 0
    if (yfield > xfield) {
      p = xfield
    } else {
      p = yfield
    }
    for (_ <- 0 to p) { // immer ein Hindernis mehr als der kleinere Wert der Matrixlänge
      val r1 = random.nextInt(xfield)
      val r2 = random.nextInt(yfield)
      field(r1)(r2) = rock
    }
  }

  /**
    * Checks if the requested Steps are valid and moves when they are.
    *
    * @param p         The moving Player.
    * @param userInput String containing "WASD" to indicate the direction the player wants to go.
    * @return The amount of steps the Player took, 0 if there was a Thing in the way.
    */
  def isValid(p: Player, userInput: String, intSteps: Int): Int = {
    val xOld = p.posx
    val yOld = p.posy
    stepsTaken = 0
    if (intSteps >= userInput.length) {
      for (i <- 0 until userInput.length) {
        if (!matchTestValidInputSpace(p, userInput.charAt(i))) {
          setPosition(empty, p.posx, p.posy)
          setPosition(p, xOld, yOld)
          stepsTaken = 0
        } else {
          stepsTaken += 1
          realmove(p, userInput.charAt(i))
        }
      }
    }
    stepsTaken
  }

  /**
    * Matches<br>
    * single Char after wasd
    * and even in the field
    * and if the next field is empty
    *
    * @param p Player
    * @param i Char
    * @return just a Checkbool
    */
  // scalastyle:off
  def matchTestValidInputSpace(p: Player, i: Char): Boolean = {
    matchTestValidInputSpace((p.posx, p.posy), i)
  }

  def matchTestValidInputSpace(x: (Int, Int), i: Char): Boolean = i match {
    case 'a' if (x._2 - 1 < yfield && x._2 - 1 >= 0) && field(x._1)(x._2 - 1).isInstanceOf[Empty] => true
    case 'w' if (x._1 - 1 < xfield && x._1 - 1 >= 0) && field(x._1 - 1)(x._2).isInstanceOf[Empty] => true
    case 's' if (x._1 + 1 < xfield && x._1 + 1 >= 0) && field(x._1 + 1)(x._2).isInstanceOf[Empty] => true
    case 'd' if (x._2 + 1 < yfield && x._2 + 1 >= 0) && field(x._1)(x._2 + 1).isInstanceOf[Empty] => true
    case _ => false
  }

  def matchTestIsInField(p: Player, i: Char): Boolean = {
    matchTestIsInField((p.posx, p.posy), i)
  }

  def matchTestIsInField(x: (Int, Int), i: Char): Boolean = i match {
    case 'a' if x._2 - 1 < yfield && x._2 - 1 >= 0 => true
    case 'w' if x._1 - 1 < xfield && x._1 - 1 >= 0 => true
    case 's' if x._1 + 1 < xfield && x._1 + 1 >= 0 => true
    case 'd' if x._2 + 1 < yfield && x._2 + 1 >= 0 => true
    case _ => false
  }

  // scalastyle:on
  /**
    * Moves a Thing <br>
    *
    * @param t    Thing. Can be Player or Empty
    * @param xnew New x position
    * @param ynew New y position
    */
  def setPosition(t: Thing, xnew: Int, ynew: Int): Unit = {
    t match {
      case player: Player =>
        player.posx = xnew
        player.posy = ynew
      case _ =>
    }
    field(xnew)(ynew) = t
  }

  /**
    * Moves the Player <br>
    * And set the old Place to empty.
    *
    * @param input is a Char from the userinput, that were checked of valid from the method isvalid
    * @param p     is the Thing that will be moved.
    * @return just a Checkbool
    */
  // verknüpfen mit move2 weil das eine liste erstellt
  def realmove(p: Player, input: Char): Boolean = { // unbedingt clear zuerst

    if (input == 'a') {
      setPosition(empty, p.posx, p.posy) // Set empty behind player
      setPosition(p, p.posx, p.posy - 1) // SetPlayer
    }
    if (input == 'w') {
      setPosition(empty, p.posx, p.posy) // Set empty behind player
      setPosition(p, p.posx - 1, p.posy) // SetPlayer
    }
    if (input == 'd') {
      setPosition(empty, p.posx, p.posy) // Set empty behind player
      setPosition(p, p.posx, p.posy + 1) // SetPlayer
    }
    if (input == 's') {
      setPosition(empty, p.posx, p.posy) // Set empty behind player
      setPosition(p, p.posx + 1, p.posy) // SetPlayer
    }
    true
  }

  /**
    * Calculate the Distance between two Players
    *
    * @param start is a Player
    * @param end   is a Player
    * @return the Distance in int
    */
  def getDistance(start: Player, end: Player): Int = { // Ohne Rocks zu beabsichtigen
    val x = start.posx - end.posx
    val y = start.posy - end.posy
    x.abs + y.abs
  }

  /**
    * Calculate the MINIMUM Distance between two Players
    *
    * @param me is a Player
    * @param pl is the Enemy Player List
    * @return the Enemy with th min Distance
    */
  def getMinDistanceToNextPlayer(me: Player, pl: List[Player]): Player = {
    var min = Int.MaxValue
    var tmp = 0
    var minDisPly: Player = NoPlayer
    for (p <- pl) {
      tmp = getDistance(me, p)
      if (min >= tmp) {
        min = tmp
        minDisPly = p
      }

    }
    minDisPly
  }

  // scalastyle:off
  def dijkstraShowEnemiesInRange(p: Player, enemies: List[Player]): List[Player] = {
    var map: Map[(Int, Int), Boolean] = Map((p.posx, p.posy) -> false)
    var foundEnemies: List[Player] = List()
    for (_ <- 0 until p.getRange) {
      for (x <- map.filter((t) => !t._2).keys) {
        if (matchTestIsInField((x._1, x._2), 'w')) {
          if (enemies.contains(field(x._1 - 1)(x._2)) && !foundEnemies.contains(field(x._1 - 1)(x._2))) {
            foundEnemies = foundEnemies :+ field(x._1 - 1)(x._2).asInstanceOf[Player]
          }
          map = map ++ Map((x._1 - 1, x._2) -> false)
        }
        if (matchTestIsInField((x._1, x._2), 'a')) {
          if (enemies.contains(field(x._1)(x._2 - 1)) && !foundEnemies.contains(field(x._1)(x._2 - 1))) {
            foundEnemies = foundEnemies :+ field(x._1)(x._2 - 1).asInstanceOf[Player]
          }
          map = map ++ Map((x._1, x._2 - 1) -> false)
        }
        if (matchTestIsInField((x._1, x._2), 's')) {
          if (enemies.contains(field(x._1 + 1)(x._2)) && !foundEnemies.contains(field(x._1 + 1)(x._2))) {
            foundEnemies = foundEnemies :+ field(x._1 + 1)(x._2).asInstanceOf[Player]
          }
          map = map ++ Map((x._1 + 1, x._2) -> false)
        }
        if (matchTestIsInField((x._1, x._2), 'd')) {
          if (enemies.contains(field(x._1)(x._2 + 1)) && !foundEnemies.contains(field(x._1)(x._2 + 1))) {
            foundEnemies = foundEnemies :+ field(x._1)(x._2 + 1).asInstanceOf[Player]
          }
          map = map ++ Map((x._1, x._2 + 1) -> false)
        }
        map = map.updated(x, true)
      }
    }
    foundEnemies
  }

  // scalastyle:on

  def dijkstra(p: Player, remainingMoves: Int): List[(Int, Int)] = {
    var map: Map[(Int, Int), Boolean] = Map((p.posx, p.posy) -> false)

    for (_ <- 0 until remainingMoves) {
      for (x <- map.filter((t) => !t._2).keys) {
        if (matchTestValidInputSpace((x._1, x._2), 'w')) {
          map = map ++ Map((x._1 - 1, x._2) -> false)
        }
        if (matchTestValidInputSpace((x._1, x._2), 'a')) {
          map = map ++ Map((x._1, x._2 - 1) -> false)
        }
        if (matchTestValidInputSpace((x._1, x._2), 's')) {
          map = map ++ Map((x._1 + 1, x._2) -> false)
        }
        if (matchTestValidInputSpace((x._1, x._2), 'd')) {
          map = map ++ Map((x._1, x._2 + 1) -> false)
        }
        map = map.updated(x, true)
      }
    }
    map.keys.toList
  }

  def highlight(p: Player): String = {
    highlight(List((p.posx, p.posy)))
  }

  //scalastyle : off
  def highlight(list: List[(Int, Int)]): String = {
    var block = true
    val reset = Console.RESET
    val highlightColor = Console.BLUE_B
    var s: String = ""
    var vertical = "----"
    vertical = (vertical * yfield) + "-"
    for (i <- 0 until xfield) {
      if (block) {
        for (k <- 0 until xfield) {
          if (k > 9) {
            s += " " + k + " "
          } else {
            s += "  " + k + " "
          }
        }
        block = false
      }
      if (i > 0) {
        s += "|"
      }
      s += "\n    " + vertical + "\n"
      if (i > 9) {
        s += " " + i + " "
      } else {
        s += "  " + i + " "
      }
      for (j <- 0 until yfield) {
        val thing: Thing = field(i)(j)
        if (list.contains((i, j))) {
          if (thing == empty) {
            s += "|" + highlightColor + "   " + reset
          } else if (thing == rock) {
            s += "|" + highlightColor + " " + rock.display + " " + reset
          } else {
            s += "|" + highlightColor + " " + thing.display + " " + reset
          }
        } else {
          if (thing == empty) {
            s += "|   "
          } else if (thing == rock) {
            s += "| " + rock.color + rock.display + reset + " "
          } else {
            s += "| " + thing.color + thing.display + reset + " "
          }
        }
      }
    }
    s += "|\n" + vertical
    s
  }  // scalastyle:on

}