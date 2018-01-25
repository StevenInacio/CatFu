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


  preferredSize = new Dimension(1300, 600)
  visible = true
  title = "CatFu"

  contents = new BorderPanel {

    add(new GridPanel(1, 1) {

      contents += new FlowPanel {
        background = Color.black
        val textArea = new TextArea {
          text = "hier\nkönnte\nihre\nwerbung\nstehen\n\n...\nalles mit backslash n"
          background = Color.green
          foreground = Color.BLUE
        }
        contents += textArea
      }

    }, BorderPanel.Position.East)

    add(new GridPanel(1, 1) {
      background = Color.blue
      contents += new FlowPanel {
        contents += Swing.HStrut(30)
        contents += new Button("Move")
        contents += new Button("Attack")
        contents += new Button("Cancel")
        contents += new Button("End Turn")
        contents += new Button("Help")
        contents += Button("Exit") {
          sys.exit(0)
        }
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
          background = Color.green
          var butt = new Button()
          butt.background = Color.red
          //contents += butt
          contents += new Button() {
            background = Color.orange
            //TODO : Print Things in their colors
            var t : Thing = board.getInstance(i,j)
            var color : String = ""
            color = board.getColor(t)
            //foreground(color)
            //color = board.getColor(i,j)
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