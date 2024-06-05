/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 640km.
 * [[Isle8]] 77942.286km² => 100112.536km². (Hispaniola 76192km²) + (Puerto Rico 8897km²) = 85089km².
 * [[Isle3]] 8660.254km² => 16974.097km². Falkland Islands 12173km². */
object Terr640W60 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w60(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(130, tundra),
    VRow(129, BendIn(10750, HVDR, 13, siceWin, SeaIcePerm), BendMax(10752, HVUL, SeaIcePerm)),
    TRow(128, tundra),
    VRow(127, BendIn(10750, HVUR, 13, siceWin), Bend(10752, HVDL, 2, 7, siceWin)),
    TRow(126, tundra, hillyTundra),

    VRow(125, Bend(10748, HVDn, 4, 5, siceWin), BendMin(10750, HVUp, 3, siceWin), ThreeUp(10752, 13, 13, 2, siceWin), BendMax(10754, HVDL, siceWin),
      BendOut(10758, HVDR)),

    TRow(124, mtainTundra, mtainTundra),
    VRow(123, Orig(10752, HVUR, 2, 4, siceWin), ThreeUp(10754, 13, 13, 13, siceWin), ThreeDown(10756, 13, 0, 13, siceWin), BendIn(10758, HVUL, 9, siceWin)),
    TRow(122, taiga, hillyLakesTaiga),
    VRow(121, Bend(10754, HVDR, 4, 2, siceWin), ThreeUp(10756, 0, 13, 13, sea, sea, siceWin), BendIn(10758, HVDL, 13)),
    TRow(120, taiga * 2, hillyLakesTaiga),

    VRow(119, OrigLt(10750, HVDR, 7, siceWin), BendIn(10752, HVUp, 13, siceWin), BendIn(10754, HVUL, 13, siceWin), OrigLt(10756, HVUR),
      BendIn(10758, HVUL, 13, siceWin, sea)),

    TRow(118, taiga, sea),
    VRow(117, BendOut(10750, HVDR, 7), OrigRt(10752, HVDR, 7)),
    TRow(116, hillyCont),
    VRow(115, OrigLt(10746, HVDR), BendIn(10748, HVUp, 13), BendIn(10750, HVUL, 13)),
    VRow(109, BendIn(10746, HVDL, 6), ThreeUp(10744, 0, 13, 13)),
    TRow(108, Isle8(hillyJungle)),
    VRow(107, BendIn(10746, HVUL, 7)),

    VRow(105, BendOut(10742, HVUp, 7), BendIn(10744, HVDn), BendOut(10746, HVUp), BendIn(10748, HVDn, 11), BendMin(10750, HVUp), BendIn(10752, HVDn, 12),
      BendIn(10754, HVDL, 13)),

    TRow(104, hillyJungle, jungle, jungle),
    VRow(103, BendOut(10754, HVUR), BendOut(10756, HVUp), BendIn(10758, HVDn, 13), BendIn(10760, HVDL, 13)),
    TRow(102, savannah, hillyJungle, jungle, hillyJungle),
    VRow(101, BendOut(10760, HVUR, 7), OrigLt(10762, HVUL, 7)),
    TRow(100, jungle * 3, hillyJungle, jungle),
    TRow(98, jungle * 4, hillyJungle),
    TRow(96, jungle * 4, hillyJungle),
    TRow(94, mtainSavannah, jungle, hillySavannah, savannah, hillySavannah),
    TRow(92, hillySahel, jungle, savannah, jungle),
    VRow(91, BendIn(10744, HVDR, 12), BendMin(10746, HVUL, 2)),
    TRow(90, mtainDeshot, savannah * 2, hillySavannah),
    VRow(89, BendIn(10744, HVUR, 13), OrigMin(10746, HVUL, 5), OrigLt(10760, HVDR, 7)),
    TRow(88, hillySahel, subtrop * 2),
    VRow(87, BendIn(10744, HVDR, 10), OrigRt(10746, HVDL)),
    TRow(86, hillySahel, savannah, subtrop),
    VRow(85, OrigRt(10744, HVUp), BendMin(10754, HVDR, 2), OrigRt(10756, HVDL, 7)),
    TRow(84, hillySavannah, savannah),
    VRow(83, OrigMin(10746, HVDL, 4), BendOut(10752, HVDR, 7), BendIn(10754, HVUL, 13)),
    TRow(82, hillySahel),
    VRow(81, BendOut(10750, HVDR, 4), BendIn(10752, HVUL, 13)),
    TRow(80, hillySteppe, Isle3(hillySteppe)),
    VRow(79, BendOut(10750, HVUR), BendIn(10752, HVDL, 13)),
    TRow(78, hillyOceForest),
    VRow(77, OrigLt(10748, HVDR, 7), BendIn(10750, HVUp, 11), BendIn(10752, HVUL, 13)),
    VRow(75, BendIn(10748, HVDR, 13), BendIn(10750, HVDn, 13), BendOut(10752, HVUp, 6, SeaIceWinter, sea), OrigLt(10754, HVDL, 6, SeaIceWinter)),
    TRow(74, ice * 2),
    VRow(73, OrigRt(10748,  HVUp, 7)),
    TRow(72, ice),
    TRow(70, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(124, "", "Greenland south")
    str(120, "" * 2, "Newfoundland")
    str(116, "New York")
    str(108, "Hispaniola")
    str(80, "", "Falklands")
  }
}