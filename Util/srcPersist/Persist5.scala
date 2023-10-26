/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for Unshow5+, declares the common properties of name1 - 5 and opt1 - 5. */
trait Persist5Plus[A1, A2, A3, A4, A5] extends Any with Persist4Plus[A1, A2, A3, A4]
{ /** 5th parameter name. */
  def name5: String

  /** The optional default value for parameter 5. */
  def opt5: Option[A5]
}

trait Persist5[A1, A2, A3, A4, A5] extends Any with Persist5Plus[A1, A2, A3, A4, A5]
{ override def paramNames: StrArr = StrArr(name1, name2, name3, name4, name5)
  override def numParams: Int = 5
}

/** [[Show]] type class for 5 parameter case classes. */
trait Show5[A1, A2, A3, A4, A5, R] extends Persist5[A1, A2, A3, A4, A5] with ShowN[R]
{ def show1: Show[A1]
  def show2: Show[A2]
  def show3: Show[A3]
  def show4: Show[A4]
  def show5: Show[A5]
}

/** Companion object for [[Show5]] trait contains implementation class and factory apply method. */
object Show5
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, opt5: Option[A5] = None, opt4: Option[A4] = None, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit show1: Show[A1], show2: Show[A2], show3: Show[A3], show4: Show[A4], show5: Show[A5]) =
    new Show5Imp[A1, A2, A3, A4, A5, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, opt5, opt4, opt3, opt2, opt1)(
      show1, show2, show3, show4, show5)

  /** Implementation class for the general cases of [[Show5]] type class. */
  class Show5Imp[A1, A2, A3, A4, A5, R](val typeStr: String, val name1: String, fArg1: R => A1, val name2: String, fArg2: R => A2,
    val name3: String, fArg3: R => A3, val name4: String, fArg4: R => A4, val name5: String, fArg5: R => A5, override val opt5: Option[A5],
    opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val show1: Show[A1], val show2: Show[A2], val show3: Show[A3], val show4: Show[A4], val show5: Show[A5]) extends
    Show5[A1, A2, A3, A4, A5, R]
  {
    override val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
    override val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepth(obj: R): Int = show1.syntaxDepth(fArg1(obj)).max(show2.syntaxDepth(fArg2(obj))).max(show3.syntaxDepth(fArg3(obj))).
      max(show4.syntaxDepth(fArg4(obj))).max(show5.syntaxDepth(fArg5(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
      StrArr(show1.show(fArg1(obj), way), show2.show(fArg2(obj), way), show3.show(fArg3(obj), way), show4.show(fArg4(obj), way),
        show5.show(fArg5(obj), way))
  }
}

trait ShowInt5[R] extends Show5[Int, Int, Int, Int, Int, R]
{ override def show1: Show[Int] = Show.intEv
  override def show2: Show[Int] = Show.intEv
  override def show3: Show[Int] = Show.intEv
  override def show4: Show[Int] = Show.intEv
  override def show5: Show[Int] = Show.intEv
}

/** [[Unshow]] trait for 5 parameter product / case classes. */
trait Unshow5[A1, A2, A3, A4, A5, R] extends UnshowN[R] with Persist5[A1, A2, A3, A4, A5]
{ def fArg1: R => A1
  def fArg2: R => A2
  def fArg3: R => A3
  def fArg4: R => A4
  def fArg5: R => A5
  def newT: (A1, A2, A3, A4, A5) => R

  implicit def unshow1: Unshow[A1]
  implicit def unshow2: Unshow[A2]
  implicit def unshow3: Unshow[A3]
  implicit def unshow4: Unshow[A4]
  implicit def unshow5: Unshow[A5]

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), unshow1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), unshow2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), unshow3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), unshow4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    def e5: EMon[A5] = ife(len > pSeq(4), unshow5.fromSettingOrExpr(name5, sortedExprs(pSeq(4))), opt5.toEMon)
    e1.map5(e2, e3, e4, e5)(newT)
  }
}

class UnshowInt5[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val name3: String,
  val fArg3: R => Int, val name4: String, val fArg4: R => Int, val name5: String, val fArg5: R => Int, val newT: (Int, Int, Int, Int, Int) => R,
  override val opt5: Option[Int] = None, opt4In: Option[Int] = None, opt3In: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends Unshow5[Int, Int, Int, Int, Int, R]
{
  override val opt4: Option[Int] = ife(opt5.nonEmpty, opt4In, None)
  override val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
  override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
  override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

  val defaultNum: Int = None match {
    case _ if opt3.isEmpty => 0
    case _ if opt2.isEmpty => 1
    case _ if opt1.isEmpty => 2
    case _ => 3
  }

  override def unshow1: Unshow[Int] = Unshow.intEv
  override def unshow2: Unshow[Int] = Unshow.intEv
  override def unshow3: Unshow[Int] = Unshow.intEv
  override def unshow4: Unshow[Int] = Unshow.intEv
  override def unshow5: Unshow[Int] = Unshow.intEv
}

object UnshowInt5
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, name3: String, fArg3: R => Int, name4: String,
    fArg4: R => Int, name5: String, fArg5: R => Int, newT: (Int, Int, Int, Int, Int) => R, opt5: Option[Int] = None, opt4: Option[Int] = None,
    opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None): UnshowInt5[R] =
    new UnshowInt5[R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, newT, opt5, opt4, opt3, opt2, opt1)
}