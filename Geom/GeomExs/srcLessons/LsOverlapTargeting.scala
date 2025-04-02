/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsOverlapTargeting extends LessonGraphics
{ override def title: String = "Targeting overlap Lesson"

  override def bodyStr: String = """Targeting overlap objects."""

  override def canv: CanvasPlatform => Any = LsC5Canv(_)

  case class LsC5Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C5") {
    case class Holder(rect: Rectangle, var colour: Colour = Red)

    def hol(width: Double, height: Double, xCen: Double = 0, yCen: Double = 0): Holder = Holder(Rect(width, height, xCen, yCen))

    val r1 = hol(500, 300)
    val r2 = hol(400, 250)
    val r3 = hol(200, 100, -200)
    val r4 = hol(300, 300, 250, 150)
    val r5 = hol(100, 500)
    var rArr = RArr(r1, r2, r3, r4, r5)

    def gArr = rArr.map(h => h.rect.fillActiveDraw(h.colour, h))

    val startText = TextFixed.xy("Click on the rectangles. All rectangles under the point will cycle their colour.", 28, 0, 400)
    repaint(gArr +% startText)

    mouseUp = (b, s, v) => {
      s.foreach { obj =>
        val h = obj.asInstanceOf[Holder]
        h.colour = h.colour.nextFromRainbow
      }
      repaint(gArr +% startText)
    }
  }
}