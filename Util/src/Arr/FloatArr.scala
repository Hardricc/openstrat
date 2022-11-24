/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Float]]s. */
class FloatArr(val unsafeArray: Array[Float]) extends AnyVal with ArrNoParam[Float]
{ type ThisT = FloatArr
  override def typeStr: String = "FloatArr"
  override def unsafeSameSize(length: Int): FloatArr = new FloatArr(new Array[Float](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Float = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Float): Unit = unsafeArray(i) = value
  def unsafeArrayCopy(operand: Array[Float], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Float => String = _.toString

  override def tail: FloatArr =
  { val newArray = new Array[Float]((length - 1).max0)
    iUntilForeach(1, length) { i => newArray(i - 1) = unsafeArray(i) }
    new FloatArr(newArray)
  }

  override def reverse: FloatArr =
  { val newArray = new Array[Float](length)
    iUntilForeach(0, length) { i => newArray(i) = unsafeArray(length - 1 - i) }
    new FloatArr(newArray)
  }

  override def append(op: FloatArr): FloatArr =
  { val newArray = new Array[Float](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new FloatArr(newArray)
  }
}

object FloatArr
{ def apply(input: Float*): FloatArr = new FloatArr(input.toArray)

  implicit val eqImplicit: EqT[FloatArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object FloatArrBuilder extends ArrMapBuilder[Float, FloatArr] with ArrFlatBuilder[FloatArr]
{ type BuffT = FloatBuff
  override def uninitialised(length: Int): FloatArr = new FloatArr(new Array[Float](length))
  override def indexSet(seqLike: FloatArr, index: Int, value: Float): Unit = seqLike.unsafeArray(index) = value
  override def newBuff(length: Int = 4): FloatBuff = new FloatBuff(new ArrayBuffer[Float](length))
  override def buffGrow(buff: FloatBuff, value: Float): Unit = buff.unsafeBuffer.append(value)
  override def buffToSeqLike(buff: FloatBuff): FloatArr = new FloatArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: FloatBuff, arr: FloatArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

class FloatBuff(val unsafeBuffer: ArrayBuffer[Float]) extends AnyVal with Buff[Float]
{ override type ThisT = FloatBuff
  override def typeStr: String = "FloatsBuff"
  override def apply(index: Int): Float = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Float): Unit = unsafeBuffer(i) = value
  override def fElemStr: Float => String = _.toString
  override def grow(newElem: Float): Unit = unsafeBuffer.append(newElem)
}