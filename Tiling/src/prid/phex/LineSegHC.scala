/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A 2d line upon a HexGrid defined by its start and end [[HGrid]] [[HCoord]]s. */
case class LineSegHC(r1: Int, c1: Int, r2: Int, c2: Int) extends LineSegLike[HCoord] with ElemInt4
{ def int1: Int = r1
  def int2: Int = c1
  def int3: Int = r2
  def int4: Int = c2

  /** The start [[HCoord]] point. */
  def startPt: HCoord = HCoord(r1, c1)

  /** The end [[HCoord]] point. */
  def endPt: HCoord = HCoord(r2, c2)

  /** Uses the implicit [[HGridSysFlat]] parameter to convert from [[HCen]]s to [[Pt2]]s. */
  def lineSeg(implicit grider: HGridSysFlat): LineSeg = LineSeg(startPt.toPt2, endPt.toPt2)
}

/** companion object for [[LineSegHC]] class contains factory apply method. */
object LineSegHC
{ /** Factory apply method to create a hex coordinate line segment a [[LineSegHC]] from the start and end hex coordinates [[HCoord]]s. */
  def apply(hCoord1: HCoord, hCoord2: HCoord): LineSegHC = new LineSegHC(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)

  /** Implicit instance / evidence for [[ArrBuilder]] type class. */
  implicit val buildEv: ArrInt4sBuilder[LineSegHC, LineSegHCArr] = new ArrInt4sBuilder[LineSegHC, LineSegHCArr]
  { type BuffT = LineSegHCBuff
    override def fromIntArray(array: Array[Int]): LineSegHCArr = new LineSegHCArr(array)
    def fromIntBuffer(buffer: ArrayBuffer[Int]): LineSegHCBuff = new LineSegHCBuff(buffer)
  }
}

/** Compact immutable Array[Int] based collection class for [[LineSegHC]]s. LineSegHC is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegHCArr(val unsafeArray: Array[Int]) extends ArrInt4s[LineSegHC]
{ type ThisT = LineSegHCArr
  def fromArray(array: Array[Int]): LineSegHCArr = new LineSegHCArr(array)
  override def typeStr: String = "Line2s"
  override def fElemStr: LineSegHC => String = _.toString
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def newElem(d1: Int, d2: Int, d3: Int, d4: Int): LineSegHC = new LineSegHC(d1, d2, d3, d4)
}

/** Companion object for the LineSegHCs class. */
object LineSegHCArr extends ArrInt4sCompanion[LineSegHC, LineSegHCArr]
{
  val factory: Int => LineSegHCArr = i => new LineSegHCArr(new Array[Int](i * 4))

  /*implicit val persistImplicit: DataInt4sPersist[LineSegHC, LineSegHCs] = new DataDbl4sPersist[LineSegHC, LineSegHCs]("Line2s")
  { override def fromArray(value: Array[Int]): LineSegHCs = new LineSegHCs(value)

    override def showDecT(obj: LineSegHCs, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }*/

  /*implicit val arrArrBuildImplicit: ArrFlatBuilder[LineSegHCs] = new ArrInt4sFlatBuilder[LineSegHC, LineSegHCs]
  { type BuffT = LineSegHCBuff
    override def fromIntArray(array: Array[Int]): LineSegHCs = new LineSegHCs(array)
    def fromDblBuffer(inp: ArrayBuffer[Int]): LineSegHCBuff = new LineSegHCBuff(inp)
  }*/

  //implicit val transImplicit: AffineTrans[LineSegHCs] = (obj, f) => obj.dataMap(_.ptsTrans(f))

  override def buff(initialSize: Int): Int4Buff[LineSegHC, LineSegHCArr] = ???
}

/** Efficient expandable buffer for Line2s. */
class LineSegHCBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Int4Buff[LineSegHC, LineSegHCArr]
{ override def typeStr: String = "Line2sBuff"
  override def intsToT(d1: Int, d2: Int, d3: Int, d4: Int): LineSegHC = new LineSegHC(d1, d2, d3, d4)
}