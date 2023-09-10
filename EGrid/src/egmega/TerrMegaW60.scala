/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 75° west to 45° west, centred on 60° west. Hex tile scale 1 Megametre or 1000km. */
object TerrMegaW60 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w60(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, ice),
//      TRow(116, taiga),
      TRow(114, Hland(3, 0, taiga)),
      TRow(112, Hland(2, 2,taiga), sea),
      VRow(109, Mouth(10748, HVUL)),
      TRow(108, SideB(), sea * 2),
      //VRow(107, SetSide(10748)),
      //TRow(106, SideB(), sea * 3),
     // VRow(105, SetSide(10748)),
//      VRow(105, VertIn(1540, HVUR), VertIn(1542, HVDL)),
//      TRow(104, desert * 3),
//      VRow(103, VertIn(1542, HVUR)),
//      TRow(102, jungle * 2, hills),
//      TRow(100, jungle * 2, plain),
//      TRow(98, jungle * 2, sea),
//      TRow(96, plain * 2, Hland(3, 1)),
//      VRow(95, Mouth(1538, HVUL)),
//      TRow(94, desert, Hland(2, 1), Hland(2, 4)),
//      TRow(92, Hland(2, 2, Hilly()), sea),
//      TRow(90, sea * 2),
//      TRow(88, sea * 2),
//      TRow(86, sea),
//      TRow(84, sea),
//      TRow(82, ice),
    )
  }
  help.run
}