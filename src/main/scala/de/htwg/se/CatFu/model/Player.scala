package de.htwg.se.CatFu.model

abstract case class Player() extends Thing {
  val name: String
  var currentXP = 0
  var lvl = 1
  var currentHP: Int

  def getMaxHP: Int
  def getAtt: Int
  def getDef: Int
  def getRange: Int
  def getSpeed: Int

  /**
   * Decrease this characters current HP. <br>
   * It ensures that the current HP is always between 0 and the max HP of this character.
   * @param dmg the damage that needs to be dealt to this character
   * @return the actual damage this character got.
   */
  def damage(dmg: Int): Int = {
    if (currentHP - dmg > getMaxHP) {
      val result: Int = currentHP - getMaxHP
      currentHP = getMaxHP
      result
    } else if (currentHP - dmg < 0) {
      val result = currentHP
      currentHP = 0
      result
    } else {
      currentHP -= dmg
      dmg
    }
  }

  /**
   * Increases the characters current HP
   * For this it uses the damage method with a negative value.
   * @param amount how much the character should be healed.
   * @return The actual hp healed.
   */
  def heal(amount: Int): Int = {
    -damage(-amount) // negative damage returns a negative result which needs to be negated again to get a positive return
  }

  /**
   * Sets the character to the level and calculate the stat increase.
   * The actual stat increase is role dependent so it's only abstract.
   * @param level the level the character should be.
   */
  def setLvl(level: Int)

  /**
   * Calculates a hit or miss and damages the enemy player accordingly.
   * @param target The character you want to attack.
   * @return The damage you did, 0 if you missed or -1 if the enemy character is already dead.
   */
  def attack(target: Player): Int = {
    if (target.currentHP == 0) {
      -1
    } else {
      val random = new scala.util.Random
      // scalastyle:off magic.number
      var dmg = 0
      if (hitrate(target) > random.nextInt(100)) {
        dmg = target.damage(random.nextInt(this.getAtt - lvl) + lvl)
        target.currentHP match {
          case 0 => this.increaseXP((target.lvl / 2) * 5)
          case _ => this.increaseXP(target.lvl / 2)
        }
      }
      dmg
      // scalastyle:on magic.number
    }
  }

  /**
   * Increase the characters XP and levels it up, if the xp limit is reached.
   * The character can level up more than once, if the xp limit is reached multiple times.
   * @param xp The XP the character gets.
   * @return True, if the character leveled up in the process.
   */
  def increaseXP(xp: Int): Boolean = {
    currentXP += xp
    var result = false
    while (currentXP >= xpToLvlUp) {
      currentXP -= xpToLvlUp
      this.setLvl(lvl + 1)
      result = true
    }
    result
  }

  /**
   * Calculate the hitrate of this character to hit the target character.
   * @param target The enemy player, this character wants to hit.
   * @return The hitrate between 0 and 100 (percent)
   */
  def hitrate(target: Player): Int = {
    // scalastyle:off magic.number
    (100.0 - ((target.getDef.toDouble / this.getAtt.toDouble) * 10.0)).toInt match {
      case x if x >= 100 => 100
      case x if x < 0 => 0
      case x => x
    }
    // scalastyle:on magic.number
  }

  /**
   * Calculates the XP to level up.
   * @return XP to the next level
   */
  def xpToLvlUp: Int = {
    10 + 5 * lvl ^ 2
  }

  /**
   * Shows all attributes of the character and gives back a String.
   * @return A full description of the Character.
   */
  def fullDescription: String = {
    var s: String = toString + "\n"
    s += "XP: " + currentXP + "/" + xpToLvlUp + "\n"
    s += "HP: " + currentHP + "/" + getMaxHP + "\n"
    s += "Attack: " + getAtt + "\n"
    s += "Defense: " + getDef + "\n"
    s += "Speed: " + getSpeed + "\n"
    s += "Range: " + getRange + "\n"
    s
  }
}

class Mage(val name: String) extends Player() {
  override val display: Char = 'F'
  val base_hp: Int = 8
  val base_att: Int = 0
  val base_def: Int = 0
  private var range: Int = 3
  private var hp = base_hp + 4
  private var att = base_att + 3
  private var defense = base_def + 2
  override var currentHP: Int = hp
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

class Warrior(val name: String) extends Player() {
  override val display: Char = 'P'
  val base_hp: Int = 9
  val base_att: Int = 0
  val base_def: Int = 1
  private var hp = base_hp + 6
  private var att = base_att + 2
  private var defense = base_def + 4
  override var currentHP: Int = hp
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
