/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 640km.  */
object Terr640E0 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e0(110)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(124, sea, taiga),
      VRow(123, Mouth(512, HVUp)),
      TRow(122, Hland(3, 4), plain),
      VRow(121, BendAll(512, HVUL)),
      TRow(120, sea, Hland(1, 5), plain),
      TRow(118, sea, plain, hills),
      TRow(116, Hland(3, 4, hills), Hland(1, 2, hills), sea),
      VRow(115, BendAll(512, HVUp)),
      TRow(114, sea, Hland(2, 5, hills), hills * 2),
      TRow(112, desert * 4),
      TRow(110, desert * 4)
    )
  }
  help.run
}