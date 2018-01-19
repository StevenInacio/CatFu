package de.htwg.se.CatFu.model

abstract class Thing() {
  val display: Char
  val color: String
}

case class Empty() extends Thing() {
  override val display: Char = ' '
  override val color: String = Console.WHITE
}