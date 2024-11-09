/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import impunits._

/** Extension methods class for [[Int]], for the geom package. */
class IntGeomExtensions(thisInt: Int)
{
  /** Returns this [[Int]] value in [[Metres]]. */
  @inline def metres: Metres = Metres(thisInt)

  /** Returns this [[Int]] value in [[Kilometres]]. */
  @inline def kiloMetres: Kilometres = Kilometres(thisInt)

  /** Returns this [[Int]] value in [[Megametres]]. */
  @inline def megaMetres: Megametres = Megametres(thisInt)

  /** Returns this [[Int]] value in [[Gigametres]]. */
  @inline def gigaMetres: Gigametres = Gigametres(thisInt)

  /** Returns this [[Int]] value in [[Kilare]], [[Kilometres]]². kilares folows the same naming convention as hectares. */
  @inline def kilares: Kilare = Kilare(thisInt)

  /** Returns this [[Int]] value in [[Kilare]]. */
  @inline def `km²`: Kilare = Kilare(thisInt)

  /** Extension methods multiplies this scalar [[Int]] by the operand [[Length]]. If you want a more precise return type such as [[Netres]] or [[Miles]] put the
   * [[Length]] object first. */
  @inline def * (operator: Length): Length = operator * thisInt

  /** Returns this [[Int]] value in [[Miles]]. */
  @inline def miles: Miles = Miles(thisInt)

  /** Returns this [[Int]] value in [[MegaMiles]] millions of miles. */
  @inline def megaMiles: MegaMiles = MegaMiles(thisInt)

  def ° : Angle = Angle(thisInt)

  /** Succinct syntax for creating 2-dimensional points [[Pt2]]s, from 2 numbers. Note the low precedence of this method relative to most numerical operators.
   * A third number as example {{{3.1 pp 4 pp -7.25}}} can be used to create a [[Pt3]]. */
  @inline infix def pp(y: Double): Pt2 = Pt2(thisInt, y)

  /** Succinct syntax for creating 2-dimensional vectors, [[Vec2]]s from 2 numbers. Note the low precedence of this method relative to most numerical operators.
   * A third number as example {{{3.1 vv 4 vv -7.25}}} can be used to create a [[Vec3]]. */
  @inline infix def vv(y: Double): Vec2 = Vec2(thisInt, y)

  /** Converts this Int into an absolute angle of the given degrees from 0 until 360 degrees. */
  def degs: Angle = Angle(thisInt)

  /** Degrees rotation anti-clockwise. Converts this Int into an [[AngleVec]] a positive angle of rotation. Can return values greater than 360 degrees and less
   * than -360 degrees. */
  def degsVec: AngleVec = AngleVec(thisInt)

  /** Degrees rotation clockwise. Converts this Int into an [[AngleVec]] a negative angle of rotation. Can return values greater than 360 degrees and less than
   * -360 degrees. */
  def degsClk: AngleVec = AngleVec(-thisInt)

  /** Multiplication of a 2-dimensional vector by this scalar [[Int]]. */
  def *(operand: Vec2): Vec2 = new Vec2(thisInt * operand.x, thisInt * operand.y)
}