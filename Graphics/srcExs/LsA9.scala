/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LsA9(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A9")
{
  def bd(c1: Pt2, c2: Pt2, colour: Colour) = Bezier(Vec2Z, c1, c2, 500 vv 350).draw(colour, 3)
  //This can be more elegantly expressed in dotty
  def fun(a: Int, b: String, c: Double, d: Boolean): Int = a + b.length + c.toInt + (if (d) 1 else 0)
  val pt1 = 500 vv - 400
  val sh1 = PolyCurve(LineTail(Vec2Z), LineTail(200 vv 0), BezierTail(300 vv 300, 350 vv 100, pt1), LineTail(100 vv -200))
  
  def stuff = Arr(
         bd(-100 vv 200, 300 vv 400, Green),
         bd(-150 vv -50, 250 vv 350, Violet),
         bd(-250 vv 50, 200 vv 400, Orange),
         bd(-300 vv 100, 200 vv 0, Pink),
         PolyCurveFill(sh1, Yellow),
         TextGraphic(pt1.toString, pt1, 12),
         )
   repaint(stuff)
}