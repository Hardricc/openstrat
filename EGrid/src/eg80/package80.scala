/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pEarth._, prid.phex._

/** Hex tile grids for Earth with a hex scale of 80km, a C scale of 20km. */
package object eg80
{
  val fullTerrs: RArr[Long80Terrs] = RArr(Terr80E0, Terr80E30, null, null, null, null, null, null, null, null, null, null)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid80LongMulti): HCenLayer[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).hCenLayerSpawn(ft.grid, ft.terrs)
  }.combine

  def fullTerrsSideOptLayerSpawn(implicit subSys: EGrid80LongMulti): HSideOptLayer[WSide] = {
    val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sideOptsFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid80LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).cornerLayerSpawn(ft.grid, ft.corners)
  }.combine
}