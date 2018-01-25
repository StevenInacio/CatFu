package de.htwg.se.CatFu

import de.htwg.se.CatFu.logic._
import de.htwg.se.CatFu.model.Mage
import de.htwg.se.CatFu.view.CatFuGUI

object CatFu {

  val gui: CatFuGUI.type = CatFuGUI

  def main(args: Array[String]): Unit = {
    val p = new Mage("Name", Console.WHITE)
   println( p.fullDescription
   )// Controller.menu()
  }
}

//undo should get a "last called function" but then turn parameters around accordingly.
