/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 135° west to 105° wast, centred on 120° west. */
object Terr220W120 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w120(150, 152)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(166, SeaIcePerm),
//      TRow(164, SeaIceWinter),
//      TRow(162, SeaIceWinter, tundra),
//      TRow(160, Cape(5, 4, tundra, SeaIceWinter), Cape(1, 4, tundra, SeaIceWinter)),
//      TRow(158, Cape(2, 4, tundra, SeaIceWinter), Cape(4, 3, tundra, SeaIceWinter), Cape(0, 2, tundra, SeaIceWinter)),
//      TRow(156, Cape(0, 1, tundra, SeaIceWinter), Cape(0, 2, tundra, SeaIceWinter), Cape(2, 3, tundra, SeaIceWinter)),
//      TRow(154, tundra * 4),
//      VRow(153, Mouth(8702, HVDL, Lake), Mouth(8704, HVUR, Lake)),
      TRow(152, sea * 3, mtain * 5, hillySahel, mtain),
      VRow(151, MouthLt(8696, HVUp, 7)),
      TRow(150, sea * 3, mtain, hillySavannah, hillySahel * 3, mtain, sahel, hillyDesert),
//      VRow(149, Mouth(8706, HVDL, Lake), BendAll(8708, HVUL, Lake)),
//      TRow(148, hillyTaiga * 2, taiga * 3),
//      TRow(146, mtain, taiga * 4),
//      TRow(144, mtain, taiga * 4),
//      TRow(142, sea, mtain * 2, land * 3),
//      TRow(140, sea, mtain * 2, land * 3),
//      TRow(138, sea * 2, mtain * 2, land * 3),
//      TRow(136, sea * 2, hillyForest, hilly, mtain * 2, hillyDesert),
//      TRow(134, sea * 2, hilly, hillyDesert, desert, hillyDesert * 2),
//      TRow(132, sea * 2, hilly * 2, hillyDesert * 2, mtain),
//      TRow(130, sea * 3, hilly, hilly, hillyDesert * 2, mtain),
//      TRow(128, sea * 3, hilly, hillyDesert * 2, desert, hillyDesert),
//      TRow(126, sea * 5, hilly, hillyDesert, desert),
//      TRow(124, sea * 6, hilly, hilly, hillyDesert),
    )
  }

  help.run
}