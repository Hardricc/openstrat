/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 460km.
 * [[Isle10]] 64603.127km² => 78919.609km². Sri Lanka 65610km². */
object Terr460E90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e90(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(146, SeaIcePerm),
    TRow(144, SeaIceWinter),
    TRow(142, hillyTundra),
    VRow(143, BendIn(3582, HVDn, 10, sea, SeaIceWinter), ThreeDown(3584, 0, 13, 13, SeaIceWinter)),
    TRow(140, tundra, hillyTundra),
    TRow(138, taiga, mtainTundra),
    TRow(136, taiga, hillyTaiga * 2),
    TRow(134, taiga, hillyTaiga, taiga),
    TRow(132, taiga * 2, hillyTaiga),
    TRow(130, steppe * 2, hillyTaiga, hillySteppe),
    TRow(128, mtainSteppe * 4),
    TRow(126, sahel * 2, mtainSteppe, hillyDeshot),
    TRow(124, hillySahel, mtainSteppe, hillySahel * 3),
    TRow(122, deshot * 2, hillyDeshot, hillySahel, deshot),
    TRow(120, mtainSteppe, hillyDeshot * 2, mtainSteppe * 2),
    TRow(118, mtainJungle, hillySahel * 2, mtainSavannah * 3),
    TRow(116, deshot, deshot, hillyDeshot, mtainJungle * 3),
    TRow(114, hillyOce, oceanic, hillySavannah, savannah, hillyJungle, mtainJungle),
    TRow(112, hillySavannah * 2, Land(Plain, Tropical), hillySavannah, mtainJungle * 2),
    VRow(111, BendMin(3580, HVDR, 4), BendOut(3582, HVDn, 7)),
    TRow(110, savannah, hillySavannah, sea * 2, hillyJungle, mtainJungle),
    VRow(109, BendOut(3578, HVDR, 7), BendIn(3580, HVUL, 13), OrigMin(3590, HVDn)),
    TRow(108, sahel, savannah, sea * 3, mtainJungle, hillySavannah),
    VRow(107, BendOut(3576, HVDR, 7), BendIn(3578, HVUL, 13)),
    TRow(106, savannah, sea * 4, mtainJungle, jungle),
    VRow(105, BendOut(3570, HVDL, 7), Bend(3574, HVDR, 6, 7), ThreeUp(3576, 0, 6, 11), BendIn(3578, HVDL), BendIn(3590, HVDR), OrigMin(3592, HVDL)),
    TRow(104, hillySavannah, hillyJungle, sea * 3, hillyJungle),

    VRow(103, BendIn(3570, HVUR, 13), BendIn(3572, HVUp, 13), ThreeUp(3574, 6, 0, 13), BendIn(3576, HVUp), BendIn(3578, HVUL), BendIn(3590, HVUR, 13),
      BendOut(3592, HVDL, 7)),

    TRow(102, sea * 4, hillyJungle, hillyJungle),
    VRow(101, BendIn(3588, HVUR, 13), BendOut(3590, HVDL, 7), BendIn(3592, HVUR, 13), BendIn(3594, HVUp, 13), OrigMin(3596, HVDL)),
    TRow(100, sea * 5, hillyJungle, hillyJungle),
    VRow(99, BendIn(3590, HVUR, 10), OrigRt(3592, HVUL, 7), BendIn(3600, HVDL, 7)),
    TRow(98, sea * 6, hillyJungle),
    VRow(97, OrigRt(3598, HVUR), BendIn(3600, HVUL)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(104, "", "Sri Lanka")
  }
}