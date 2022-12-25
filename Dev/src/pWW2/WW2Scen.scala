/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWW2
import prid._, phex._, egrid._, eg320._, pEarth._

trait WW2Scen extends EScenFlat with HSysTurnScen
{
  def terrs: HCenLayer[WTile]
}

object WW2Scen1 extends WW2Scen
{ override def turn: Int = 0

  override implicit def gridSys: HGridSys = Grids320S0E11
  override val terrs: HCenLayer[WTile] = Scen320S0E11.terrs
  override def sTerrs: HSideBoolLayer = Scen320S0E11.sTerrs
}