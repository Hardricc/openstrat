/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import Colour._
import pGrid._

trait ZugTerr
{
   def colour: Colour   
}

object ZugTerr
{
   implicit val zugMakerImplicit: (Int, Int, ZugTerr) => ZugTile = ZugTile.apply 
}

case class ZugTile(x: Int, y: Int, terr: ZugTerr) extends GridElem
{   
   def colour = terr.colour
   var lunits: List[Squad] = Nil   
}

object ZugTile
{   
   implicit object ZugTileIsType extends IsType[ZugTile]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ZugTile]
      override def asType(obj: AnyRef): ZugTile = obj.asInstanceOf[ZugTile]   
   }   
}

object Plain extends ZugTerr
{
   override def colour = LightGreen
}

object WheatField extends ZugTerr
{
   override def colour = Wheat
}

object Hill extends ZugTerr
{
   override def colour = Brown
}

trait Building extends ZugTerr

object Stone extends Building
{
   override def colour = Gray
}

object Wood extends Building
{
   override def colour = Brown
}

object Lake extends ZugTerr
{
   override def colour = Blue
}
