package de.htwg.se.CatFu.model

import de.htwg.se.CatFu.model._

class Field {
  var inputlength = 0
  var xfield: Int = 7
  var yfield: Int = 7
  var xold: Int = 0
  var yold: Int = 0
  var field: Array[Array[Thing]] = Array.ofDim[Thing](xfield, yfield)

  /**
   * Fills the Field with empty Things
   */
  def clearField(): Unit = {
    var empty = Empty()
    field = Array.ofDim[Thing](xfield, yfield)
    for (i <- 0 until xfield) {
      for (j <- 0 until yfield) {
        field(i)(j) = empty
      }
    }
  }
  /**
   * ToString TUI <br>
   * Prints the field
   * @return s a String
   */
  override def toString: String = {
    val empty = Empty()
    val rock = new Obstacle
    val reset = Console.RESET
    var s: String = ""
    var vertical = "----"
    vertical = (vertical * yfield) + "-"
    for (i <- 0 until xfield) { // to -1
      if (i > 0) { s += "|" }
      s += "\n" + vertical + "\n"
      for (j <- 0 until yfield) { // to -1
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
    s += "|\n" + vertical
    s
  }

  /**
   * Sets Random the Players<br>
   *   At the beginning the game.
   * @param enemy Team at the top of the field.
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
   *   in the Field
   */
  def fillrandomField(): Unit = {
    val random = new scala.util.Random
    var rock = new Obstacle
    var p = 0
    if (yfield > xfield) {
      p = xfield
    } else { p = yfield }
    for (_ <- 0 to p) { // immer ein Hindernis mehr als der kleinere Wert der Matrixlänge
      var r1 = random.nextInt(xfield)
      var r2 = random.nextInt(yfield)
      field(r1)(r2) = rock
    }
  }

  /**
   * Checks if Userinput is valid<br>
   * goes to match
   *   and move
   * MAKES A LIST = IS NEEDED???????
   * @param p Player
   * @param userinput String
   * @return inputlength is an Int that said how many steps Player did.
   */
  def isvalid(p: Player, userinput: String, intsteps: Int): Int = {
    import scala.collection.mutable.ListBuffer
    var remainingMoves = intsteps
    inputlength = 0
    xold = p.posx
    yold = p.posy
    println(userinput.length)
    if (p.getSpeed < userinput.length) {
      -1
    } else {
      var input = new ListBuffer[Char]()
      for (i <- 0 until userinput.length) {
        if (!matchTestValidInputSpace(p, userinput.charAt(i))) {
          p.posx = xold
          p.posy = yold
          -2
        } else {
          inputlength += 1
          input += userinput.charAt(i)
          print(userinput.charAt(i))
          realmove(p, userinput.charAt(i))
        }
      }
      val inputList = input.toList
      remainingMoves = inputlength
      inputlength
    }
  }

  /**
   * Matches<br>
   * single Char after wasd
   * and even in the field
   * and if the next field is empty
   * @param p Player
   * @param i Char
   * @return just a Checkbool
   */

  def matchTestValidInputSpace(p: Player, i: Char): Boolean = i match {
    case 'a' if (p.posy - 1 < yfield && p.posy - 1 >= 0) && field(p.posx)(p.posy - 1).isInstanceOf[Empty] => true
    case 'w' if (p.posx - 1 < xfield && p.posx - 1 >= 0) && field(p.posx - 1)(p.posy).isInstanceOf[Empty] => true
    case 'd' if (p.posx + 1 < xfield && p.posx + 1 >= 0) && field(p.posx + 1)(p.posy).isInstanceOf[Empty] => true
    case 's' if (p.posy + 1 < yfield && p.posy + 1 >= 0) && field(p.posx)(p.posy + 1).isInstanceOf[Empty] => true
    case _ => false
  }

  /**
   * Moves a Thing <br>
   * @param t Thing. Can be Player or Empty
   * @param xnew New x position
   * @param ynew New y position
   */
  def setPosition(t: Thing, xnew: Int, ynew: Int): Unit = {
    field(xnew)(ynew) = t
  }
  /**
   * Moves the Player <br>
   * And set the old Place to empty.
   * @param input is a Char from the userinput, that were checked of valid from the method isvalid
   * @param p is the Thing that will be moved.
   * @return just a Checkbool
   */
  // verknüpfen mit move2 weil das eine liste erstellt
  def realmove(p: Player, input: Char): Boolean = { // unbedingt clear zuerst
    val empty = Empty()

    if (input == 'a') {
      setPosition(empty, p.posx, p.posy) // Set empty behind player
      setPosition(p, p.posx, p.posy - 1) // SetPlayer
      p.posy = p.posy - 1
    }
    if (input == 'w') {
      setPosition(empty, p.posx, p.posy) // Set empty behind player
      setPosition(p, p.posx - 1, p.posy) // SetPlayer
      p.posx -= 1
    }
    if (input == 'd') {
      setPosition(empty, p.posx, p.posy) // Set empty behind player
      setPosition(p, p.posx, p.posy + 1) // SetPlayer
      p.posy += 1
    }
    if (input == 's') {
      setPosition(empty, p.posx, p.posy) // Set empty behind player
      setPosition(p, p.posx + 1, p.posy) // SetPlayer
      p.posx += 1
    }
    true
  }

  /**
   * Calculate the Distance between two Players
   * @param start is a Player
   * @param end is a Player
   * @return the Distance in int
   */
  def getDistance(start: Player, end: Player): Int = {
    val x = start.posx - end.posx
    val y = start.posy - end.posy
    x + y
  }

  /*
    var newx = 0
  var newy = 0
  input match {
    case 'a' => {newy = player.posy -1
                 newx = player.posx}
    case 'w' =>
  }

  (0 until y).contains(newy)
   */

}