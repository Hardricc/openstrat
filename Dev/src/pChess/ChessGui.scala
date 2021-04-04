/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pChess
import geom._, pCanv._, Colour._, pGrid._

case class ChessGui(canv: CanvasPlatform, scen: ChessScen) extends CmdBarGui("Chess")
{ implicit val grid: SquareGridSimple = scen.grid
  var statusText: String = "Welcome to Chess Gui"
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val darkSquareColour = DarkGreen
  val lightSquareColour = LightBlue
  val tiles: GraphicElems = grid.mapRPolygons{ (r, p) =>
    val col = ife(r.yPlusC %% 4 == 0, darkSquareColour, lightSquareColour)
    val yStr: String = ('A' + r.y / 2 - 1).toChar.toString
    val cStr: String = ('0' + r.c / 2).toChar.toString
    p.fillText(col, yStr + cStr, 20) }
  val pieces = scen.pieces.mapSomeWithRoords((r, p) => p.piece().slate(r.gridPt2).fillDraw(p.player.colour, p.player.contrastBW))

  def bTurn = clickButtonOld("Turn ", _ => {
    repaint()
    thisTop()
  })
  def thisTop(): Unit = reTop(Arr(bTurn))
  thisTop()
  def frame = (tiles ++ pieces).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}