/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e150(114, 140)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, mtain),
      TRow(128, Cape(1, 2, mtain)),
      VRow(127, BendOut(5628, HVDR)),
      TRow(126, Cape(2, 2, mtain)),
      VRow(125, Mouth(5624, HVUL)),
      TRow(124, sea, Cape(1, 3, mtain)),
      VRow(123, Mouth(5622, HVUR) ),
      TRow(122, Cape(5, 4, hilly)),
      VRow(121, BendOut(5622, HVUp), BendOut(5624, HVUL), BendOut(5626, HVDR)),
      TRow(120, Cape(2, 2, hilly), sea * 4),
      VRow(117, Mouth(5620, HVUR)),
    )
  }
  help.run
}