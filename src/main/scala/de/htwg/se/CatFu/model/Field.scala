package de.htwg.se.CatFu.model

class Field {

  var x: Int = 4
  var y: Int = 9
  val field = Array.ofDim[Char](x, y)

  def fillemptyField{
    for (i <- 0 to x - 1) {
      for (j <- 0 to y - 1) {
        field(i)(j) = 'o'
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
}

