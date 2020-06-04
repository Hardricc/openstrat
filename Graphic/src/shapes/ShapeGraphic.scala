/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._

trait ShapeGraphic extends GraphicElem

trait ShapeFillTr extends ShapeGraphic
{ def fillColour: Colour
  def fillAttrib: Attrib = FillAttrib(fillColour)
}

trait ShapeFill extends ShapeFillTr

trait ShapeDrawTr extends ShapeGraphic
{ def lineWidth: Double
  def lineColour: Colour
  def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(lineWidth)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(lineColour)
}

trait ShapeDraw extends ShapeDrawTr
{
  def drawAttribs: Arr[Attrib] = Arr(strokeWidthAttrib, strokeAttrib)
}

trait ShapeFillDraw extends ShapeFillTr with ShapeDrawTr
{ def fillDrawAttribs: Arr[Attrib] = Arr(fillAttrib, strokeWidthAttrib, strokeAttrib)
}

case class FillAttrib(colour: Colour) extends Attrib
{ override def name: String = "fill"
  override def valueStr: String = colour.svgStr
}

case class StrokeWidthAttrib(lineWidth: Double) extends Attrib
{ override def name: String = "stroke-width"
  override def valueStr: String = lineWidth.toString
}

case class StrokeAttrib(colour: Colour) extends Attrib
{ override def name: String = "stroke"
  override def valueStr: String = colour.svgStr
}
