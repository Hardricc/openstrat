/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** E Series lessons deal with games. */
case class LessonE1(canv: CanvasPlatform) extends CanvasSimple("Lesson E1")
{  
   
  repaints(TextGraphic("Start of a game", 28))  
}

case class ScenE(posn: Vec2)
