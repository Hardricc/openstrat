/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** the Square trait can either be a [[Sqlign]], an aligned square or a [[SquareImp]], a general square. */
trait Square extends Rectangle
{ type ThisT <: Square
  override def typeStr: String = "Square"
  /** The width of this square. */
  def width: Double

  def rotation: AngleVec

  def mapSquare(f: Pt2 => Pt2): Square = Square.fromArray(unsafeMap(f))

  /** Translate geometric transformation on a Square returns a Square. */
  override def slate(offset: Vec2Like): Square = ??? //Square.s2s4(sd1Cen.slate(offset), sd3Cen.slate(offset))

  /** Translate geometric transformation on a Square returns a Square. */
  override def slateXY(xDelta: Double, yDelta: Double): Square = mapSquare(_.addXY(xDelta, yDelta))

  /** Uniform scaling transformation on a Square returns a Square. */
  override def scale(operand: Double): Square = ???//Square.s2s4(sd1Cen.scale(operand), sd3Cen.scale(operand))

  /** Mirror, reflection transformation across the X axis on a Square, returns a Square. */
  override def negY: Square = Square.fromArray(unsafeNegY)

  /** Mirror, reflection transformation across the X axis on a Square, returns a Square. */
  override def negX: Square = Square.fromArray(unsafeNegX)

  override def rotate90: Square = ???//Square(width, rotation, xCen, yCen)
  override def rotate180: Square = ???
  override def rotate270: Square = ???

  override def prolign(matrix: ProlignMatrix): Square = ???//Square.s2s4(sd1Cen.prolign(matrix), sd3Cen.prolign(matrix))

  override def reflect(lineLike: LineLike): Square = ???//Square.s2s4(sd1Cen.reflect(lineLike), sd3Cen.reflect(lineLike))

  override def rotate(angle: AngleVec): Square = ???//Square.s2s4(sd1Cen.rotate(angle), sd3Cen.rotate(angle))
}

/** Companion object for the Square trait. However its apply methods delegate to the [[SquareImp]] implementation class. */
object Square extends ShapeIcon
{
  override type ShapeT = Sqlign
  //def sd3sd1(s3cen: Pt2, s1cen: Pt2): Square = SquareImp.sd3sd1(s3cen, s1cen)

  def fromArray(array: Array[Double]) = new SquareImp(array)

  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the
   * square's scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with
   * its centre at the axes centre. Otherwise the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre.
   * If you want to create a square aligned to the axes, then you are probably better using the Sqlign factory apply method. */
  def apply(width: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): Square =
  { val rtVec = xVec2(width / 2).rotate(rotation)
    val upVec = yVec2(width / 2).rotate(rotation)
    val p0 = cen - rtVec + upVec
    val p1 = cen + rtVec + upVec
    val p2 = cen + rtVec - upVec
    val p3 = cen - rtVec - upVec
    val array: Array[Double] = Array[Double](p0.x, p0.y, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
    new SquareImp(array)
  }

  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the
   * square's scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with
   * its centre at the axes centre. Otherwise the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre.
   * If you want to create a square aligned to the axes, then you are probably better using the Sqlign factory apply method. */
  def apply(width: Double, rotation: AngleVec, xCen: Double, yCen: Double): Square = apply(width, rotation, xCen pp yCen)


  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the
   * square's scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with
   * its centre at the axes centre. Otherwise the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre.
   * If you want to create a square aligned to the axes, then you are probably better using the Sqlign factory apply method. */
  //def apply(width: Double, rotation: AngleVec, cen: Pt2): Square = xy(width, rotation, cen.x, cen.y)


  /** Scale the Square and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, xCen: Double, yCen: Double): Sqlign = Sqlign(scale, xCen, yCen)

  /** Scale the Shape and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, cen: Pt2 = Pt2Z): Sqlign = Sqlign(scale, cen)

  override def fill(colour: Colour): ShapeGraphicIcon = ???

  /** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and
   *  rotated while remaining a Square. */
  final class SquareImp(val unsafeArray: Array[Double]) extends Square //with RectS2S4
  { override type ThisT = SquareImp

    override def unsafeFromArray(array: Array[Double]): SquareImp = new SquareImp(array)

    //val sd1CenX: Double, val sd1CenY: Double, val sd3CenX: Double, val sd3CenY: Double
    @inline override def width: Double = width1
    @inline override def width2: Double = width1

    override def alignAngle: AngleVec = ???

    override def attribs: Arr[XANumeric] = ???

    override def rotation: AngleVec = ???

   // override def productArity: Int = 3
    //override def productElement(n: Int): Any = 4
    override def toString: String = s"SquareClass($v0x, $v0y; $v1x, $v1y)"
  }

  /** Factory object for squares. */
  object SquareImp
  {
    def sd3sd1(sd3cen: Pt2, sd1cen: Pt2): SquareImp = {
      val vright: Vec2 = sd1cen - sd3cen
      val array = Array[Double]()
      new SquareImp(array)
    }

    def sd3sd1(sd3cenX: Double, sd3cenY: Double, sd1cenX: Double, sd1cenY: Double): SquareImp = sd3sd1(Pt2(sd3cenX, sd3cenY), Pt2(sd1cenX, sd1cenY))
  }
}