package de.htwg.se.CatFu.model

case class Obstacle() extends Thing {
  override val display: Char = 'X'

  override def toString: String = "A Rock"
}
