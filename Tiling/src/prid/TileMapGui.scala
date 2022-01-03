/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pgui._

abstract class TileMapGui(title: String) extends CmdBarGui(title)
{
  def grid: TGrid
  /** The number of pixels displayed per c column coordinate. */
  var cPScale: Double

  /** The number of pixels from a side of a tile to the opposite tile. */
  def tilePScale: Double

  var focus: Vec2 = Vec2(0, 0)

  def tilePScaleStr = s"scale = ${tilePScale.str2} pixels per tile"
  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit
  def frame: GraphicElems
  def repaint(): Unit = mainRepaint(frame)

  def zoomIn: PolygonCompound = clickButton("+"){_ =>
    cPScale *= 1.1
    repaint()
    statusText = tilePScaleStr
    thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-"){_ =>
    cPScale /= 1.1
    repaint()
    statusText = tilePScaleStr
    thisTop()
  }

  def focusAdj(uniStr: String)(f: (Vec2, Double) => Vec2): PolygonCompound = clickButton(uniStr){butt =>
    val delta = butt(1, 10, 100, 0)
    focus = f(focus, cPScale * delta / 40)
    repaint()
    statusText = focus.strSemi(2, 2)
    thisTop()
  }

  def focusLeft: PolygonCompound = focusAdj("\u2190"){ (v, d) =>
    val newX: Double = (v.x - d).max(grid.left)
    Vec2(newX, v.y)
  }
  def focusRight: PolygonCompound = focusAdj("\u2192"){ (v, d) =>
    val newX: Double = (v.x + d).min(grid.right)
    Vec2(newX, v.y)
  }
  def focusUp: PolygonCompound = focusAdj("\u2191"){ (v, d) =>
    val newY: Double = (v.y + d).min(grid.top)
    Vec2(v.x, newY)
  }

  def focusDown: PolygonCompound = focusAdj("\u2193"){ (v, d) =>
    val newY: Double = (v.y - d).max(grid.bottom)
    Vec2(v.x, newY)
  }
  def navButtons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut, focusLeft, focusRight, focusUp, focusDown)
}

abstract class HexMapGui(title: String) extends TileMapGui(title)
{
  /** The number of pixels from a side of a tile to the opposite tile.The number of pixels. For Hex tiles this is 4 times the value of the tilePScale
   * property. */
  override def tilePScale: Double = cPScale * 4
}

abstract class SquareMapGui(title: String) extends TileMapGui(title)
{ /** The number of pixels from a side of a tile to the opposite tile. For Square tiles this is twice the value of the tilePScale property. */
  override def tilePScale: Double = cPScale * 2
}