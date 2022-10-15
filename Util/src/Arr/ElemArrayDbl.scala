/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Trait for Array[Double] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Double]s. */
trait ArrayDblBacked extends Any
{ def unsafeArray: Array[Double]
}

/** Not sure if this class is needed. */
trait ElemArrayDbl extends Any// with SpecialT
{
  def unsafeArray: Array[Double]
}

/** Base trait for collections of elements that are based on [[array]][Double]s, backed by an underlying Array[Array[Double]]. */
trait ArrayDblArr[A <: ArrayDblBacked] extends Any with Arr[A]
{ type ThisT <: ArrayDblArr[A]
  def unsafeArrayOfArrays: Array[Array[Double]]
  override final def length: Int = unsafeArrayOfArrays.length
  def unsafeFromArrayArray(array: Array[Array[Double]]): ThisT
  final def unsafeSameSize(length: Int): ThisT = unsafeFromArrayArray(new Array[Array[Double]](length))
  final def unsafeSetElem(i: Int, value: A): Unit = unsafeArrayOfArrays(i) = value.unsafeArray
}

/** This is the builder for Arrays Arrays of Double. It is not the builder for Arrays of Double.  */
trait ArrayDblArrBuilder[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]] extends ArrBuilder[A, ArrT]
{ @inline def fromArray(array: Array[Array[Double]]): ArrT
  type BuffT <: ArrayDblBuff[A]
  @inline override def newArr(length: Int): ArrT = fromArray(new Array[Array[Double]](length))
  override def arrSet(arr: ArrT, index: Int, value: A): Unit = arr.unsafeArrayOfArrays(index) = value.unsafeArray
  override def buffToBB(buff: BuffT): ArrT = fromArray(buff.unsafeBuffer.toArray)
  override def buffGrow(buff: BuffT, value: A): Unit = { buff.unsafeBuffer.append(value.unsafeArray); () }
  override def buffGrowArr(buff: BuffT, arr: ArrT): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArrayOfArrays); () }
  //override def buffGrowArr(buff: BuffT, arr: ArrT): Unit = arr.unsafeArrayOfArrays.foreach{array => buff.unsafeBuff.append(array) }
}

class ArrArrayDblEq[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]] extends EqT[ArrT]
{ override def eqT(a1: ArrT, a2: ArrT): Boolean = if (a1.length != a2.length) false
  else a1.iForAll((i, el1) =>  el1.unsafeArray === a2(i).unsafeArray)
}

object ArrArrayDblEq
{ def apply[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]]: ArrArrayDblEq[A, ArrT] = new ArrArrayDblEq[A, ArrT]
}

/** This is a buffer class for Arrays of Double. It is not a Buffer class for Arrays. */
trait ArrayDblBuff[A <: ArrayDblBacked] extends Any with Sequ[A]
{ def unsafeBuffer: ArrayBuffer[Array[Double]]
  override final def length: Int = unsafeBuffer.length

  def grow(elem: A): Unit = unsafeBuffer.append(elem.unsafeArray)

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[Arr]]'s final type. */
  override def unsafeSameSize(length: Int): ThisT = ???

  def arrayArrayDbl: Array[Array[Double]] = unsafeBuffer.toArray
}