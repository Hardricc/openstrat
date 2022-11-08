/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

trait ElemIntNPair[A1 <: ElemIntN, A2] extends ElemPair[A1, A2]

trait IntNPairArr[A1 <: ElemIntN, ArrA1 <: IntNArr[A1], A2, A <: ElemIntNPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]
{ type ThisT <: IntNPairArr[A1, ArrA1, A2, A]

  /** The backing Array for the first elements of the pairs. */
  def a1ArrayInt: Array[Int]

  def newFromArrays(a1Array: Array[Int], a2Array: Array[A2]): ThisT

  def filterOnA1(f: A1 => Boolean)(implicit ct: ClassTag[A2]): ThisT =
  { val a1Buffer = new ArrayBuffer[Int]()
    val a2Buffer = new ArrayBuffer[A2]()
    foreach{ p =>
      if (f(p.a1))
      { p.a1.intForeach(a1Buffer.append(_))
        a2Buffer.append(p.a2)
      }
    }
    newFromArrays(a1Buffer.toArray, a2Buffer.toArray)
  }
}

/** Specialised efficient [[Buff]] classes for accumulating pairs where the first component of the pair is and [[ElemIntN]]. */
trait IntNPairBuff[B1 <: ElemIntN, B2, B <: ElemIntNPair[B1, B2]] extends PairBuff[B1, B2, B]
{ def b1IntBuffer: ArrayBuffer[Int]

  final def growArr(newElems: IntNPairArr[B1, _, B2, B]): Unit =
  { newElems.a1ArrayInt.foreach(b1IntBuffer.append(_))
    newElems.a2Array.foreach(b2Buffer.append(_))
  }
}

trait IntNPAirArrCommonBuilder[B1 <: ElemIntN, ArrB1 <: IntNArr[B1], B2, ArrB <: IntNPairArr[B1, ArrB1, B2, _]] extends
  PairArrCommonBuilder[B1, ArrB1, B2, ArrB]
{ type BuffT <: IntNPairBuff[B1, B2, _]
  type B1BuffT <: IntNBuff[B1]

  /** Constructs the [[Arr]] class from an [[Array]][Int] object for the first components of the pairs and an [[Array]][B2] for the second
   *  components of the pairs. */
  def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): ArrB

  /** Constructs the [[Buff]] class from an [[ArrayBuffer]][Int] object for the first components of the pairs and an [[ArrayBuffer]][B2] for the
   * second components of the pairs. */
  def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): BuffT

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = newElem.intForeach(buff.unsafeBuffer.append(_))
  final override def newBuff(length: Int): BuffT = buffFromBuffers(new ArrayBuffer[Int](length), new ArrayBuffer[B2](length))
  final override def buffToBB(buff: BuffT): ArrB = arrFromArrays(buff.b1IntBuffer.toArray, buff.b2Buffer.toArray)
}

trait IntNPairArrMapBuilder[B1 <: ElemIntN, ArrB1 <: IntNArr[B1], B2, B <: ElemIntNPair[B1, B2], ArrB <: IntNPairArr[B1, ArrB1, B2, B]] extends
  IntNPAirArrCommonBuilder[B1, ArrB1, B2, ArrB] with PairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: IntNPairBuff[B1, B2, B]

  /** The number of [[Int]]s required to construct the first component of the pairs. */
  def a1IntNum: Int

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  final override def arrFromArrAndArray(b1Arr: ArrB1, b2s: Array[B2]): ArrB = arrFromArrays(b1Arr.unsafeArray, b2s)

  def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): BuffT
  final override def uninitialised(length: Int): ArrB = arrFromArrays(new Array[Int](length * a1IntNum), new Array[B2](length))
  inline final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
  final override def arrFromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB = arrFromArrays(a1Buff.toArray, b2s.toArray)
}

/** Helper trait for Companion objects of [[IntNPairArr]] classes. */
trait IntNPairArrCompanion[A1 <: ElemIntN, ArrA1 <: IntNArr[A1]]
{
  /** The number of [[Int]] values that are needed to construct an element of the defining-sequence. */
  def elemNumInts: Int
}
trait ElemInt2Pair[A1 <: ElemInt2, A2] extends ElemIntNPair[A1, A2]
{ def a1Int1: Int
  def a1Int2: Int
}

trait Int2PairArr[A1 <: ElemInt2, ArrA1 <: Int2Arr[A1], A2, A <: ElemInt2Pair[A1, A2]] extends IntNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Int2PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 2 [[Int]]s and a third parameter of type A2. */
  def newPair(int1: Int, int2: Int, a2: A2): A

  override final def apply(index: Int): A = newPair(a1ArrayInt(index * 2), a1ArrayInt(index * 2 + 1), a2Array(index))

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { a1ArrayInt(i * 2) = value.a1Int1;
    a1ArrayInt(i * 2 + 1) = value.a1Int2
    a2Array(i) = value.a2
  }

  def newA1(int1: Int, int2: Int): A1

  override def a1Index(index: Int): A1 = newA1(a1ArrayInt(index * 2), a1ArrayInt(index * 2 + 1))
}

trait Int2PairBuff[A1 <: ElemInt2, A2, A <: ElemInt2Pair[A1, A2]] extends IntNPairBuff[A1, A2, A]
{ /** Constructs new pair element from 2 [[Int]]s and a third parameter of type A2. */
  def newElem(int1: Int, int2: Int, a2: A2): A
  inline final override def apply(index: Int): A = newElem(b1IntBuffer (index * 2), b1IntBuffer(index * 2 + 1), b2Buffer(index))

  override final def grow(newElem: A): Unit =
  { b1IntBuffer.append(newElem.a1Int1)
    b1IntBuffer.append(newElem.a1Int2)
    b2Buffer.append(newElem.a2)
  }

  def grow(newA1: A1, newA2: A2): Unit =
  { b1IntBuffer.append(newA1.int1)
    b1IntBuffer.append(newA1.int2)
    b2Buffer.append(newA2)
  }

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { b1IntBuffer(i * 3) = value.a1Int1
    b1IntBuffer(i * 3 + 1) = value.a1Int2
    b2Buffer(i) = value.a2
  }
}

trait Int2PairArrMapBuilder[B1 <: ElemInt2, ArrB1 <: Int2Arr[B1], B2, B <: ElemInt2Pair[B1, B2], ArrB <: Int2PairArr[B1, ArrB1, B2, B]] extends
  IntNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Int2PairBuff[B1, B2, B]
  final override def a1IntNum: Int = 2

  final override def indexSet(seqLike: ArrB, index: Int, value: B): Unit = {
    seqLike.a1ArrayInt(index * 3) = value.a1Int1
    seqLike.a1ArrayInt(index * 3 + 1) = value.a1Int2
    seqLike.a2Array(index) = value.a2
  }
}

trait Int2PairArrCompanion[A1 <: ElemInt2, ArrA1 <: Int2Arr[A1]] extends IntNPairArrCompanion[A1, ArrA1]
{
  override def elemNumInts: Int = 2

  def seqToArrays[A2](pairs: Seq[ElemInt2Pair[_, A2]])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
  {  val intsArray = new Array[Int](pairs.length * 2)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      intsArray(i * 2) = p.a1Int1
      intsArray(i * 2 + 1) = p.a1Int2
      a2Array(i) = p.a2
      i += 1
    }
    (intsArray, a2Array)
  }
}

trait ElemInt3Pair[A1 <: ElemInt3, A2] extends ElemIntNPair[A1, A2]
{ def a1Int1: Int
  def a1Int2: Int
  def a1Int3: Int
}

trait Int3PairArr[A1 <: ElemInt3, ArrA1 <: Int3Arr[A1], A2, A <: ElemInt3Pair[A1, A2]] extends IntNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Int3PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 3 [[Int]]s and a third parameter of type A2. */
  def newPair(int1: Int, int2: Int, int3: Int, a2: A2): A

  override final def apply(index: Int): A = newPair(a1ArrayInt(index * 3), a1ArrayInt(index * 3 + 1), a1ArrayInt(index * 3 + 2), a2Array(index))

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { a1ArrayInt(i * 3) = value.a1Int1;
    a1ArrayInt(i * 3 + 1) = value.a1Int2
    a1ArrayInt(i * 3 + 2) = value.a1Int3
    a2Array(i) = value.a2
  }

  def newA1(int1: Int, int2: Int, int3: Int): A1

  override def a1Index(index: Int): A1 = newA1(a1ArrayInt(index * 3), a1ArrayInt(index * 3 + 1), a1ArrayInt(index * 3 + 2))
}

trait Int3PairBuff[A1 <: ElemInt3, A2, A <: ElemInt3Pair[A1, A2]] extends IntNPairBuff[A1, A2, A]
{ /** Constructs new pair element from 3 [[Int]]s and a third parameter of type A2. */
  def newElem(int1: Int, int2: Int, int3: Int, a2: A2): A

  inline final override def apply(index: Int): A = newElem(b1IntBuffer (index * 3), b1IntBuffer(index * 3 + 1), b1IntBuffer(index * 3 + 2), b2Buffer(index))

  override final def grow(newElem: A): Unit =
  { b1IntBuffer.append(newElem.a1Int1)
    b1IntBuffer.append(newElem.a1Int2)
    b1IntBuffer.append(newElem.a1Int3)
    b2Buffer.append(newElem.a2)
  }

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { b1IntBuffer(i * 3) = value.a1Int1
    b1IntBuffer(i * 3 + 1) = value.a1Int2
    b1IntBuffer(i * 3 + 2) = value.a1Int3
    b2Buffer(i) = value.a2
  }
}

trait Int3PairArrMapBuilder[B1 <: ElemInt3, ArrB1 <: Int3Arr[B1], B2, B <: ElemInt3Pair[B1, B2], ArrB <: Int3PairArr[B1, ArrB1, B2, B]] extends
  IntNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Int3PairBuff[B1, B2, B]

  final override def a1IntNum: Int = 3

  final override def indexSet(seqLike: ArrB, index: Int, value: B): Unit = {
    seqLike.a1ArrayInt(index * 3) = value.a1Int1
    seqLike.a1ArrayInt(index * 3 + 1) = value.a1Int2
    seqLike.a1ArrayInt(index * 3 + 2) = value.a1Int3
    seqLike.a2Array(index) = value.a2
  }
}

trait Int3PairArrCompanion[A1 <: ElemInt3, ArrA1 <: Int3Arr[A1]] extends IntNPairArrCompanion[A1, ArrA1]
{
  override def elemNumInts: Int = 3

  def seqToArrays[A2](pairs: Seq[ElemInt3Pair[_, A2]])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
  {  val intsArray = new Array[Int](pairs.length * 3)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      intsArray(i * 3) = p.a1Int1
      intsArray(i * 3 + 1) = p.a1Int2
      intsArray(i * 3 + 2) = p.a1Int3
      a2Array(i) = p.a2
      i += 1
    }
    (intsArray, a2Array)
  }
}