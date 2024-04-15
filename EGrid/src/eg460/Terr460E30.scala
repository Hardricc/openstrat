/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 460km. A hex tile area of 183250975km².
 *  Isle3 4473.900km² => 8768.845km². Crete 8450km². */
object Terr460E30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e30(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(146, SeaIcePerm),
    VRow(145, BendIn(1534, HVUp, 13), Bend(1536, HVDn, 7, 4, sea, SeaIceWinter), BendIn(1538, HVDL, 13, SeaIceWinter)),
    TRow(144, mtainDepr),
    VRow(143, OrigLt(1538, HVUp, 7, SeaIceWinter), OrigRt(1540, HVUR, 7)),
    VRow(139, OrigRt(1538, HVDR, 7), ThreeDown(1540, 0, 6, 11, SeaIceWinter, SeaIceWinter, sea)),
    TRow(138, hillyTundra, hillyTundra),
    VRow(137, Orig(1534, HVDn, 2, 4, SeaIceWinter), OrigRt(1538, HVUR, 7, SeaIceWinter), BendIn(1540, HVUL, 6, SeaIceWinter)),
    TRow(136, hillyTaiga, Land(PlainLakes, Boreal, Forest), taiga),
    VRow(135, Bend(1532, HVDR, 4, 5, SeaIceWinter, sea), BendIn(1534, HVUL, 12, SeaIceWinter)),
    TRow(134, Land(PlainLakes, Boreal, Forest), Land(PlainLakes, Boreal, Forest), taiga),
    VRow(133, BendIn(1530, HVDR), ThreeUp(1532, 6, 6, 0), BendIn(1534, HVUp, 10, SeaIceWinter, sea), OrigRt(1536, HVDL, 6, SeaIceWinter)),
    TRow(132, oceanic, oceanic, taiga),
    VRow(131),
    TRow(130, oceanic * 4),
    TRow(128, oceanic * 2, hillyOce, oceanic),
    TRow(126, oceanic, hillyOce, savannah, oceanic),
    VRow(125, SetSep(1527), OrigMin(1536, HVDR), ThreeDown(1538, 0, 13, 13), BendIn(1540, HVDn, 13), BendIn(1542, HVDL, 13)),
    TRow(124, mtainDepr, hillyOce, hillySavannah, mtainDepr, mtainDepr),

    VRow(123, BendIn(1526, HVUR, 7), BendOut(1528, HVDL), OrigMin(1532, HVDn), OrigLtRevDepr(1536, HVDL, 7), BendIn(1538, HVUL, 13), BendMax(1542, HVUR),
      OrigRt(1544, HVUL)),

    TRow(122, hillySavannah, hillySavannah, hillySahel, mtainDepr, mtainDepr),
    VRow(121, Orig(1528, HVUp, 3, 7), BendIn(1530, HVDR, 13), ThreeUp(1532, 6, 13, 0), BendIn(1534, HVDL, 13)),
    TRow(120, SepB(), sea, hillySavannah, hillySavannah, hillySavannah, deshot),

    VRow(119, OrigRt(1528, HVUR, 7), ThreeUp(1530, 13, 11, 0), Bend(1532, HVUp, 13, 7), ThreeUp(1534, 13, 13, 13), Bend(1536, HVUp, 13, 7),
      BendIn(1538, HVDn, 13), Orig(1540, HVUL, 7, 2)),

    TRow(118, sahel, sahel, sahel, sahel, savannah, deshot),
    VRow(117, OrigMin(1534, HVUR, 3, Scarp), OrigMin(1536, HVDL, 3, Scarp), OrigMin(1538, HVDn), OrigLt(1550, HVDn)),
    TRow(116, deshot * 2, sahel, deshot, deshot * 2),
    VRow(115, BendIn(1538, HVUR, 8), Bend(1540, HVDL, 1, 7), BendIn(1550, HVUR, 13)),
    TRow(114, deshot * 3, sahel, hillySahel, deshot),
    VRow(113, BendIn(1540, HVUR, 13), Bend(1542, HVDL, 3, 7)),
    TRow(112, deshot * 3, hillyDeshot, hillyDeshot, deshot),
    VRow(111, BendIn(1542, HVUR, 13), Bend(1544, HVDL, 5, 7)),
    TRow(110, deshot, hillyDeshot, deshot * 2, hillySahel, hillySahel),
    VRow(109, BendIn(1544, HVUR, 13), BendOut(1546, HVDL, 7)),
    TRow(108, deshot * 4, sahel, hillySahel, hillySahel),
    VRow(107, BendIn(1546, HVUR, 13), BendIn(1548, HVUp, 13), BendIn(1550, HVDn, 13), Bend(1552, HVUp, 12, 7)),
    TRow(106, savannah * 2, sahel, savannah, mtainDepr, hillySahel, hillySahel),
    TRow(104, savannah * 5, mtainDepr, hillySahel),
    TRow(102, hillyJungle * 3, hillySavannah, hillySahel * 2, savannah),
    VRow(101, BendOut(1550, HVDR), OrigRtRevDepr(1552, HVUR)),
    TRow(100, jungle, hillyJungle * 3, hillySavannah * 2, savannah),
    VRow(99, OrigMin(1538, HVDR, 3, lake), OrigMinRevDepr(1540, HVDR, 3, lake), BendIn(1548, HVDR, 7), BendIn(1550, HVUL, 13)),
    TRow(98, hillyJungle * 3, hillySavannah * 3),
    VRow(97, OrigRt(1536, HVDR, 6, lake), BendIn(1538, HVDL, 6, lake), BendOut(1546, HVDR, 7), BendIn(1548, HVUL, 13)),
    TRow(96, hillyJungle * 4, hillySavannah * 2),
    VRow(95, OrigLt(1538, HVUp, 6, lake), OrigMinRevDepr(1540, HVUp, 3, lake), BendOut(1546, HVUR), BendIn(1548, HVDL, 13)),
    TRow(94, hillyJungle * 6),
    VRow(93)
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(122, "Greece", "Marmara")
    str(120, "Ionian Sea", "Crete")
  }
}