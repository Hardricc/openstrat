/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for [[Unshow5]], declares the common properties of name1 - 5 and opt1 - 5. */
trait PersistBase5Plus[A1, A2, A3, A4, A5] extends Any with PersistBase4Plus[A1, A2, A3, A4]
{ /** 5th parameter name. */
  def name5: String

  /** The optional default value for parameter 5. */
  def opt5: Option[A5]
}

trait PersistBase5[A1, A2, A3, A4, A5] extends Any with PersistBase5Plus[A1, A2, A3, A4, A5]
{ override def paramNames: StrArr = StrArr(name1, name2, name3, name4, name5)
  override def numParams: Int = 5
}

/** [[Showing]] type class for 5 parameter case classes. */
trait Show5T[A1, A2, A3, A4, A5, R] extends PersistBase5[A1, A2, A3, A4, A5] with ShowNing[R]
{ override def persist1: Showing[A1]
  override def persist2: Showing[A2]
  override def persist3: Showing[A3]
  override def persist4: Showing[A4]
}

/** Companion object for [[Show5T]] trait contains implementation class and factory apply method. */
object Show5T
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, opt5: Option[A5] = None, opt4: Option[A4] = None, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Showing[A1], ev2: Showing[A2], ev3: Showing[A3], ev4: Showing[A4], ev5: Showing[A5]) =
    new Show5TImp[A1, A2, A3, A4, A5, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, opt5, opt4, opt3, opt2, opt1)(
      ev1, ev2, ev3, ev4, ev5)

  /** Show type class for 5 parameter case classes. */
  class Show5TImp[A1, A2, A3, A4, A5, R](val typeStr: String, val name1: String, fArg1: R => A1, val name2: String, fArg2: R => A2,
    val name3: String, fArg3: R => A3, val name4: String, fArg4: R => A4, val name5: String, fArg5: R => A5, val opt5: Option[A5],
    opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val persist1: Showing[A1], val persist2: Showing[A2], val persist3: Showing[A3], val persist4: Showing[A4], ev5: Showing[A5]) extends Show5T[A1, A2, A3, A4, A5, R]// with TypeStr5Plus[A1, A2, A3, A4, A5]
  {
    val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
    val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepthT(obj: R): Int = persist1.syntaxDepthT(fArg1(obj)).max(persist2.syntaxDepthT(fArg2(obj))).max(persist3.syntaxDepthT(fArg3(obj))).
      max(persist4.syntaxDepthT(fArg4(obj))).max(ev5.syntaxDepthT(fArg5(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
      StrArr(persist1.showT(fArg1(obj), way), persist2.showT(fArg2(obj), way), persist3.showT(fArg3(obj), way), persist4.showT(fArg4(obj), way),
        ev5.showT(fArg5(obj), way))
  }
}

/** [[Unshow]] trait for 5 parameter product / case classes. */
trait Unshow5[A1, A2, A3, A4, A5, R] extends UnshowN[R] with PersistBase5Plus[A1, A2, A3, A4, A5]
{ def fArg1: R => A1
  def fArg2: R => A2
  def fArg3: R => A3
  def fArg4: R => A4
  def fArg5: R => A5
  def newT: (A1, A2, A3, A4, A5) => R

  implicit override def persist1: Unshow[A1]
  implicit override def persist2: Unshow[A2]
  implicit override def persist3: Unshow[A3]
  implicit override def persist4: Unshow[A4]
  implicit def ev5: Unshow[A5]

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), persist1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), persist2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), persist3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), persist4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    def e5: EMon[A5] = ife(len > pSeq(4), ev5.fromSettingOrExpr(name5, sortedExprs(pSeq(4))), opt5.toEMon)
    e1.map5(e2, e3, e4, e5)(newT)
  }
}

/** Persistence class for 5 logical parameter product types. */
trait Persist5[A1, A2, A3, A4, A5, R] extends Show5T[A1, A2, A3, A4, A5, R] with Unshow5[A1, A2, A3, A4, A5, R] with PersistN[R]
{ override def persist1: Persist[A1]
  override def persist2: Persist[A2]
  override def persist3: Persist[A3]
  override def persist4: Persist[A4]
  override def ev5: Persist[A5]
}

/** Companion object for [[Persist5]] trait contains implementation class and factory apply method. */
object Persist5
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, newT: (A1, A2, A3, A4, A5) => R, opt5: Option[A5] = None, opt4: Option[A4] = None,
    opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3],
    ev4: Persist[A4], ev5: Persist[A5]): Persist5[A1, A2, A3, A4, A5, R] = new Persist5Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4,
    fArg4, name5, fArg5, newT, opt5, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, ev5)

  class Persist5Imp[A1, A2, A3, A4, A5, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val name4: String, val fArg4: R => A4, val name5: String, val fArg5: R => A5,
    val newT: (A1, A2, A3, A4, A5) => R, val opt5: Option[A5] = None, opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2], val persist3: Persist[A3], val persist4: Persist[A4],
    val ev5: Persist[A5]) extends Persist5[A1, A2, A3, A4, A5, R]
  { val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
    val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match
    { case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }

    override def syntaxDepthT(obj: R): Int = ???

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = ???
  }
}

/** Persist trait for 5 [[Int]] parameters. */
trait PersistInt5[R] extends Persist5[Int, Int, Int, Int, Int, R]
{ override def persist1: Persist[Int] = Showing.intPersistEv
  override def persist2: Persist[Int] = Showing.intPersistEv
  override def persist3: Persist[Int] = Showing.intPersistEv
  override def persist4: Persist[Int] = Showing.intPersistEv
  override def ev5: Persist[Int] = Showing.intPersistEv
}

/** Companion object for [[PersistInt5]] trait contains implementation class and factory apply method. */
object PersistInt5
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, name3: String, fArg3: R => Int, name4: String,
    fArg4: R => Int, name5: String, fArg5: R => Int, newT: (Int, Int, Int, Int, Int) => R, opt5: Option[Int] = None, opt4: Option[Int] = None,
    opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None): PersistInt5[R] =
    new PersistInt5Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, newT, opt5, opt4, opt3, opt2, opt1)

  class PersistInt5Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val name3: String,
    val fArg3: R => Int, val name4: String, val fArg4: R => Int, val name5: String, val fArg5: R => Int, val newT: (Int, Int, Int, Int, Int) => R,
    val opt5: Option[Int] = None, opt4In: Option[Int] = None, opt3In: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends PersistInt5[R]
  { val opt4: Option[Int] = ife(opt5.nonEmpty, opt4In, None)
    val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match
    { case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }

    override def syntaxDepthT(obj: R): Int = ???

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = ???
  }
}