/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

trait ElemDblNPair[A1 <: ElemDblN, A2] extends ElemPair[A1, A2]

trait DblNPairArr[A1 <: ElemDblN, ArrA1 <: DblNArr[A1], A2, A <: ElemDblNPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]
{ type ThisT <: DblNPairArr[A1, ArrA1, A2, A]

  /** The backing Array for the first elements of the pairs. */
  def a1ArrayDbl: Array[Double]

  def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): ThisT
}

trait DblNPairBuff[B1 <: ElemDblN, B2, B <: ElemDblNPair[B1, B2]] extends PairBuff[B1, B2, B]
{ /** The backing buffer for the B1 components. */
  def b1DblBuffer: ArrayBuffer[Double]

  final def growArr(newElems: DblNPairArr[B1, _, B2, B]): Unit = { newElems.a1ArrayDbl.foreach(b1DblBuffer.append(_))
    newElems.a2Array.foreach(b2Buffer.append(_)) }
}

trait DblNPAirArrCommonBuilder[B1 <: ElemDblN, ArrB1 <: DblNArr[B1], B2, ArrB <: DblNPairArr[B1, ArrB1, B2, _]] extends
PairArrCommonBuilder[B1, ArrB1, B2, ArrB]
{ type BuffT <: DblNPairBuff[B1, B2, _]
  type B1BuffT <: DblNBuff[B1]
  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = newElem.dblForeach(buff.unsafeBuffer.append(_))

  def buffFromBuffers(a1Buffer: ArrayBuffer[Double], a2Buffer: ArrayBuffer[B2]): BuffT

  def arrFromArrays(a1ArrayDbl: Array[Double], a2Array: Array[B2]): ArrB

  final override def newBuff(length: Int): BuffT = buffFromBuffers(new ArrayBuffer[Double](length), new ArrayBuffer[B2](length))

  /** converts a the buffer type to the target compound class. */
  final override def buffToBB(buff: BuffT): ArrB = arrFromArrays(buff.b1DblBuffer.toArray, buff.b2Buffer.toArray)
}

trait DblNPairArrMapBuilder[B1 <: ElemDblN, ArrB1 <: DblNArr[B1], B2, B <: ElemDblNPair[B1, B2], ArrB <: DblNPairArr[B1, ArrB1, B2, B]] extends
DblNPAirArrCommonBuilder[B1, ArrB1, B2, ArrB] with PairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: DblNPairBuff[B1, B2, B]

  def a1DblNum: Int

  final override def arrUninitialised(length: Int): ArrB = arrFromArrays(new Array[Double](length * a1DblNum), new Array[B2](length))

  inline final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)

  override def fromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB = arrFromArrays(a1Buff.toArray, b2s.toArray)
}

trait DblNPairArrFlatBuilder[B1 <: ElemDblN, ArrB1 <: DblNArr[B1], B2, ArrB <: DblNPairArr[B1, ArrB1, B2, _]] extends
DblNPAirArrCommonBuilder[B1, ArrB1, B2, ArrB] with PairArrFlatBuilder[B1, ArrB1, B2, ArrB]
{
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { arr.a1ArrayDbl.foreach(buff.b1DblBuffer.append(_))
    arr.a2Arr.foreach(buff.b2Buffer.append(_)) }
}

/** Helper trait for Companion objects of [[DblNPairArr]] classes. */
trait DblNPairArrCompanion[A1 <: ElemDblN, ArrA1 <: DblNArr[A1]]
{
  /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def elemNumDbls: Int
}