/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Sequence specified objects. An immutable class that can be specified by a sequence of elements. Uses a backing Array for efficient storage.
 *  Examples include  polygons and line paths that can be specified by a sequence of points. */
trait SeqSpec[+A] extends Any with SeqLike[A @uncheckedVariance]
{ type ThisT <: SeqSpec[A]

  /** Accesses the specifying sequence element by a 0 based index. */
  @inline def ssIndex(index: Int): A

  /** The number of data elements in the defining sequence. These collections use underlying mutable Arrays and ArrayBuffers. The length of the
   * underlying Array will be a multiple of this number. */
  def ssLength: Int

  /** Performs a side effecting function on each element of the specifying sequence in order. */
  def ssForeach[U](f: A => U): Unit =
  { var i = 0
    while (i < ssLength) {
      f(ssIndex(i))
      i = i + 1
    }
  }

  /** Foreachs over the tail of the specifying sequence. Performs a side effecting function on each element of the tail of the specifying sequence in
   *  order. */
  def ssTailForeach[U](f: A => U): Unit =
  { var count = 1
    while (count < ssLength) {
      f(ssIndex(count)); count += 1
    }
  }

  /** Index with foreach on the the specifying sequence elements. Performs a side effecting function on the index and each element of the specifying
   * sequence. It takes a function as a parameter. The function may return Unit. If it does return a non Unit value it is discarded. The [U] type
   * parameter is there just to avoid warnings about discarded values and can be ignored by method users. The method has 2 versions / name overloads.
   * The default start for the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first
   * parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of putting the collection
   * element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. */
  def ssIForeach[U](f: (Int, A) => Any): Unit =
  { var i = 0
    while (i < ssLength) {
      f(i, ssIndex(i))
      i = i + 1
    }
  }

  /** Index with foreach on the data elements. Performs a side effecting function on the index and each element of the data sequence. It takes a
   * function as a parameter. The function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just
   * to avoid warnings about discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for
   * the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set
   * the start value of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as
   * seen for example in fold methods' (accumulator, element) => B signature. */
  def ssIForeach[U](initIndex: Int)(f: (Int, A) => U): Unit = {
    var i = 0
    while (i < ssLength) {
      f(i + initIndex, ssIndex(i))
      i = i + 1
    }
  }

  /** Specialised map to an immutable [[Arr]] of B. For [[Sequ]] dataMap is the same as map, but for other structures it will be different, for
   * example a PolygonLike will map to another PolygonLike. */
  def ssMap[B, ArrB <: Arr[B]](f: A => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB = {
    val res = ev.newArr(ssLength)
    ssIForeach((i, a) => ev.arrSet(res, i, f(a)))
    res
  }

  /** specifying -sequence fold. */
  def ssFold[B](initVal: B)(f: (B, A) => B): B = {
    var res = initVal
    ssForeach(a => res = f(res, a))
    res
  }

  /** Performs a side effecting function on each element of the specifying-sequence in reverse order. The function may return Unit. If it does return a
   *  non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method
   *  users. */
  def ssReverseForeach[U](f: A => U): Unit = {
    var count = ssLength
    while (count > 0) {
      count -= 1; f(ssIndex(count))
    }
  }

  /** Last element of the specifying sequence. */
  def ssLast: A = ssIndex(ssLength - 1)

  /** FoldLeft over the tail of the specifying sequence. */
  def ssTailFold[B](initial: B)(f: (B, A) => B) = {
    var acc: B = initial
    ssTailForeach(a => acc = f(acc, a))
    acc
  }

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  override def elemsStr: String = ssMap(fElemStr).mkString("; ").enParenth
}

/** [[ShowT] type class for showing [[DataGen]][A] objects. */
class SeqSpecShowT[A, R <: SeqSpec[A]](val evA: ShowT[A]) extends ShowTSeqLike[A, R]
{
  override def syntaxDepthT(obj: R): Int = obj.ssFold(1)((acc, a) => acc.max(evA.syntaxDepthT(a)))
  override def showDecT(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    typeStr + evA.typeStr.enSquare + obj.ssMap(a => evA.showDecT(a, style, maxPlaces, minPlaces))
}