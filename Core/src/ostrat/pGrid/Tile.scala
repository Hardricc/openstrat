/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

trait GridElem
{
   def x: Int
   def y: Int
   def cood: Cood = Cood(x, y)
}

case class TileBare(x: Int, y: Int) extends GridElem
case class SideBare(x: Int, y: Int) extends GridElem
object SideBare
{
   implicit object SideBareIsType extends IsType[SideBare]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[SideBare]
      override def asType(obj: AnyRef): SideBare = obj.asInstanceOf[SideBare]   
   }
}


