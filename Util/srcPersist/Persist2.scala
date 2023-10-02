/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

/** Base trait for [[PersistBase2]] and [[PersistBase3Plus]] classes. it declares the common properties of name1, name2, opt1 and opt2. It is not a base trait
 *  for [[Show2ing]], as [[Show2eding]] classes do not need this data, as they can delegate to the [[Tell2]] object to implement their interfaces. */
trait PersistBase2Plus[A1, A2] extends Any with PersistBaseN
{ /** 1st parameter name. */
  def name1: String

  /** 2nd parameter name. */
  def name2: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]

  /** The optional default value for parameter 2. */
  def opt2: Option[A2]

  /** The declaration here allows the same field to cover [[Show]][A1], [[UnShow]][A1] and [[Persist]][A1]. */
  def persist1: Show[A1] | Unshow[A1]

  /** The declaration here allows the same field to be to cover [[Show]][A2] [[UnShow]][A2] and [[Persist]][A2]. */
  def persist2: Show[A2] | Unshow[A2]
}

/** A base trait for [[Tell2]] and [[UnShow2]]. It is not a base trait for [[Show2ing]], as [[Show2eding]] classes do not need this data, as they can
 *  delegate to the [[Tell2]] object to implement their interfaces. */
trait PersistBase2[A1, A2] extends Any with PersistBase2Plus[A1, A2]
{ override def paramNames: StrArr = StrArr(name1, name2)
  override def numParams: Int = 2
}

/** [[Tell]] trait for classes with 2+ Show parameters. */
trait Show2Plused[A1, A2] extends Any with TellN with PersistBase2Plus[A1, A2]
{ /** The optional default value for parameter 1. */
  override def opt1: Option[A1] = None

  /** The optional default value for parameter 2. */
  override def opt2: Option[A2] = None

  /** Element 1 of this Show 2+ element product. */
  def show1: A1

  /** Element 2 of this Show 2+ element product. */
  def show2: A2

  override def persist1: Show[A1]

  override def persist2: Show[A2]
}


/** Trait for Show for product of 2 Ints that is also an ElemInt2. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowInt2Eding]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2ing]]. */
trait ShowElemInt2 extends Any with TellInt2 with Int2Elem
{ final override def int1: Int = show1
  final override def int2: Int = show2
}

/** Show type class for 2 parameter case classes. */
trait Show2ing[A1, A2, R] extends ShowNing[R]
{ def fArg1: R => A1
  def fArg2: R => A2
  implicit def persist1: Show[A1]
  implicit def persist2: Show[A2]

  override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
    StrArr(persist1.showDecT(fArg1(obj), way, maxPlaces, 0), persist2.showDecT(fArg2(obj), way, maxPlaces, 0))
}

/** Companion object for the [[Show2ing]] type class trait that shows object with 2 logical fields. */
object Show2ing
{
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit persist1: Show[A1], persist2: Show[A2]): Show2ing[A1, A2, R] =
    new Show2ingImp[A1, A2, R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1In)

  /** Implementation class for the general cases of [[Show2ing]] trait. */
  class Show2ingImp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val persist1: Show[A1], val persist2: Show[A2]) extends Show2ing[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    override def syntaxDepthT(obj: R): Int = persist1.syntaxDepthT(fArg1(obj)).max(persist2.syntaxDepthT(fArg2(obj))) + 1
  }
}

/** Extension methods for [[Show2ing]] type class instances. */
class Show2ingExtensions[A1, A2, -T](ev: Show2ing[A1, A2, T], thisVal: T)
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def show2(way: ShowStyle = ShowStandard, way1: ShowStyle = ShowStandard, places1: Int = -1, way2: ShowStyle = ShowStandard, places2: Int = -1):
    String = ???
}

/** [[Show]] type class trait for types with 2 [[Int]] Show components. */
trait ShowInt2ing[R] extends Show2ing[Int, Int, R]
{ override def persist1: Persist[Int] = Show.intPersistEv
  override def persist2: Persist[Int] = Show.intPersistEv
  override def syntaxDepthT(obj: R): Int = 2
}

object ShowInt2ing
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, opt2: Option[Int] = None, opt1In: Option[Int] = None):
    ShowInt2ing[R] = new ShowInt2ingImp[R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1In)

  /** Implementation class for the general cases of [[ShowInt2ing]] trait. */
  class ShowInt2ingImp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val opt2: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt2ing[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Type class trait for Showing [[Tell2]] objects. */
trait Show2eding[A1, A2, R <: Tell2[A1, A2]] extends Show2ing[A1, A2, R] with Showeding[R]
{ override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = obj.showElemStrDecs(way, maxPlaces)
  override def fArg1: R => A1 = _.show1
  override def fArg2: R => A2 = _.show2
}

object Show2eding
{
  def apply[A1, A2, R<: Tell2[A1, A2]](typeStr: String)(
    implicit ev1: Show[A1], ev2: Show[A2]): Show2eding[A1, A2, R] =
    new Show2edingImp[A1, A2, R](typeStr)

  /** Implementation class for the general cases of the [[Show2eding]] trait. */
  class Show2edingImp[A1, A2, R<: Tell2[A1, A2]](val typeStr: String)(implicit val persist1: Show[A1], val persist2: Show[A2]) extends
    Show2eding[A1, A2, R]
}

/** A trait for making quick ShowT instances for [[TellDbl2]] types. It uses the functionality of the [[TellDbl2]]. */
trait ShowDbl2Eding[R <: TellDbl2] extends Show2eding[Double, Double, R]
{ override implicit def persist1: Persist[Double] = Show.doublePersistEv
  override implicit def persist2: Persist[Double] = Show.doublePersistEv
}

object ShowDbl2Eding
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: TellElemDbl2](typeStr: String): ShowShowDbl2TImp[R] = new ShowShowDbl2TImp[R](typeStr)

  /** Implementation class for the general cases of the [[ShowDbl2Eding]] trait. */
  class ShowShowDbl2TImp[R <: TellDbl2](val typeStr: String) extends ShowDbl2Eding[R]
}

/** A trait for making quick ShowT instances for [[ShowElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
trait ShowInt2Eding[R <: TellInt2] extends ShowInt2ing[R] with Show2eding[Int, Int, R]

object ShowInt2Eding
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: ShowElemInt2](typeStr: String, name1: String, name2: String, opt2: Option[Int] = None, opt1In: Option[Int] = None):
    ShowInt2Eding[R] = new ShowShowInt2TImp[R](typeStr, name1, name2, opt2,opt1In)

  class ShowShowInt2TImp[R <: ShowElemInt2](val typeStr: String, val name1: String, val name2: String, val opt2: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt2Eding[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** UnShow type class trait for a 2 element Product. */
trait Unshow2[A1, A2, R] extends UnshowN[R] with PersistBase2[A1, A2]
{ /** The UnShow type class instance for type A1. */
  def persist1: Unshow[A1]

  /** The UnShow type class instance for type A2. */
  def persist2: Unshow[A2]

  def newT: (A1, A2) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val r0: EMon[A1] = ife(len > pSeq(0), persist1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), persist2.fromSettingOrExpr(name2,sortedExprs(pSeq(1))), opt2.toEMon)
    r0.map2(e2)(newT)
  }
}

object Unshow2
{
  def apply[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Unshow[A1], ev2: Unshow[A2]): Unshow2[A1, A2, R] = new Unshow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  case class Unshow2Imp[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, val opt2: Option[A2], opt1In: Option[A1])(implicit
    val persist1: Unshow[A1], val persist2: Unshow[A2]) extends Unshow2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence class for product 2 type class. It ShowTs and UnShows objects with 2 logical parameters. */
trait Persist2[A1, A2, R] extends Show2ing[A1, A2, R] with Unshow2[A1, A2, R] with PersistN[R]
{ override def persist1: Persist[A1]
  override def persist2: Persist[A2]
}

/** Factory object for Persist product 2 type class that persists objects with 2 parameters. */
object Persist2
{ /** apply facory method for [[Persist2]] type class instances. */
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2]): Persist2[A1, A2, R] =
    new Persist2Imp(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2)

  class Persist2Imp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2]) extends Persist2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = ???

    /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
     * should always be determined at compile time or if sometimes it should be determined at runtime. */
    override def syntaxDepthT(obj: R): Int = ???
  }
}

trait PersistInt2[R] extends Persist2[Int, Int, R] with ShowInt2ing[R]

object PersistInt2ing
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, newT: (Int, Int) => R, opt2: Option[Int] = None,
    opt1In: Option[Int] = None): ShowInt2ing[R] = new PersistInt2Imp[R](typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1In)

  /** Implementation class for the general cases of [[ShowInt2ing]] trait. */
  class PersistInt2Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int,
    val newT: (Int, Int) => R, val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends PersistInt2[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}
/** Persist type class for types that extends [[Tell2]]. */
trait Persist2ed[A1, A2, R <: Tell2[A1, A2]] extends Persist2[A1, A2, R] with Show2eding[A1, A2, R]

/** Companion object for the [[Persist2ed]] class the persists object that extend [[Tell2]]. Contains an apply factory method. */
object Persist2ed
{ /** Factory apply method for [[Persist2ed]], that Persists [[Tell2]] objects. */
  def apply[A1, A2, R <: Tell2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]): Persist2ed[A1, A2, R] =
    new PersistShow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  class PersistShow2Imp[A1, A2, R <: Tell2[A1, A2]](val typeStr: String, val name1: String, val name2: String, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2]) extends Persist2ed[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence type class for types that extend [[TellInt2]]. */
class PersistInt2Ed[R <: TellInt2](val typeStr: String, val name1: String, val name2: String, val newT: (Int, Int) => R,
  val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends PersistInt2[R] with Persist2ed[Int, Int, R] with ShowInt2Eding[R]
{ val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
}

object PersistInt2Ed
{ /** Factory apply method for [[PersistInt2Ed]] that persists objects of type [[ShowElemInt2]]. */
  def apply[R <: ShowElemInt2](typeStr: String, name1: String, name2: String, newT: (Int, Int) => R): PersistInt2Ed[R] =
    new PersistInt2Ed[R](typeStr, name1, name2, newT)
}

/** Persistence class for types that extend [[TellDbl2]]. */
class PersistDbl2Ed[R <: TellDbl2](val typeStr: String, val name1: String, val name2: String, val newT: (Double, Double) => R,
  val opt2: Option[Double] = None, opt1In: Option[Double] = None) extends Persist2ed[Double, Double, R] with ShowDbl2Eding[R]
{ val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
}

/**  Class to persist [[Int2Arr]] collection classes. */
abstract class PersistArrInt2s[A <: Int2Elem, M <: Int2Arr[A]](val typeStr: String) extends IntNSeqLikePersist[A, M]
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value.int1
    buf += value.int2
  }

  override def syntaxDepthT(obj: M): Int = 3
}