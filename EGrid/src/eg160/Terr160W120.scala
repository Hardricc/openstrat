/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 120 west. */
object Terr160W120 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w120(312, 322)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners) {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(320, sea * 3, tundraHills * 2),
      TRow(318, sea, tundra * 2, sea * 2),
      TRow(316, sea, tundra * 2, tundraHills * 3),
      TRow(314, sea * 2, tundra * 4),
      TRow(312),
    )
  }
  help.run
}