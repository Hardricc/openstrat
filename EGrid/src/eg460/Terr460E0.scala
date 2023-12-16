/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 460km. A hex tile area of 354724.005km².
 *  A minimum island area of 59120.667km², which includes Britain but excludes Ireland and Sicily-Corsica. */
object Terr460E0 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e0(120)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(136, sea * 2, Cape(5, 1, hillyTaiga)),
      VRow(135, BendOut(514, HVUL)),
      TRow(134, sea, Cape(4, 2, mtain)),
      TRow(132, Cape(4, 3, hilly)),
      VRow(131, BendOut(508, HVDL)),
      TRow(130, Cape(4, 3), hilly),
      TRow(128, sea, Cape(1, 3)),
//      TRow(126, sea, Cape(4, 2, hillyTaiga)),
//      VRow(125, BendOut(518, HVDR)),
//      TRow(124, sea, taiga),
//      VRow(123, ThreeWay(512), BendOut(516, HVUp), BendAll(518, HVUL)),
//      TRow(122, Cape(5, 2), Cape(5, 2)),
//      VRow(121, Mouth(508, HVDn), BendAll(512, HVUL)),
//      TRow(120, sea, Cape(5, 1), land),
//      VRow(119, Mouth(510, HVDn)),
//      TRow(118, sea, land, hilly),
//      TRow(116, Cape(4, 3, hilly), Cape(2, 1, hilly), sea),
//      VRow(115, BendAll(512, HVUp), Mouth(516, HVDL)),
//      TRow(114, sea, Cape(5, 2, hilly), hilly, Cape(0, 2, hilly)),
//      VRow(113, Mouth(520, HVDn)),
//      TRow(112, hillyDesert, desert * 3),
//      TRow(110, desert * 2, hillyDesert, desert),
//      VRow(109, Mouth(504, HVUR)),
//      TRow(108, desert * 4),
//      TRow(106, sahel * 5),
//      TRow(104, Land(Hilly, Savannah, Forest), Land(Level, Savannah, Forest) * 3, Land(Hilly, Savannah, Forest)),
//      TRow(102, Cape(3, 2, jungle), Cape(3, 1, jungle) * 2, jungle, jungle),
//      VRow(101, BendOut(508, HVDn), BendOut(512, HVDn)),
//      TRow(100, sea * 4, hillyJungle),
//      TRow(98, sea * 4, hillyJungle),
//      TRow(96, sea * 4, Cape(4, 2, hillySavannah))
    )
  }
  help.run
}