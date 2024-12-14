/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 460km.  */
object Terr460E60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e60(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(146, SeaIcePerm),
    VRow(145, BendIn(2558, HVDL, 8, SeaIceWinter)),
    TRow(144, SeaIceWinter),
    VRow(143, OrigLt(2558, HVUp, 7, SeaIceWinter), OrigRt(2560, HVUR, 7, SeaIceWinter), BendIn(2562, HVDn, 10, SeaIceWinter), ThreeDown(2564, 0, 13, 13, SeaIceWinter)),
    TRow(142, ice),
    VRow(141, BendOut(2562, HVDR, 7, SeaIceWinter), BendIn(2564, HVUL, 13, SeaIceWinter)),
    TRow(140, mtainTundra, tundra),

    VRow(139, ThreeDown(2556, 0, 13, 11, SeaIceWinter, SeaIceWinter, sea), BendIn(2558, HVDn, 13, SeaIceWinter), BendIn(2560, HVUp, 8, SeaIceWinter),
      Bend(2562, HVUL, 13, 4, SeaIceWinter)),

    TRow(138, taiga, tundra),
    TRow(136, taiga, hillyTaiga, taiga),
    TRow(134, taiga * 3),
    TRow(132, taiga, hillyTaiga, taiga),
    TRow(130, oceanic * 4),
    TRow(128, savannah, sahel * 2, savannah),
    TRow(126, sahel, sahel * 3),
    VRow(125, BendOut(2554, HVDR), OrigMin(2556, HVDL)),
    TRow(124, hillySavannah, sahel * 3, hillySavannah),
    VRow(123, Bend(2554, HVUR, 4, 7), BendIn(2556, HVDL, 13)),
    TRow(122, savannah, deshot, sahel, mtainSavannah * 2),
    VRow(121, OrigLt(2554, HVUR, 7), BendIn(2556, HVUL, 13)),
    TRow(120, hillySahel, hillySahel, hillyDeshot, mtainSavannah * 2),
    TRow(118, hillySahel, hillyDeshot * 4, savannah),
    VRow(117, OrigLt(2550, HVDn)),
    TRow(116, hillyDeshot, mtainSahel, hillyDeshot, hillySahel, hillyDeshot, deshot),
    VRow(115, BendIn(2550, HVUR, 13), BendIn(2552, HVUp, 10), BendIn(2554, HVDn, 13), Bend(2556, HVUp, 3, 3), BendIn(2558, HVDn, 13), BendIn(2560, HVDL, 13)),
    TRow(114, deshot, deshot, mtainSahel, hillyDeshot, sahel * 2),
    VRow(113, BendMax(2560, HVUR), ThreeDown(2562, 13, 0, 13), BendOut(2564, HVDn), BendOut(2566, HVDL, 7)),
    TRow(112, deshot * 2, hillySahel, sea, savannah, savannah),
    VRow(111, BendOut(2560, HVDR, 7), BendIn(2562, HVUL, 13), BendIn(2566, HVUR, 13), ThreeDown(2568, 13, 13, 0), Orig(2570, HVDL, 6, 2)),
    TRow(110, deshot, hillyDeshot, deshot, sea * 2, mtainSavannah),
    VRow(109, BendOut(2554, HVDR), BendOut(2556, HVDn), BendIn(2558, HVUp, 13), BendIn(2560, HVUL, 13), BendIn(2568, HVUR, 13), BendOut(2570, HVDL, 7)),
    TRow(108, mtainDeshot * 2, sea * 4, hillySavannah),

    VRow(107, BendIn(2546, HVDn, 13), Bend(2548, HVUp, 12, 7), Bend(2550, HVDn, 10, 7), BendIn(2552, HVUp, 13), BendIn(2554, HVUL, 13), BendIn(2570, HVUR, 13),
      BendOut(2572, HVDL, 7)),

    TRow(106, hillyDeshot, sea * 5, hillySavannah),
    VRow(105, BendIn(2572, HVUR, 13), BendOut(2574, HVDL, 7)),
    TRow(104, deshot),
    VRow(101, BendOut(2546, HVDR), OrigRt(2548, HVDL)),
    VRow(99, BendIn(2546, HVUL, 13)),
    TRow(92, hillySavannah),
    TRow(90, hillySavannah),
    TRow(86, hillySavannah),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(142, "Severny north")
    str(140, "Severny south")
    str(112, "" * 2, "Oman east")
  }
}