/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A Rectangle aligned to the X and Y axes. */
trait Rect extends Rectangle with Rectangularlign with ShapeOrdinaled
{ @inline final override def x1: Double = xTopRight
  @inline final override def y1: Double = yTopRight
  @inline final override def v1: Pt2 = x1 pp y1
  @inline final override def x2: Double = xBottomRight
  @inline final override def y2: Double = yBottomRight
  @inline final override def v2: Pt2 = x2 pp y2
  @inline final override def cen: Pt2 = xCen pp yCen
  override def alignAngle: AngleVec = Deg0
  @inline final def x3: Double = xBottomLeft
  @inline final def y3: Double = yBottomLeft
  @inline final def v3: Pt2 = bottomLeft
  @inline final def x4: Double = xTopLeft
  @inline final def y4: Double = yTopLeft
  @inline final def v4: Pt2 = topLeft


  /** The X component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   * be thought of as vertex 0.5. */
  override def xSd1Cen: Double = ???

  /** The Y component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   * be thought of as vertex 0.5. */
  override def ySd1Cen: Double = ???

  final override def sd1Cen: Pt2 = Pt2(xCen, yCen + height / 2)
  final override def sd2Cen: Pt2 = Pt2(xCen + width / 2, yCen)
  final override def sd3Cen: Pt2 = Pt2(xCen, yCen -height / 2)

  final override def xSd2Cen: Double = ???

  final override def ySd2Cen: Double = ???


  override def xSd4Cen: Double = ???

  override def ySd4Cen: Double = ???

  final override def sd4Cen: Pt2 = Pt2(xCen - width / 2, yCen)

  /** Translate geometric transformation on a Rect returns a Rect. */
  override def slate(offset: Vec2Like): Rect = Rect(width, height, cen.slate(offset))

  /** Translate geometric transformation on a Rect returns a Rect. */
  override def xySlate(xOffset: Double, yOffset: Double): Rect = Rect(width, height, xCen + xOffset, yCen + yOffset)

  /** Uniform scaling transformation on a Rect returns a Rect. */
  override def scale(operand: Double): Rect = Rect(width * operand, height * operand, cen.scale(operand))

  /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
  override def negY: Rect = Rect(width, height, cen.negY)

  /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
  override def negX: Rect = Rect(width, height, cen.negX)

  override def rotate90: Rect = ???
  override def rotate180: Rect = ???
  override def rotate270: Rect = ???

  override def prolign(matrix: ProlignMatrix): Rect = Rect.cenV0(cen.prolign(matrix), v1.prolign(matrix))

  override def xyScale(xOperand: Double, yOperand: Double): Rect = Rect.cenV0(cen.xyScale(xOperand, yOperand), v1.xyScale(xOperand, yOperand))

  override def activeChildren(id: Any, children: GraphicElems): RectCompound = RectCompound(this, Arr(), active(id) +: children)

  override def fill(fillColour: Colour): RectangleFill = RectFill(this, fillColour)
  override def fillHex(intValue: Int): RectFill = RectFill(this, Colour(intValue))
}

/** Companion object for the [[Rect]] trait contains factory methods for the Rect trait which delegate to the [[RectImp]] class. */
object Rect
{
  def apply(width: Double, height: Double, cen: Pt2 = Pt2Z): Rect = new RectImp(width, height, cen.x, cen.y)
  def apply(width: Double, height: Double, xCen: Double, yCen: Double): Rect = new RectImp(width, height, xCen, yCen)

  /** Factory method for Rect from width, height and the topRight position parameters. The default position for the topLeft parameter places the top
   *  right vertex of the Rect at the origin. */
  def tr(width: Double, height: Double, topRight: Pt2 = Pt2Z): Rect = new RectImp(width, height, topRight.x - width / 2, topRight.y - height / 2)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the topLeft parameter places the top
   *  left vertex of the Rect at the origin. */
  def tl(width: Double, height: Double, topLeft: Pt2 = Pt2Z): Rect = new RectImp(width, height, topLeft.x + width / 2, topLeft.y - height / 2)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the bottomRight parameter places the
   * bottom right vertex of the Rect at the origin. */
  def br(width: Double, height: Double, bottomRight: Pt2 = Pt2Z): Rect = new RectImp(width, height, bottomRight.x - width / 2, bottomRight.y + height / 2)

  /** Factory method for Rect from width, height and the bottomLeft position parameters. The default position for the bottomLeft parameter places the
   * bottom left vertex of the Rect at the origin. */
  def bl(width: Double, height: Double, bottomLeft: Pt2 = Pt2Z): Rect = new RectImp(width, height, bottomLeft.x + width / 2, bottomLeft.y + height / 2)

  /** Factory method for Rect from width, height and the bottomCentre position parameters. The default position for the bottomCentre parameter places
   *  the bottom centre of the Rect at the origin. */
  def bCen(width: Double, height: Double, bottomCentre: Pt2 = Pt2Z): Rect = new RectImp(width, height, bottomCentre.x, bottomCentre.y + height / 2)

  def cross(width: Double, height: Double, barWidth: Double): Arr[Polygon] = Arr(apply(width, barWidth), apply(barWidth, height))

  def goldenRatio(height: Double): Rectangle = apply(Phi * height, height)

  def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolygonCompound =
    goldenRatio(height).fillDraw(colour, colour.contrast, lineWidth)

  /** Factory method to create a Rect from the centre point and the v0 point. The v0 point or vertex is y convention the top left vertex of the
   * rectangle, but any of the 4 corner vertices will give the correct constructor values. */
  def cenV0(cen: Pt2, v0: Pt2): Rect = new RectImp((v0.x - cen.x).abs * 2, (v0.y - cen.y).abs * 2, cen.x, cen.y)

  implicit val slateImplicit: Slate[Rect] = (obj: Rect, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[Rect] = (obj: Rect, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[Rect] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[Rect] = new TransAxes[Rect]
  { override def negYT(obj: Rect): Rect = obj.negY
    override def negXT(obj: Rect): Rect = obj.negX
    override def rotate90(obj: Rect): Rect = obj.rotate90
    override def rotate180(obj: Rect): Rect = obj.rotate180
    override def rotate270(obj: Rect): Rect = obj.rotate270
  }
  
  /** Implementation class for Rect, a rectangle aligned to the X and Y axes. */
  final case class RectImp(width: Double, height: Double, xCen: Double, yCen: Double) extends Rect
  { override def fTrans(f: Pt2 => Pt2): RectImp = RectImp.cenV0(f(cen), f(v1))
    override def width1: Double = width
    override def width2: Double = height

    override def attribs: Arr[XANumeric] = ???

    /** Translate geometric transformation on a RectImp returns a RectImp. */
    override def xySlate(xOffset: Double, yOffset: Double): RectImp = RectImp(width, height, xCen + xOffset, yCen + yOffset)

    /** Translate geometric transformation on a RectImp returns a RectImp. */
    override def slate(offset: Vec2Like): RectImp = RectImp(width, height, cen.slate(offset))

    /** Uniform scaling transformation on a RectImp returns a RectImp. */
    override def scale(operand: Double): RectImp = RectImp(width * operand, height * operand, cen.scale(operand))
    
    /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
    override def negY: RectImp = RectImp(width, height, cen.negY)

    /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
    override def negX: RectImp = RectImp(width, height, cen.negX)

    override def prolign(matrix: ProlignMatrix): Rect = Rect.cenV0(cen.prolign(matrix), v1.prolign(matrix))

    override def xyScale(xOperand: Double, yOperand: Double): RectImp = RectImp.cenV0(cen.xyScale(xOperand, yOperand), v1.xyScale(xOperand, yOperand))
  }

  /** Companion object for the [[Rect.RectImp]] class */
  object RectImp
  { /** Factory method for Rect.RectImp class. */
    def apply(width: Double, height: Double, cen: Pt2 = Pt2Z): RectImp = new RectImp(width, height, cen.x, cen.y)

    /** Factory method to create a RectImp from the centre point and the v0 point. The v0 point or vertex is y convention the top left vertex of the
     * rectangle, but any of the 4 corner vertices will give the correct constructor values. */
    def cenV0(cen: Pt2, v0: Pt2): RectImp = new RectImp((v0.x - cen.x).abs * 2, (v0.y - cen.y).abs * 2, cen.x, cen.y)
  }
}