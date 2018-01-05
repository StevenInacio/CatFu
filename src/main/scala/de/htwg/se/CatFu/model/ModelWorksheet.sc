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

  var x: Int = 6
  var y: Int = 6
  val field = Array.ofDim[Char](x, y)

  def fillemptyField{
    for (i <- 0 to x - 1) {
      for (j <- 0 to y - 1) {
        field(i)(j) = ' '
      }
    }
  }

  override def toString(): String = {
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

  def fillrandomField{
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



}

val f = new Field
f.fillemptyField
println(f)
f.fillrandomField
println(f)
