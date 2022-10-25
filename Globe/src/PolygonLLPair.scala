/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import geom._, annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonLLPair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDblNPair[LatLong, PolygonLL, A2] with SpecialT {
  override def a1: PolygonLL = new PolygonLL(a1ArrayDbl)
}

object PolygonLLPair
{ def apply[A2](poly: PolygonLL, a2: A2): PolygonLLPair[A2] = new PolygonLLPair[A2](poly.unsafeArray, a2)
}

final class PolygonLLPairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDblNPairArr[LatLong, PolygonLL, PolygonLLArr, A2, PolygonLLPair[A2]]
{ override type ThisT = PolygonLLPairArr[A2]
  override def unsafeSetElem(i: Int, value: PolygonLLPair[A2]): Unit = { arrayArrayDbl(i) = value.a1ArrayDbl; a2Array(i) = value.a2 }
  override def fElemStr: PolygonLLPair[A2] => String = _.toString
  override def typeStr: String = "PolygonLLPairArray"
  override def apply(index: Int): PolygonLLPair[A2] = new PolygonLLPair[A2](arrayArrayDbl(index), a2Array(index))
  override def a1Arr: PolygonLLArr = new PolygonLLArr(arrayArrayDbl)
  override def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](array1, array2)
  override def a1Buff: ArrayDblBuff[PolygonLL] = ???

  override def a1FromArrayDbl(array: Array[Double]): PolygonLL = new PolygonLL(array)
}

final class PolygonLLPairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[LatLong, PolygonLL, PolygonLLArr, A2, PolygonLLPair[A2], PolygonLLPairArr[A2]]
{ override type BuffT = PolygonLLPairBuff[A2]
  override type B1BuffT = PolygonLLBuff
  override def arrUninitialised(length: Int): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonLLPairArr[A2], index: Int, value: PolygonLLPair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.a1ArrayDbl ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonLLPairBuff[A2], value: PolygonLLPair[A2]): Unit = ???
  override def newBuff(length: Int): PolygonLLPairBuff[A2] = new PolygonLLPairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonLLPairBuff[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](buff.arrayDoubleBuff.toArray, buff.a2Buffer.toArray)

  /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeBuilder[LatLong, PolygonLL] = LatLong.polygonBuildImplicit

  /** Builder for an Arr of the first element of the pair. */
  override def b1ArrBuilder: ArrBuilder[PolygonLL, PolygonLLArr] = PolygonLL.arrBuildImplicit

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  override def pairArrBuilder(b1Arr: PolygonLLArr, b2s: Array[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)

  override def newB1Buff(): PolygonLLBuff = PolygonLLBuff()

  override def fromArrayArrayDbl(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](arrayArrayDbl, a2Array)

  override def fromBuffs(a1Buff: PolygonLLBuff, b2s: ArrayBuffer[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](a1Buff.arrayArrayDbl, b2s.toArray)
}

class PolygonLLPairBuff[A2](val arrayDoubleBuff: ArrayBuffer[Array[Double]], val a2Buffer: ArrayBuffer[A2]) extends
  SeqSpecPairBuff[LatLong, PolygonLL, A2, PolygonLLPair[A2]]
{ override type ThisT = PolygonLLPairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonLLPair[A2]): Unit = { arrayDoubleBuff(i) = value.a1ArrayDbl; a2Buffer(i) = value.a2 }
  override def fElemStr: PolygonLLPair[A2] => String = _.toString
  override def typeStr: String = "PolygonLLPairBuff"
  override def apply(index: Int): PolygonLLPair[A2] = new PolygonLLPair[A2](arrayDoubleBuff(index), a2Buffer(index))

  override def grow(newElem: PolygonLLPair[A2]): Unit = ???

  override def grows(newElems: Arr[PolygonLLPair[A2]]): Unit = ???
}