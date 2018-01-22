package de.htwg.se.CatFu.model

abstract class Thing() {
  val display: Char
  val color: String
}

case class Empty(override val color: String) extends Thing() {
  override val display: Char = ' '
}