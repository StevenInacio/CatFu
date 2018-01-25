package de.htwg.se.CatFu.view


import de.htwg.se.CatFu.logic.PlayerManagement
import de.htwg.se.CatFu.model._
import scala.swing._
import java.awt.Color

//noinspection ScalaStyle
class CatFuGUI extends Frame {

  val board = new Field()
  val p: List[Player] = PlayerManagement.randomTeam()
  val e: List[Player] = PlayerManagement.enemyTeam(p)
  board.clearField()
  board.setRandomObstacle()
  board.setUpTeams(p, e)


  preferredSize = new Dimension(600, 500)
  visible = true
  title = "CatFu"

  contents = new BorderPanel {

    add(new GridPanel(6, 6) {
     var buttonButtons : BorderPanel = new BorderPanel {
       add(new Button() {
         text = "Move"
       }, BorderPanel.Position.West)
       add(new Button() {
         text = "Attack"
       }, BorderPanel.Position.West)
       add(new Button() {
         text = "Cancel"
       }, BorderPanel.Position.West)
       add(new Button() {
         text = "End Turn"
       }, BorderPanel.Position.West)
       add(new Button() {
        text = "Help"
      }, BorderPanel.Position.West)
      add(new Button() {
        text = "Exit"
        //sys.exit(0)
      }, BorderPanel.Position.South)
      }
    }, BorderPanel.Position.South)


    add(new GridPanel(13, 13) {
      var block = true
      contents += new Label("     ")
      for (i <- 0 until 12) {
        if (block) {
          for (k <- 0 until board.xfield) {
            contents += new Label("  " + k + "  ")
          }
          block = false
        }
        contents += new Label("  " + i + "  ")
        for (j <- 0 until board.yfield) {
          foreground = Color.blue
          background = Color.green
          var butt = new Button()
          butt.background = Color.red
          //contents += butt
          contents += new Button() {
            background = Color.orange
            foreground = Color.black
            val figure: String = board.getDisplay(i, j).toString
            text = figure
          } // button end
        } // for j
      } // i for 12
      repaint()
    }, l = BorderPanel.Position.Center) //gridpanel end
  } //borderpanel end

}

//Catgui end

