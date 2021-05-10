/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait OneScen extends HexGridScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. */
  def oPlayers: HCenArrOpt[Player]

  /** Resolves turn. Takes a set of commands / orders, resolves them and returns the new game state scenario. */
  def doTurn(has: Arr[HexAndStep]): OneScen =
  { /** A mutable grid of data. The tile data is an Array buffer of [[HexAndStep]]s. */
    val resolve: HCenArrBuff[HexAndStep] = grid.newHCenArrBuff
    has.foreach{ hts => resolve.appendAt(hts.hc2, hts) }
    val resValue: HCenArrOpt[Player] = oPlayers.clone

    resolve.foreach { (r, b) => b match
      { case _ if b.length == 1 => resValue.mutMove(has.head.hc1, r)
        case _ =>
      }
    }
    OneScen(turn + 1, grid, resValue)
  }
}

/** Companion object for OneScen trait, cantains factory apply method. */
object OneScen
{ /** Factory apply method for OneScen trait. */
  def apply(turnIn: Int, gridIn: HGrid, opIn: HCenArrOpt[Player]): OneScen = new OneScen
  { override val turn = turnIn
    override implicit val grid: HGrid = gridIn
    override def oPlayers: HCenArrOpt[Player] = opIn
  }
}

/** This trait just puts the value 0 in for the turn. */
trait OneScenStart extends OneScen
{ override val turn: Int = 0
}

/** 1st example Turn 0 scenario state for Game One. */
object OneScen1 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 6, 2, 10)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
  oPlayers.setSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

/** 2nd example Turn 0 scenario state for Game One. */
object OneScen2 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 10, 4, 8)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
}