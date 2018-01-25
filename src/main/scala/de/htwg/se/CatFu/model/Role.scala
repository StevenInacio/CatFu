package de.htwg.se.CatFu.model

class Mage(val name: String, override val color: String) extends Player() {
  override val display: Char = 'F'
  val base_hp: Int = 8
  val base_att: Int = 0
  val base_def: Int = 0
  override var currentHP: Int = hp
  private var range: Int = 3
  private var hp = base_hp + 4
  private var att = base_att + 3
  private var defense = base_def + 2
  private var speed = 3

  def setLvl(level: Int): Unit = {
    lvl = level
    hp = base_hp + 4 * lvl
    att = base_att + 3 * lvl
    defense = base_def + 2 * lvl
    // scalastyle:off magic.number
    level match {
      case x if x < 10 =>
        range = 3
        speed = 3
      case x if x < 20 =>
        range = 4
        speed = 4
      case _ =>
        range = 5
        speed = 4
    }
    // scalastyle:on magic.number
    currentHP = hp
  }

  def getMaxHP: Int = hp

  def getAtt: Int = att

  def getDef: Int = defense

  def getRange: Int = range

  def getSpeed: Int = speed

  override def toString: String = name + ", the level " + lvl + " Fleazard"
}

class Warrior(val name: String, override val color: String) extends Player() {
  override val display: Char = 'P'
  val base_hp: Int = 9
  val base_att: Int = 0
  val base_def: Int = 1
  override var currentHP: Int = hp
  private var hp = base_hp + 6
  private var att = base_att + 2
  private var defense = base_def + 4
  private var speed = 4 // scalastyle:ignore

  def setLvl(level: Int): Unit = {
    lvl = level
    hp = base_hp + 6 * lvl
    att = base_att + 2 * lvl
    defense = base_def + 4 * lvl
    // scalastyle:off magic.number
    level match {
      case x if x < 10 => speed = 4
      case x if x < 15 => speed = 5
      case x if x < 20 => speed = 6
      case _ => speed = 7
    }
    // scalastyle:on magic.number
    currentHP = hp
  }

  def getMaxHP: Int = hp

  def getAtt: Int = att

  def getDef: Int = defense

  def getRange: Int = 1

  def getSpeed: Int = speed

  override def toString: String = name + ", the level " + lvl + " Pawrior"
}

object NoPlayer extends Player {
  override val name: String = ""
  override val display: Char = '\u0000'
  override var currentHP: Int = 0

  override def getMaxHP: Int = 0

  override def getAtt: Int = 0

  override def getDef: Int = 0

  override def getRange: Int = 0

  override def getSpeed: Int = 0

  /**
    * Sets the character to the level and calculate the stat increase.
    * The actual stat increase is role dependent so it's only abstract.
    *
    * @param level the level the character should be.
    */
  override def setLvl(level: Int): Unit = {}
}
