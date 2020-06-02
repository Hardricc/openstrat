/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Circle class is defined by its centre and radius. It fulfills the interface for an Ellipse. */
final case class Circle(radius: Double, xCen: Double, yCen: Double) extends Ellipse
{   
  override def slate(offset: Vec2): Circle = Circle(radius, cen + offset)

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): Circle = Circle(radius, xCen + xOffset, yCen + yOffset)

  override def scale(operand: Double): Circle = Circle(radius * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): Circle = Circle(radius, cen.mirrorXOffset(yOffset))

  override def mirrorX: Circle = Circle(radius, xCen, -yCen)

  override def mirrorYOffset(xOffset: Double): Circle = Circle(radius, cen.mirrorYOffset(xOffset))

  override def mirrorY: Circle = Circle(radius, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Circle = Circle(radius * matrix.vFactor, cen.prolignTrans(matrix))

  override def fill(colour: Colour): CircleFill = CircleFill(this, colour)

  override def rotate90: Circle = Circle(radius, cen.rotate90)

  override def rotate180: Circle = Circle(radius, cen.rotate180)

  override def rotate270: Circle = Circle(radius, cen.rotate270)

  override def rotateRadians(radians: Double): Circle = Circle(radius, cen.rotateRadians(radians))

  override def mirror(line: Line2): Circle = Circle(radius, cen.mirror(line))
}

/** This object provides factory methods for circles. */
object Circle extends ShapeIcon
{ def apply(radius: Double, xCen: Double, yCen: Double): Circle = new Circle(radius, xCen, yCen)
  def apply(radius: Double, cen: Vec2 = Vec2Z): Circle = new Circle(radius, cen.x, cen.y)
  
  implicit val slateImplicit: Slate[Circle] = (obj, offset) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Circle] = (obj, operand) => obj.scale(operand)
  
  override def canEqual(that: Any): Boolean = ???

  override def slate(offset: Vec2): Circle = Circle(offset.x, offset.y, 0.5)
  override def slate(xOffset: Double, yOffset: Double): Circle = Circle(xOffset, yOffset, 0.5)

  override def scale(operand: Double): Circle = ???
  override def mirrorX: Circle.type =Circle
  override def mirrorY: Circle.type = Circle
  override def mirrorXOffset(yOffset: Double): Circle = Circle(0, -2 * yOffset, 0.5)
  override def mirrorYOffset(xOffset: Double): Circle = Circle(-2 * xOffset, 0,  0.5)

  override def prolign(matrix: ProlignMatrix): Circle = ???

  override def rotate90: TransElem = ???

  override def rotate180: TransElem = ???

  override def rotate270: Circle.type = this

  override def rotateRadians(radians: Double): Circle.type = this

  override def mirror(line: Line2): Circle = ???

  override def productArity: Int = 0

  override def productElement(n: Int): Any = ???

  def fill(colour: Colour): GraphicElem = ???
}