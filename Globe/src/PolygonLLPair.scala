/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import geom._, annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonLLPair[A2](val unsafeArray: Array[Double], val a2: A2) extends PolygonDblsPair[LatLong, PolygonLL, A2] with SpecialT {
  override def a1: PolygonLL = new PolygonLL(unsafeArray)
}

object PolygonLLPair
{
  def apply[A2](poly: PolygonLL, a2: A2): PolygonLLPair[A2] = new PolygonLLPair[A2](poly.unsafeArray, a2)

}

final class PolygonLLPairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikePairArr[LatLong, PolygonLL, PolygonLLArr, A2, PolygonLLPair[A2]]
{ override type ThisT = PolygonLLPairArr[A2]
  override def unsafeSetElem(i: Int, value: PolygonLLPair[A2]): Unit = { arrayArrayDbl(i) = value.unsafeArray; a2Array(i) = value.a2 }
  override def fElemStr: PolygonLLPair[A2] => String = _.toString
  override def typeStr: String = "PolygonLLPairArray"
  override def apply(index: Int): PolygonLLPair[A2] = new PolygonLLPair[A2](arrayArrayDbl(index), a2Array(index))
  override def a1Arr: PolygonLLArr = new PolygonLLArr(arrayArrayDbl)
}

final class PolygonLLPairBuilder[A2](implicit ct: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikePairArrBuilder[LatLong, PolygonLL, PolygonLLArr, A2, PolygonLLPair[A2], PolygonLLPairArr[A2]]
{ override type BuffT = PolygonLLPairBuff[A2]
  override def newArr(length: Int): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonLLPairArr[A2], index: Int, value: PolygonLLPair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.unsafeArray ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonLLPairBuff[A2], value: PolygonLLPair[A2]): Unit = ???
  override def buffGrowArr(buff: PolygonLLPairBuff[A2], arr: PolygonLLPairArr[A2]): Unit = ???
  override def newBuff(length: Int): PolygonLLPairBuff[A2] = new PolygonLLPairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonLLPairBuff[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](buff.arrayDoubleBuff.toArray, buff.a2Buff.toArray)

  /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeBuilder[LatLong, PolygonLL] = LatLong.polygonBuildImplicit

  /** Builder for an Arr of the first element of the pair. */
  override def b1ArrBuilder: ArrBuilder[PolygonLL, PolygonLLArr] = PolygonLL.arrBuildImplicit

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  override def pairArrBuilder(polygonArr: PolygonLLArr, a2s: Array[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](polygonArr.unsafeArrayOfArrays, a2s)
}

class PolygonLLPairBuff[A2](val arrayDoubleBuff: ArrayBuffer[Array[Double]], val a2Buff: ArrayBuffer[A2]) extends
  SeqSpecPairBuff[LatLong, PolygonLL, A2, PolygonLLPair[A2]]
{ override type ThisT = PolygonLLPairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonLLPair[A2]): Unit = { arrayDoubleBuff(i) = value.unsafeArray; a2Buff(i) = value.a2 }
  override def fElemStr: PolygonLLPair[A2] => String = _.toString
  override def typeStr: String = "PolygonLLPairBuff"
  override def apply(index: Int): PolygonLLPair[A2] = new PolygonLLPair[A2](arrayDoubleBuff(index), a2Buff(index))
}