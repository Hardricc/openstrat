/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid

/** The local climate. */
trait LandUse extends ShowSimple
{ override def typeStr: String = "LandUse"
}
object LandUse
{
  implicit val showEv: ShowShowT[LandUse] = ShowShowSimpleT[LandUse]("LandUse")
}

object CivMix extends LandUse
{ override def str: String = "Mixed use"
}

object LandFree extends LandUse
{
  override def str: String = "No Civilisation"
}

/** forest that is not taiga or rain forest. */
case object Forest extends LandUse
{ def str = "Forest"
  def colour: Colour = Colour.ForestGreen

  //override def shortDescrip: String = "Forested"
}