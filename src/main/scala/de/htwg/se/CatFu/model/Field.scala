class Field {
  var inputlength = 0
  var person = 'O'
  var x: Int = 4
  var y: Int = 4
  var xold = 3
  var yold = 1
  var xE = 3  //current position
  var yE = 1
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


  //prüft, ob die Eingabe gültig war vong String her
  def isvalid(userinput : String): Boolean = {
    inputlength = 0
    //if(eingabe.length <= getSpeed) geht
    var wentwell = true
    import scala.collection.mutable.ListBuffer
    var input = new ListBuffer[Char]()
    for (i <- 0 until userinput.length) {
      if(!matchTestValidInputSpace(userinput.charAt(i))) {// teste gültigkeit
        println("falsche eingabe")
        field(xE)(yE)=' '
        field(xold)(yold) = person
        return false
      }else{
        println("Eingabe richtig")
        inputlength += 1
        input += userinput.charAt(i)
        realmove(userinput.charAt(i))
      }
    }
    val inputList = input.toList
    println(inputList)
    println("Eingelesene Zeichen  :"+inputlength)
    wentwell
  }

  def matchTestValidInputSpace(q: Char): Boolean = q match {
    case 'a' if yE - 1 < y && yE - 1 >= 0   => true
    case 'w' if xE-1 < x && xE-1 >= 0       => true
    case 'd' if xE+1 < x && xE+1 >= 0       => true
    case 's' if yE+1 < y && yE+1 >= 0       => true
    case _ => false
  }

  def move1(eingabe : String): Unit ={
    if(!isvalid(eingabe)){
      println("Du bist nicht du, wenn du hungrig bist. Falsche Eingabe")
    }else{println("Purrrrfect")}
  }


  def vorunsfrei(zeichen: Char): Boolean = {  //spike
    var zeichenvoruns = letter(zeichen)
    if (zeichenvoruns != ' ') {
      println("Halt, vor uns liegt :" + zeichenvoruns)
      false
    } else {
      println("Test vor uns liegt :" + zeichenvoruns)
      true
    }
  }

  // verknüpfen mit move2 weil das eine liste erstellt
  def realmove(input: Char): Int = { // unbedingt clear zuerst
    field(xE)(yE) = person
    println("lets start")
    if(!vorunsfrei(input)){
      return 3
    }
    if (input == 'a') {
      field(xE)(yE - 1) = person
      field(xE)(yE) = ' '
      yE -= 1
      println("a")
    }
    if (input == 'w') {
      field(xE - 1)(yE) = person
      field(xE)(yE) = ' '
      xE -= 1
      println("w")
    }
    if (input == 's') {
      field(xE)(yE + 1) = person
      field(xE)(yE) = ' '
      yE += 1
      println("s")
    }
    if (input == 'd') {
      field(xE + 1)(yE) = person
      field(xE)(yE) = ' '
      xE += 1
      println("d")
    }

    println( "ja" + inputlength)

    println("funktioniert")
    42
  }


  //Keine Wiederholungen und deswegen das match erweitern

  def letter(q: Char): Char= q match {
    case 'a' => field(xE)(yE-1)
    case 'w' => field(xE - 1)(yE);
    case 'd' => field(xE+1)(yE)
    case 's' => field(xE)(yE+1)
    case _ => 'e'
  }

}