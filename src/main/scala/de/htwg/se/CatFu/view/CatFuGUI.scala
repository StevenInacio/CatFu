package de.htwg.se.CatFu.view

import de.htwg.se.CatFu.logic._
import de.htwg.se.CatFu.model._

import de.htwg.util.Observer

object CatFuGUI extends Observer {

  val board = new Field()
  val p: List[Player] = PlayerManagement.randomTeam()
  val e: List[Player] = PlayerManagement.enemyTeam(p)
  board.clearField()
  board.setRandomObstacle()
  board.setUpTeams(p, e)

  def startDialog(): Unit = {
    new StartDialog
  }

  def helpDialog(): Unit = {
    new HelpDialog
  }

  def teamDialog(): Unit = {
    new TeamDialog
  }

  def loadCharacterDialog(): Unit = {
    new LoadCharacter
  }

  def createCharacterDialog(): Unit = {
    new CreateCharacter
  }

  def gameGUI(): Unit = {
    new GameGUI
  }

  def updateGameGUI(x: State.Value): Unit = {
    x match {
      case _ =>
    }
  }

  override def update: Unit = {
    Controller.state match {
      case x if x == State.Start => startDialog()
      case x if x == State.Help => helpDialog()
      case x if x == State.Team => teamDialog()
      case x if x == State.Load => loadCharacterDialog()
      case x if x == State.Create => createCharacterDialog()
      case x if x == State.Random || x == State.Done => gameGUI()
      case x: State.Value => updateGameGUI(x)
      case _ =>
    }
  }
}

//Catgui end
