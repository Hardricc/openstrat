/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, math.sqrt, reflect.ClassTag, collection.mutable.ArrayBuffer

/** A grid of Hexs. The grid may be a regular rectangle of hexs or an irregular grid with variable length rows.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
trait HGrid extends TGrid
{ /** The number of tile centre rows where r %% 4 == 0.  */
  def numRow0s: Int

  /** The number of tile centre rows where r %% 4 == 2.  */
  def numRow2s: Int

  /** The number of tile centres in the given row. */
  def rowNumCens(row: Int): Int

  /** The r coordinate of the bottom row of this grid divided by 4 leaves remainder of 0. */
  def rBottomRow0: Boolean = rCenMin.div4Rem0

  /** The r coordinate of the bottom row of this grid divided by 4 leaves remainder of 2. */
  def rBottomRow2: Boolean = rCenMin.div4Rem2

  /** Boolean, true if for top hex tile centre row of this hex grid r %% 4 == 0. */
  final def rTopCenRow0: Boolean = rCenMin.div4Rem0

  /** Boolean, true if for top hex tile centre row of this hex grid r %% 4 == 2. */
  final def rTopRow2: Boolean = rCenMin.div4Rem2

  /** Carries out the procedure function on each [[HCen]] hex tile centre coordinate in the given tile row. This method is defined here rather than on
   *  TileGrid so it can take the specific narrow [[HCen]] parameter to the foreach function. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit

  /** Carries out the procedure function on each [[HCen]] hex tile centre coordinate and an index counter in the given tile row. This method is
   *  defined here rather than on TileGrid so it can take the specific narrow [[HCen]] parameter to the foreach function. */
  def rowIForeach(r: Int, count: Int = 0)(f: (HCen, Int) => Unit): Int

  /** The conversion factor for c column tile grid coordinates. 1.0 / sqrt(3). */
  override def xRatio: Double = 1.0 / sqrt(3)

  /** The centre of the hex grid in terms of c column coordinates. */
  def cCen: Double = (cTileMin + cTileMax) / 2.0

  /** The centre of the hex grid along the X axis after the XRatio has been applied to c column value. */
  final override def xCen: Double = cCen * xRatio

  /** foreachs over each Hex tile's centre HCen. */
  final def foreach(f: HCen => Unit): Unit = foreachRow(r => rowForeach(r)(f))

  final def iForeach(f: (HCen, Int) => Unit) =
  { var count: Int = 0
    foreachRow{r => count = rowIForeach(r, count)(f) }
  }

  /** Maps over the [[HCen]] hex centre tile coordinates. B is used rather than A as a type parameter, as this method maps from HCen => B,
   *  corresponding to the standard Scala map function of A => B. */
  final def map[B, ArrB <: ArrImut[B]](f: HCen => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB =
  { val res = build.newArr(numCens)
    iForeach((hCen, i) => res.unsafeSetElem(i, f(hCen)))
    res
  }

  /** flatMaps from all hex tile centre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   *  TileGrid structure is lost in the flatMap operation. */
  final def flatMap[ArrT <: ArrImut[_]](f: HCen => ArrT)(implicit build: ArrTFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numCens)
    foreach{ hCen => build.buffGrowArr(buff, f(hCen))}
    build.buffToArr(buff)
  }

  /** Is the specified tile centre row empty? */
  final def cenRowEmpty(row: Int): Boolean = rowNumCens(row) == 0

  /** The minimum or starting column of the tile centre of the given row. */
  def cRowCenMin(row: Int): Int

  override def foreachCenCoord(f: TCoord => Unit): Unit = foreach(f)

  /** The active tiles without any PaintElems. */
  def activeTiles: Arr[PolygonActive] = map(_.active())

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(hc: HCen): Int = arrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array data. */
  def arrIndex(r: Int, c: Int): Int

  /** New immutable Arr of Tile data. */
  final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): HCenArr[A] =
  { val res = HCenArr[A](numCens)
    res.mutSetAll(value)
    res
  }

  /** New immutable Arr of Tile data. */
  final def newTileArrArr[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrArr[A] =
  { val newArray = new Array[Array[A]](numCens)
    val init: Array[A] = Array()
    iUntilForeach(0, numCens)(newArray(_) = init)
    new HCenArrArr[A](newArray)
  }

  /** Creates a new [[HCenArrBuff]] An [[HCen] hex tile centre corresponding Arr of empty [[ArrayBuffer]]s of the given or inferred type. */
  final def newHCenArrBuff[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrBuff[A] = HCenArrBuff(numCens)

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newTileArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrOpt[A] = new HCenArrOpt(new Array[A](numCens))

  /** Combine adjacent tiles of the same value. */
  /*def combinedPolygons[A <: AnyRef](implicit arr: HCenArr[A]): Arr[(HVertPolygon, A)] =
  {
    implicit def grid: HGrid = this
    if (numCenRows > 0)
    {
      val incomplete: ArrayBuffer[(HVertPolygon, A)] = Buff()
      val complete: ArrayBuffer[(HVertPolygon, A)] = Buff()

      foreachRow { r =>
        var curr: Option[(HVertPolygon, A)] = None
        rowIForeach(r) { (hc, i) =>
          val newValue: A = arr(hc)(this)
          curr match {
            case None => curr = Some((hc.hVertPolygon, newValue))
            case Some((p, a)) if a == newValue =>
            case Some(pair) => incomplete.append(pair)
          }
        }
      }
      ???
    }
    else Arr()
  }*/

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCenExists(hc: HCen): Boolean = hCenExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCenExists(r: Int, c:Int): Boolean

  /* Methods that operate on Hex tile sides. ******************************************************/

  /** The number of Sides in the TileGrid. Needs reimplementing.
   *  @group SidesGroup */
  final val numSides: Int =
  { var count = 0
    sidesForeach(r => count += 1)
    count
  }

  override def sideLines: LineSegs = sideCoordLines.map(_.toLine2)

  /** foreach Hex side's coordinate HSide, calls the effectfull function.
   * @group SidesGroup */
  final def sidesForeach(f: HSide => Unit): Unit = sideRowForeach(r => rowForeachSide(r)(f))

  /** Calls the Foreach procedure on every Hex Side in the row given by the input parameter.
   *  @group */
  def rowForeachSide(r: Int)(f: HSide => Unit): Unit

  /** maps over each Hex Side's coordinate [[HSide]] in the given Row.
   *  @group SidesGroup */
  final def sidesMap[B, ArrT <: ArrImut[B]](f: HSide => B)(implicit build: ArrTBuilder[B, ArrT]): ArrT =
  {
    val res: ArrT = build.newArr(numSides)
    var count = 0
    sidesForeach{hs =>
      res.unsafeSetElem(count, f(hs))
      count += 1
    }
    res
  }

  /** maps over each Hex Side's coordinate [[HSide]] in the given Row.
   *  @group SidesGroup */
  final def sidesFlatMap[ArrT <: ArrImut[_]](f: HSide => ArrT)(implicit build: ArrTFlatBuilder[ArrT]): ArrT =
  {
    val buff = build.newBuff()// newArr(numSides)
    sidesForeach{hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToArr(buff)
  }

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def sideArrIndex(hc: HSide): Int = sideArrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array data. */
  def sideArrIndex(r: Int, c: Int): Int

  /** Array of indexs for Side data Arrs giving the index value for the start of each side row. */
  def sideRowIndexArray: Array[Int]

  /** The Hex Sides of the Hex Grid defined in integer constructed [[HCoordLineSeg.]].
   *  @group SidesGroup */
  def sideCoordLines: Arr[HCoordLineSeg] = sidesMap[HCoordLineSeg, Arr[HCoordLineSeg]](_.coordLine)

  def newSideBooleans: HSideBooleans = new HSideBooleans(new Array[Boolean](numSides))
}