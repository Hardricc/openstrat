/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile scale 460km. Maximum Isle area is 120974.276km², which would
 *  include Iceland if it was centred.
 *  Isle3 from 8768.845km² down to 4473.900km² includes the Canaries. */
object Terr460W30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w30(90)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val terrSet: WTerrSetter = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(146, ice),
      VRow(145, BendOut(11780, HVDn, 6, SeaIceWinter, sea)),
      TRow(144, ice),
      VRow(143, BendOut(11778, HVUR, 7, SeaIceWinter), BendIn(11780, HVDL, 11, SeaIceWinter)),
      TRow(142, ice),
      VRow(141, BendIn(11778, HVDR, 6, SeaIceWinter), BendIn(11780, HVUL, 11, SeaIceWinter)),
      TRow(140, ice),
      VRow(139, BendIn(11776, HVDR, 6, SeaIceWinter), BendOut(11778, HVUL, 6, SeaIceWinter)),
      TRow(138, ice),
      VRow(137, Bend(11774, HVDR, 4, 2, SeaIceWinter), BendIn(11776, HVUL, 6, SeaIceWinter)),
      TRow(136, ice, sea, mtainTundra),
      VRow(135, BendMin(11772, HVDR, 3, SeaIceWinter), BendIn(11774, HVUL, 13, SeaIceWinter), OrigMin(11778, HVDR), BendIn(11780, HVUp), OrigRt(11782, HVDL)),
      VRow(133, BendIn(11770, HVUp, 6, SeaIceWinter), BendIn(11772, HVUL, 6, SeaIceWinter)),
      VRow(129, OrigMin(11786, HVUL)),
      VRow(117, BendIn(11786, HVDR, 13), BendIn(11788, HVDn, 13), ThreeDown(11790, 0, 10, 13)),
      TRow(116, sea * 5, mtainSavannah),
      VRow(115, BendIn(11786, HVUR, 13), BendIn(11788, HVUp, 13), BendIn(11790, HVUL, 13)),
      VRow(113, OrigLt(11786, HVDn)),
      TRow(112, sea * 5, deshot),
      VRow(111, BendIn(11784, HVDR, 13), BendOut(11786, HVUL, 7)),
      TRow(110, sea * 5, sahel),
      VRow(109, BendIn(11784, HVUR, 13), BendOut(11786, HVDL, 7)),
      TRow(108, sea * 6, savannah),
      VRow(107, BendIn(11786, HVUR, 13), BendOut(11788, HVDL, 7)),
      TRow(106, sea * 6, hillySavannah),
      VRow(105, BendIn(11788, HVUR, 13), BendOut(11790, HVDL, 7)),
      VRow(103, BendIn(11790, HVUR, 13), BendIn(11792, HVUp, 13)),
      VRow(101, BendIn(11762, HVDL, 13)),
      VRow(99, OrigMin(11762, HVUp), OrigRt(11766, HVDR, 7), BendOut(11768, HVUp, 7), BendIn(11770, HVDn, 13), BendIn(11772, HVDL, 13)),
      TRow(98, hillySavannah, savannah),
      VRow(97, BendOut(11772, HVUR, 7), BendIn(11774, HVDL, 13)),
      TRow(96, hillyJungle * 2, hillySavannah),
      TRow(94, hillySavannah * 2),
    )
  }
  terrSet.run
}