/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import scala.reflect.ClassTag

/** An immutable Arr of Tile data for a specific TileGrid. This is specialised for AnyRef types The tileGrid can map the Roord of the Tile to the
 *  index of the Arr. Hence most methods take an implicit TIleGrid parameter. */
class TilesArr[A <: AnyRef](val unsafeArr: Array[A])
{
  def length: Int = unsafeArr.length

  def apply(roord: Roord)(implicit grid: TileGrid): A = unsafeArr(grid.arrIndex(roord))
  def apply(y: Int, c: Int)(implicit grid: TileGrid): A = unsafeArr(grid.arrIndex(y, c))
  /** Set tile row from the Roord. */
  final def setRow(roord: Roord, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord = setRow(roord.y, roord.c, tileValues: _*)(grid)

  /** Note set Row starts with the y (row) parameter. */
  final def setRow(yRow: Int, cStart: Int, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles: List[A] = tileValues.toSingles
    tiles.iForeach { (e, i) =>
      val c = cStart + i * grid.cStep
      unsafeArr(grid.arrIndex(yRow, c)) = e
    }
    Roord(yRow, cStart + (tiles.length - 1) * grid.cStep)
  }

  def foreach(f: (Roord, A) => Unit)(implicit grid: TileGrid): Unit = grid.foreach{ r => f(r, unsafeArr(grid.arrIndex(r))) }

  def mutSetAll(value: A): Unit = iUntilForeach(0, length){i => unsafeArr(i) = value }

  def sqSetAllOfRow(y: Int, tileMakers: Multiple[A]*)(implicit grid: SquareGridSimple): Unit =
  { val tiles = tileMakers.flatMap(_.singlesList)
    tiles.iForeach{(el , i) =>
      val index = grid.arrIndex(y, grid.cTileMin + i * 2)
      unsafeArr(index) =  el
    }
  }

  /** Note set RowBack starts with the y (row) parameter */
  final def setRowBack(yRow: Int, cStart: Int, tileMakers: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles = tileMakers.toSingles
    tiles.iForeach{(el, i) =>
      val c = cStart - i * grid.cStep
      val index = grid.arrIndex(yRow, c)
      unsafeArr(index) = el
    }
    Roord(yRow, cStart - (tiles.length - 1) * grid.cStep)
  }

  final def setRowBack(roord: Roord, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
  setRowBack(roord.y, roord.c, tileValues: _*)(grid)

  final def setColumn(c: Int, yStart: Int, tileMakers: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles = tileMakers.flatMap(_.singlesList)
    tiles.iForeach{(el, i) =>
      val y = yStart + i * 2
      val index = grid.arrIndex(y, c)
      unsafeArr(index) =  el
    }
    Roord(yStart + (tiles.length - 1) * 2, c)
  }

  final def setColumn(roordStart: Roord, multis: Multiple[A]*)(implicit grid: TileGrid): Roord =
  setColumn(roordStart.c, roordStart.y, multis: _*)(grid)

  final def setColumnDown(c: Int, yStart: Int, tileMakers: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles = tileMakers.flatMap(_.singlesList)

    tiles.iForeach{(el, i) =>
      val y = yStart - i * 2
      val index = grid.arrIndex(y, c)
      unsafeArr(index) = el
    }
    Roord(c, yStart - (tiles.length - 1) * 2)
  }

  def setColumnDown(roordStart: Roord, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
  setColumnDown(roordStart.c, roordStart.y, tileValues: _*)(grid)

  def setTerrPath(startRoord: Roord, value: A, dirns: Multiple[SquareGrid.PathDirn]*)(implicit grid: SquareGridSimple): Roord =
  {
    var curr = startRoord
    import SquareGrid._

    dirns.foreach
    { case Multiple(Rt, i) => curr = setRow(curr, value * i)
    case Multiple(Lt, i) => curr = setRowBack(curr, value * i)
    case Multiple(Up, i) => curr = setColumn(curr, value * i)
    case Multiple(Dn, i) => curr = setColumnDown(curr, value * i)
    }
    curr
  }

  /** Sets a rectangle of tiles to the same terrain type. */
  def sqGridSetRect(yFrom: Int, yTo: Int, cFrom: Int, cTo: Int, tileValue: A)(implicit grid: SquareGridSimple): Unit =
    ijToForeach(yFrom, yTo, 2)(cFrom, cTo, 2) { (y, c) => unsafeArr(grid.arrIndex(y, c)) =  tileValue }
}

object TilesArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): TilesArr[A] = new TilesArr[A](new Array[A](length))

  implicit class TilesListImplicit[A](thisRefs: TilesArr[List[A]])
  { def prependAt(y: Int, c: Int, value: A)(implicit grid: TileGrid): Unit = prependAt(Roord(y, c), value)
    def prependAt(roord: Roord, value: A)(implicit grid: TileGrid): Unit = thisRefs.unsafeArr(grid.arrIndex(roord)) ::= value
    def prependAts(value : A, roords: Roord*)(implicit grid: TileGrid): Unit = roords.foreach{ r =>  thisRefs.unsafeArr(grid.arrIndex(r)) ::= value }

    def gridHeadsMap[B, BB <: ArrImut[B]](f: (Roord, A) => B)(implicit grid: TileGrid, build: ArrTBuilder[B, BB]): BB =
    {
      val buff = build.newBuff()
      grid.foreach { r => thisRefs(r) match
        {
          case h :: _ => build.buffGrow(buff, f(r, h))
          case _ =>
        }
      }
      build.buffToArr(buff)
    }

    def gridHeadsFlatMap[BB <: ArrImut[_]](f: (Roord, A) => BB)(implicit grid: TileGrid, build: ArrTFlatBuilder[BB]): BB =
    {
      val buff = build.newBuff()
      grid.foreach { r => thisRefs(r) match
      {
        case h :: _ => build.buffGrowArr(buff, f(r, h))
        case _ =>
      }
      }
      build.buffToArr(buff)
    }
  }
}

/** An immutable Arr of Boolean Tile data for a specific TileGrid. This is specialised for Boolean. The tileGrid can map the Roord of the Tile to the
 *  index of the Arr. Hence most methods take an implicit TIleGrid parameter. */
class TileBooleans(val unsafeArr: Array[Boolean]) extends AnyVal
{
  def gridSetTrues(roords: Roords)(implicit grid: TileGrid): Unit = roords.foreach(r => unsafeArr(grid.sideArrIndex(r)) = true)
  def gridSetTrues(roords: Roord*)(implicit grid: TileGrid): Unit = roords.foreach(r => unsafeArr(grid.sideArrIndex(r)) = true)

  def gridMap[A, AA <: ArrImut[A]](f: (Roord, Boolean) => A)(implicit grid: TileGrid, build: ArrTBuilder[A, AA]): AA =
    grid.map(r => f(r, unsafeArr(grid.sideArrIndex(r))))
}