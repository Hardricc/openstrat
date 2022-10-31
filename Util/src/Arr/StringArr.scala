/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for [[String]]s. */
class StringArr(val unsafeArray: Array[String]) extends AnyVal with ArrSingle[String]
{ override type ThisT = StringArr
  override def typeStr: String = "Strings"
  override def unsafeSameSize(length: Int): StringArr = new StringArr(new Array[String](length))
  override def unsafeSetElem(i: Int, value: String): Unit = unsafeArray(i) = value
  override def fElemStr: String => String = s => s
  override def apply(index: Int): String = unsafeArray(index)
  override def length: Int = unsafeArray.length

  /** Make 1 string with separator from this collection of strings. */
  def mkStr(separator: String): String = if(empty) ""
  else {
    var acc = head
    tailForeach{ s => acc += separator + s }
    acc
  }

  /** Append. */
  def ++ (operand: StringArr): StringArr =
  { val newArray: Array[String] = new Array[String](length + operand.length)
    var i = 0
    while (i < length) {
      newArray(i) = unsafeArray(i)
      i += 1
    }
    i = 0
    while (i < operand.length) {
      newArray(i + length) = unsafeArray(i)
      i += 1
    }
    new StringArr(newArray)
  }

  /** Alias for append. Functionally appends the operand [[String]]. */
  @inline def +%(op: String): StringArr = append(op)
  /** Functionally appends the operand [[String]]. This method by the :+ operator, rather than the +- operator alias used for append on [[RArr]] to
   *  avoid confusion with arithmetic operations. */
  def append(op: String): StringArr =
  { val newArray = new Array[String](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = op
    new StringArr(newArray)
  }

  def appendOption(optElem: Option[String]): StringArr =
    optElem.fld(this, this +% _)

  def appendsOption(optElem: Option[StringArr]): StringArr =
    optElem.fld(this, ++ _)

  /** Finds the index of the first [[String]] element that fulfills the predicate parameter or returns -1. */
  def findIndex(f: String => Boolean): Int =
  {
    def loop(index: Int): Int = index match
    { case i if i == length => -1
      case i if f(apply(i)) => i
      case i => loop(i + 1)
    }
    loop(0)
  }
}

/** Companion object of ArrStrings class contains repeat parameter apply factor method. */
object StringArr
{ /** Repeat parameter apply factor method. */
  def apply(input: String*): StringArr = new StringArr(input.toArray)

  implicit val eqImplicit: EqT[StringArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object StringArrBuilder extends ArrMapBuilder[String, StringArr] with ArrFlatBuilder[StringArr]
{ type BuffT = StringBuff
  override def arrUninitialised(length: Int): StringArr = new StringArr(new Array[String](length))
  override def arrSet(arr: StringArr, index: Int, value: String): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): StringBuff = new StringBuff(new ArrayBuffer[String](length))
  override def buffGrow(buff: StringBuff, value: String): Unit = buff.unsafeBuffer.append(value)
  override def buffToBB(buff: StringBuff): StringArr = new StringArr(buff.unsafeBuffer.toArray)

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  override def buffGrowArr(buff: StringBuff, arr: StringArr): Unit = buff.grows(arr)
}

class StringBuff(val unsafeBuffer: ArrayBuffer[String]) extends AnyVal with Buff[String]
{   override type ThisT = StringBuff

  override def typeStr: String = "Stringsbuff"
  override def apply(index: Int): String = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: String): Unit = unsafeBuffer(i) = value
  override def fElemStr: String => String = s => s
  override def grow(newElem: String): Unit = unsafeBuffer.append(newElem)
}

object StringBuff
{ def apply(startSize: Int = 4): StringBuff = new StringBuff(new ArrayBuffer[String](startSize))
}