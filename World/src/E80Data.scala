/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pGrid._

trait E80Data
{ implicit val grid: HexGridIrrOld
  def terrs: TilesArr[WTile]
  def sTerrs: SideBooleans
  def vTerrs: VertInts
}

object EuropeNWGrid extends HexGridIrrOld(446, EGrid80KmOld.getBounds(200, 446, 540))

object EuropeNEGrid extends HexGridIrrOld(446, EGrid80KmOld.getBounds(400, 446, 540))