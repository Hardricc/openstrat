package ostrat
package pZug
import pCanv._, geom._, pGrid._

case class ZugGui(canv: CanvasPlatform, scen: ZugScen) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val tiles = grid.mapPolygons[GraphicElem, GraphicElems](scale){(r, p) => p.fillTextActive(terrs(grid.index(r)).colour, r.toHexTile, r.ycStr, 16) }
  val sides = grid.sideLinesAll(scale).draw(2.0)
  var statusText = "Welcome to ZugFuhrer"
  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  mainRepaint(tiles +- sides)
}