/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** British Imperial system of measurement units. */
trait ImperialUnits extends Any

trait ImperialLength extends Any with Length with ImperialUnits
{ /** The number of yards in this length */
  def yardsNum: Double

  /** The number of miles in this length */
  def milesNum: Double

  /** The number of MegaMiles in this length */
  def megaMilesNum: Double

  /** Add a [[Length]] defined in [[ImperialLength]] units. Use addLength method if you wish to mix units from different unit measurement systems. */
  def +(operand: ImperialLength): ImperialLength

  /** Subtract a [[Length]] defined in [[ImperialLength]] units. Use addLength method if you wish to mix units from different unit measurement systems. */
  def -(operand: ImperialLength): ImperialLength

  override def addLength(operand: Length): ImperialLength
  override def subLength(operand: Length): ImperialLength
  override def *(operand: Double): ImperialLength
  override def /(operand: Double): ImperialLength
  override def unary_- : ImperialLength
  override def kiloMetresNum: Double = metresNum / 1000
  override def megaMetresNum: Double = metresNum / 1000000
}

object ImperialLength
{ /** Converts yards to metres. */
  val yardsToMetres: Double = 0.9144

  /** The number of yards in a mile. */
  val yardsInMile: Double = 1760

  /** Converts miles to metres. */
  val milesToMetres: Double = yardsInMile * yardsToMetres
}