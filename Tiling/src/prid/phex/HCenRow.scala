/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

class HCenRow(val r: Int, val c: Int, val num: Int)
{
  def verts: HVertArr = new HVertArr(setHVertArray)

  /** The polygon of this tile, specified in [[HVert]] coordinates. */
  def hVertPolygon: PolygonHC = new PolygonHC(setHVertArray)

  /** Creates the backing Array[Int] of [[HVert]]s for this HCenRow. This same array can be used inside an [[HVertArr]] or a [[PolygonHC]] class. */
  def setHVertArray: Array[Int] =
  { val res = new Array[Int]((num * 4 + 2) * 2)
    res.set2Elems(0, r + 1, c + 2)
    iToForeach(2, num ){ i =>
      res.set2Elems(i * 2 - 3, r + 1, c + i * 4 - 4)
      res.set2Elems(i * 2 - 2, r + 1, c + i * 4 - 2)
    }
    iToForeach(num, 2, - 1) { i =>
      res.set2Elems(num * 4 - i * 2 - 1, r - 1, c + i * 4 - 2)
      res.set2Elems(num * 4 - i * 2,     r - 1, c + i * 4 - 4)
    }
    res.set2Elems(num * 4 - 3, r - 1, c + 2)
    res.set2Elems(num * 4 - 2, r - 1, c)
    res.set2Elems(num * 4 - 1, r - 1, c - 2)
    res.set2Elems(num * 4,     r + 1, c - 2)
    res.set2Elems(num * 4 + 1, r + 1, c)
    res
  }
}

object HCenRow
{
  def apply(r: Int, c: Int, num: Int): HCenRow = new HCenRow(r, c, num)
}

case class HCenRowPair[A](r: Int, c: Int, num: Int, a2: A) extends PairElem[HCenRow, A]
{
  override def a1: HCenRow = HCenRow(r, c, num)

  def polygonHCTuple: PolygonHCPair[A] = new PolygonHCPair[A](a1.setHVertArray, a2)
}