/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Any]]s. */
class AnyArr(val unsafeArray: Array[Any]) extends AnyVal with ArrNoParam[Any]
{ type ThisT = AnyArr

  /** Copy's the backing Array[[Any]] to a new Array[char]. End users should rarely have to use this method. */
  def unsafeArrayCopy(operand: Array[Any], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  override def typeStr: String = "Anys"
  override def unsafeSameSize(length: Int): AnyArr = new AnyArr(new Array[Any](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Any = unsafeArray(index)
  override def setElemUnsafe(i: Int, newElem: Any): Unit = unsafeArray(i) = newElem
  override def fElemStr: Any => String = _.toString

  @targetName("appendArr") def ++ (op: AnyArr): AnyArr =
  { val newArray = new Array[Any](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new AnyArr(newArray)
  }

  override def drop(n: Int): AnyArr = ???

  /** Reverses the order of the elements of this sequence. */
  override def reverse: AnyArr = ???

  /** append. appends element to this [[Arr]]. */
  @targetName("append") override def +%(operand: Any): AnyArr = ???
}

object AnyArr
{ def apply(input: Any*): AnyArr = new AnyArr(input.toArray)

  implicit val EqImplicit: EqT[AnyArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object AnyArrBuild extends ArrMapBuilder[Any, AnyArr] with ArrFlatBuilder[AnyArr]
{ type BuffT = AnyBuff

  override def uninitialised(length: Int): AnyArr = new AnyArr(new Array[Any](length))
  override def indexSet(seqLike: AnyArr, index: Int, elem: Any): Unit = seqLike.unsafeArray(index) = elem
  override def newBuff(length: Int = 4): AnyBuff = new AnyBuff(new ArrayBuffer[Any](length))
  override def buffGrow(buff: AnyBuff, newElem: Any): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: AnyBuff): AnyArr = new AnyArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: AnyBuff, arr: AnyArr): Unit = arr.foreach(el => buff.unsafeBuffer.append(el))
}

final class AnyBuff(val unsafeBuffer: ArrayBuffer[Any]) extends AnyVal with Buff[Any]
{ override type ThisT = AnyBuff
  override def typeStr: String = "AnysBuff"
  override def apply(index: Int): Any = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def setElemUnsafe(i: Int, newElem: Any): Unit = unsafeBuffer(i) = newElem
  override def fElemStr: Any => String = _.toString
  override def grow(newElem: Any): Unit = unsafeBuffer.append(newElem)
  def growArr(newArr: AnyArr): Unit = newArr.unsafeArray.foreach(el => unsafeBuffer.append(el))
}

object AnyArrHead
{ /** Extractor for the head of an Arr, immutable covariant Array based collection. The tail can be any length. */
  def unapply(arr: AnyArr): Option[Any] = ife(arr.nonEmpty, Some(arr(0)), None)
}

/** Extractor object for Arr[A] of length == 1. Arr[A] is an immutable covariant Array based collection. */
object AnyArr1
{ /** Extractor for [[AnyArr]] of length == 1. Arr[A] is an immutable covariant Array based collection. */
  def unapply(arr: AnyArr): Option[Any] = arr.length match
  { case 1 => Some(arr(0))
    case _ => None
  }
}