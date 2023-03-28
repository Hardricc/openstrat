/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320 km terrain for 120 west. */
object Terr320W120 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w120(128, 166)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }


    wr(152, taiga * 3, tundra)
    wr(150, mtain, taiga * 3)
    wr(148, taigaHills * 2, taiga * 3)
    wr(146, mtain, taiga * 4)
    wr(144, mtain, taiga * 4)
    wr(142, sea ,mtain * 2, plain * 3)
    wr(140, sea, mtain * 2, plain * 3)
    wr(138, sea * 2, mtain * 2, plain * 3)
    wr(136, sea * 2, forestHills, hills, mtain * 2, desertHills)
    wr(134, sea * 2, hills, desertHills, desert, desertHills *2)
    wr(132, sea * 2, hills * 2, desertHills * 2, mtain)
    wr(130, sea * 4, hills, desertHills * 2, mtain)
    wr(128, sea * 4, desertHills * 2, desert, desertHills)
    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
    //res.setSomeInts(WSideMid(), 155,8707,  155,8709,  157,8701)//,  158,8704,  158,8712,  159,8711)
    res
  }

  override val corners: HCornerLayer =
  { val res =grid.newHVertOffsetLayer

    //res.setMouth3(160, 8704)//Banks Island - Victoria Island north
//    res.setMouth0(156, 8704)//Banks Island - Victoria Island south
//
//    //res.setCornerPair(160, 8708, 2, HVUR, HVDR)
//    res.setCorner(158, 8710, 0, HVDL)//Victoria Island - Prince of Wales Island north
//    //res.setSideCorner2(158, 8710, 0, HVDL, HVUR)
//    //res.setCornerIn(158, 8710, 1)//Victoria Island - Prince of Wales
//    //res.setCornerIn(158, 8710, 2)//Victoria Island - Prince of Wales south
//
//    res.setMouth2(156, 8704)//Victoria Island - Canada west
//    res.setVert3In(156, 8708)//Victoria Island - Canada
//    res.setVert2In(156, 8708)//Victoria Island - Canada east
//    //res.setCorner(154, 8710, 0, HVDL)//Victoria Island - Canada east
//    //res.setCorner(154, 8710, 1, HVDn)//Victoria Island - Canada east
//
//    //res.setCornerIn(158, 8702, 4)//Banks Island - Canada west
//    res.setCornerIn(156, 8700, 0)//Canada - Banks Island
//    res.setMouth5(156, 8704)//Banks Island - Canada east

    res
  }
  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(162, sea, tundra),
      TRow(160, Headland(4, 5, Level, Tundra), Headland(4, 1, Level, Tundra)),
      TRow(158, Headland(4, 2, Level, Tundra), Headland(3, 4, Level, Tundra), Headland(2, 0, Level, Tundra)),
      TRow(156, Headland(1, 0, Level, Tundra), Headland(2, 0, Level, Tundra), Headland(3, 2, Level, Tundra)),
      TRow(154, tundra * 4),
    )
  }

  help.run
}