/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait Length extends Any with Ordered[Length]
{ /** The number of metres in this [[Length]]. */
  def metresNum: Double

  /** The number of kilometres in this [[Length]]. */
  def kilometresNum: Double

  /** The number of megametres in this [[Length]]. */
  def megametresNum: Double

  /** The number of gigametres in this [[Length]]. */
  def gigametresNum: Double

  /** The number of millimetres in this [[Length]]. */
  def millimetresNum: Double

  /** The number of micrometres in this [[Length]]. */
  def micrometresNum: Double

  /** The number of nanometres in this [[Length]]. */
  def nanometresNum: Double

  /** The number of angstroms in this [[Length]]. */
  def angstromsNum: Double

  /** The number of picometres in this [[Length]]. */
  def picometresNum: Double

  /** The number of femtoometres in this [[Length]]. */
  def femtometresNum: Double


  /** The negative of this [[Length]] */
  def unary_- : Length

  /** Add a [[Length]] defined in [[Length]] units. Use addLength method if you wish to mix units from different unit measurement systems. */
  def +(operand: Length): Length

  /** Subtract a [[Length]] defined in [[LengthMetric]] units. Use subLength method if you wish to mix units from different unit measurement systems. */
  def -(operand: Length): Length

  /** Multiply by the given scalar. */
  def *(operand: Double): Length

  /** Combine with an operand [[Length]] for a [[Rectangle]] to return an [[Area]]. */
  def toRectArea(operand: Length): Area

  /** Divide by the given scalar. */
  def /(operand: Double): Length

  def divByLength(operand: Length): Double

  /** Is the length units greater or equal to zero. */
  def nonNeg: Boolean

  def pos: Boolean

  def neg: Boolean

  /** Converts this [[Length]] to [[Metres]]. */
  def toMetres: Metres = Metres(metresNum)

  /** Converts this [[Length]] to [[Metres]]. */
  def toKiloMetres: Kilometres = Kilometres(kilometresNum)
}

object Length
{
  implicit class LengthExtensions(thisLength: Length)
  { /** Extension operator method to produce [[Area]], multiplying this [[Length]] by an operand [[Length]]. */
    def *(operand: Length): Area = thisLength.toRectArea(operand)

    /** Convenience extension operator to divide by a [[Length]] to return a scalar. Defers to the divByLength class method. */
    def / (operand: Length): Double = thisLength.divByLength(operand)
  }
}