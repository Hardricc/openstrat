/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance

/** Biased bifunctor for errors. */
sealed trait Ebbf[+E <: Throwable, +A]
{ /** Classic map function as we see on a Scala [[Option]] or [[List]]. */
  def map[B](f: A => B): Ebbf[E, B]

  /** Classic flatMap function as we see on a Scala [[Option]]. */
  def flatMap[B](f: A => Ebbf[E, B] @uncheckedVariance): Ebbf[E, B]

  def isSucc: Boolean
  
  def toEMon: EMon[A] = this match{
    case Succ(value) => Good(value)
    case Fail(err) => Bad(StrArr(err.toString))
  }
}

/** Success, boxes a good value of the desired type. */
case class Succ[+E <: Throwable, +A](value: A) extends Ebbf[E, A]
{ override def map[B](f: A => B): Ebbf[E, B] = new Succ[E, B](f(value))

  override def flatMap[B](f: A => Ebbf[E, B] @uncheckedVariance): Ebbf[E, B] = f(value) match
  { case succ: Succ[E, B] => succ
    case fail: Fail[E, B] => fail
  }

  override def isSucc: Boolean = true
}

/** Failure to return a value of the desired type. Boxes a [[Throwable]] error. */
case class Fail[+E <: Throwable, +A](val error: E) extends Ebbf[E, A]
{ override def map[B](f: A => B): Ebbf[E, B] = new Fail[E, B](error)
  override def flatMap[B](f: A => Ebbf[E, B] @uncheckedVariance): Ebbf[E, B] = new Fail[E, B](error)
  override def isSucc: Boolean = false
}

object Fail
{ def apply[A](errStr: String): FailE[A] = new Fail[Exception, A](new Exception(errStr))
}

type EEMon[A] = Ebbf[Exception, A]
type SuccE[A] = Succ[Exception, A]
type FailE[A] = Fail[Exception, A]

object SuccE
{
  def unapply[A](inp: EEMon[A]): Option[A] = inp match{
    case succ: SuccE[A] => Some(succ.value)
    case _ => None
  }
}

type EEArr[A <: AnyRef] = EEMon[RArr[A]]

/** Extractor function object for a successful Arr Sequence of length 1. */
object SuccEArr1
{ /** Extractor method for a Good [[Arr]] Sequence of length 1. */
  def unapply[A](eArr: EEMon[Arr[A]]): Option[A] = eArr match
  { case Succ(Arr1(head)) => Some(head)
    case _ => None
  }
}

type EEMon3[A1, A2, A3] = EEMon[(A1, A2, A3)]

implicit class EEMon3Extensions[A1, A2, A3](val thisEE3: EEMon3[A1, A2, A3])
{
  /*def toEMon3: EMon3[A1, A2, A3] = thisEE3 match {
    case Succ3(a1, a2, a3) => Good3(a1, a2, a3)
    case Fail(err) => Bad3(StrArr(err.toString))
  }*/

  def flatMap3[B1, B2, B3](f: (A1, A2, A3) => EEMon3[B1, B2, B3]): EEMon3[B1, B2, B3] = thisEE3 match
  { case Succ3(a1, a2, a3) => f(a1, a2, a3)
    case Fail(err) => Fail(err)
  }
}

/** Success for a [[Tuple3]] value. */
type Succ3[E <: Throwable, A1, A2, A3] = Succ[E, (A1, A2, A3)]

object Succ3
{ /** Factory apply method for creating [[Succ]] with a [[Tuple3]] value. */
  def apply[E <: Throwable, A1, A2, A3](a1: A1, a2: A2, a3: A3): Succ3[E, A1, A2, A3] = new Succ[E, (A1, A2, A3)]((a1, a2, a3))

  /** unapply extractor for success on an [[Ebbf]] with a [[Tuple3]] value type. */
  def unapply[E <: Throwable, A1, A2, A3](inp: EEMon3[A1, A2, A3]): Option[(A1, A2, A3)] = inp match{
    case succ: Succ3[E, A1, A2, A3] => Some(succ.value._1, succ.value._2, succ.value._3)
    case _ => None
  }
}