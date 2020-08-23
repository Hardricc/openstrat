/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA1b(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1")
{
  val cs = Arr(
    Circle(100, 0, 0).draw(2, SeaGreen),
    Circle(70, 50, 80).draw(4, Orange),
    Circle(80, 300, 0).draw(1, Red),
    Circle(80, -250, 150).draw(6, LemonLime),
    Circle(40, 0, -220).draw(10, DarkGoldenRod),
  )

  repaint(cs)
}
