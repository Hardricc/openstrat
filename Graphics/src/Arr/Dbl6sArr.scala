/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from 6 [[Double]]s. These are used in [[Dbl6sArr]] Array[Double] based collections. */
trait Dbl6Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl6Elem]]s. */
trait Dbl6sArr[A <: Dbl6Elem] extends Any with DblNsArr[A]
{ def elemProductNum: Int = 6
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A

  def apply(index: Int): A =
  { val offset = index * 6
    newElem(arrayUnsafe(offset), arrayUnsafe(offset + 1), arrayUnsafe(offset + 2), arrayUnsafe(offset + 3), arrayUnsafe(offset + 4), arrayUnsafe(offset + 5))
  }

  def setElem(index: Int, elem: A): Unit =
  { val offset = index * 6
    arrayUnsafe(offset) = elem.dbl1; arrayUnsafe(offset + 1) = elem.dbl2; arrayUnsafe(offset + 2) = elem.dbl3; arrayUnsafe(offset + 3) = elem.dbl4; arrayUnsafe(offset + 4) = elem.dbl5
    arrayUnsafe(offset + 5) = elem.dbl6
  }

  def head1: Double = arrayUnsafe(0); def head2: Double = arrayUnsafe(1); def head3: Double = arrayUnsafe(2); def head4: Double = arrayUnsafe(3); def head5: Double = arrayUnsafe(4)
  def head6: Double = arrayUnsafe(5)

  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5, el.dbl6)))
}

/** Helper class for companion objects of final [[Dbl6sArr]] classes. */
abstract class Dbl6sArrCompanion[A <: Dbl6Elem, ArrA <: Dbl6sArr[A]]
{ val factory: Int => ArrA
  def apply(length: Int): ArrA = factory(length)

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { val offset = count * 6
      res.arrayUnsafe(offset) = elems(count).dbl1; res.arrayUnsafe(offset + 1) = elems(count).dbl2; res.arrayUnsafe(offset + 2) = elems(count).dbl3
      res.arrayUnsafe(offset + 3) = elems(count).dbl4; res.arrayUnsafe(offset + 4) = elems(count).dbl5; res.arrayUnsafe(offset + 5) = elems(count).dbl6
      count += 1
    }
    res
  }

  def doubles(elems: Double*): ArrA =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 6)
    var count: Int = 0
    while (count < arrLen) { res.arrayUnsafe(count) = elems(count); count += 1 }
    res
  }

  def fromList(list: List[A]): ArrA =
  { val res = factory(list.length)
    var count: Int = 0
    var rem = list
     
    while (count < list.length)
    { val offset = count * 6
      res.arrayUnsafe(offset) = rem.head.dbl1; res.arrayUnsafe(offset +  1) = rem.head.dbl2; res.arrayUnsafe(offset +  2) = rem.head.dbl3; res.arrayUnsafe(offset +  3) = rem.head.dbl4
      res.arrayUnsafe(offset +  4)= rem.head.dbl5; res.arrayUnsafe(offset + 5 )= rem.head.dbl6
      count += 1
      rem = rem.tail
    }
    res
  }
}