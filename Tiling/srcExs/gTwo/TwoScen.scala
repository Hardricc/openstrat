/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import prid._

/** Scenario trait for Game Two. */
trait TwoScen extends SqGridScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. this is the same as Game one. */
  def oPlayers: SqCenArrOpt[Player]

  def doTurn(sat: Arr[SqAndStep]): TwoScen =
  {
    val resolve: SqCenArrBuff[SqAndStep] = grid.newTileBuffArr
    sat.foreach{sat => resolve.appendAt(sat.sc2, sat) }
    val resValue: SqCenArrOpt[Player] = oPlayers.clone

    resolve.foreach { (r, b) => b match
    { case _ if b.length == 1 => resValue.mutMove(sat.head.sc1, r)
      case _ =>
    } }
    TwoScen(turn + 1, grid, resValue)
  }
}

/** Companion object for TwoScen trait, contains factory apply method. */
object TwoScen
{ /** Apply factory method for TwoScen game. */
  def apply(turnIn: Int, gridIn: SqGrid, opIn: SqCenArrOpt[Player]): TwoScen = new TwoScen
  { override val turn = turnIn
    override implicit val grid: SqGrid = gridIn
    override def oPlayers: SqCenArrOpt[Player] = opIn
  }
}

/** This trait just puts the value 0 in for the turn. */
trait TwoScenStart extends TwoScen
{ override val turn: Int = 0
}

object TwoScen1 extends TwoScenStart
{ implicit val grid = SqGrid(2, 8, 2, 10)
  val oPlayers: SqCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
  oPlayers.setSomes((4, 6, PlayerB), (6, 8, PlayerC))
}