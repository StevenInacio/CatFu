package de.htwg.se.CatFu.aview

import de.htwg.se.CatFu.logic.PlayerManagement
import de.htwg.se.CatFu.model.Field
import scala.swing._
import java.awt.Color

//noinspection ScalaStyle
class CatFuGUI extends Frame {
  var board = new Field()
  var butt = new Button()
  

  board.clearField()
  board.setRandomObstacle()
  val p = PlayerManagement.randomTeam()
  val e = PlayerManagement.enemyTeam(p)
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

}

/*

contents = new GridPanel(12, 12) {
  for (i <- 0 until 12) {
    for (j <- 0 until 12) {
      contents += new Button(i + "-" + j)
      //i ist x ist zeile
      //j = y = spalte
    }
  }
}
contents = new BoxPanel(Orientation.Vertical) {
  contents += new Label("Look at me!")
  contents += Button("Press me, please") {
    println("Thank you")
  }
  contents += Button("Close") {
    sys.exit(0)
  }


}
object PanelThree {
def main(args: Array[String]) {
  val ui = new CatFuGUI
  ui.visible = true
}

*/

/*
contents = new GridBagPanel {
  def constraints(x: Int, y: Int,
                  gridwidth: Int = 1, gridheight: Int = 1,
                  weightx: Double = 0.0, weighty: Double = 0.0,
                  fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
  : Constraints = {
    val c = new Constraints
    c.gridx = x
    c.gridy = y
    c.gridwidth = gridwidth
    c.gridheight = gridheight
    c.weightx = weightx
    c.weighty = weighty
    c.fill = fill
    c
  }

  val la = new Label("Look at me!")
  la.foreground = Color.BLUE

  add(new Label("Catfu @ (0,0)") {border=Swing.EtchedBorder(Swing.Lowered) },
    constraints(0, 0, gridheight=2, fill=GridBagPanel.Fill.Both))
  add(new ToggleButton("Button     gg    @ (2,0)"),
    constraints(2, 0))
  add(new Button("Button @ (2,1)"),
    constraints(2, 1))
  add(new Button("Button @ (2,2)"),
    constraints(2, 2))
  add(new Button("Button @ (2,2)"),
    constraints(2, 3))
  add(new Button("Button @ (2,2)"),
    constraints(2, 4))
  add(new Button("Button @ (2,5)"),
    constraints(2, 5))
  add(new CheckBox("Check me!"),
    constraints(0, 2))
  add(new CheckBox("Check me!"),
    constraints(0, 3))
  add(new CheckBox("Check me!"),
    constraints(0, 4))
  add(new CheckBox("Check me!"),
    constraints(0, 5))
  add(new TextField { columns = 42 },
    constraints(1, 0, weightx=1.0, fill=GridBagPanel.Fill.Horizontal))
  add(new ScrollPane(new TextArea),
    constraints(1, 1, gridheight=3, weighty = 1.0,
      fill=GridBagPanel.Fill.Both))
  add(Button("Close") { sys.exit(0) },
    constraints(0, 4, gridwidth=3, fill=GridBagPanel.Fill.Horizontal))
  border = Swing.EmptyBorder(10, 10, 10, 10)
}

def pressMe() {
  Dialog.showMessage(contents.head, "Thank you!", title="You pressed me")
}


object PanelFour {
def main(args: Array[String]) {
  val ui = new CatFuGUI
  ui.visible = true
}

*/


/*


  var boardPanel: GridPanel = new GridPanel(12,12)
  var containerPanel_1: BoxPanel = new BoxPanel(Orientation.Vertical) {}
  var containerPanel_2: BoxPanel = new BoxPanel(Orientation.Vertical) {}

  val width = 300
  val height = 300

  contents = new GridBagPanel {
  def constraints (x: Int, y: Int,
  gridWidth: Int = 1, gridHeight: Int = 1,
  weightX: Double = 1.0, weightY: Double = 1.0,
  fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.Both,
  anchor: Anchor.Value = Anchor.Center): Constraints = {
  val c = new Constraints
  c.gridx = x
  c.gridy = y
  c.gridwidth = gridWidth
  c.gridheight = gridHeight
  c.weightx = weightX
  c.weighty = weightY
  c.fill = fill
  c.anchor = anchor
  c
}

  add (containerPanel_1, constraints (0, 0, gridHeight = 3, weightX = 0.0, anchor = Anchor.NorthEast) )
  add (boardPanel, constraints (1, 0, gridHeight = 3) )
  add (containerPanel_2, constraints (2, 0, gridHeight = 3, weightX = 0.0, anchor = Anchor.SouthWest) )
  /*


    var highlightedPiece: (Int, Int) = (-1, -1)
    //var containerPiece: PieceInterface = PieceFactory.getEmptyPiece
    //noinspection ScalaStyle
    val boardColor: java.awt.Color = getColorFromRGB(255, 235, 182)
    //noinspection ScalaStyle
    val pieceColor: java.awt.Color = getColorFromRGB(249, 250, 242)
    val markedColor: java.awt.Color = java.awt.Color.BLUE
    //noinspection ScalaStyle
    val containerBorderColor: Color = getColorFromRGB(153, 51, 0)
    //noinspection ScalaStyle
    val containerBackgroundColor: Color = getColorFromRGB(246, 217, 157)
   // val resourcesPath: String = new File(".").getCanonicalPath + "/src/main/scala/de/htwg/se/ShoShogi/zresources"
   // val backgroundPath: String = resourcesPath + "/images/background.jpg"

   // def getBoardArray: Array[Array[PieceInterface]] = controller.boardToArray()
    object Panels extends Enumeration {
      type EnumType = Value
      val boardP, containerP_1, containerP_2, All = Value
    }



    def getColorFromRGB(r: Int, g: Int, b: Int): Color = {
      val ret: Array[Float] = Array.ofDim[Float](3)
      java.awt.Color.RGBtoHSB(r, g, b, ret)
      java.awt.Color.getHSBColor(ret(0), ret(1), ret(2))
    }

    minimumSize = new Dimension(width, height)
    maximize()



    */



  def closeOperation () {
  //noinspection ScalaStyle
  Dialog.showConfirmation (
  parent = null,
  title = "Exit",
  message = "Are you sure you want to quit?"
  ) match {
  case Dialog.Result.Ok => System.exit (0)
  case _ => ()
}
}


}


*/


/*
    val label = new Label {
      text = "I'm a big label!."
      font = new Font("Ariel", java.awt.Font.ITALIC, 24)
      visible = true
    }
    val button = new Button {
      text = "Throw!"
      foreground = blue
      background = red
      borderPainted = true
      enabled = true
      tooltip = "Click to throw a dart"
    }
    val toggle = new ToggleButton { text = "Toggle" }
    val checkBox = new CheckBox { text = "Check me" }
    val textField = new TextField {
      columns = 10
      text = "Click on the target!"
    }
    val textArea = new TextArea {
      text = "initial text\nline two"
      background = Color.green
    }

    val gridPanel = new GridPanel(1, 2) {
      contents += checkBox
      contents += label
      contents += textArea
    }


*/


/*
var containerPanel_1: BoxPanel = new BoxPanel(Orientation.Vertical) {}
var containerPanel_2: BoxPanel = new BoxPanel(Orientation.Vertical) {}
var highlightedPiece: (Int, Int) = (-1, -1)

val markedColor: java.awt.Color = java.awt.Color.BLUE



// declare Components here
val label = new Label {
  text = "I'm a big label!."
  font = new Font("Ariel", java.awt.Font.ITALIC, 24)
}
}*/






