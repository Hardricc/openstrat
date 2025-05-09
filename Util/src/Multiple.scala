/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import ostrat.pParse.*, annotation.unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** The Multiple type class allow you to represent multiple values of type A. Implicit conversion in package object. To create a Multiple instance follow the
 * value by the "*" symbol followed by an integer. There is a n implicit conversion from an object of type to a Multiple of type T with quantity of 1. */
case class Multiple[+A](value: A, num: Int)
{ /** multiply the [[Multiple]] number with the operand. */
  def * (operand: Int): Multiple[A] = Multiple(value, num * operand)

  override def toString = "Multiple" + (value.toString + "; " + num.toString).enParenth

  def foreach(f: A => Unit): Unit = num.doTimes(() => f(value))

  def map[B](f: A => B): Multiple[B] = Multiple[B](f(value), num)

  def flatMap[B](f: A => Multiple[B]): Multiple[B] =
  { val res: Multiple[B] = f(value)
    Multiple[B](res.value, res.num * num)
  }

  def toArr[ArrA <: Arr[A]@uncheckedVariance](implicit build: BuilderArrMap[A, ArrA]@uncheckedVariance): ArrA =
  { val res: ArrA = build.uninitialised(num)
    iUntilForeach(num){i => res.setElemUnsafe(i, value)}
    res
  }
}

/** Companion object for the [[Multiple]][+A] type class. */
object Multiple
{
  implicit def arrMapBuilderEv[A](implicit ct: ClassTag[A]): MultipleArrMapBuilder[A] = new MultipleArrMapBuilder[A]

  implicit def eqImplicit[A](implicit ev: EqT[A]): EqT[Multiple[A]] = (a1, a2) => (a1.num == a2.num) & ev.eqT(a1.value, a2.value)

  implicit def toMultipleImplicit[A](value: A): Multiple[A] = Multiple(value, 1)

  implicit class RefsImplicit[A](thisRefs: RArr[Multiple[A]])
  { /** The total number of elements in this sequence of [[Multiple]]s. */
    def numSingles: Int = thisRefs.sumBy(_.num)

    /** Converts this sequence of [[Multiple]]s to an [[Arr]] of the type of the [[Multiple]]. */
    def toArr[R <: Arr[A]](implicit builder: BuilderArrMap[A, R]): R =
    { val newLen = thisRefs.numSingles
      val res = builder.uninitialised(newLen)
      var i = 0
      thisRefs.foreach { multi =>
        iUntilForeach(multi.num) { j => res.setElemUnsafe(i + j, multi.value) }
        i = i + multi.num
      }
      res
    }

    def toColl[R](builder: BuilderCollMap[A, R]): R =
    { val buff = builder.newBuff()
      thisRefs.foreach { multi => iUntilForeach(multi.num) { _ => builder.buffGrow(buff, multi.value) } }
      builder.buffToSeqLike(buff)
    }
  }

  implicit def seqImplicit[A](thisSeq: Seq[Multiple[A]]): MultipleSeqImplicit[A] = new MultipleSeqImplicit[A](thisSeq)

  /** [[Show]] type class instance / evidence for [[Multiple]] class. */
  implicit def showEv[A](typeStr: String)(implicit evA: Show[A]): MultipleShow[A] = new MultipleShow[A](typeStr)(evA)

  class MultipleShow[A](val typeStr: String)(implicit val evA: Show[A]) extends Show[Multiple[A]]()
  { override def strT(obj: Multiple[A]): String = show(obj, ShowStdNoSpace)
    override def syntaxDepth(obj: Multiple[A]): Int = evA.syntaxDepth(obj.value)

    override def show(obj: Multiple[A], style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = style match
    { case ShowTyped | ShowStdTypedFields => showFullEv.show(obj, style, maxPlaces, minPlaces)
      case _ if obj.num == 1 => evA.show(obj.value, ShowStdNoSpace, maxPlaces, minPlaces)
      case _ => evA.show(obj.value, ShowStdNoSpace, maxPlaces, minPlaces) + " * " + obj.num
    }
  }

  /** [[Show]] type class instance / evidence for full show of [[Multiple]] class. */
  def showFullEv[A](implicit evA: Show[A]): Show2[A, Int, Multiple[A]] = Show2[A, Int, Multiple[A]]("Multiple", "value", _.value, "num", _.num)

  /** [[Unshow]] type class instance / evidence for [[Multiple]] class. */
  implicit def unshowEv[A](implicit evA: Unshow[A]): UnshowMultiple[A] = new UnshowMultiple[A]()(evA)

  class UnshowMultiple[A]()(implicit val evA: Unshow[A]) extends Unshow[Multiple[A]]
  { override def typeStr: String = "Multiple"
    override def useMultiple: Boolean = false

    override def fromExpr(expr: Expr): ExcMon[Multiple[A]] =  expr match
    { case InfixOpExpr(left, OperatorPrec1Token(startPosn, "*"), IntExpr(i)) => evA.fromExpr(left).map(a => Multiple(a, i))
      case AlphaMaybeSquareParenth(name,  RArr2(Statement(e1), Statement(IntExpr(i)))) if name == "Multiple" => evA.fromExpr(e1).map{ a => Multiple(a, i) }
      case expr => evA.fromExpr(expr).map(a => Multiple(a, 1))
    }

    def fromArrExpr(inp: Arr[Expr]): ExcMon[RArr[Multiple[A]]] = inp.mapErrBi(fromExpr(_))

    /** Collection from [[Arr]] of [[Expr]]. */
    def collFromArrExpr[R](inp: Arr[Expr], builderColl: BuilderCollMap[A, R]): ExcMon[R] = fromArrExpr(inp).map(_.toColl(builderColl))

    /** Collection from [[Arr]] of [[Statement]]. */
    def collFromArrStatement[R](inp: Arr[Statement], builderColl: BuilderCollMap[A, R]): ExcMon[R] = collFromArrExpr(inp.map(_.expr), builderColl)
  }

  /** Collection from [[Arr]] of [[Expr]]. */
  def collFromArrExpr[Ae, A](inp: Arr[Expr])(implicit evA: Unshow[Ae], builderColl: BuilderCollMap[Ae, A]): ExcMon[A] =
    unshowEv(evA).fromArrExpr(inp).map(_.toColl(builderColl))

  /** Collection from [[Arr]] of [[Statement]]. */
  def collFromArrStatement[A, R](inp: Arr[Statement])(implicit evA: Unshow[A], builderColl: BuilderCollMap[A, R]): ExcMon[R] =
    unshowEv(evA).collFromArrExpr(inp.map(_.expr), builderColl)  
}

class MultipleArr[A](arrayInt: Array[Int], values: Array[A]) extends Arr[Multiple[A]]
{ type ThisT = MultipleArr[A]
  override def typeStr: String = "MultipleArr"

  def numSingles: Int = this.sumBy(_.num)

  def toSinglesArr[ArrA <: Arr[A]](implicit build: BuilderArrMap[A, ArrA]): ArrA =
  { val res = build.uninitialised(numSingles)
    var i = 0
    foreach{ m => iUntilForeach(m.num){ _ => res.setElemUnsafe(i, m.value); i += 1 } }
    res
  }
  
  override def elem(index: Int): Multiple[A] = new Multiple[A](values(index), arrayInt(index))
  override def apply(index: Int): Multiple[A] = new Multiple[A](values(index), arrayInt(index))
  override def length: Int = arrayInt.length
  override def numElems: Int = arrayInt.length
  override def setElemUnsafe(index: Int, newElem: Multiple[A]): Unit = { values(index) = newElem.value; arrayInt(index) =newElem.num }
  override def fElemStr: Multiple[A] => String = _.toString
  def unsafeSameSize(length: Int)(implicit ct: ClassTag[A]): ThisT = new MultipleArr[A](new Array[Int](length), new Array[A](length))
}

class MultipleSeqImplicit[A](thisSeq: Seq[Multiple[A]])
{ /** Extension method. The number of single values of type A in this [[Seq]] of [[Multiple]]s. */
  def numSingles: Int = thisSeq.sumBy (_.num)

  /** Extension method. Converts this [[Seq]] of [[Multiple]]s, to an [[Arr]] of the Single values
   * of type A. The appropriate Arr type is found by implicit look up for type A. */
  def toSinglesArr[ArrA <: Arr[A]](implicit build: BuilderArrMap[A, ArrA]): ArrA =
  { val res = build.uninitialised(numSingles)
    var i = 0
    thisSeq.foreach { m => iUntilForeach(m.num) { _ => res.setElemUnsafe(i, m.value); i += 1 } }
    res
  }

  /** Foreachs over each single value  of this [[Seq]] of [[Multiple]]s with an index. */
  def iForeachSingle(f: (Int, A) => Unit): Unit =
  { var i = 0
    thisSeq.foreach{m => repeat(m.num){ f(i, m.value); i += 1} }
  }
}

class MultipleBuff[A](val numBuffer: ArrayBuffer[Int], val valuesBuffer: ArrayBuffer[A]) extends BuffSequ[Multiple[A]]
{ override type ThisT = MultipleBuff[A]
  override def typeStr: String = "MultipleBuff"
  override def grow(newElem: Multiple[A]): Unit = { numBuffer.append(newElem.num); valuesBuffer.append(newElem.value) }
  override def apply(index: Int): Multiple[A] = new Multiple[A](valuesBuffer(index), numBuffer(index))
  override def elem(index: Int): Multiple[A] = new Multiple[A](valuesBuffer(index), numBuffer(index))
  override def length: Int = numBuffer.length
  override def numElems: Int = numBuffer.length
  override def setElemUnsafe(index: Int, newElem: Multiple[A]): Unit = { numBuffer(index) = newElem.num; valuesBuffer(index) = newElem.value }
  override def fElemStr: Multiple[A] => String = _.toString
}

object MultipleBuff
{ def apply[A](initLen: Int = 4): MultipleBuff[A] = new MultipleBuff[A](new ArrayBuffer[Int](initLen), new ArrayBuffer[A](initLen))
}

class MultipleArrMapBuilder[A](implicit ct: ClassTag[A]) extends BuilderArrMap[Multiple[A], MultipleArr[A]]
{ override type BuffT = MultipleBuff[A]
  override def buffGrow(buff: MultipleBuff[A], newElem: Multiple[A]): Unit = buff.grow(newElem)
  override def uninitialised(length: Int): MultipleArr[A] = new MultipleArr[A](new Array[Int](length), new Array[A](length))
  override def indexSet(seqLike: MultipleArr[A], index: Int, newElem: Multiple[A]): Unit = { seqLike.setElemUnsafe(index, newElem) }
  override def newBuff(length: Int): MultipleBuff[A] = MultipleBuff(length)
  override def buffToSeqLike(buff: MultipleBuff[A]): MultipleArr[A] = new MultipleArr[A](buff.numBuffer.toArray, buff.valuesBuffer.toArray)
}