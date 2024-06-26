/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

/** This lesson is working, but has what looks like a very dubious implementation. */
case class LsC4(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C4")
{ val r0 = Rect(200, 100)
  val r1 = r0.slateXY(-300,300)
  val r2 = r0.slateXY(300, 300)
  val r3 = r0.slateXY(300, -300)
  val r4 = r0.slateXY(-300, -300)
  val rArr = RArr(r1, r2, r3, r4)
  var colour = Red
  def gArr = rArr.map(r => r.fillActive(colour, r.cenPt))
  val textPosn = 0 pp 0
  val startText = TextFixed("Click on the rectangles to cycle the colour.", 28, textPosn)
  deb((gArr +% startText).length.toString)
  repaint(gArr +% startText)
  
  /** Note you can use what names you like. Here I put the types explicitly for clarity. When you are familiar with an anonymous function, you will
   *  probably want to use a short parameter list like (v, b, s).  */
  mouseUp = (button: MouseButton, selected: RArr[Any], posn: Pt2) => selected match
  {
    case RArrHead(cen: Pt2/*, tail*/) =>
    { colour = colour.nextFrom(ColourArr(Red, Orange, Green))
      repaint(gArr +% startText)
    }
      
    case _ =>  deb("Missed")
  }
}