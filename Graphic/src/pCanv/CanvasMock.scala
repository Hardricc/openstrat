/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** A class for testing mouse pointer functionality. */
case class CanvasMock(width: Double, height: Double) extends CanvasPlatform
{ override def clip(pts: Polygon): Unit = {}
   
  override def getTime: Long = ???

  /** A callback timer with an elapsed time from a given start point. The function is of form:
   *  (elapsedTime(in milliseconds), Startime(in millseconds) => Unit.
   *  The startTime is to be used to call the next frame at then end of the function, if another frame is needed */
  override def timeOut(f: () => Unit, millis: Integer): Unit = {}

  override def pPolyFill(poly: PolygonTr, col: Colour): Unit = {}
  override def pPolyDraw(poly: PolygonTr, lineWidth: Double, colour: Colour): Unit = {}
  override def pLinePathDraw(pod: LinePathDraw): Unit = {}
   
  override def lineDraw(ld: LineDraw): Unit = {}
  override def cArcDrawOld(ad: CArcDrawOld): Unit = {}
  override def cArcDraw(cad: CArcDraw): Unit = {}
  override def bezierDraw(bd: BezierDraw): Unit = {}
  override def linesDraw(lsd: LinesDraw): Unit = {}
  override def pShapeFill(shape: PolyCurve, colour: Colour): Unit = {}
  override def pShapeDraw(shape: PolyCurve, lineWidth: Double, colour: Colour): Unit = {}
  override def textGraphic(tg: TextGraphic) = {}
  override def textOutline(to: TextOutline): Unit = {}
  override def dashedLineDraw(dld: DashedLineDraw): Unit = {}
  override def circleFillOld(cf: CircleFill): Unit = println("Mock output " + cf.toString)
  override def circleFill(circle: Circle, colour: Colour): Unit = println("Mock output " + circle.toString -- colour.toString)
  override def circleDrawOld(cd: CircleDraw): Unit = println("Mock output " + cd.toString)
  override def circleDraw(circle: Circle, lineWidth: Double, lineColour: Colour): Unit = {}
  override def ellipseFill(cf: EllipseFill): Unit = ???
  override def circleFillDraw(cfd: CircleFillDraw): Unit = println("Mock output " + cfd.toString)
  override def clear(colour: Colour = Colour.White): Unit = {}
  override def gcSave(): Unit = {}
  override def gcRestore(): Unit = {}
  override def saveFile(fileName: String, output: String): Unit = {}
  override def loadFile(fileName: String): EMon[String] = ???
}