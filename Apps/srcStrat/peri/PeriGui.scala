/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri
import geom._, prid._, phex._, pgui._, egrid._

class PeriGui(val canv: CanvasPlatform, scenIn: PeriScen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("Diceless Gui") {
  var scen: PeriScen = scenIn
  override implicit val gridSys: EGridSys = scenIn.gridSys

  override def terrs: HCenLayer[WTile] = scen.terrs

  override def sTerrs: HSideOptLayer[WSide, WSideSome] = scen.sTerrs

  override def corners: HCornerLayer = scen.corners

  override implicit def proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))

  override def frame: GraphicElems =
  {
    /*def units: GraphicElems = armies.projSomesHcPtMap { (armies, hc, pt) =>
      val str = pixPerTile.scaledStr(170, armies.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, armies.toString)
      val ref = ife(armies.length == 1, HCenPair(hc, armies.head), HCenPair(hc, armies))
      pStrat.InfantryCounter(proj.pixelsPerTile * 0.45, ref, armies.head.colour).slate(pt)
    }*/

    //def moveSegPairs: LineSegPairArr[Army] = moves.optMapOnA1(_.projLineSeg)

    /** This is the graphical display of the planned move orders. */
    //def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(lineColour = pl.colour).arrow }

    tileFills ++ tileActives ++ sideFills ++ sideActives ++ lines2 //++ hexStrs2(armies.emptyTile(_)) ++ units ++ moveGraphics
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " /* + (scen.turn + 1).toString*/) { _ =>
    //scen = scen.endTurn(moves)
    //moves = NoMoves
    repaint()
    thisTop()
  }
  override def thisTop(): Unit = reTop(bTurn %: proj.buttons)
}

object PeriGui
{ def apply(canv: CanvasPlatform, scen: PeriScen, view: HGView, isFlat: Boolean = false): PeriGui = new PeriGui(canv, scen, view, isFlat)
}