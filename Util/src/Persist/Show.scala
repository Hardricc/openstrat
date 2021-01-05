/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A trait for providing an alternative to toString. USing this trait can be convenient, but at some level of the inheritance the type must provide a
 *  ShowT type class instance. */
trait Show extends Any
{ def typeStr: String

  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  def str: String

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  def show(way: Show.Way = Show.Standard, decimalPlaces: Int = -1): String
}

/** Companion object of the Show trait contains the Way trait, used by the show method on Show and the showT method on [[ShowT]] */
object Show
{ /** Currently can't think of a better name for this trait */
  sealed trait Way

  /** Show the object just as its comma separated constituent values. */
  object Commas extends Way

  /** Show the object as semicolon separated constituent values. */
  object Semis extends Way

  /** Show the object in the standard default manner. */
  object Standard extends Way

  /** Show the object in the standard default manner, with field names. */
  object StdFields extends Way

  /** Show the object with the type of the object even if the string representation does not normally states its type. Eg Int(7). */
  object Typed extends Way
}

/** All the leaves of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of the object,
 *  but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait ShowSingleton extends Show
{ /** The string for the leaf object. This will normally be different from the typeStr in the instance of the PersistSingletons. */
  def str: String
  final override def toString: String = str

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  final override def show(way: Show.Way, decimalPlaces: Int): String = way match
  { case Show.Typed => typeStr.appendParenth(str)
    case _ => str
  }
}

trait ShowCase extends Show
{
  def strs(way: Show.Way, decimalPlaces: Int): Strings
  def names: Strings

  override def show(way: Show.Way, decimalPlaces: Int): String =
  { def semisStr = strs(Show.Commas, decimalPlaces).mkStr("; ")

    way match
    { case Show.Semis => semisStr
      case Show.Commas => strs(Show.Standard, decimalPlaces).mkStr(", ")
      case Show.StdFields =>
      { val inner = names.zipMap(strs(Show.Standard, decimalPlaces))((n, s) => n + " = " + s).mkStr(", ")
        typeStr + inner.enParenth
      }
      case _ => typeStr.appendParenth(semisStr)
    }
  }

  override def str: String = show(Show.Standard, 1)
}

trait Show2[A1, A2] extends ShowCase
{ def name1: String
  def name2: String
  def names: Strings = Strings(name1, name2)
  def arg1: A1
  def arg2: A2
  implicit def ev1: ShowT[A1]
  implicit def ev2: ShowT[A2]
  def strs(way: Show.Way, decimalPlaces: Int): Strings = Strings(ev1.showT(arg1, way, decimalPlaces), ev2.showT(arg2, way, decimalPlaces))
}