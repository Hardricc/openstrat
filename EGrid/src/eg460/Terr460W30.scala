/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile scale 460km. Maximum Isle area is 120974.276km², which would
 *  include Iceland if it was centred.
 *  Isle3 from 8768.845km² down to 4473.900km² includes the Canaries. */
object Terr460W30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w30(94)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, ice),
      TRow(144, ice),
      VRow(143, Mouth(11778, HVUL)),
      TRow(142, Cape(1, 2, ice)),
      VRow(141, Mouth(11778, HVDL)),
      TRow(140, ice),
      TRow(138, ice),
      TRow(136, Cape(2, 1, ice), sea, Cape(3, 1, mtain)),
      VRow(135, BendAll(11772, HVDR), Mouth(11778, HVUL), Mouth(11782, HVUR)),
      VRow(133, SetSide(11771), MouthLt(11784, HVUR, 7)),
      VRow(129, Mouth(11786, HVDR)),
      TRow(116, sea * 5, Isle3(mtain)),
      VRow(113, MouthLt(11786, HVUp)),
      TRow(112, sea * 5, desert),
      VRow(111, BendIn(11784, HVDR, 13), BendOut(11786, HVUL, 7)),
      TRow(110, sea * 5, sahel),
      VRow(109, BendIn(11784, HVUR, 13), BendOut(11786, HVDL, 7)),
      TRow(108, sea * 6, savannah),
      VRow(107, BendIn(11786, HVUR, 13), BendOut(11788, HVDL, 7)),
      TRow(106, sea * 6, hillySavannah),
      VRow(105, BendIn(11788, HVUR, 13), BendOut(11790, HVDL, 7)),
      VRow(103, BendIn(11790, HVUR, 13), BendIn(11792, HVUp, 13)),
      VRow(101, BendIn(11762, HVDL, 13)),
      VRow(99, Mouth(11762, HVDn), MouthRt(11766, HVUL, 7), MouthLt(11768, HVDR, 7)),
      TRow(98, hillySavannah),
      TRow(96, hillyJungle * 2),
      TRow(94, hillySavannah),
    )
  }
  help.run
}