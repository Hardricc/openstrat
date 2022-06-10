/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr160E0 extends WarmTerrs
{
  override implicit val grid: EGrid160Warm = EGrid160.e0(276)

  override val terrs: HCenDGrid[WTile] =
  {
    val res: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
//    gs(152, 460 + 60, taiga)
//    gs(150, 460 + 58, taiga)
//    gs(148, 460 + 56, taiga * 2)
//    gs(146, 460 + 50, hills, sea * 2, plain)
//    gs(144, 460 + 48, plain, sea * 2, plain)
//    gs(142, 460 + 46, plain, plain, sea, plain * 2)
//    gs(140, 516, plain * 3)
    gs(292, 504, hills * 2, sea * 5, plain)
    gs(290, 506, hills, sea * 4, plain, sea, plain)
    gs(288, 500, plain, hills, plain, sea * 4, plain, sea)
    gs(286, 498, plain, sea * 2, plain, sea * 3, plain * 3)
    gs(284, 496, plain * 2, hills, plain * 2, sea * 2, plain * 4)
    gs(282, 506, hills, plain * 8)
    gs(280, 504, hills, sea * 2, plain * 6)
    gs(278, 506, plain * 9)
    gs(276, 508, plain * 4, mtain * 5)
    res
  }

  override val sTerrs: HSideBoolDGrid =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507), (144, 522), (145, 521))
    res
  }

  def regGrid: HGridReg = HGridReg(138, 148, 504, 520)

  def regTerrs: HCenDGrid[WTile] = regGrid.newHCenDSubGrid(EGrid160.e0(138), terrs)

  def regScen: EScenFlat = new EScenFlat {
    override implicit val gridSys: HGridSys = regGrid
    override val terrs: HCenDGrid[WTile] = regTerrs
    override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
    sTerrs.setTruesInts((142, 508), (143, 507))
  }
}