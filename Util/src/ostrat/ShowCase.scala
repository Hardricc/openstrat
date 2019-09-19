/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The base trait for the persistence of Case classes, aka Product types */
trait ShowCase[R]/*(val typeStr: String)*/ extends ShowCompound[R]
{ def showMems: Arr[Show[_]]
  final override def syntaxDepth: Int = showMems.map(_.syntaxDepth).max + 1
}

/** Show type class for 1 parameter case classes. */
abstract class Show1[A1, R](val typeStr: String, fArg1: R => A1, val opt1: Option[A1] = None)(implicit ev1: Show[A1], eq1: Eq[A1]) extends
  EqCase1[A1, R](fArg1) with ShowCase[R]
{ override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2))
  final override def showMems: Arr[Show[_]] = Arr(ev1)
  def showSemi(obj: R): String = ev1.showComma(fArg1(obj))
  def showComma(obj: R): String = ev1.show(fArg1(obj))
}

/** Show type class for 2 parameter case classes. */
class Show2[A1, A2, R](val typeStr: String, pName1: String, fArg1: R => A1, pName2: String, fArg2: R => A2, val opt2: Option[A2] = None, opt1In: Option[A1] = None)(
  implicit ev1: Show[A1], ev2: Show[A2], eq1: Eq[A1], eq2: Eq[A2]) extends EqCase2[A1, A2, R](fArg1, fArg2) with ShowCase[R]
{
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2)) & eq2.eqv(fArg2(a1), fArg2(a2))

  final override def showMems: Arr[Show[_]] = Arr(ev1, ev2)
  override def showSemi(obj: R): String = ev1.showComma(fArg1(obj)) + "; " + ev2.showComma(fArg2(obj))
  override def showComma(obj: R): String = ev1.show(fArg1(obj)) + ", " + ev2.show(fArg2(obj))
  override def showSemiNames(obj: R): String = pName1 :- ev1.showComma(fArg1(obj)) + "; " + pName2 :- ev2.showComma(fArg2(obj))
  override def showCommaNames(obj: R): String = pName1 :- ev1.show(fArg1(obj)) + ", " + pName2 :- ev2.show(fArg2(obj))
}

/** Show type class for 3 parameter case classes. */
class Show3[A1, A2, A3, R](val typeStr: String, fArg1: R => A1, fArg2: R => A2, fArg3: R => A3, val opt3: Option[A3] = None, opt2In: Option[A2] = None,
  opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]) extends
  EqCase3[A1, A2, A3, R](fArg1, fArg2, fArg3) with ShowCase[R]
{
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  val defaultNum = ife3(opt3.isEmpty, 0, opt2.isEmpty, 1, opt1.isEmpty, 2, 3)
  override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2)) & eq2.eqv(fArg2(a1), fArg2(a2)) & eq3.eqv(fArg3(a1), fArg3(a2))
  override def showMems: Arr[Show[_]] = Arr(ev1, ev2, ev3)

  final override def showSemi(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    (opt1, opt2, opt3) match
    {
      case (Some(v1), Some(v2), Some(v3)) if v1 == p1 & v2 == p2 & v3 == p3 => ""
      case (_, Some(v2), Some(v3)) if v2 == p2 & v3 == p3 => ev1.showComma(p1)
      case (_, _, Some(v3)) if v3 == p3 => ev1.showComma(p1).appendSemicolons(ev2.showComma(p2))
      case _ => ev1.showComma(p1).appendSemicolons(ev2.showComma(p2), ev3.showComma(p3))
    }
  }

  final override def showComma(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)

    (opt1, opt2, opt3) match
    { case (Some(v1), Some(v2), Some(v3)) if v1 == p1 & v2 == p2 & v3 == p3 => ""
      case (_, Some(v2), Some(v3)) if v2 == p2 & v3 == p3 => ev1.show(p1) + ","
      case (_, _, Some(v3)) if v3 == p3 => ev1.showComma(p1).appendCommas(ev2.showComma(p2))
      case _ => ev1.showComma(p1).appendCommas(ev2.showComma(p2), ev3.showComma(p3))
    }
  }
}

/** Show type class for 4 parameter case classes. */
abstract class Show4[A1, A2, A3, A4, R](val typeStr: String, fArg1: R => A1, fArg2: R => A2, fArg3: R => A3, fArg4: R => A4,
  val opt4: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2],
  ev3: Show[A3], ev4: Show[A4], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4]) extends EqCase4[A1, A2, A3, A4, R](fArg1, fArg2, fArg3, fArg4)(
  eq1, eq2, eq3, eq4) with ShowCase[R]
{
  val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

  override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2)) & eq2.eqv(fArg2(a1), fArg2(a2)) & eq3.eqv(fArg3(a1), fArg3(a2))  &
    eq4.eqv(fArg4(a1), fArg4(a2))
  final override def showMems = Arr(ev1, ev2, ev3, ev4)

  override def showSemi(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    ev1.showComma(p1).appendSemicolons(ev2.showComma(p2), ev3.showComma(p3), ev4.showComma(p4))
  }

  final override def showComma(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    ev1.show(p1).appendCommas(ev2.show(p2), ev3.show(p3), ev4.show(p4))
  }
}

/** Show type class for 5 parameter case classes. */
class Show5[A1, A2, A3, A4, A5, R](val typeStr: String, fArg1: R => A1, fArg2: R => A2, fArg3: R => A3, fArg4: R => A4, fArg5: R => A5,
  val opt5: Option[A5], optIn4: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit
  ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4], ev5: Show[A5], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]) extends
  EqCase5[A1, A2, A3, A4, A5, R](fArg1, fArg2, fArg3, fArg4, fArg5) with ShowCase[R]
{
  val opt4: Option[A4] = ife(opt5.nonEmpty, optIn4, None)
  val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

  override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2)) & eq2.eqv(fArg2(a1), fArg2(a2)) & eq3.eqv(fArg3(a1), fArg3(a2))  &
    eq4.eqv(fArg4(a1), fArg4(a2)) & eq5.eqv(fArg5(a1), fArg5(a2))

  final override def showMems = Arr(ev1, ev2, ev3, ev4, ev5)

  override def showSemi(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    val p5 = fArg5(obj)
    ev1.showComma(p1).appendSemicolons(ev2.showComma(p2), ev3.showComma(p3), ev4.showComma(p4), ev5.showComma(p5))
  }

  final override def showComma(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    val p5 = fArg5(obj)
    ev1.show(p1).appendCommas(ev2.show(p2), ev3.show(p3), ev4.show(p4), ev5.show(p5))
  }
}

object Show5
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, fArg1: R => A1, fArg2: R => A2, fArg3: R => A3, fArg4: R => A4, fArg5: R => A5,
    opt5: Option[A5] = None, opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4], ev5: Show[A5], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]) =
    new Show5[A1, A2, A3, A4, A5, R](typeStr, fArg1, fArg2, fArg3, fArg4, fArg5, opt5, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, ev5, eq1, eq2, eq3,
    eq4: Eq[A4], eq5)
}