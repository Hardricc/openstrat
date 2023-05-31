/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package h1p
import prid._, phex._, gPlay._

/** Simple Game manager for [[G1HScen]]. Contains the Scenario and sequence of counters controlled by the single GUI player. */
case class G1HGame(var scen: G1HScen, guiCounters: RArr[Counter])
{ /** Resolves turn. Takes an [[HStepPairArr]] of [[Counter]]s. The directives are passed in as relative moves. This is in accordance with the
   *  principle in more complex games that the entity issuing the command may not know its real location. */
  def endTurn(directives: HCenStepPairArr[Counter]): G1HScen =
  { val intensions = HCenOptStepLayer[Counter](scen.gridSys)
    directives.pairForeach { (hcst, ct) => if (guiCounters.contains(ct)) intensions.setSome(hcst.startHC, hcst.step, ct) }
    val countersNew = scen.resolve(intensions)
    val newScen = G1HScen(scen.turn + 1, scen.gridSys, countersNew)
    scen = newScen
    scen
  }
}