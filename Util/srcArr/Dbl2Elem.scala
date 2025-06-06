/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance

/** An object that can be constructed from 2 [[Double]]s. These are used as elements in [[ArrDbl2]] Array[Double] based collections. */
trait Dbl2Elem extends Any, DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dblsEqual(that: Dbl2Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2
  def dblsApprox(that: Dbl2Elem, delta: Double = 1e-12): Boolean = dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta)
  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2) }
  override def dblBufferAppend(buffer: ArrayBuffer[Double]) : Unit = buffer.append2(dbl1, dbl2)
}

/** A Sequence like class of [[Dbl2Elem]] elements that can be constructed from 2 [[Double]]s. */
trait SeqLikeDbl2[+A <: Dbl2Elem] extends Any, SeqLikeValueN[A]
{ /** Constructs a [[Dbl2Elem]] from 2 [[Double]]s. */
  def elemFromDbls(d1: Double, d2: Double): A

  final override def elemProdSize: Int = 2
  final override def elemEq(a1: A @uncheckedVariance, a2: A @uncheckedVariance): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2)
}

/** A Sequence like class of [[Dbl2Elem]] elements that can be constructed from 2 [[Double]]s. */
trait SeqLikeDbl2Imut[+A <: Dbl2Elem] extends Any, SeqLikeDblNImut[A], SeqLikeDbl2[A]
{
  override def setElemUnsafe(index: Int, newElem: A @uncheckedVariance): Unit = arrayUnsafe.setIndex2(index, newElem.dbl1, newElem.dbl2)

  /** Produces a new [[Array]][Double] of the same size, with the functions acting on the first and second [[Double]] of each element. */
  def arrayUnsafeMap2(f1: Double => Double, f2: Double => Double): Array[Double] =
  { val newArray = new Array[Double](arrayLen)
    var i = 0
    while(i < arrayLen / 2)
    { newArray(i * 2) = f1(arrayUnsafe(i * 2))
      newArray(i * 2 + 1) = f2(arrayUnsafe(i * 2 + 1))
      i += 1
    }
    newArray
  }

  /** This maps from the final type to the final type by just using functions on the underlying [[Double]]s. */
  def dblsMap(f1: Double => Double, f2: Double => Double): ThisT = fromArray(arrayUnsafeMap2(f1, f2))

  /** Maps the 2 [[Double]]s of each element to a new [[Array]][Double]. */
  def arrayElemMap(f: A => A @uncheckedVariance): Array[Double] =
  { val newArray: Array[Double] = new Array[Double](arrayUnsafe.length)
    iUntilForeach(0, arrayLen, 2) { i =>
      val newElem = f(elemFromDbls(arrayUnsafe(i), arrayUnsafe(i + 1)))
      newArray(i) = newElem.dbl1
      newArray(i + 1) = newElem.dbl2
    }
    newArray
  }

  /** Maps the [[Tuple2]][Double, Double]s to [[Tuple2]][Double, Double}. */
  def arrayT2T2Map(f: (Double, Double) => (Double, Double)): Array[Double] =
  { val newArray: Array[Double] = new Array[Double](arrayUnsafe.length)
    iUntilForeach(0, arrayLen, 2) { i =>
      val (new1, new2) = f(arrayUnsafe(i), arrayUnsafe(i + 1))
      newArray(i) = new1
      newArray(i + 1) = new2
    }
    newArray
  }

  /** Maps the 1st [[Double]]s of each element to a [[double]] with one functions and then the second [[Doubles]] with a second [[Double]]. */
  def arrayD1D2Map(f1: Double => Double)(f2: Double => Double): Array[Double] =
  { val newArray: Array[Double] = new Array[Double](arrayUnsafe.length)
    iUntilForeach(0, arrayLen, 2) { i => newArray(i) = f1(arrayUnsafe(i)) }
    iUntilForeach(1, arrayLen, 2) { i => newArray(i) = f2(arrayUnsafe(i)) }
    newArray
  }

  /** Maps the 1st [[Double]] of each element to a new [[Array]][Double], copies the 2nd elements. */
  def arrayD1Map(f: Double => Double): Array[Double] =
  { val newArray: Array[Double] = new Array[Double](arrayUnsafe.length)
    iUntilForeach(0, arrayLen, 2) { i => newArray(i) = f(arrayUnsafe(i)) }
    iUntilForeach(1, arrayLen, 2) { i => newArray(i) = arrayUnsafe(i) }
    newArray
  }

  /** Maps the 2nd [[Double]] of each element with the parameter function to a new [[Array]][Double], copies the 1st [[Double]] of each element. */
  def arrayD2Map(f: Double => Double): Array[Double] =
  { val newArray: Array[Double] = new Array[Double](arrayUnsafe.length)
    iUntilForeach(0, arrayLen, 2) { i => newArray(i) = arrayUnsafe(i) }
    iUntilForeach(1, arrayLen, 2) { i => newArray(i) = f(arrayUnsafe(i)) }
    newArray
  }

  def elem1sArray: Array[Double] =
  { val res = new Array[Double](arrayLen / 2)
    var count = 0
    while (count < arrayLen / 2) {res(count) = arrayUnsafe(count * 2); count += 1 }
    res
  }

  def elem2sArray: Array[Double] =
  { val res = new Array[Double](arrayLen / 2)
    var count = 0
    while (count < arrayLen / 2) {res(count) = arrayUnsafe(count * 2 + 1); count += 1 }
    res
  }

  final override def elem(index: Int): A = elemFromDbls(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))
  final override def numElems: Int = arrayUnsafe.length / 2
}

object SeqLikeDbl2Imut
{ /** Puts the elements into an [[Array]]. */
  def array(elems: Dbl2Elem*): Array[Double] =
  { val newArray: Array[Double] = new Array[Double](elems.length * 2)
    var i = 0
    while(i < elems.length)
    { newArray(i * 2) = elems(i).dbl1
      newArray(i * 2 + 1) = elems(i).dbl2
      i += 1
    }
    newArray
  }
}

/** A sequence-defined specialised immutable, flat Array[Double] based trait defined by a sequence of a type of [[Dbl2Elem]]s. */
trait SeqSpecDbl2[+A <: Dbl2Elem] extends Any with SeqLikeDbl2Imut[A] with SeqSpecDblN[A]
{ def tailForeachPair[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < numElems) { f(arrayUnsafe(count * 2), arrayUnsafe(count * 2 + 1)); count += 1 }
  }
}

/** A specialised immutable, flat Array[Double] based sequence of a type of [[Dbl2Elem]]s. */
trait ArrDbl2[A <: Dbl2Elem] extends Any with ArrDblN[A] with SeqLikeDbl2Imut[A]
{ type ThisT <: ArrDbl2[A]
  final override def length: Int = arrayUnsafe.length / 2
  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def getPair(index: Int): (Double, Double) = (arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))

  /** Foreachs over the [[Double]] pairs of the tail of this [[Arr]]. */
  def tailPairsForeach[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < length) { f(arrayUnsafe(count * 2), arrayUnsafe(count * 2 + 1)); count += 1 }
  }

  override def apply(index: Int): A = elemFromDbls(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](arrayLen + 2)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex2(length, operand.dbl1, operand.dbl2)
    fromArray(newArray)
  }

  override def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2)))
}

/** Base trait for Builders for [[SeqLike]]s with [[Dbl2Elem]] elements via both map and flatMap methods. */
trait BuilderSeqLikeDbl2[BB <: SeqLikeDbl2Imut[?]] extends BuilderSeqLikeDblN[BB]
{ type BuffT <: BuffDbl2[?]
  final override def elemProdSize = 2
}

/** Builder for [[SeqLike]]s with [[Dbl2Elem]] elements via the map method. Hence the type of the element is known at the call site. */
trait BuilderSeqLikeDbl2Map[B <: Dbl2Elem, BB <: SeqLikeDbl2Imut[B]] extends BuilderSeqLikeDbl2[BB] with BuilderSeqLikeDblNMap[B, BB]
{ type BuffT <: BuffDbl2[B]
  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit = seqLike.arrayUnsafe.setIndex2(index, newElem.dbl1, newElem.dbl2)
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrDbl2]] final classes. Instances for the [[BuilderArrMap]] type class, for classes / traits
 * you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called B, because it corresponds to the
 * B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait BuilderArrDbl2Map[B <: Dbl2Elem, ArrB <: ArrDbl2[B]] extends BuilderSeqLikeDbl2Map[B, ArrB] with BuilderArrDblNMap[B, ArrB]

/** Trait for creating the ArrTFlatBuilder type class instances for [[ArrDbl2]] final classes. Instances for [[BuilderArrFlat] should go in the companion object
 * the ArrT final class. The first type parameter is called B, because it corresponds to the B in
 * ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait BuilderArrDbl2Flat[ArrB <: ArrDbl2[?]] extends BuilderSeqLikeDbl2[ArrB] with BuilderArrDblNFlat[ArrB]

/** Class for the singleton companion objects of [[ArrDbl2]] final classes to extend. */
trait CompanionSeqLikeDbl2[A <: Dbl2Elem, AA <: SeqLikeDbl2Imut[A]] extends CompanionSeqLikeDblN[A, AA]
{ final def numElemDbls: Int = 2

  /** Apply factory method for creating Arrs of [[Dbl2Elem]]s. */
  def apply(elems: A*): AA =
  { val length = elems.length
    val res = uninitialised(length)
    var i: Int = 0
    while (i < length)
    { res.arrayUnsafe.setIndex2(i, elems(i).dbl1, elems(i).dbl2)
      i += 1
    }
    res
  }
}

/** [[BuffSequ]] class for building [[Dbl2Elem]]s collections. */
trait BuffDbl2[A <: Dbl2Elem] extends Any, BuffDblN[A], SeqLikeDbl2[A]
{ type ArrT <: ArrDbl2[A]
  final override def length: Int = bufferUnsafe.length / 2
  final override def numElems: Int = bufferUnsafe.length / 2
  final override def grow(newElem: A): Unit = bufferUnsafe.append2(newElem.dbl1, newElem.dbl2)
  final override def apply(index: Int): A = elemFromDbls(bufferUnsafe(index * 2), bufferUnsafe(index * 2 + 1))
  final override def elem(index: Int): A = elemFromDbls(bufferUnsafe(index * 2), bufferUnsafe(index * 2 + 1))
  final override def setElemUnsafe(index: Int, newElem: A): Unit = bufferUnsafe.setIndex2(index, newElem.dbl1, newElem.dbl2)
}

trait CompanionBuffDbl2[A <: Dbl2Elem, AA <: BuffDbl2[A]] extends CompanionBuffDblN[A, AA]
{
  override def apply(elems: A*): AA =
  { val buffer: ArrayBuffer[Double] =  new ArrayBuffer[Double](elems.length * 2 + 6)
    elems.foreach{ elem => buffer.append2(elem.dbl1, elem.dbl2) }
    fromBuffer(buffer)
  }

  final override def elemNumDbls: Int = 2
}