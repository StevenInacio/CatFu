package de.htwg.se.CatFu.model

abstract class Thing() {
  val display: Char
}

case class Empty() extends Thing() {
  override val display: Char = ' '
}
