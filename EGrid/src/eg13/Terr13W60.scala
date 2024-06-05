/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15 West to 15 East.
 * Isle6 172942.905km² => 243930.488km². (Cuba 105806km²) + (Hispaniola 76479km²) + (Jamaica	11188km²) = 193473km².
 * Below 35732.005km² Falklands 12173km². */
object Terr13W60 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w60(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      VRow(115, BendIn(10752, HVDL, 13, SeaIceWinter)),
      TRow(114, ice),
      VRow(113, BendOut(10750, HVUp, 6, siceWin), ThreeUp(10752, 4, 13, 2, siceWin), BendIn(10754, HVDL, 13, siceWin)),
      TRow(112, hillyTaiga),
      VRow(111, BendOut(10754, HVUR, 7, siceWin), ThreeDown(10756, 12, 12, 13)),
      TRow(110, hillyContForest),
      VRow(109, OrigLt(10752, HVDR), ThreeDown(10754, 13, 0, 13), ThreeUp(10756, 12, 0, 13)),
      TRow(108, hillyCont),
      VRow(107, OrigLt(10750, HVDR), BendIn(10752, HVUp, 13), BendIn(10754, HVUL, 13)),
      VRow(105, BendIn(10748, HVDn, 10), BendIn(10750, HVDL, 10)),
      VRow(103, Bend(10748, HVUp, 13, 4), ThreeUp(10750, 0, 13, 8), OrigLt(10752, HVUL, 7)),
      TRow(102, hillyOce, sea),
      VRow(101, OrigRt(10756, HVDR), BendIn(10758, HVDL, 13)),
      TRow(100, jungle, jungle),
      VRow(99, BendIn(10758, HVUR)),
      TRow(98, hillyOce, oceanic),
      TRow(96, hillyOce, oceanic),
      TRow(94, mtainDeshot, oceanic),
      VRow(93, BendOut(10754, HVDR, 7), BendIn(10756, HVUL, 12)),
      TRow(92, savannah),
      VRow(91, OrigMin(10750, HVDL, 1), OrigRt(10752, HVUR), BendIn(10754, HVUL, 13)),
      VRow(89, OrigRt(10750, HVUL, 7)),
      VRow(87, BendIn(10750, HVDn, 13, siceWin), BendIn(10752, HVUp, 13, siceWin), BendOut(10754, HVDn, 7, siceWin), BendMax(10756, HVUp, siceWin)),
      TRow(86, SeaIcePerm),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(114, "Greenland north")
  }
}