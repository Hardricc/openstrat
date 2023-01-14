/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, pEarth._

/** 2 Grid system for 0E and 30E */
object Grids160S0E1 extends EGrid160LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] = EGrid160.grids(2, 0, 280)
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

/** Scenario for 2 Grid system for 0E and 30E */
object Scen160s0e1 extends EScenLongMulti
{ override val title: String = "320km 0E - 30E"
  override implicit val gridSys: EGrid160LongMulti = Grids160S0E1
  override lazy val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override lazy val sTerrs: HSideBoolLayer = fullTerrsSideBoolLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}