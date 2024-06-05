/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e150(90)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIceWinter),
      TRow(142, SeaIceWinter),
      VRow(141, OrigRt(5632, HVDR, 7, siceWin), OrigLt(5634, HVUL, 7, siceWin)),
      TRow(140, tundra, SeaIceWinter),
      TRow(138, tundra, lakesTundra),
      TRow(136, mtainTundra, hillyTaiga, mtainTundra),
      VRow(135, OrigLt(5636, HVDn)),
      TRow(134, mtainTundra * 3),
      VRow(133, OrigRt(5630, HVDn, 7), BendIn(5634, HVDR, 13), Bend(5636, HVUL, 11, 5), Bend(5638, HVDR, 5, 1, siceWin)),
      TRow(132, mtainTaiga, sea, hillyTaiga),
      VRow(131, OrigLt(5628, HVUR, 7), BendIn(5630, HVUL, 13), OrigRt(5634, HVUp), VertRightsLeft(5638, siceWin, 6), BendIn(5640, HVDL, 10, siceWin)),
      TRow(130, mtainTaiga, sea * 2, hillyTaiga),
      VRow(129, OrigRt(5630, HVDn, 7), OrigLt(5638, HVUR, 7, siceWin), BendIn(5640, HVUL, 13, siceWin)),
      TRow(128, mtainTaiga),
      VRow(127, BendMin(5628, HVDR), BendIn(5630, HVUL, 7)),
      TRow(126, mtainTaiga),
      VRow(125, OrigLt(5626, HVUR, 7), ThreeUp(5628, 0, 6, 9), BendIn(5630, HVDL)),
      TRow(124, sea, mtainTaiga),
      VRow(123, OrigRt(5622, HVDL, 7), BendIn(5624, HVDR), BendIn(5626, HVDn), ThreeDown(5628, 6, 0, 13), BendIn(5630, HVUL, 12)),
      TRow(122, hillyOce),
      VRow(121, BendOut(5622, HVUp), BendOut(5624, HVUL), BendOut(5626, HVDR), BendIn(5628, HVUL, 13)),
      TRow(120, hillyOce),
      VRow(119, BendIn(5626, HVUL, 10)),
      VRow(117, OrigMin(5620, HVDL), OrigRt(5620, HVDL, 7)),
      VRow(101, BendIn(5618, HVDL, 13)),
      VRow(99, OrigMin(5618, HVUp)),
      TRow(98, hillyJungle * 2),
      VRow(97, BendOut(5618, HVDL, 7)),
      TRow(96, jungle * 2, mtainDepr),
      VRow(95, BendIn(5618, HVUR, 13), OrigRt(5620, HVUL)),
      TRow(94, sea * 2, mtainDepr),
    )
  }
  help.run
}