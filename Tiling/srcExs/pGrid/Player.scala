/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, Colour._

/** A Player has a very simple token with a letter and colour for recognition." */
case class RPlayer(value: Player, rd: Roord) extends TileMem[Player]

case class Player(val char: Char, val colour: Colour)
{ override def toString = "Player " + char
}

object Player
{
  implicit val persistPlayer: Persist[Player] = Persist2[Char, Colour, Player]("Player", "char", _.char, "colour", _.colour, apply)
}
object PlayerA extends Player('A', Red)
object PlayerB extends Player('B', Orange)
object PlayerC extends Player('C', Green)
object PlayerD extends Player('D', Violet)

