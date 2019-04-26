/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The classic Show type class. A functional version of toString .Mostly you will want to use Persist which not only gives the Show methods
 *   to String representation, but the methods to parse Strings back to objects of the type T. However it may often be useful to start with Show
 *   type class and upgrade it later to Persist[T]. */
abstract class Show[T]
{
  def typeStr: String
  /** Provides the standard string representation for the object */
  def show(obj: T): String
  def syntaxDepth: Int  
  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  def showComma(obj: T): String
  
  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  def showSemi(obj: T): String
  /** For most objects showTyped will return the same value as show(obj: T), for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4) */
  def showTyped(obj: T): String
}

object Show
{
  implicit def someToPersist[A](implicit ev: Persist[A]): Show[Some[A]] = new SomeShowImplicit[A](ev)
  
  class SomeShowImplicit[A](val ev: Persist[A]) extends Show[Some[A]]
  {
    override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepth: Int = ev.syntaxDepth
    override def show(obj: Some[A]) = ev.show(obj.value)
    def showComma(obj: Some[A]): String = show(obj)
    def showSemi(obj: Some[A]): String = show(obj)
    override def showTyped(obj: Some[A]): String = typeStr + ev.show(obj.value).enParenth
  }
  
}
