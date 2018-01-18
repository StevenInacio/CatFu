package de.htwg.se.CatFu.model

import de.htwg.se.CatFu.model._

class Field {
  var inputlength = 0
  var xfield: Int = 7
  var yfield: Int = 7
  var xold : Int= 0
  var yold : Int= 0
  var field = Array.ofDim[Thing](xfield, yfield)

  /**
    * Fills the Field with empty Things
    */
  def clearField(): Unit = {
    var empty = Empty()
    field = Array.ofDim[Thing](xfield,yfield)
    for (i <- 0 until xfield){
      for (j <- 0 until yfield){
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
    var empty = Empty()
    var rock = new Obstacle
    var s :String = ""
    var vertical = "----"
    vertical = (vertical * yfield) + "-"
    for (i <- 0 until xfield) { // to -1
      if (i > 0){ s += "|"}
      s += "\n" + vertical + "\n"
      for (j <- 0 until yfield) { // to -1
        if (field(i)(j) == empty) {
          s += "|   "
        } else  if (field(i)(j) == rock) {
          s += "| "+rock.display+" "
        }else{
          s += "| " + field(i)(j) +" "
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
def setUpTeams(player : List[Player], enemy : List[Player]): Unit ={
  val random = new scala.util.Random
  for(s <- player){
    field(random.nextInt((1/3)*xfield) + (2/3)*xfield)(random.nextInt(yfield)) = s
  }
  for(s <- enemy){
    field(random.nextInt((1/3)*xfield))(random.nextInt(yfield)) = s
  }
}
  /**
    * Sets Obstacles on random Positions<br>
    *   in the Field
    */
  def fillrandomField(): Unit = {
    var rock = new Obstacle
    var p = 0
    if(yfield > xfield) {
      p = xfield
    } else{p = yfield}
    for(_ <- 0 to p){ // immer ein Hindernis mehr als der kleinere Wert der Matrixlänge
      var r1 = (Math.random()*xfield).toInt
      var r2 = (Math.random()*yfield).toInt
      if(r1 <= xfield && r1 <= yfield && r2 <= xfield && r2 <= yfield){
      field(r1)(r2) = rock
       }
    }
  }

  /**
    * Checks if Userinput is valid<br>
    * goes to match
    *   and move
    * MAKES A LIST = IS NEEDED???????
    * @param p Player
    * @param userinput String
    * @return just a Checkbool
    */
  def isvalid(p: Player, userinput : String): Boolean = {
    import scala.collection.mutable.ListBuffer
    inputlength = 0
    xold = p.posx
    yold = p.posy
    var wentwell = true
    if (p.getSpeed < userinput.length) {
          false
    } else {
      var input = new ListBuffer[Char]()
      for (i <- 0 until userinput.length) {
        if (!matchTestValidInputSpace(p,userinput.charAt(i))) {
          p.posx = xold
          p.posy = yold
          return false
        } else {
          inputlength += 1
          input += userinput.charAt(i)
          realmove(p,userinput.charAt(i))
        }
      }
      val inputList = input.toList
      wentwell
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

  def matchTestValidInputSpace(p : Player, i: Char): Boolean = i match {
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
  def setPosition(t : Thing, xnew: Int, ynew: Int): Unit ={
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
  def realmove(p : Player, input: Char): Boolean = { // unbedingt clear zuerst
    val empty = Empty()

    if (input == 'a') {
      setPosition(empty, p.posx, p.posy)  // Set empty behind player
      setPosition(p, p.posx, p.posy - 1) // SetPlayer
      p.posy = p.posy - 1
    }
    if (input == 'w') {
      setPosition(empty, p.posx, p.posy)  // Set empty behind player
      setPosition(p, p.posx - 1, p.posy) // SetPlayer
      p.posx -= 1
    }
    if (input == 's') {
      setPosition(empty, p.posx, p.posy)  // Set empty behind player
      setPosition(p, p.posx, p.posy + 1) // SetPlayer
      p.posy += 1
    }
    if (input == 'd') {
      setPosition(empty, p.posx, p.posy)  // Set empty behind player
      setPosition(p, p.posx + 1, p.posy) // SetPlayer
      p.posx += 1
    }
    true
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