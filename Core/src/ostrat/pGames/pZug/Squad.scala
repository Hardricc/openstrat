/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._
import pStrat._

class Squad(val polity: Polity, var xCood: Int, var yCood: Int) extends Lunit
{
  var action: Action = NoAction
  def setMove(newMove: Cood *): Unit = action = Move(newMove.toList)
   val colour = polity.colour
   override def toString = "Squad" - (polity.toString).enParenth
   override def equals(other: Any): Boolean = other match
   {
      case that: Squad => polity == that.polity
      case _ => false
   }
   def canMove(tile: ZugTile): Boolean = tile.terr != Lake
   def terrCost(tile: ZugTile): Int = tile.terr match
   {
      case Plain => 4
      case WheatField => 6
      case StoneBuilding => 10 
      case Hill => 6
      case _ => 4
   }
}

object Squad
{
   def apply(polity: Polity, cood: Cood): Squad = new Squad(polity, cood.x, cood.y)
   def apply(polity: Polity, x: Int, y: Int): Squad = new Squad(polity, x, y)   
}

sealed trait Action
case class Move(coods: List[Cood]) extends Action
case class Fire(cood: Cood) extends Action
object NoAction extends Action