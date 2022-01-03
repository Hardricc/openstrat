/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import reflect.ClassTag

/** An Array of tile centre data of type A. An appropriate [[SqGrid]] or [[HGrid]] is required to utilise its functionality. */
trait TileCenArr[A <: AnyRef] extends Any
{ /** The mutable backing Array. End users should rarely if ever have to access this field */
  def unsafeArray: Array[A]

  /** The number of tile centres this array of data represents. */
  final def length: Int = unsafeArray.length

  /** Set all tiles to the given value. */
  final def mutSetAll(value: A): Unit = iUntilForeach(0, length){i => unsafeArray(i) = value }

  /** For each element in the underlying array performs the side effecting function. This method treats the [[TileCenArr]] class like a standard Arr
   *  or Array. It does not utilise the grid [[TGrid]] from which this [[TileCenArr]] was created. */
  def foreach[U](f: A => U): Unit = unsafeArray.foreach(f)

  /** Each element in the underlying array is mapped by the parameter function to an element of type B. This method treat the [[HCenArr]] class like a
   *  standard Arr or Array. It does not utilise the grid HGrid from which this HCenArr was created. */
  def map[B, BB <: SeqImut[B]](f: A => B)(implicit build: ArrBuilder[B, BB]): BB =
  { val res = build.newArr(length)
    var count = 0
    foreach{a => res.unsafeSetElem(count, f(a)); count += 1 }
    res
  }
}

/** An array of hex tile or hex centre data. */
class SqCenArr[A <: AnyRef](val unsafeArray: Array[A]) extends AnyVal with TileCenArr[A]
{
  def apply(sc: SqCen)(implicit grid: SqGrid): A = unsafeArray(grid.arrIndex(sc))
}

object SqCenArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenArr[A] = new SqCenArr[A](new Array[A](length))
}