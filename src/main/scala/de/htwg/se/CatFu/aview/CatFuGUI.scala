package de.htwg.se.CatFu.aview

import de.htwg.se.CatFu.logic.PlayerManagement
import de.htwg.se.CatFu.model._
import scala.swing._
import java.awt._

//noinspection ScalaStyle
class CatFuGUI extends Frame {
  var board = new Field()
  var butt = new Button()


  board.clearField()
  board.setRandomObstacle()
  val p  = PlayerManagement.randomTeam()
  val e  = PlayerManagement.enemyTeam(p)
  board.setUpTeams(p, e)


  preferredSize = new Dimension(850, 700)
  visible = true
  title = "CatFu"

  contents = new BorderPanel {
    //add(new Button("North"), BorderPanel.Position.North)
    //add(new Button("Center"), BorderPanel.Position.Center)
    //add(new Button("East"), BorderPanel.Position.East)
    //add(new Button("West"), BorderPanel.Position.West)
    add(Button("South: Close") {
      sys.exit(0)
    }, BorderPanel.Position.South)


    add(c = new GridPanel(13, 13) {
      var block = true
      contents += new Label("     ")
      for (i <- 0 until 12) {
          for (k <- 0 until board.xfield) {
          if(block){contents += new Label("  " + k + "  ")
          }
          block = false

        contents += new Label("  " + i + "  ")
        for (j <- 0 until board.yfield) {
          foreground = Color.blue
          background = Color.green

          butt.background = Color.red
          //contents += butt
          contents += new Button() {
            background = Color.orange
            foreground = Color.black
            var figure = board.getDisplay(i, j).toString
            text = figure

          }
          //i ist x ist zeile
          //j = y = spalte
        }
      }
      repaint()

    }, l = BorderPanel.Position.Center)
  }
