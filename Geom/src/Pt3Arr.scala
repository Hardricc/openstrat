/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** An immutable flat efficient Array backed sequence collection class of 3 dimensional points. This is the default collection class for [Pt3]s. */
final class Pt3Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl3[Pt3]
{ type ThisT = Pt3Arr
  override def typeStr: String = "Vec3s"
  def elemFromDbls(d1: Double, d2: Double, d3: Double): Pt3 = Pt3(d1, d2, d3)
  def fromArray(array: Array[Double]): Pt3Arr = new Pt3Arr(array)
  override def fElemStr: Pt3 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt3]]s collections. */
final class Pt3Buff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal with Dbl3Buff[Pt3]
{ override def typeStr: String = "Pt3Buff"
  def elemFromDbls(d1: Double, d2: Double, d3: Double): Pt3 = Pt3(d1, d2, d3)
}