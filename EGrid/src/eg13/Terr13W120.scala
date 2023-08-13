/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 75 East to 105 East. 1300km per hex tile. */
object Terr13W120 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w120(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, mtain),
      TRow(110, hillyTaiga),
      TRow(108, hillyDesert),
      TRow(106, sea, Hland(1, 4, Hilly(Desert))),
      TRow(104, sea, Hland(2, 3, Hilly(Desert))),
//      TRow(102, SideB(), sea * 2),
//      VRow(101, SetSide(4604)),
//      TRow(100, Isle(Hilly(Jungle)), Hland(4, 4, Hilly(Jungle))),
//      VRow(99, SetSide(4604)),
//      TRow(98, Isle(Hilly(Jungle)), sea),
//      TRow(96, Hland(2, 5, Level(Desert)), desert),
//      TRow(94, Hland(3, 3), Hland(1, 3, Level(Desert))),
//      TRow(86, Hland(1, 0, Level(IceCap)))
    )
  }
  help.run
}