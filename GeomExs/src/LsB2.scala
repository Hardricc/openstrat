/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pgui._, Colour._

object LsB2 extends LessonGraphics {
  override def title: String = "Timer Lesson"

  override def bodyStr: String = """"""

  override def canv: CanvasPlatform => Any = LsB2Canv(_)

  case class LsB2Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson B2")
  {
    /* This again uses the simplest timer method. The screen is repainted every 15 milliseconds, that forty frames a second. The % operator divides, and returns
     the remainder. This means every 5000 milliseconds or 5 seconds the rectangle goes back to the start. */
    timedRepaint1 { e =>
      val e2 = e % 5000
      Rect(200, 100).slateX(e2 / 4 - 600).fill(Red)
    }
  }
}