/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be constructed from a fixed number of [[Long]]s. It can be stored as an Array[Long] of primitive values. */
trait LongNElem extends Any with ValueNElem

/** Base trait for Array[Long] based collections of Products of Longs. */
trait LongNsSeq[A <: LongNElem] extends Any with ValueNsSeq[A]
{ def array: Array[Long]
  def arrLen = array.length

  /** The number of Longs, that specify / construct an element of this immutable flat Array based collection class. */
  def elemProdSize: Int
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait LongNsBuffer[A <: LongNElem] extends Any with ValueNsBuffer[A]
{ def buffer: ArrayBuffer[Long]
  def toArray: Array[Long] = buffer.toArray[Long]
//  def unBuff: M
  def grow(newElem: A): Unit
//  def addAll(newElems: M): Unit = { buffer.addAll(newElems.array); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[LongNsSeq]] final classes. Instances for the [[SeqBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[SeqFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
abstract class LongNsArrBuilders[B <: LongNElem, ArrB <: LongNsSeq[B]](typeStr: String) extends ValueNsArrPersist[B, ArrB](typeStr)
{ type VT = Long
  override def fromBuffer(buf: Buff[Long]): ArrB = fromArray(buf.toArray)
  override def newBuffer: Buff[Long] = Buff[Long](0)
}

/** Helper trait for Companion objects of [[LongNsSeq]] classes. */
trait ProductLongsCompanion[A <: LongNElem, ArrA <: LongNsSeq[A]] extends ValueNsDataCompanion[A, ArrA]
{ def fromBuffer(buff: Buff[Long]): ArrA = fromArray(buff.toArray[Long])
  //val factory: Int => M
  def fromArray(array: Array[Long]): ArrA
}