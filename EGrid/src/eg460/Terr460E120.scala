/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** 460km [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. A hex tile area of 183250.975km².
 *  Isle 120974.276km² down to 57981.753km²
 *  Isle8 35075.382 km² => 57981.753km², includes Taiwan,
 *  Isle4 14495.438km² <= 8768.845km². Palawan 12189 km2.*/
object Terr460E120 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e120(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIceWinter),
      TRow(142, SeaIceWinter),
      TRow(140, tundra, tundra),
      TRow(138, hillyTaiga * 2),
      TRow(136, hillyTaiga, taiga, mtain),
      TRow(134, taiga, hillyTaiga * 2),
      TRow(132, mtain * 3),
      TRow(130, mtain * 4),
      TRow(128, mtain * 4),
      TRow(126, desert, sahel, savannah, mtain),
      TRow(124, desert * 2, sahel, hillyForest, mtain),
      VRow(123, MouthOld(4608, HVUp), BendOut(4616, HVDR), MouthOld(4618, HVUR)),
      TRow(122, mtain, land, Cape(3, 2, hilly), mtain),
      VRow(121, MouthOld(4612, HVUR), ThreeUp(4616, 0, 13, 6), BendOut(4618, HVUp), BendOut(4620, HVUL)),
      TRow(120, hillySavannah, land, Cape(2, 1, hilly), Cape(4, 2, hilly), Cape(4, 3, hilly)),
      VRow(119, MouthOld(4608, HVDL), MouthOld(4616, HVDR)),
      TRow(118, mtain, land * 2, Cape(0, 3), sea, Cape(3, 1, hilly)),
      VRow(117, MouthOld(4616, HVUL), MouthOld(4620, HVUR)),
      TRow(116, mtain, hilly, mtain),
      TRow(114, mtain * 2, Cape(2, 1, mtain), Isle8(mtain)),
      TRow(112, hillyJungle),
      VRow(111, MouthOld(4598, HVUL), ThreeDown(4600, 6, 0, 7), MouthRt(4602, HVUR, 7)),
      TRow(110, mtain, SepB(), sea * 2, Cape(0, 3, hillyJungle)),
      VRow(109, MouthOld(4598, HVDL), BendIn(4600, HVUL, 13)),
      TRow(108, hillyJungle, sea * 2, Cape(4, 2, hillyJungle)),
      TRow(106, sea * 2, Isle4(mtain), Isle10(mtain), Isle8(mtain)),
      TRow(104, sea * 2, mtain, sea, hillyJungle),
      TRow(102, sea, hillyJungle * 2),
      VRow(101, BendIn(4606, HVDR), MouthOld(4608, HVUR), MouthRt(4618, HVDL, 7), BendIn(4620, HVDn, 13), BendIn(4622, HVDL, 13)),
      TRow(100, sea, hillyJungle, jungle, mtain, sea, sea, hillyJungle),
      VRow(99, BendIn(4596, HVDL, 7), MouthRt(4606, HVDn, 7), MouthOld(4622, HVDn)),
      TRow(98, SepB(), sea, jungle, sea, hillyJungle, sea * 2, mtain),
      VRow(97, MouthRt(4594, HVDL), BendIn(4596, HVUL), MouthOld(4600, HVUL, 7, sea), MouthOld(4602, HVDR, 5, sea), MouthLt(4620, HVUL), BendOut(4622, HVDL, 7)),
      TRow(96, hillyJungle * 2),
      VRow(95, BendIn(4622, HVUR, 13), MouthRt(4624, HVDR)),
    )
  }
  help.run
}