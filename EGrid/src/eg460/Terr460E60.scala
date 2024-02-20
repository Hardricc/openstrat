/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 460km.  */
object Terr460E60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e60(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      VRow(145, BendIn(2558, HVDL, 13, SeaIceWinter)),
      TRow(144, SeaIceWinter),
      VRow(143, MouthLt(2558, HVDn, 7, SeaIceWinter), MouthRt(2560, HVDL, 7), BendIn(2562, HVDn, 10, sea, SeaIceWinter), ThreeDown(2564, 0, 13, 13, SeaIceWinter)),
      TRow(142, ice),
      VRow(141, BendOut(2562, HVDR, 7, SeaIceWinter), BendIn(2564, HVUL, 13, SeaIceWinter)),
      TRow(140, mtain, tundra),

      VRow(139, ThreeDown(2556, 0, 13, 11, SeaIceWinter, SeaIceWinter, sea), BendIn(2558, HVDn, 13, SeaIceWinter), BendIn(2560, HVUp, 8, SeaIceWinter),
        Bend(2562, HVUL, 13, 4, SeaIceWinter)),

      TRow(138, taiga, tundra),
      TRow(136, taiga, hillyTaiga, taiga),
      TRow(134, taiga * 3),
      TRow(132, taiga, hillyTaiga, taiga),
      TRow(130, land * 4),
      TRow(128, savannah, sahel * 2, savannah),
      TRow(126, sahel, sahel * 3),
      VRow(125, BendOut(2554, HVDR), MouthOld(2556, HVUR)),
      TRow(124, hillySavannah, sahel * 3, hillySavannah),
      VRow(123, BendOut(2554, HVUR)),
      TRow(122, Cape(1, 2, savannah), desert, sahel, mtain * 2),
      VRow(121, MouthOld(2554, HVDL)),
      TRow(120, hillySahel, hillySahel, hillyDesert, mtain * 2),
      TRow(118, hillySahel, hillyDesert * 4, savannah),
      VRow(117, MouthOld(2550, HVUp)),
      TRow(116, hillyDesert, mtain, hillyDesert, hillySahel, hillyDesert, desert),
      VRow(115, Bend(2556, HVUp, 3, 3), BendIn(2558, HVDn, 13), BendIn(2560, HVDL, 13)),
      TRow(114, desert, desert, mtain, hillyDesert, sahel * 2),
      VRow(113, BendMax(2560, HVUR), ThreeDown(2562, 13, 0, 13), BendOut(2564, HVDn), BendOut(2566, HVDL)),
      TRow(112, desert * 2, hillySahel, sea, Cape(3, 2, savannah), savannah),
      VRow(111, MouthMin(2558, HVUL), BendIn(2560, HVUp, 13), BendIn(2562, HVUL, 13)),
      TRow(110, desert, hillyDesert),
      VRow(109, BendOut(2554, HVDR), MouthRt(2556, HVUR)),
      TRow(108, mtain, mtain),
      VRow(107, BendAllOld(2548, HVUp, sea, 7), BendAllOld(2550, HVDn, sea, 7), BendIn(2552, HVUp, 13), BendIn(2554, HVUL, 13)),
      TRow(106, hillyDesert),
      TRow(104, desert),
      VRow(101, BendOut(2546, HVDR), MouthRt(2548, HVUR)),
      VRow(99, BendIn(2546, HVUL, 13)),
    )
  }
  help.run
}