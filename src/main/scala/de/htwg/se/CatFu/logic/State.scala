package de.htwg.se.CatFu.logic

object State extends Enumeration {
  type EnumType = Value
  val Init, Start, Help, Team, Load, Create, Random, Done, PlayerTurn, EnemyTurn, SelectActivePlayer, DeselectActivePlayer, ActionMenu, Move, Attack = Value
}
