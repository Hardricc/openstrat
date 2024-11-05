/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Describes climate with broad brush strokes for strategy games. Can be used as a crude proxy for biome. Uses both classical Köppen and Köppen–Trewartha. */
trait Climate extends TellSimple
{ override def typeStr: String = "Climate"
  def colour: Colour
}

object Climate
{ /** Implicit [[Show]] type class instance / evidence for [[Climate]]. */
  implicit lazy val showEv: ShowTell[Climate] = ShowTellSimple[Climate]("Climate")

  /** Implicit [[Unshow]] type class instance / evidence for [[Climate]]. */
  implicit lazy val unshowEv: UnshowSingletons[Climate] = UnshowSingletons[Climate]("Climate", Oceanic, DesertHot, Sahel, Savannah, Boreal, Tundra, Tropical)

  given CanEqual[Climate, Climate] = CanEqual.derived
}

/** Trewartha Fi Ice cap classification. All year round snow. */
case object IceCap extends Climate
{ override def str: String = "IceCap"
  override def colour: Colour = White
}

/** Trewartha Ft Tundra climate classification. Too cold and dry for [[Forest]] */
case object Tundra extends Climate
{ override def str: String = "Tundra"
  override def colour: Colour = Plum.average(Thistle)
}

trait TemperateLike extends Climate
{ def colour: Colour = LightGreen
}

/** Trewartha E Boreal climate classification. Subartic or Boreal Taiga climate. Normally has [[Forest]]. Assumes a northern latitude with a significantly
 *  colder northern winter. */
case object Boreal extends TemperateLike
{ override def str: String = "Boreal"
}

/** Köppen BW desert climate classification. */
trait Desert extends Climate

/** Köppen BWk cold desert climate classification. Average annual temperature less than 18°C. */
case object DesertCold extends Desert
{ override def str: String = "DesertCold"
  override def colour: Colour = LemonChiffon
}

/** Trewartha D Temperate classification. */
trait Temperate extends TemperateLike

/** Trewartha Dc Temperate Continental classification. No intense dry season. The coldest monthly mean temperature reaches below 0 °C. */
case object Continental extends TemperateLike
{ override def str: String = "Continental"
}

/** Köppen BSk. Trewartha Bs. Dry Semi-Arid climate classification. */
trait SemiArid extends Climate

/** Köppen BSk Dry Semi-Arid Cold climate classification. Assumes a northern latitude with a significantly colder northern winter. */
case object Steppe extends SemiArid
{ override def str: String = "Steppe"
  override def colour: Colour = YellowGreen
}

/** Trewartha Do Temperate oceanic climate classification. No intense dry season. The coldest monthly mean temperature reaches is not below 0 °C. */
case object Oceanic extends Temperate
{ override def str: String = "Oceanic"
}

/** Trewartha C Subtropical climate classification. No intense dry season. Category may be split up later. */
case object Subtropical extends Climate
{ def colour: Colour = LightGreen
  override def str: String = "Subtropical"
}

/** Köppen BWh hot desert climate classification. Average annual temperature above 18°C. */
case object DesertHot extends Desert
{ override def str: String = "Deshot"
  override def colour: Colour = LemonChiffon
}

/** Semi hot desert climate and biome. This is kind of intermediatry between Köppen BWh and BSh. */
case object Sahel extends Climate
{ override def str: String = "Sahel"
  override def colour: Colour = LemonChiffon.average(YellowGreen)
}


/** Köppen BSh Dry Semi-Arid hot climate classification. */
case object Savannah extends SemiArid
{ override def str: String = "Savannah"
  override def colour: Colour = YellowGreen
}

/** Tropical climate. High precipitation through out year. */
case object Tropical extends Climate
{ override def str: String = "Tropical"
  override def colour: Colour = YellowGreen
}