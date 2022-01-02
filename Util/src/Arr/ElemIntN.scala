/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be construct from a fixed number of [[Int]]s can be stored as an Array[Int] of primitive values. */
trait ElemIntN extends Any with ElemValueN

trait DataIntNs[A <: ElemIntN] extends Any with DataValueNs[A]
{ type ThisT <: DataIntNs[A]

  /** The backing Array[Int] of this collection class. End users should not normally need to interact with this directly. */
  def unsafeArray: Array[Int]

  def unsafeFromArray(array: Array[Int]): ThisT

  /** The length of the Array[Int] backing array. */
  def arrLen = unsafeArray.length

  override def reverseData: ThisT =
  { val res: ThisT = unsafeSameSize(dataLength)
    dataIForeach({ (i, el) => res.unsafeSetElem(dataLength - 1 - i, el)})
    res
  }
  /** Method for creating a new Array[Int] backed collection class of this collection class's final type. */
  final override def unsafeSameSize(length: Int): ThisT = unsafeFromArray(new Array[Int](length * elemProdSize))
}

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait ArrIntNs[A <: ElemIntN] extends Any with ArrValueNs[A] with DataIntNs[A]
{ /** The final type of this Array[Int] backed collection class. */
  type ThisT <: ArrIntNs[A]
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrIntNs]] final classes. Instances for the [[ArrBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait ArrIntNsBuilder[B <: ElemIntN, ArrB <: ArrIntNs[B]] extends ArrValueNsBuilder[B, ArrB]
{ type BuffT <:  BuffIntNs[B]
  def fromIntArray(inp: Array[Int]): ArrB

  /* Not sure about the return type of this method. */
  def fromIntBuffer(inp: ArrayBuffer[Int]): BuffT
  final override def newArr(length: Int): ArrB = fromIntArray(new Array[Int](length * elemProdSize))
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromIntArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[ArrIntNs]] final classes. Instances for [[ArrFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait ArrIntNsFlatBuilder[B <: ElemIntN, ArrB <: ArrIntNs[B]] extends ArrValueNsFlatBuilder[B, ArrB]
{ type BuffT <:  BuffIntNs[B]
  def fromIntArray(inp: Array[Int]): ArrB

  /* Not sure about the return type of this method. */
  def fromIntBuffer(inp: ArrayBuffer[Int]): BuffT
  //final override def newArr(length: Int): ArrB = fromIntArray(new Array[Int](length * elemSize))
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromIntArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Specialised flat ArrayBuffer[Int] based collection class. */
trait BuffIntNs[A <: ElemIntN] extends Any with BuffValueNs[A]
{ type ArrT <: ArrIntNs[A]
  def unsafeBuffer: ArrayBuffer[Int]
  def toArray: Array[Int] = unsafeBuffer.toArray[Int]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuffer.addAll(newElems.unsafeArray); () }
  override def dataLength = unsafeBuffer.length / elemProdSize
}

/**  Class to persist specialised flat Array[Int] based collections. */
abstract class DataIntNsPersist[A <: ElemIntN, M <: DataIntNs[A]](typeStr: String) extends DataValueNsPersist[A, M](typeStr)
{ type VT = Int
  override def fromBuffer(buf: Buff[Int]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Int] = Buff[Int](0)
}

/** Helper trait for Companion objects of [[IntNArr]] collection classes, where the type parameter ArrA is the [[ElemIntN]] type of the of the
 *  collection class. */
trait DataIntNsCompanion[A <: ElemIntN, ArrA <: DataIntNs[A]] extends DataValueNsCompanion[A, ArrA]
{ /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an ArrayBuffer[Int]. */
  def fromBuffer(buff: Buff[Int]): ArrA = fromArray(buff.toArray[Int])

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  def fromArray(array: Array[Int]): ArrA

  /** returns a collection class of type ArrA, whose backing Array[Int] is uninitialised. */
  override implicit def uninitialised(length: Int): ArrA = fromArray(new Array[Int](length * elemProdSize))
}