/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

trait PolyCurveElem extends GraphicAffineElem with GraphicBoundedAffine
{ type ThisT <: PolyCurveElem
  def shape: ShapeGenOld
  def segsLen: Int = shape.elemsLen
  override def boundingRect: BoundingRect = shape.boundingRect
}

case class PolyCurveFill(shape: ShapeGenOld, colour: Colour) extends PolyCurveElem
{ override type ThisT = PolyCurveFill
  override def fTrans(f: Pt2 => Pt2) = PolyCurveFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.shapeGenFill(ShapeGenFill(shape, colour))
  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
  //override def slateTo(newCen: Pt2): PolyCurveFill = ???
}

case class PolyCurveDraw(shape: ShapeGenOld, colour: Colour = Black, lineWidth: Double = 2.0) extends PolyCurveElem
{ override type ThisT = PolyCurveDraw
  override def fTrans(f: Pt2 => Pt2) = PolyCurveDraw(shape.fTrans(f), colour, lineWidth)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.shapeGenDraw(ShapeGenDraw(shape, colour, lineWidth))
  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
 // override def slateTo(newCen: Pt2): PolyCurveDraw = ???
}

case class PolyCurveFillDraw(shape: ShapeGenOld, fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2.0) extends PolyCurveElem
{ override type ThisT = PolyCurveFillDraw
  override def fTrans(f: Pt2 => Pt2) = PolyCurveFillDraw(shape.fTrans(f), fillColour, lineColour, lineWidth)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeGenFill(ShapeGenFill(shape, fillColour))
    cp.shapeGenDraw(ShapeGenDraw(shape, lineColour, lineWidth))
  }
  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
  //override def slateTo(newCen: Pt2): PolyCurveFillDraw = ???
}

case class PolyCurveFillDrawText(shape: ShapeGenOld, fillColour: Colour, str: String, fontSize: Int = 24, lineColour: Colour = Black,
                                 lineWidth: Double = 2) extends PolyCurveElem
{ override type ThisT = PolyCurveFillDrawText
  override def fTrans(f: Pt2 => Pt2) = PolyCurveFillDrawText(shape.fTrans(f), fillColour, str, fontSize, lineColour, lineWidth)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyCurveFillDraw = PolyCurveFillDraw(shape, fillColour, lineColour, lineWidth)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeGenFill(ShapeGenFill(shape, fillColour))
    cp.shapeGenDraw(ShapeGenDraw(shape, lineColour, lineWidth))
    cp.textGraphic(textOnly)
  }
  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
  //override def slateTo(newCen: Pt2): PolyCurveFillDrawText = ???
}

case class PolyCurveAll(shape: ShapeGenOld, pointerId: Any, str: String, fillColour: Colour, fontSize: Int = 24, lineColour: Colour = Black,
                        lineWidth: Double = 2) extends PolyCurveElem with PolyCurveActive
{ override type ThisT = PolyCurveAll
  override def fTrans(f: Pt2 => Pt2) = PolyCurveAll(shape.fTrans(f), pointerId, str, fillColour, fontSize, lineColour, lineWidth)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyCurveFillDraw = PolyCurveFillDraw(shape, fillColour, lineColour, lineWidth)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeGenFill(ShapeGenFill(shape, fillColour))
    cp.shapeGenDraw(ShapeGenDraw(shape, lineColour, lineWidth))
    cp.textGraphic(textOnly)
  }

  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
 // override def slateTo(newCen: Pt2): PolyCurveAll = ???
}