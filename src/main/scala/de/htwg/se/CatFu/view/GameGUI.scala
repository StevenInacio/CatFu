package de.htwg.se.CatFu.view

import de.htwg.se.CatFu.model.Thing
import de.htwg.se.CatFu.view.CatFuGUI.board

import scala.swing._
import java.awt.Color

class GameGUI extends Frame {
  preferredSize = new Dimension(1300, 600)
  visible = true
  title = "CatFu"

  contents = new BorderPanel {

    add(new GridPanel(1, 1) {

      contents += new FlowPanel {
        background = Color.black
        val textArea: TextArea = new TextArea {
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

    add(new GridPanel(board.xfield + 1, board.yfield + 1) {
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
            var t: Thing = board.getInstance(i, j)
            var color: String = ""
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

class HelpDialog extends Frame {

}

class StartDialog extends Frame {

}

class TeamDialog {

}

class LoadCharacter {

}

class CreateCharacter {

}