/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320W30 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w30(130)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    gs(160, 11776, ice, sea)
    gs(158, 11774, ice * 2, sea)
    gs(156, 11772, ice * 2, sea)
    gs(154, 11770, ice, tundra, sea * 2)
    gs(152, 11780, hills * 2)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507))
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}