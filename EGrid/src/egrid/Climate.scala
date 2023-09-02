/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Describes climate and biome. */
trait Climate extends ShowSimple
{ override def typeStr: String = "Climate"
  def colour: Colour
}

object Climate
{ implicit val showEv: ShowShowT[Climate] = ShowShowSimpleT[Climate]("Climate")
}

/** Temperate climate with out intense dry season. */
case object Temperate extends Climate
{ def colour: Colour = LightGreen
  override def str = "Temperate"
}

/** Desert climate and biome. */
case object Desert extends Climate
{ override def str = "Desert"
  override def colour = LemonChiffon
}

/** Semi Desert climate and biome. */
case object Sahel extends Climate
{ override def str = "Sahel"
  override def colour = LemonChiffon.average(YellowGreen)
}

/** Savannah, steppe and prairie biome. */
case object Savannah extends Climate
{ override def str = "Savannah"
  override def colour = YellowGreen
}

/** Tropical climate. High precipitation through out year. */
object Tropical extends Climate
{ override def str = "Tropical"
  override def colour = DarkGreen
}

/** Ice cap / all year round snow. */
object IceCap extends Climate
{ override def str = "IceCap"
  override def colour = White
}

/** Permanent all year round sea ice. */
object SeaIce extends WTile with ShowSimple
{ override def str = "SeaIce"
  override def colour = White
  override def isLand: Boolean = false
}

/** Taiga climate normally has [[Forest]]. */
case object Taiga extends Climate
{ override def str = "Taiga"
  override def colour = DarkCyan
}

/** Tundra climate, no [[Foresr]] */
case object Tundra extends Climate
{ override def str = "Tundra"
  override def colour = Plum.average(Thistle)
}