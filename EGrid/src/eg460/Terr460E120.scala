/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** 460km [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. A hex tile area of 183250.975km².
 *  Isle 120974.276km² down to 57981.753km²
 *  [[Isle7]] 30243.569km² => 40265.106km². Timor Island + Rote Island 32057.1km², Taiwan 36197km².
 *  [[Isle5]] 14495.438km² => 21653.679km². Samar 13429km² + Leyte 7367.6km² = 20796.6.
 *  [[Isle4]] 8768.845km² => 14495.438km². Palawan 12189km².
 *  [[Isle3]] 4473.900km² => 8768.845km².*/
object Terr460E120 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e120(90)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(146, SeaIcePerm),
    TRow(144, SeaIceWinter),
    TRow(142, SeaIceWinter),
    TRow(140, tundra, tundra),
    TRow(138, hillyTaiga * 2),
    TRow(136, hillyTaiga, taiga, mtainTaiga),
    TRow(134, taiga, hillyTaiga * 2),
    TRow(132, mtainTaiga * 3),
    TRow(130, mtainTaiga * 4),
    TRow(128, mtainTaiga * 4),
    TRow(126, deshot, sahel, savannah, mtainContForest),
    TRow(124, deshot * 2, sahel, hillyOceForest, mtainContForest),
    VRow(123, OrigLt(4608, HVDn), BendOut(4616, HVDR), OrigRt(4618, HVDL, 7)),
    TRow(122, mtainSteppe, oceanic, hillyOce, mtainContForest),

    VRow(121, Bend(4608, HVUR, 8, 5), ThreeDown(4610, 13, 10, 0), OrigRt(4612, HVDL), BendIn(4614, HVDR, 13), ThreeUp(4616, 0, 13, 6), BendOut(4618, HVUp),
      BendOut(4620, HVUL)),

    TRow(120, hillySavannah, oceanic, hillyOce, hillyOce, hillyOce),
    VRow(119, BendMax(4610, HVUR), BendMax(4612, HVDL), OrigRt(4614, HVUp, 7)),
    TRow(118, mtainSteppe, oceanic * 2, oceanic, sea, hillyOce),
    VRow(117, OrigLt(4612, HVUp, 7), OrigRt(4616, HVDR), BendIn(4618, HVUp, 13), OrigRt(4620, HVDL, 7)),
    TRow(116, mtainSubForest, hillyOce, mtainSubForest),
    TRow(114, mtainSubForest * 3, Isle7(hillyJungle)),
    VRow(113, BendOut(4606, HVDR), BendIn(4608, HVUL, 7)),
    TRow(112, hillyJungle * 2),
    VRow(111, BendMin(4600, HVDR, 4), BendOut(4602, HVDn, 7), BendIn(4604, HVUp, 13), BendIn(4606, HVUL, 13)),
    TRow(110, mtainJungle, sea * 2, hillyJungle),
    VRow(109, OrigMin(4598, HVUR), BendIn(4600, HVUL, 13)),
    TRow(108, hillyJungle, sea * 2, hillyJungle),
    TRow(106, sea * 2, Isle4(mtainJungle), Isle10(mtainJungle), Isle5(hillyJungle)),
    TRow(104, sea * 2, mtainJungle, sea, hillyJungle),
    TRow(102, sea, hillyJungle * 2),
    VRow(101, BendIn(4606, HVDR), OrigMin(4608, HVDL), OrigRt(4618, HVUR, 7), BendIn(4620, HVDn, 13), BendIn(4622, HVDL, 13)),
    TRow(100, sea, hillyJungle, jungle, mtainJungle, sea, sea, hillyJungle),
    VRow(99, BendIn(4596, HVDL, 7), OrigRt(4606, HVUp, 7), OrigMin(4622, HVUp)),
    TRow(98, SepB(), sea, jungle, sea, hillyJungle, sea * 2, mtainJungle),

    VRow(97, OrigRt(4594, HVUR), ThreeUp(4596, 0, 6, 6), BendOut(4598, HVUp), BendInRt(4600, HVDn, 10, 7), Orig(4602, HVUL, 6, 7), OrigLt(4620, HVDR),
      BendOut(4622, HVDL)),

    TRow(96, hillyJungle * 2),
    VRow(95, OrigLt(4594, HVDR, 7), BendIn(4596, HVUp, 13), BendMin(4598, HVDn, 4), OrigRt(4600, HVUL, 7), BendIn(4622, HVUR, 13), OrigRt(4624, HVDR)),
    TRow(94, sea * 4, Isle7(mtainJungle)),
    TRow(92, sea * 4, mtainSahel, hillySahel, hillySavannah),
    TRow(90, sea * 3, sahel),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(114, "" * 3, "Taiwan")
    str(106, "" * 4, "Samar")
    str(96, "Java west", "Java east")
    str(94, "" * 4, "Timor")
  }
}