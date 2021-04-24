/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWW2
import geom._

case class Army(tile: W2TileAncient, polity: Polity) extends WithColour
{
  def colour = polity.colour
  override def toString = "Army" + (polity.toString).enParenth

  override def equals(other: Any): Boolean = other match
  { case that: Army => polity == that.polity
    case _ => false
  }
}