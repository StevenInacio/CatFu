import de.htwg.se.CatFu.model._

val pawn: Player = new Mage("Whiskers")

val obs1 = Obstacle()

pawn.setLvl(100) // scalastyle:ignore

val enemy: Player = new Warrior("Mittens")

println(pawn.hitrate(enemy))

