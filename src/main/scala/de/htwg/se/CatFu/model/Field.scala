class Field {
  var anzahlzeichenderEingabe = 0
  var x: Int = 4
  var y: Int = 4
  var xE = 3
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
  def prueftGueltigkeit(eingabe : String): Boolean = {
    anzahlzeichenderEingabe = 0
    //if(eingabe.length == getSpeed)
    var gelungen = true
    import scala.collection.mutable.ListBuffer
    var input = new ListBuffer[Char]()
    for (i <- 0 until eingabe.length) {
      if(!matchTest(eingabe.charAt(i))) {// teste gültigkeit
        return false
      }else{
        anzahlzeichenderEingabe += 1
        input += eingabe.charAt(i)
      }
      println(input)
    }
    val inputList = input.toList
    println(inputList)
    println("Eingelesene Zeichen  :"+anzahlzeichenderEingabe)
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


  def vorunsfrei(zeichen: Char): Boolean = {
    var zeichenvoruns = letter(zeichen)
    if (zeichenvoruns != ' ') {
      println("Halt, vor uns liegt :" + zeichenvoruns)
      false
    } else {
      println("Test vor uns liegt :" + zeichenvoruns)
      true
    }
  }

  def nochImFeld(zeichen: Char): Boolean ={

    if (zeichen == 'a') {
      if (yE - 1 < y && yE - 1 >= 0) {
        return true
      }
    }
    if (zeichen == 'w') {
      if (xE-1 < x && xE-1 >= 0) {
        return true
      }
    }
    if (zeichen == 's') {
      if (yE+1 < y && yE+1 >= 0) {
        return true
      }
    }
    if (zeichen == 'd') {
      if (xE+1 < x && xE+1 >= 0) {
        return true
      }
    }

    false
  }

  def verandern(){}

  // verknüpfen mit move2 weil das eine liste erstellt
  def realmove(eingabe: String): Int = { // unbedingt clear zuerst
    var person = 'O'
    field(xE)(yE) = person
    println("lets start")
    if (!prueftGueltigkeit(eingabe)) {
      println("falsche eingabe")
      return 1
    } else {
      var i = 0
      println("Eingabe richtig")


      for (i <- 0 until anzahlzeichenderEingabe) {
        var zeichen = eingabe.charAt(i)
        if(!nochImFeld(zeichen)){return 2}
        if(!vorunsfrei(zeichen)){
          return 3
        }


        if (zeichen == 'a') {
          field(xE)(yE - 1) = person
          field(xE)(yE) = ' '
          yE -= 1
          println("a")
        }
        if (zeichen == 'w') {
          field(xE - 1)(yE) = person
          field(xE)(yE) = ' '
          xE -= 1
          println("w")
        }
        if (zeichen == 's') {
          field(xE)(yE + 1) = person
          field(xE)(yE) = ' '
          yE += 1
          println("s")
        }
        if (zeichen == 'd') {
          field(xE + 1)(yE) = person
          field(xE)(yE) = ' '
          xE += 1
          println("d")
        }

        println(i + "ja" + anzahlzeichenderEingabe)


      }
    }
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

