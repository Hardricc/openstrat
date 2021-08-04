/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all classes backed by Arrays, ArrayBuffers etc. */
trait ArrayLikeBacked[A] extends Any
{
  /** The number of elements in the collection. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying
   * Array will be this number or a multiple of this number. */
  def elemsNum: Int

  /** Just a handy short cut to give the length of this collection as a string. */
  def elemsLenStr: String = elemsNum.toString

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   *  methods. */
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit

  /** Sets / mutates elements in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory methods. */
  def unsafeSetElems(index: Int, elems: A @uncheckedVariance *): Unit = elems.iForeach((a, i) => unsafeSetElem(i, a), index)

  def fElemStr: A @uncheckedVariance => String

  /** apply method accesses the individual elements of the sequence by 0 based index. */
  @inline def indexData(index: Int): A
}