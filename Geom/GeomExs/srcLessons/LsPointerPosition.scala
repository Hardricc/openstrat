/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

object LsPointerPosition extends LessonGraphics
{ override def title: String = "Pointer position Lesson"
  override def bodyStr: String = """Shows the location and [[Pt2]] coordinates of the point on the screen the user has clicked."""
  override def canv: CanvasPlatform => Any = LsC2Canv(_)

  case class LsC2Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C2")
  { repaints(TextFixed("Please click on the screen in different places.", 28, 0 pp 200, Green))
    setMouseSimple(pt => repaints(TextFixed("You clicked the screen at: " + pt.tellSemisNames(), 28, pt, Red)))
  }
}