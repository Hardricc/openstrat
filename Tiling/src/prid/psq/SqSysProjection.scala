/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, pgui._

trait SqSysProjection extends TSysProjection
{ type GridT <: SqGridSys
  var gChild: SqGridSys
  def foreach(f: SqCen => Unit): Unit = gChild.foreach(f)
}

case class SqSysProjectionFlat(gridSys: SqGridSys, panel: Panel) extends SqSysProjection with TSysProjectionFlat
{
  type GridT = SqGridSys
  var pixCScale: Double = gridSys.fullDisplayScale(panel.width, panel.height)
  override def pixTileScale: Double = pixCScale * 2
  override def pixRScale: Double = pixCScale
  var focus: Vec2 = gridSys.defaultView(pixCScale).vec
  override def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems = ???

  var gChild: SqGridSys = getGChild

  def getGChild: SqGridSys = gridSys

  def setGChild: Unit = gChild = getGChild

  override def tiles: PolygonArr = gChild.map(_.sqVertPolygon.map(gridSys.flatSqCoordToPt2(_)).slate(-focus).scale(pixCScale))

  override def tileActives: Arr[PolygonActive] =
    gChild.map(hc => hc.sqVertPolygon.map(gridSys.flatSqCoordToPt2(_)).slate(-focus).scale(pixCScale).active(hc))

  /** The visible hex sides. */
  override def sideLines: LineSegArr = gChild.sideLines.slate(-focus).scale(pixCScale)//LineSegArr()
    //gChild.sideLineSegSqCs.map(_.map(gridSys.hCoordToPt2(_))).slate(-focus).scale(pixCScale)

  /** The visible inner hex sides. */
  override def innerSideLines: LineSegArr = LineSegArr()

  /** The visible outer hex sides. */
  override def outerSideLines: LineSegArr = LineSegArr()
}