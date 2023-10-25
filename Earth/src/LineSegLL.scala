/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import geom._, collection.mutable.ArrayBuffer

/** A 2 dimensional line segment defined in units of latitude and longitude rather than scalars in X and Y. A line on the service of the earth. */
final case class LineSegLL(val dbl1: Double, val dbl2: Double, val dbl3: Double, val dbl4: Double) extends LineSegLikeDbl4[LatLong]
{
//  inline def startSecsLat: Double = dbl1
//  inline def startSecsLong: Double = dbl2
//  inline def endSecsLat: Double = dbl3
//  inline def endSecsLong: Double = dbl4
  def startPt: LatLong = new LatLong(dbl1, dbl2)//startSecsLat, startSecsLong)
  def endPt: LatLong = new LatLong(dbl3, dbl4)//.secs(endSecsLat, endSecsLong)
}

/** Companion object for the [[LineSegLL]] class. */
object LineSegLL
{ def apply(startPt: LatLong, endPt: LatLong): LineSegLL = new LineSegLL(startPt.dbl1, startPt.dbl2, endPt.dbl1, endPt.dbl2)

  /** [[Show]] type class instance / evidence for [[LineSegLL]]. */
  implicit val showEv: Show2[LatLong, LatLong, LineSegLL] = Show2[LatLong, LatLong, LineSegLL]("LineSegLL", "start", _.startPt, "end", _.endPt)

  /** [[Unshow]] type class instance / evidence for [[LineSegLL]]. */
  implicit val unshowEv: Unshow2[LatLong, LatLong, LineSegLL] = Unshow2[LatLong, LatLong, LineSegLL]("Line2", "start", "end", apply)

  /** Implicit instance / evidence for [[MapBuilderArr]] type class. */
  implicit val buildEv: Dbl4ArrMapBuilder[LineSegLL, LineSegLLArr] = new LineSegLLArrMapBuilder
}

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegLLArr(val unsafeArray: Array[Double]) extends LineSegLikeDbl4Arr[LatLong, LineSegLL]
{ type ThisT = LineSegLLArr
  def fromArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)
  override def typeStr: String = "LineSegLLArr"
  override def fElemStr: LineSegLL => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegLL = new LineSegLL(d1, d2, d3, d4)
}

/** Companion object for the LineSegLLs class. */
object LineSegLLArr extends Dbl4SeqLikeCompanion[LineSegLL, LineSegLLArr]
{
  override def fromArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)

  /** [[Show]] type class instance / evidence for [[LineSegLLArr]]. */
  implicit val showEv: ShowSequ[LineSegLL, LineSegLLArr] = ShowSequ[LineSegLL, LineSegLLArr]()

  /** [[Unshow]] type class instance / evidence for [[LineSegLLArr]]. */
  implicit val unshowEv: UnshowArrDbl4[LineSegLL, LineSegLLArr] = UnshowArrDbl4[LineSegLL, LineSegLLArr]("LineSegLLArr", LineSegLLArr.fromArray)

  /** Implicit instance /evidence for [[FlatBuilderArr]] type class instance. */
  implicit val flatBuildEv: FlatBuilderArr[LineSegLLArr] = new LineSegArrLLFlatBuilder
}

/** Efficient expandable buffer for [[LineSegLL]]s. */
class LineSegLLBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl4Buff[LineSegLL]
{ override def typeStr: String = "Line2sBuff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegLL = new LineSegLL(d1, d2, d3, d4)
}

trait LineSegLLArrCommonBuilder extends Dbl4ArrCommonBuilder[LineSegLLArr]
{ type BuffT = LineSegLLBuff
  final override def fromDblArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)
  final def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegLLBuff = new LineSegLLBuff(inp)
}

class LineSegLLArrMapBuilder extends LineSegLLArrCommonBuilder with Dbl4ArrMapBuilder[LineSegLL, LineSegLLArr]
class LineSegArrLLFlatBuilder extends LineSegLLArrCommonBuilder with Dbl4ArrFlatBuilder[LineSegLLArr]