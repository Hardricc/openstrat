/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._
import Colour.Black

/** This trait is for Canvas Implementations with a Top left origin and downward y axis. It should not be used by graphical applications. */
trait CanvasTopLeft extends CanvasPlatform
{
   protected def tlx: Double = width / 2
   protected def tly: Double = height / 2
   def tlCen: Vec2 =>  Vec2 = v => Vec2(tlx + v.x, tly - v.y)
   def fTl(pts: Vec2s): Vec2s = pts.fTrans(tlCen)
   
   override def fillPoly(fp: FillPoly): Unit = tlFillPoly(fp.fTrans(tlCen))
   override def drawPoly(dp: DrawPoly): Unit = tlDrawPoly(dp.fTrans(tlCen))   
   override def polyFillDraw(fdp: FillDrawPoly): Unit = tlPolyFillDraw(fdp.fTrans(tlCen))
   override def lineSegsDraw(lineSegs: List[Line2], lineWidth: Double, linesColour: Colour): Unit =
      tlLineSegsDraw(lineSegs.fTrans(tlCen), lineWidth, linesColour): Unit
   
   override def shapeFill(segs: List[ShapeSeg], colour: Colour): Unit = tlShapeFill(segs.fTrans(tlCen), colour)
   override def shapeFillDraw(segs: List[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): Unit =
      tlShapeFillDraw(segs.fTrans(tlCen), fillColour, lineWidth, lineColour)
   override def shapeDraw(segs: List[ShapeSeg], lineWidth: Double, lineColour: Colour): Unit =
      tlShapeDraw(segs.fTrans(tlCen), lineWidth, lineColour)   
   
   override def arcDraw(arc: Arc, lineWidth: Double, lineColour: Colour): Unit = tlArcDraw(arc.fTrans(tlCen), lineWidth, lineColour)   
   override def textFill(posn: Vec2, text: String,  fontSize: Int, colour: Colour, align: TextAlign): Unit =
      tlTextFill(tlx + posn.x, tly - posn.y, text, fontSize, colour, align)
   override def textDraw(posn: Vec2, text: String,  fontSize: Int, colour: Colour = Black): Unit =
      tlTextDraw(tlx + posn.x, tly - posn.y, text, fontSize, colour)
    
//   override def textFill(x: Double, y: Double, text: String,  fontSize: Int, colour: Colour): Unit =
//      tlTextFill(tlx + x, tly - y, text, fontSize, colour)
//   override def textDraw(x: Double, y: Double, text: String,  fontSize: Int, colour: Colour): Unit =
//      tlTextDraw(tlx + x, tly - y, text, fontSize, colour)
   override def clip(pts: Vec2s): Unit = tlClip(fTl(pts))   
   
   protected def tlFillPoly(fp: FillPoly): Unit
   protected def tlDrawPoly(dp: DrawPoly): Unit
   protected def tlPolyFillDraw(fdp: FillDrawPoly): Unit
   protected def tlLineSegsDraw(lineSegs: List[Line2], lineWidth: Double, linesColour: Colour): Unit

   protected def tlShapeFill(segs: List[ShapeSeg], colour: Colour): Unit
   protected def tlShapeFillDraw(segs: List[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit
   protected def tlShapeDraw(segs: List[ShapeSeg], lineWidth: Double, lineColour: Colour): Unit
   protected def tlArcDraw(arc: Arc, lineWidth: Double, lineColour: Colour): Unit
   
   protected def tlTextFill(x: Double, y: Double, text: String, fontSize: Int, textColour: Colour, align: TextAlign): Unit
   protected def tlTextDraw(x: Double, y: Double, text: String, fontSize: Int, lineColour: Colour): Unit
//   protected def tlCircleFill(x: Double, y: Double, radius: Double, colour: Colour): Unit
   protected def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Vec2(x - width / 2, height / 2 - y), mb)
   protected def mouseDownTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDown(Vec2(x - width / 2, height / 2 - y), mb)
   protected def mouseMovedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseMoved(Vec2(x - width / 2, height / 2 - y), mb)
   protected def mouseDraggedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDragged(Vec2(x - width / 2, height / 2 - y), mb)
   protected def tlClip(pts: Vec2s): Unit
   
}