/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, math.Pi, Colour.Black

/** Circle class is defined by its centre and radius. It fulfills the interface for an Ellipse.
 *  @groupdesc EllipticalGroup Class members that treat this circle as a special case of an ellipse.
 *  @groupname EllipticalGroup Elliptical Members
 *  @groupprio EllipticalGroup 1010 */
final class Circle protected[geom](val radius: Double, val cenX: Double, val cenY: Double) extends Ellipselign with OrdinaledElem with AxisFree
{ type ThisT = Circle

  override def fTrans(f: Pt2 => Pt2): Circle =
  { val v1: Pt2 = cen.addX(radius)
    val newV1: Pt2 = f(v1)
    val newCen = f(cen)
    val newRadius = newCen.distTo(newV1)
    Circle(newRadius, newCen)
  }
  
  /** Diameter of the circle. This has the same value as width, a property that hasn't been created yet. */
  @inline def diameter: Double = radius * 2

  override def area: Double = Pi * radius * radius
  override def e: Double = 0
  override def h: Double = 0
  override def boundingWidth: Double = diameter
  override def boundingHeight: Double = diameter

  /** Translate geometric transformation on a Circle returns a Circle. */
  override def slateXY(xDelta: Double, yDelta: Double): Circle = Circle(radius, cen.addXY(xDelta, yDelta))

  /** uniform scaling transformation on a Circle returns a circle. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): Circle = Circle(radius * operand, cen.scale(operand))

  override def prolign(matrix: ProlignMatrix): Circle = fTrans(_.prolign(matrix))

  override def rotate(angle: AngleVec): Circle = Circle(radius, cen.rotate(angle))

  override def reflect(lineLike: LineLike): Circle = Circle(radius, cen.reflect(lineLike))

  def boundingRect: Rect = Rect(diameter, diameter, cenX, cenY)// BoundingRect(cenX - radius, cenX + radius, cenY - radius, cenY + radius)
  
  override def fill(fillfacet: FillFacet): CircleFill = CircleFill(this, fillfacet)
  override def fillInt(intValue: Int): CircleFill = CircleFill(this, Colour(intValue))

  def fillRadial(cenColour: Colour, outerColour: Colour): CircleCompound = CircleCompound(this, RArr(FillRadial(cenColour, outerColour)), RArr())
  
  override def draw(lineWidth: Double = 2, lineColour: Colour = Colour.Black): CircleDraw = CircleDraw(this, lineWidth, lineColour)

  /** Returns a [[CircleCompound]] with a [[FillFacet]] and a [[DrawFact]]. */
  override def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2.0): CircleCompound =
    CircleCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)), RArr())

  override def fillActive(fillColour: Colour, pointerID: Any): CircleCompound = CircleCompound(this, RArr(fillColour), RArr(CircleActive(this, pointerID)))

  def fillActiveTextAbs(fillColour: Colour, pointerID: Any, str: String, fontSize: Double, fontColour: Colour = Black): CircleCompound =
    CircleCompound(this, RArr(fillColour), RArr(CircleActive(this, pointerID), TextFixed(str, fontSize, cen, fontColour)))

  def fillActiveTextlign(fillColour: Colour, pointerEv: Any, str: String, fontSize: Double, fontColour: Colour = Black, align: TextAlign = CenAlign):
  CircleCompound = CircleCompound(this, RArr(fillColour), RArr(CircleActive(this, pointerEv), Textlign(str, fontSize, cenDefault, fontColour, align)))

  override def fillActiveText(fillColour: Colour, pointerEv: Any, str: String, fontRatio: Double, fontColour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle, minSize: Double = 4): CircleCompound =
    CircleCompound(this, RArr(fillColour, TextFacet(str, fontRatio, fontColour, align, baseLine, minSize)), RArr(CircleActive(this, pointerEv)))

  def rAttrib: XmlAtt = XmlAtt("r", radius.toString)
  override def attribs: RArr[XmlAtt] = RArr(cxAttrib, cyAttrib, rAttrib)

  private def rr2: Double = diameter * 2.sqrt
  override def topRight: Pt2 = Pt2(rr2, rr2)
  override def bottomRight: Pt2 = Pt2(rr2, -rr2)
  override def bottomLeft: Pt2 = Pt2(-rr2, -rr2)
  override def topLeft: Pt2 = Pt2(-rr2, rr2)

  @inline override def radius1: Double = radius
  @inline override def radius2: Double = radius
  @inline override def rMajor: Double = radius
  @inline override def rMinor: Double = radius
  @inline override def xRadius: Double = radius
  @inline override def yRadius: Double = radius

  override def axesPt1x: Double = cenX + radius
  override def axesPt1y: Double = cenY
  override def axesPt2x: Double = cenX
  override def axesPt2y: Double = cenY - axesPt4y
  override def axesPt3x: Double = cenX - radius
  override def axesPt3y: Double = cenY
  override def axesPt4x: Double = cenX
  override def axesPt4y: Double = cenY + radius
  override def axesPt4: Pt2 = Pt2(cenX, axesPt4y)
  override def cenP1: Vec2 = Vec2(radius, 0)
  override def cenP2: Vec2 = Vec2(0, -radius)
  override def cenP3: Vec2 = Vec2(-radius, 0)
  override def cenP4: Vec2 = Vec2(0, radius)

  /** Determines if the parameter point lies inside this [[Circle]]. */
  override def ptInside(pt: Pt2): Boolean = pt match
  { case Pt2(x, y) if x > cenX + radius | x < cenX - radius | y > cenY + radius | y < cenY - radius => false
    case Pt2(x, y) => radius >= ((x -cenX).squared + (y - cenY).squared).sqrt
  }
}

/** This is the companion object for the Circle case class. It provides factory methods for creating [[Circle]]s. */
object Circle extends ShapeIcon
{ override type ShapeT = Circle

  /** Factory apply method for creating a circle. The first parameter gives the radius of the circle. The default centre is at the origin. There is an apply
   * method name overload that takes the X and Y centre values as parameters There are corresponding d methods that take a diameter as the first parameter. */
  def apply(radius: Double, cen: Pt2 = Pt2Z) = new Circle(radius, cen.x, cen.y)

  /** Factory apply method for creating a circle. The first parameter gives the radius of the circle, followed by the X and Y centre values. There is an apply
   * method name overload that takes a [[Pt2]] as a second parameter with a default value of the origin. */
  def apply(radius: Double, cenX: Double, cenY: Double): Circle = new Circle(radius, cenX, cenY)

  /** Factory method for creating a circle. The first parameter gives the diameter of the circle. The default centre is at the origin. There is a name overload
   * that takes the X and Y centre values as parameters. There are corresponding apply methods that take a radius as the first parameter. */
  def d(diameter: Double, cen: Pt2 = Pt2Z) = new Circle(diameter / 2, cen.x, cen.y)

  /** Factory method for creating a circle. The first parameter gives the diameter of the circle, followed by the X and Y centre values. There is a method name
   * overload that takes a [[Pt2]] as a second parameter with a default value of the origin. There are corresponding apply methods that take a radius as the
   * first parameter. */
  def d(diameter: Double, cenX: Double, cenY: Double): Circle = new Circle(diameter / 2, cenX, cenY)

  override def reify(scale: Double, cen: Pt2): Circle = Circle(scale, cen)
  override def reify(scale: Double, xCen: Double, yCen: Double): Circle = Circle(scale, xCen, yCen)
  
  implicit val slateImplicit: Slate[Circle] = (obj, dx, dy) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Circle] = (obj, operand) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Circle] = (obj: Circle, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Circle] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[Circle] = new TransAxes[Circle]
  { override def negYT(obj: Circle): Circle = obj.negY
    override def negXT(obj: Circle): Circle = obj.negX
    override def rotate90(obj: Circle): Circle = obj.rotate90
    override def rotate180(obj: Circle): Circle = obj.rotate180
    override def rotate270(obj: Circle): Circle = obj.rotate270
  }

  override def fill(colour: Colour): CircleFillIcon = CircleFillIcon(colour)

  /** [[Filling]] type class instance / evidence for [[Circle]] and [[CircleFill]] */
  val fillerEv: Filling[Circle, CircleFill] = (obj, ff) => obj.fill(ff)
}

final class CircleLen2 protected[geom](radius: Length, cenX: Length, cenY: Length) extends EllipseLen2
{ type ThisT = CircleLen2
  override def slate(operand: VecPtLen2): CircleLen2 = CircleLen2(radius, cenX + operand.x, cenY + operand.y)
  override def slate(xOperand: Length, yOperand: Length): CircleLen2 = CircleLen2(radius, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Length): CircleLen2 = CircleLen2(radius, cenX + xOperand, cenY)
  override def slateY(yOperand: Length): CircleLen2 = CircleLen2(radius, cenX, cenY + yOperand)
  override def scale(operand: Double): CircleLen2 = CircleLen2(radius, cenX * operand, cenY * operand)
  override def mapGeom2(operand: Length): Circle = Circle(radius / operand, cenX / operand, cenY / operand)
  override def draw(lineWidth: Double, lineColour: Colour): CircleLen2Draw = CircleLen2Draw(this, lineWidth, lineColour)
  
  override def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2.0): CircleLen2Compound =
    CircleLen2Compound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)))
}

object CircleLen2
{ /** Factory apply method for creating a circle with [[Length]] units. The first parameter gives the radius of the circle. The default centre is at the origin.
   * There is an apply name overload that takes the X and Y centre [[Length]] values as parameters There are corresponding d methods that take a diameter as the
   * first parameter. */
  def apply(radius: Length, cenX: Length, cenY: Length): CircleLen2 = new CircleLen2(radius, cenX, cenY)

  /** Factory apply method for creating a circle with [[Length]] units. The first parameter gives the radius of the circle, followed by the X and Y centre
   * [[Length]] values. There is an apply method name overload that takes a [[PtLen2]] as a second parameter with a default value of the origin. */
  def apply(radius: Length, cen: PtLen2): CircleLen2 = new CircleLen2(radius, cen.x, cen.y)

  /** Factory method for creating a circle. The first parameter gives the diameter of the circle. The default centre is at the origin. There is a name
   * overload that takes the X and Y centre values as parameters. There are corresponding to apply methods that take a radius as the first parameter. */
  def d(diameter: Length, cenX: Length, cenY: Length): CircleLen2 = new CircleLen2(diameter / 2, cenX, cenY)

  /** Factory method for creating a circle with [[Length]] units. The first parameter gives the diameter of the circle, followed by the X and Y centre values.
   * There is a method name overload that takes a [[PtLen2]] as a second parameter with a default value of the origin. There are corresponding apply methods
   * that take a radius as the first parameter. */
  def d(diameter: Length, cen: PtLen2): CircleLen2 = new CircleLen2(diameter / 2, cen.x, cen.y)
}