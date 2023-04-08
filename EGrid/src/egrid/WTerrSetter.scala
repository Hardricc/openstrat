/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._

/** Helper class for setting  [[HCenLayer]][WTile], [[HSideLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class WTerrSetter(gridIn: HGrid, val terrs: HCenLayer[WTile], val sTerrs: HSideOptLayer[WSide, WSideSome], val corners: HCornerLayer) extends
  HSetter[WTile, WSide, WSideSome]
{
  implicit val grid: HGrid = gridIn

  sealed trait RowBase
  case class TRow(row: Int, mutlis: Multiple[WTileHelper]*) extends RowBase
  trait TRowElem extends WTileHelper

  trait TRunner extends TRowElem
  { def run (row: Int, c: Int): Unit
  }

  case class Isle(terr: Land = Level(), sTerr: Water = Sea) extends TRunner with IsleBase
  case class Hland(numIndentedVerts: Int, indentStartIndex: Int, terr: Land = Level(), sideTerrs: Water = Sea) extends TRunner with HlandBase

  /** This is for setting sides on the edge of grids that sit within the heex area of the tile on the neighbouring grid. */
  case class BSide(terr: WSideSome = Sea) extends TRowElem
  {
     def run(row: Int, c: Int): Unit = sTerrs.set(row, c, terr)
  }

  case class VRow(row: Int, edits: VRowElem*) extends RowBase

  sealed trait VRowElem
  { def run(row: Int): Unit
  }

  val rowDatas: RArr[RowBase]

  def run: Unit = rowDatas.foreach{
    case data: TRow => tRowRun(data)
    case data: VRow => data.edits.foreach(_.run(data.row))
  }

  def tRowRun(inp: TRow): Unit =
  { val row = inp.row
    var c = grid.rowLeftCenC(row)
    inp.mutlis.foreach { multi =>
      multi.foreach { help =>
        if (c > grid.rowRightCenC(row)) excep("Too many tiles for row.")
        help match {
          case wt: WTile => tileRun(row, c, wt)
          case il: TRunner => il.run(row, c)
          case _ =>
        }
        c += 4
      }
    }
  }

  def tileRun(row: Int, c: Int, tile: WTile): Unit =
  {  terrs.set(row, c, tile)
  }


  case class SetSide(c: Int, terr: WSideSome = Sea) extends  VRowElem with SetSideBase
  case class Mouth(c: Int, dirn: HVDirn, st: WSideSome = Sea) extends VRowElem with MouthBase

  /** Creates the head of a strait / river / etc with the head up and the straits going down. */
  case class MouthUp(c: Int, st: WSideSome = Sea) extends VRowElem
  { override def run(row: Int): Unit =
    { corners.setMouth3(row + 1, c)
      sTerrs.set(row - 1, c, st)
    }
  }

  /** Creates the head of a strait / river with the head up right and the straits going down left. */
  case class MouthUR(c: Int, st: WSideSome = Sea) extends VRowElem {
    override def run(row: Int): Unit = {
      corners.setMouth4(row + 1, c + 2)
      sTerrs.set(row, c - 1, st)
    }
  }

  /** Creates the head of a strait / river with the head down right and the straits going up left. */
  case class MouthDR(c: Int, st: WSideSome = Sea) extends VRowElem {
    override def run(row: Int): Unit = {
      corners.setMouth5(row - 1, c + 2)
      sTerrs.set(row, c - 1, st)
    }
  }

  /** Creates the head of a strait / river / etc with the head down and the straits going up. */
  case class MouthDn(c: Int, st: WSideSome = Sea) extends VRowElem {
    override def run(row: Int): Unit = {
      corners.setMouth0(row - 1, c)
      sTerrs.set(row + 1, c, st)
    }
  }

  case class VertIn(c: Int, dirn: HVDirn, terr: WSideSome = Sea) extends VRowElem with VertInBase
}