/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class HexXlign(val height: Double, val cenX: Double, val cenY: Double) extends Hexlign with Show2[Double, Pt2]
{ override def typeStr = "HexXlign"
  override def name1: String = "height"
  override def name2: String = "cen"
  override def diameterIn: Double = height
  override def width: Double = diameterOut
  override def el1: Double = height
  override def el2: Pt2 = cen
  override implicit def ev1: ShowT[Double] = ShowT.doublePersistImplicit
  override implicit def ev2: ShowT[Pt2] = Pt2.persistImplicit
  override def syntaxdepth: Int = 3

  override def cen: Pt2 = cenX pp cenY

  override def v1x: Double = cenX + radiusOut / 2
  override def v1y: Double = cenY + radiusIn
  @inline override def v1: Pt2 = Pt2(v1x, v1y)

  override def v2x: Double = cenX + radiusOut
  override def v2y: Double = cenY
  @inline override def v2: Pt2 = Pt2(v2x, v2y)

  override def v3x: Double = cenX + radiusOut / 2
  override def v3y: Double = cenY - radiusIn
  @inline override def v3: Pt2 = Pt2(v3x, v3y)

  override def v4x: Double = cenX - radiusOut / 2
  override def v4y: Double = cenY - radiusIn
  @inline override def v4: Pt2 = Pt2(v4x, v4y)

  override def v5x: Double = cenX - radiusOut
  override def v5y: Double = cenY
  @inline override def v5: Pt2 = Pt2(v5x, v5y)

  override def v6x: Double = cenX - radiusOut / 2
  override def y6: Double = cenY + radiusIn
  @inline override def v6: Pt2 = Pt2(v6x, y6)

  override def sd1CenX: Double = cenX
  override def sd1CenY: Double = cenY + radiusIn
  override def sd1Cen: Pt2 = sd1CenX pp sd1CenY

  override def sd2CenX: Double = cenX + radiusIn * Cos30
  override def sd2CenY: Double = cenY + radiusIn * Sin30
  override def sd2Cen: Pt2 = sd2CenX pp sd2CenY

  override def sd3CenX: Double = cenX + radiusIn * Cos30
  override def sd3CenY: Double = cenY - radiusIn * Sin30
  override def sd3Cen: Pt2 = sd3CenX pp sd3CenY

  override def sd4CenX: Double = cenX
  override def sd4CenY: Double = cenY - radiusIn
  override def sd4Cen: Pt2 = sd4CenX pp sd4CenY

  override def sd5CenX: Double = cenX - radiusIn * Cos30
  override def sd5CenY: Double = cenY - radiusIn * Sin30
  override def sd5Cen: Pt2 = sd5CenX pp sd5CenY
  override def sd6CenX: Double = cenX - radiusIn * Cos30
  override def sd6CenY: Double = cenY + radiusIn * Sin30
  override def sd6Cen: Pt2 = sd6CenX pp sd6CenY

  /** Translate 2D geometric transformation on this HexXlign returns a HexXlign. */
  override def slateXY(xOffset: Double, yOffset: Double): HexXlign = HexXlign(diameterIn, cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexXlign returning a HexXlign. */
  override def scale(operand: Double): HexXlign = HexXlign(diameterIn * operand, cen.scale(operand))

  /** Mirror, reflection 2D geometric transformation on this HexXlign across the X axis, negates Y, returns a HexXlign. */
  override def negY: HexXlign = HexXlign(diameterIn, cen.negY)

  /** Mirror, reflection 2D transformation on this HexXlign across the Y axis, negates X, returns a HexXlign. */
  override def negX: HexXlign = HexXlign(diameterIn, cen.negX)

  /** Rotate 90 degrees in a positive or clockwise direction 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a
   *  HexYlign. Note the change in type. Equivalent to a 270 degree negative or clock wise transformation. */
  override def rotate90: HexYlign = HexYlign(diameterIn, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a HexXlign. */
  override def rotate180: HexXlign = HexXlign(diameterIn, cen.rotate180)

  /** Rotate 270 degrees in a positive or clockwise direction 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a
   *  HexYlign. Note the change in type. Equivalent to a 90 degree negative or clock wise transformation. */
  override def rotate270: HexYlign = HexYlign(diameterIn, cen.rotate270)

  /** Prolign 2d geometric transformations, similar transformations that retain alignment with the axes on this HexXlign returns a HexXlign. */
  override def prolign(matrix: ProlignMatrix): HexXlign = HexXlign(diameterIn, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the X Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexXlign
{ /** Apply factory method for HexXlign, Creates a regular hexagon with 2 of its side aligned to the X axis. */
  def apply(height: Double, cen: Pt2 = Pt2Z): HexXlign = new HexXlign(height, cen.x, cen.y)

  /** Apply factory method for [[HexXlign]], Creates a regular hexagon with 2 of its side aligned to the Y axis. */
  def apply(height: Double, xCen: Double, yCen: Double): HexXlign = new HexXlign(height, xCen, yCen)

  def unapply(input: HexXlign): Some[(Double, Pt2)] = Some((input.height, input.cen))

  implicit val persistImplicit: Persist[HexXlign] =
    new Persist2[Double, Pt2, HexXlign]("HexXlign", "height", _.height,"cen", _.cen, apply)

  implicit val slateImplicit: Slate[HexXlign] = (obj: HexXlign, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[HexXlign] = (obj: HexXlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexXlign] = (obj, matrix) => obj.prolign(matrix)
}