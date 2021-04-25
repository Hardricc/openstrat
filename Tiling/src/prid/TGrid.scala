/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._, Colour.Black

/** A TileGrid is a description of an abstract TileGrid. It contains no data for the elements of any particular TileGrid. The Data for TileGrids is
 *  stored in flat arrays. The TileGrid gives the dimensions of a tileGrid. It has methods to interpret the data in flat Arrays created for that
 *  TileGrid specification. It has methods to map the elements of an Array to the the 2 dimensional geometry of the Tile Grid. On its own a TileGrid
 *  can produce the outlines of the grid, coordinates vector positions and other pure mathematical data. Combined with a simple function it can for
 *  example produce a Chess board. Combined with a 64 length array it can produce a Chess board position. For anything but the most simple games, you
 *  will probably want multiple arrays to describe the game state. The terrain for example may remain invariant, so the terrain data does not need to
 *  be reproduced with every move.
 *
 *  A TileGrid is for use cases where the proportions of the Grid predetermine the proportions of the visual representation, as opposed to a use case
 *  where the proportions of the enclosing space are a factor in determining the proportions of the grid. For example the various grid layouts of the
 *  Stars on the American flag.
 *  @groupname SidesGroup Tile Sides Methods
 *  @groupdesc SidesGroup Methods that operate on the tile sides of the grid. Remember a TileGrid object contains no data about the sides or the
 *             boundaries of the tiles.
 *  @groupprio SidesGroup 1010 */
trait TGrid
{
  /** Number of rows of tiles. This will be different to the number of rows of sides and and will be different to the number of rows of vertices for
   * HexGrids. */
  def numOfTileRows: Int

  def rTileMin: Int
  def rTileMax: Int

  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  def cTileMin: Int

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  def cTileMax: Int

  def width: Double
  def height: Double

  /** The total number of Tiles in the tile Grid. */
  def numOfTiles: Int

  def xRatio: Double

  def xCen: Double
  def yCen: Double = (rTileMin + rTileMax) / 2

  //def cenPt: Pt2 = Pt2(xCen, yCen)
  def cenVec: Vec2 = Vec2(xCen, yCen)

  /** Foreach grid Row y coordinate. */
  final def foreachRow(f: Int => Unit): Unit = iToForeach(rTileMin, rTileMax, 2)(f)

  /** Foreach tile centre coordinate. A less strongly typed method than the foreach's in the sub traits. */
  def foreachCenCoord(f: TCoord => Unit): Unit

  def mapCenCoords[B, BB <: ArrImut[B]](f: TCoord => B)(implicit build: ArrTBuilder[B, BB]): BB =
  { val res = build.newArr(numOfTiles)
    var count = 0
    foreachCenCoord { tc => res.unsafeSetElem(count, f(tc))
      count += 1
    }
    res
  }

  def fullDisplayScale(dispWidth: Double, dispHeight: Double, padding: Double = 20): Double =
  {
    def adj(inp : Double): Double =inp match
    { case n if n > 1000 => inp - padding
      case n if n > 500 => inp - padding * inp / 1000.0
      case n if n > 10 => n
      case _ => 10
    }
    (adj(dispWidth) / adj(width).max(1)).min(adj(dispHeight) / height.max(1))
  }

  /** The number of Rows of vertices. */
  @inline final def numOfVertRows: Int = ife(numOfTileRows > 1, numOfTileRows + 1, 0)

  /** The active tiles without any PaintElems. */
  def rcTexts = mapCenCoords(tc => tc.toTextGraphic(16, tc.toPt2))

  /* SideGroup Methods that operate on tile sides. **********************************************************/

  /** The number of Rows of Sides.
   *  @group SidesGroup */
  @inline final def numOfSideRows: Int = ife(numOfTileRows > 1, numOfTileRows * 2 + 1, 0)

  /** The bottom Side Row of this TileGrid. The r value, the row number value.
   *  @group SidesGroup */
  @inline final def rSideMin: Int = rTileMin - 1

  /** The top Side Row of this TileGrid. The r value, the row number.
   *  @group SidesGroup*/
  @inline final def rSideMax: Int = rTileMax + 1

  /** Foreachs over each Row of Sides. Users will not normally need to use this method directly.
   *  @group SidesGroup */
  def sideRowForeach(f: Int => Unit) : Unit = iToForeach(rTileMin - 1, rTileMax + 1)(f)

  /** The line segments [[LineSeg]]s for the sides of the tiles.
   *  @group SidesGroup */
  def sideLines: LineSegs //= ???
  /*flatMap { roord =>
    val c1: Roords = sideRoordsOfTile(roord)
    val c2s: LineSegs = c1.map(orig => sideRoordToLine2(orig))
    c2s
  }*/

  /** This gives the all tile grid lines in a single colour and line width.
   *  @group SidesGroup  */
  final def sidesDraw(colour: Colour = Black, lineWidth: Double = 2.0): LinesDraw = sideLines.draw(lineWidth, colour)
}
