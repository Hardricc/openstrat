/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** This is a compound graphic based on a Rect shape. A rectangle aligned to the X and Y axes.  */
trait RectCompound extends RectGraphic with RectangleCompound
{
  override def shape: Rect

  /*override def svgElem: SvgRect = SvgRect(shape.negY.slateXY(0, boundingRect.bottom + boundingRect.top).
    attribs ++ facets.flatMap(_.attribs))*/
  override def mainSvgElem: SvgRect = SvgRect(attribs)
  /** Translate geometric transformation. */
  override def slateXY(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.slateXY(xOperand, yOperand), facets, children.SlateXY(xOperand, yOperand))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectCompound = RectCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: RectCompound = RectCompound(shape.negY, facets, children.negY)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: RectCompound = RectCompound(shape.negX, facets, children.negX)

  override def rotate90: RectCompound = RectCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: RectCompound = RectCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: RectCompound = RectCompound(shape.rotate270, facets, children.rotate270)

  override def prolign(matrix: ProlignMatrix): RectCompound = RectCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def scaleXY(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.scaleXY(xOperand, yOperand), facets, children.scaleXY(xOperand, yOperand) )

  override def addChildren(newChildren: Arr[Graphic2Elem]): RectCompound = RectCompound(shape, facets, children ++ newChildren)

  def htmlSvg: HtmlSvg =
  { val atts = RArr(WidthAtt(shape.width), HeightAtt(shape.height), ViewBox(shape.left, -shape.top, shape.width, shape.height), CentreBlockAtt)
    val svgElems = children.flatMap(_.svgElems)
    new HtmlSvg(svgElems, atts)
  }
}

/** Companion object for the RectCompound trait, contains implicit instances for 2D geometric transformation type classes. */
object RectCompound
{
  def apply(shape: Rect, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()): RectCompound =
    RectCompoundImp(shape, facets, children)

  implicit val slateImplicit: SlateXY[RectCompound] = (obj: RectCompound, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[RectCompound] = (obj: RectCompound, operand: Double) => obj.scale(operand)
  implicit val XYScaleImplicit: ScaleXY[RectCompound] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[RectCompound] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[RectCompound] = new TransAxes[RectCompound]
  { override def negYT(obj: RectCompound): RectCompound = obj.negY
    override def negXT(obj: RectCompound): RectCompound = obj.negX
    override def rotate90(obj: RectCompound): RectCompound = obj.rotate90
    override def rotate180(obj: RectCompound): RectCompound = obj.rotate180
    override def rotate270(obj: RectCompound): RectCompound = obj.rotate270
  }

  case class RectCompoundImp(shape: Rect, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends RectCompound
}