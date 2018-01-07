/*
case class Cell(x:Int, y:Int)

val cell1 = Cell(4,5)
cell1.x
cell1.y

case class Field(cells: Array[Cell])

val field1 = Field(Array.ofDim[Cell](1))
field1.cells(0)=cell1
field1.cells(0).x
field1.cells(0).y
*/


class Field {
  var x: Int = 4
  var y: Int = 4
  val field = Array.ofDim[Char](x, y)

  def clearField(): Unit = {
    for (i <- 0 to x - 1) {
      for (j <- 0 to y - 1) {
        field(i)(j) = ' '
      }
    }
  }

  override def toString: String = {
    var s :String = ""
    var waagstrich = "----"
    waagstrich = (waagstrich * y)+ "-"
    for (i <- 0 to x-1) {
      if (i > 0){ s += "|"}
      s += "\n" + waagstrich + "\n"
      for (j <- 0 to y-1) {
        if (field(i)(j) == 0) {
          s += "|   "
        }
        else {
          s += "| " + field(i)(j) +" "
        }
      }
    }
    s += "|\n" + waagstrich
    s
  }


  def fillrandomField(): Unit = {
    var n = 0
    var p = 0
    if(y>x) {
      p = x
    } else{p = y}
       for(n <- 0 to p){ // immer ein Hindernis mehr als der kleinere Wert der Matrixlänge
         var r1 = (Math.random()*x).toInt
         var r2 = (Math.random()*y).toInt
         println(r1)
         println(r2)
        // if(r1 <= x && r1 <= y && r2 <= x && r2 <= y){
           field(r1)(r2) = 'R'
        // }
       }
  }



// EINGABE PRÜFUNG MIT LEERTASTE
  def setFigure(eingabe : String){
    var eingabe_x = 0
    var eingabe_y = 0
    val eingabe_array = eingabe.split(" ")
    eingabe_x = eingabe_array(0).toInt
    eingabe_y = eingabe_array(1).toInt
    if(eingabe_x < x && eingabe_y < y && eingabe_x >= 0 && eingabe_y >= 0 ) { // methzode erlaubte züge
      if(field(eingabe_x)(eingabe_y) == ' '){
        field(eingabe_x)(eingabe_y) = 'Y'
      }else{
        println("Dieser Zug ist nicht möglich. Bitte versuche es nochmal.\n" +
          "Set failed. Please make another cat jump.\n" +
          "R u a blind cat??? There is something else!!!")
      }
    }else{
      println("Dieser Zug ist nicht möglich. Bitte versuche es nochmal.\n" +
        "Set failed. Please make another cat jump.\n" +
        "What u imagine doesn´t exists...")
    }
  }




def move2(eingabe : String): Boolean = {
  //if(eingabe.length == getSpeed)
  var gelungen = true
  import scala.collection.mutable.ListBuffer
 var input = new ListBuffer[Char]()
  for (i <- 0 until eingabe.length) {
    if(!matchTest(eingabe.charAt(i))) {// teste gültigkeit
      return false
    }else{
      input += eingabe.charAt(i)
    }
    println(input)
  }
  val inputList = input.toList
  println(inputList)
gelungen
}

  def matchTest(q: Char): Boolean = q match {
    case 'a' => true
    case 'w' => true
    case 'd' => true
    case 's' => true
    case _ => false
  }


def move1(eingabe : String): Unit ={
  if(!move2(eingabe)){
    println("Du bist nicht du, wenn du hungrig bist. Falsche Eingabe")
  }else{println("Purrrrfect")}
}




  /*
  def move3(eingabe : String): Boolean = {
      var xE = 3
      var yE = 1
    field(xE)(yE) = 'O'
    var done = true
    println("hmmmmmmmmmm")
    if(move2(eingabe)==false){
      println("hmmmmmmmmmmllllllll")
      return false
    }else{
      println("hmpppmm" +eingabe.charAt(0))
      var bla = letter(eingabe.charAt(0))

      println("hmmmmmmmmpppmm"+bla+ eingabe.charAt(0))
    }
    done
  }



  def letter(q: Char): Array= q match {
    case 'a' => field(xE)(yE-1)
    case 'w' => field(xE-1)(yE)
    case 'd' => field(xE+1)(yE)
    case 's' => field(xE)(yE+1)
    case _ => 'e'
  }
*/
}

val f = new Field
//f.move3("s")
println(f)

