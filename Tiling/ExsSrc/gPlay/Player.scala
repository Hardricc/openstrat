/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gPlay
import prid._, phex._, Colour._

/** A Player has a very simple token with a letter and colour for recognition. */
case class Player(char: Char, colour: Colour) extends Show2[Char, Colour]
{ override def typeStr: String = "Player"
  override def show1: Char = char
  override def show2: Colour = colour
  override implicit def showT1: ShowT[Char] = ShowT.charImplicit
  override implicit def showT2: ShowT[Colour] = Colour.persistImplicit
  override def name1: String = "char"
  override def name2: String = "colour"
  override def str: String = "Player" + char
  def charStr: String = char.toString
  override def show(style: ShowStyle): String = "Player" + char
  override def syntaxDepth: Int = 1
  def hSteps(steps: HStep*): (Player, HStepArr) = (this, steps.toArr)
  def hPath(r: Int, c: Int, steps: HStep*): HDirnPathPair[Player] = HDirnPathPair[Player](this, r, c, steps:_*)
  def hPath(hCen: HCen, steps: HStep*): HDirnPathPair[Player] = HDirnPathPair[Player](this, hCen, steps:_*)
}

/** Companion object for Player case class contains implicit instance for Persist. */
object Player
{ /* Implicit [[ShowT]] instance / evidence for [[Player]]. */
  implicit val showTEv: Show2T[Char, Colour, Player] = ShowShow2T[Char, Colour, Player]("Player", "char", "colour")
}

object PlayerA extends Player('A', Red)
object PlayerB extends Player('B', Orange)
object PlayerC extends Player('C', Pink)
object PlayerD extends Player('D', Violet)

/** A class identifying a Player and a hex coordinate position. */
case class HPlayer(hc: HCen, value: Player) extends HexMemShow[Player]
{ override def typeStr: String = "HPlayer"
  override def name2: String = "player"
  override implicit def showT2: ShowT[Player] = Player.showTEv
  override def syntaxDepth: Int = 2
}