/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Not totally sure if trait hierarchy is correct. */
case class EllipseGenGraphic(shape: Ellipse, facets: Arr[ShapeFacet], children: Arr[ShapeGraphic] = Arr()) extends EllipseGraphic
{ 
  /** Return type narrowed to [[SvgEllipse]] from [[SvgElem]] */
  override def svgElem(bounds: BoundingRect): SvgEllipse =
  { //val bounds = shape.boundingRect
    val newEllipse = shape.reflectX.slate(0, bounds.minY + bounds.maxY)
    val newAtts = newEllipse.shapeAttribs
    val atts2 = if (shape.ellipeRotation == 0.degs) newAtts else newAtts +- SvgRotate(- shape.ellipeRotation.degs, shape.xCen, shape.yCen)
    SvgEllipse(atts2 ++ facets.flatMap(_.attribs))
  }
  
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
  { 
    case FillColour(c) => cp.ellipseFill(shape, c)
  //case CurveDraw(w, c) => cp.circleDraw(shape, w, c)
  //case fr: FillRadial => cp.circleFillRadial(shape, fr)*/
  case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }
  
  /** Translate geometric transformation. Translates this Ellipse Graphic into a modified EllipseGraphic. */
  override def slate(offset: Vec2): EllipseGenGraphic = EllipseGenGraphic(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): EllipseGenGraphic =
    EllipseGenGraphic(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): EllipseGenGraphic = EllipseGenGraphic(shape.scale(operand), facets, children.scale(operand))  

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: EllipseGenGraphic = EllipseGenGraphic(shape.reflectX, facets, children.reflectX)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: EllipseGenGraphic = EllipseGenGraphic(shape.reflectY, facets, children.reflectY)

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): EllipseGenGraphic =
    EllipseGenGraphic(shape.reflectXOffset(yOffset), facets, children.reflectXOffset(yOffset))

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): EllipseGenGraphic =
    EllipseGenGraphic(shape.reflectYOffset(xOffset), facets, children.reflectYOffset(xOffset))
  
  override def prolign(matrix: ProlignMatrix): EllipseGenGraphic = EllipseGenGraphic(shape.prolign(matrix), facets, children.prolign(matrix))

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: EllipseGenGraphic = EllipseGenGraphic(shape.rotate90, facets, children.rotate90)

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: EllipseGenGraphic = EllipseGenGraphic(shape.rotate180, facets, children.rotate180)

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: EllipseGenGraphic = EllipseGenGraphic(shape.rotate270, facets, children.rotate270)

  override def rotateRadians(radians: Double): EllipseGenGraphic = EllipseGenGraphic(shape.rotateRadians(radians), facets, children.rotateRadians(radians))

  override def reflect(line: Line): EllipseGenGraphic = ???

  override def scaleXY(xOperand: Double, yOperand: Double): EllipseGenGraphic = ???

  override def shearX(operand: Double): EllipseGenGraphic = ???

  override def shearY(operand: Double): EllipseGenGraphic = ???

  override def reflect(line: Sline): EllipseGenGraphic = ???
}