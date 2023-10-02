/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon where two of the sides are parallel to the Y Axis. This will be the standard Hex for the Tiling module. */
final class HexParrY(val unsafeArray: Array[Double]) extends Hexlign with Tell2[Double, Pt2]
{ override type ThisT = HexParrY
  override def fromArray(array: Array[Double]): HexParrY = new HexParrY(array)

  override def typeStr = "HexParrY"
  def width: Double = (v1x - v4x).abs

  override def name1: String = "width"
  override def name2: String = "cen"
  override def diameterIn: Double = (v1x - v5x).abs
  override def height: Double = (v0y - v3y).abs
  override def diameterOut: Double = (v0y - v3y).abs
  override def radiusIn: Double = diameterIn / 2
  override def radiusOut: Double = diameterOut / 2
  override def show1: Double = width
  override def show2: Pt2 = cen
  override implicit def persist1: Show[Double] = Show.doublePersistEv
  override implicit def persist2: Show[Pt2] = Pt2.persistImplicit
  override def syntaxDepth: Int = 3

  /** maps the vertices of this [[HexParrY]] to a new [[HexparrY]] instance. */
  override def vertsTrans(f: Pt2 => Pt2): HexParrY = HexParrY.fromArray(unsafeMap(f))

  /** Translate 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def slate(delta: Vec2Like): HexParrY = vertsTrans(_.slate(delta))

  /** Translate 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def slateXY(xDelta: Double, yDelta: Double): HexParrY = vertsTrans(_.xySlate(xDelta, yDelta))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def scale(operand: Double): HexParrY = vertsTrans(_.scale(operand))

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the X axis, negates Y, returns a HexYlign. */
  override def negY: HexParrY = HexParrY.fromArray(unsafeNegY)

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the Y axis, negates X, returns a HexYlign. */
  override def negX: HexParrY = HexParrY.fromArray(unsafeNegX)

  /** Rotate 90 degrees positively or anti clockwise  2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 270 degrees
   *  clockwise. */
  override def rotate90: HexParrX = HexParrX.fromArray(unsafeMap(_.rotate90))

  /** Rotate 180 degrees 2D geometric transformation on a HexYlign, returns a HexYlign. */
  override def rotate180: HexParrY = vertsTrans(_.rotate180)

  /** Rotate 270 degrees positively or anti clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 90 degrees
   *  clockwise. */
  override def rotate270: HexParrX = HexParrX.fromArray(unsafeMap(_.rotate270))

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes on this HexTlign returns a HexYlign. */
  override def prolign(matrix: ProlignMatrix): HexParrY = HexParrY(diameterIn, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the Y Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexParrY
{
  /** Apply factory method for the [[HexParrY]] class takes the width and centre point as specification. The centre poit can be specified as a [[Pt2]]
   *  or 2 [[Double]]s. */
  def apply(width: Double, cen: Pt2 = Pt2Z): HexParrY = apply(width, cen.x, cen .y)

  /** Apply factory method for the [[HexParrY]] class takes the width and centre point as specification. The centre poit can be specified as a [[Pt2]]
   *  or 2 [[Double]]s. */
  def apply(width: Double, xCen: Double, yCen: Double): HexParrY =
  { val dsq3 = width / 3.sqrt
    val d2sq3: Double = width /(3.sqrt * 2)
    val w2 = width / 2
    val array = Array[Double](
      xCen, yCen + dsq3,
      xCen + w2, yCen + d2sq3,
      xCen + w2, yCen - d2sq3,
      xCen, yCen - dsq3,
      xCen - w2, yCen - d2sq3,
      xCen - w2, yCen + d2sq3)
    new HexParrY(array)
  }

  def unapply(input: HexParrY): Some[(Double, Pt2)] = Some((input.width, input.cen))

  def fromArray(array: Array[Double]): HexParrY = new HexParrY(array)

  implicit val persistImplicit: Persist[HexParrY] = Persist2[Double, Pt2, HexParrY]("HexYlign", "width", _.width,"cen", _.cen, apply)
  implicit val slateImplicit: Slate[HexParrY] = (obj: HexParrY, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[HexParrY] = (obj: HexParrY, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexParrY] = (obj, matrix) => obj.prolign(matrix)
}