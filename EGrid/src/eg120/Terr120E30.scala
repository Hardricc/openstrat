/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._, phex._, egrid._, WTiles._

object Terr120E30 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e30(274, 286)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(286, hillySub, subtrop, mtainSub, mtainSubForest, mtainSavannah, mtainSavannah, sea, hillyOce, hillyOce * 13),
    VRow(285, BendIn(1504, HVUR, 13)),
    TRow(284, mtainSubForest, mtainSavannah, sea, hillyOce * 3, sea * 2, hillyOce * 13),
    TRow(282, mtainSavannah, sea * 2, hillyOce * 3, sea * 2, hillyOce * 13),
    TRow(280, sea * 4, hillyOce * 2, sea * 2, hillyOce * 14),
    TRow(278, sea * 5, hillyOce, sea * 9, hillyOce * 7),
    TRow(276, sea * 6, hillyOce * 2, sea * 7, hillyOce * 7),
    TRow(274, sea * 8, sea * 5, hillyOce, sea, hillyOce * 7),
    )
  }
  help.run
}