/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.immutable.ArraySeq

/** A type class for string, text and visual representation of objects. An alternative to toString. This trait has mor demanding ambitions . The
 *  capabilities of decimal place precision and explicit typing for numbers are placed defined here and in the corresponding [[Show]] type class
 *  although they have n meaning / purpose for many types, as separating them adds enormous complexity for very little gain. */
trait Show[-A] extends Persist
{
  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  def strT(obj: A): String

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  def syntaxDepth(obj: A): Int

  def show(obj: A, style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = -1): String

  override def toString: String = "Show" + typeStr
}

/* The companion object for the ShowT type class. Persist extends ShowT with UnShow. As its very unlikely that anyone would want to create an UnShow
   instance without a ShowT instance. Many Persist instances are placed inside the Show companion object. However type instances that themselves
   one or more Show type instances as parameters require a specific Show instance. The Persist instance for these types will require corresponding
   Persist type instances, and these will be placed in the Persist companion object. */
object Show
{ /** Implicit [[Show]] type class instance / evidence for [[Int]]. */
  implicit val intEv: Show[Int] = ShowSimple("Int", _.toString)

  val hexadecimal: Show[Int] = new ShowSimple[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.hexStr
  }

  val base32: Show[Int] = new ShowSimple[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.base32
  }

  /** Implicit [[Show]] instance / evidence for [[Double]]. */
  implicit val doubleEv: Show[Double] = PersistBoth.doubleEv

  /** Implicit [[Show]] type class instance / evidence for [[Long]]. */
  implicit val longEv: Show[Long] = ShowSimple[Long]("Long", _.toString)

  /** Implicit [[Show]] type class instance / evidence for [[Long]]. */
  implicit val floatEv: Show[Float] = ShowSimple[Float]("SFloat", _.toString)

  /** Implicit [[Show]] type class instance / evidence for [[Boolean]]. */
  implicit val booleanEv: Show[Boolean] = ShowSimple[Boolean]("Bool", _.toString)


  /** Implicit [[Show]] type class instance / evidence for [[String]]. */
  implicit val stringEv: Show[String] = ShowSimple[String]("Str", _.enquote)

  /** Implicit [[Show]] type class instance / evidence for [[Char]]. */
  implicit val charEv: Show[Char] = ShowSimple[Char]("Char", _.toString.enquote1)

  class ShowIterableClass[A, R <: Iterable[A]](val evA: Show[A]) extends ShowIterable[A, R] with Show[R]{}

  implicit def ShowIterableImplicit[A](implicit evA: Show[A]): Show[Iterable[A]] = new ShowIterableClass[A, Iterable[A]](evA)
  implicit def ShowSeqImplicit[A](implicit evA: Show[A]): Show[Seq[A]] = new ShowIterableClass[A, Seq[A]](evA)

  /** Implicit method for creating List[A: Show] instances. */
  implicit def listImplicit[A](implicit ev: Show[A]): Show[List[A]] = new ShowIterableClass[A, List[A]](ev)

  implicit def vectorImplicit[A](implicit ev: Show[A]): Show[Vector[A]] = new ShowIterableClass[A, Vector[A]](ev)

  implicit val arrayIntImplicit: Show[Array[Int]] = new ShowSeq[Int, Array[Int]]
  { override def evA: Show[Int] = Show.intEv
    override def syntaxDepth(obj: Array[Int]): Int = 2
    override def showForeach(obj: Array[Int], f: Int => Unit): Unit = obj.foreach(f)
  }

  /** Implicit method for creating Arr[A <: Show] instances. This seems toRich have to be a method rather directly using an implicit class */
  implicit def arraySeqImplicit[A](implicit ev: Show[A]): Show[collection.immutable.ArraySeq[A]] = new ShowSeq[A, ArraySeq[A]]
  { override def syntaxDepth(obj: ArraySeq[A]): Int = ???
    override def evA: Show[A] = ev
    override def showForeach(obj: ArraySeq[A], f: A => Unit): Unit = obj.foreach(f)
  }

  /** [[Show]] type class instance evidence for [[Some]]. */
  implicit def someEv[A](implicit ev: Show[A]): Show[Some[A]] = new  Show[Some[A]]
  { override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepth(obj: Some[A]): Int = ev.syntaxDepth(obj.value)
    override def strT(obj: Some[A]): String = ev.strT(obj.value)

    override def show(obj: Some[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** [[Show]] type class instance evidence for [[None.type]]. */
  implicit val noneEv: Show[None.type] = new Show[None.type]
  { override def typeStr: String = "Option"
    override def strT(obj: None.type): String = "None"
    override def syntaxDepth(obj: None.type): Int = 1

    override def show(obj: None.type, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match{
      case ShowTyped => "None"
      case _ => " "
    }
  }

  /** [[Show]] type class instance evidence for [[Option]]. */
  implicit def optionEv[A](implicit evA: Show[A]): Show[Option[A]] =
    ShowSum2[Option[A], Some[A], None.type]("Opt", someEv[A](evA), noneEv)
}

/** Show trait for Compound types contain elements, requiring the Show class or classes for the type or types of the constituent elements. */
trait ShowCompound[A] extends Show[A]
{ override def strT(obj: A): String = show(obj, ShowStd, -1, 0)
}