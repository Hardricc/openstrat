/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 2 [[Int]]s. These are used in [[ArrInt2]] Array[Int] based collections. */
trait Int2Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int
  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]) : Unit = buffer.append2(int1, int2)
}

/** Specialised immutable flat [[Array]][Int] based classes. */
trait SeqLikeInt2[A <: Int2Elem] extends Any with SeqLikeIntN[A]
{ override def elemProdSize: Int = 2
  final override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex2(index, newElem.int1, newElem.int2)
  def newElem(i1: Int, i2: Int): A
}

/** A specialised immutable, flat [[Array]][Int] based trait defined by a data sequence of a type of [[Int2Elem]]s. */
trait SeqSpecInt2[A <: Int2Elem] extends Any with SeqLikeInt2[A] with SeqSpecIntN[A]
{ final override def index(index: Int): A = newElem(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))
  final override def ssElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2)
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int2Elem]]s. */
trait ArrInt2[A <: Int2Elem] extends Any with ArrIntN[A] with SeqLikeInt2[A]
{ def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  final override def length: Int = arrayUnsafe.length / 2
  final override def apply(index: Int): A = newElem(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))
  override def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2)

  @targetName("appendElem") final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](arrayLen + 2)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex2(length, operand.int1, operand.int2)
    fromArray(newArray)
  }
}

/** base trait for constructing [[SeqlikeInt2]] objects by both map and flatMap methods. */
trait BuilderSeqLikeInt2[BB <: SeqLike[?]] extends BuilderSeqLikeIntN[BB]
{ type BuffT <: BuffInt2[?]
  final override def elemProdSize: Int = 2
}

/** Builds [[SeqLikeInt2]] objects via the map method. */
trait BuilderSeqLikeInt2Map[B <: Int2Elem, BB <: SeqLikeInt2[B]] extends BuilderSeqLikeInt2[BB] with BuilderSeqLikeIntNMap[B, BB]
{ type BuffT <: BuffInt2[B]
  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit = seqLike.arrayUnsafe.setIndex2(index, newElem.int1, newElem.int2)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.unsafeBuffer.append2(newElem.int1, newElem.int2)
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrInt2]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int2Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt2Map[B <: Int2Elem, ArrB <: ArrInt2[B]] extends BuilderSeqLikeInt2Map[B, ArrB] with BuilderArrIntNMap[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrInt2]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[BuilderArrFlat] should go in the companion
 *  object the ArrT final class. The first type parameter is called B a sub class of Int2Elem, because to corresponds to the B in the
 *  ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt2Flat[ArrB <: ArrInt2[?]] extends BuilderSeqLikeInt2[ArrB] with BuilderArrIntNFlat[ArrB]

/** A specialised flat ArrayBuffer[Int] based trait for [[Int2Elem]]s collections. */
trait BuffInt2[A <: Int2Elem] extends Any with BuffIntN[A]
{ type ThisT <: BuffInt2[A]

  /** Constructs a new element of this [[BuffSequ]] from 2 [[Int]]s. */
  def newElem(i1: Int, i2: Int): A

  override def elemProdSize: Int = 2
  final override def length: Int = unsafeBuffer.length / 2
  override def grow(newElem: A): Unit = unsafeBuffer.append2(newElem.int1, newElem.int2)
  def growInts(int1: Int, int2: Int): Unit = unsafeBuffer.append2(int1, int2)
  override def apply(index: Int): A = newElem(unsafeBuffer(index * 2), unsafeBuffer(index * 2 + 1))
  override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer.setIndex2(i, newElem.int1, newElem.int2)
}

/** Helper class for companion objects of final [[SeqSpecInt2]] classes. */
trait CompanionSeqLikeInt2[A <: Int2Elem, ArrA <: SeqLikeInt2[A]] extends CompanionSeqLikeIntN[A, ArrA]
{ override def elemNumInts: Int = 2

  /** Apply factory method */
  def apply(elems: A*): ArrA =
  { val res = uninitialised(elems.length)
    var i: Int = 0
    while (i < elems.length)
    { res.arrayUnsafe.setIndex2(i, elems(i).int1, elems(i).int2)
      i += 1
    }
    res
  }
}

/** Helper trait for Companion objects of buffers of [[Int2Elem]]s. */
trait CompanionBuffInt2[A <: Int2Elem, AA <: BuffInt2[A]] extends CompanionBuffIntN[A, AA]
{
  override def apply(elems: A*): AA =
  { val buffer: ArrayBuffer[Int] =  new ArrayBuffer[Int](elems.length * 2 + 6)
    elems.foreach{ elem => buffer.append2(elem.int1, elem.int2) }
    fromBuffer(buffer)
  }

  final override def elemNumInts: Int = 2
}