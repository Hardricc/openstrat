/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° west to 15° west centred on 30° west. 130km per hex tile.
 * Isle8 243930.488km² => 463086.787km², British Isles combined 315159 km²
 * Isle4 70034.730km² => 115771.696km², Iceland 103000 km², Ireland 84421km². */
object Terr13W30 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w30(86)

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(115, BendIn(11776, HVDR, 12), BendIn(11778, HVDn, 12), BendIn(11780, HVDL, 12)),
      TRow(114, hillyTundra),
      VRow(113, BendIn(11776, HVUR, 12), ThreeDown(11778, 12, 0, 13), BendIn(11780, HVUL, 12)),
      TRow(112, mtain),
      VRow(111, BendIn(11774, HVUR), ThreeDown(11776, 12, 12, 13), ThreeUp(11778, 5, 12, 12), BendIn(11780, HVDL, 12)),
      TRow(110, land),
      VRow(109, ThreeUp(11776, 12, 0, 13), ThreeDown(11778, 12, 6, 0), BendIn(11780, HVUL, 12)),
      TRow(108, SepB()),
      VRow(105, BendIn(11778, HVDR, 13), BendAllOld(11780, HVUL)),
      TRow(104, sea, desert),
      VRow(103, MouthRt(11778, HVDn, 7)),
      VRow(101, BendIn(11774, HVDL, 13)),
      TRow(100, SepB(), sea * 2),
      VRow(99, BendIn(11774, HVUR), BendIn(11776, HVDL, 8)),
      TRow(98, jungle, SepB(), sea),
      VRow(97, BendAllOld(11774, HVDR), BendIn(11776, HVUL, 13)),
      TRow(96, SepB(), sea * 2),
      VRow(95, SetSep(11772)),
      VRow(95, BendAllOld(11772, HVDR)),
      TRow(94, SepB(), sea * 2),
      VRow(93, SetSep(11772)),
      TRow(92, SepB(), sea),
      VRow(87, MouthOld(11776, HVDL, 3, wice), BendOut(11780, HVUp, 6, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run
}