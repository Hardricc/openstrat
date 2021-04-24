/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pEarth
import geom._, pCanv._, pGrid._

/** Gui to display E80Data objects in isolation. */
case class E80GridGui(canv: CanvasPlatform, scen: E80DataOld, cenRoord: Roord) extends CmdBarGui("North West Europe Gui")
{
  implicit val grid: HexGridIrrOld = scen.grid
  val scale = 40
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }

  val sides: GraphicElems = scen.sTerrs.gridMap { (r, b) =>
    if (b) grid.sidePolygon(r).fill(Colour.Blue)
    else grid.sideRoordToLine2(r).draw()
  }

  var statusText = "Tile Grid for North West Europe"
  def thisTop(): Unit = reTop(Arr())
  thisTop()
  def frame = (tiles ++ sides).gridRoordScale(cenRoord, scale)
  mainRepaint(frame)
}