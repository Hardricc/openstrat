/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pReactor
import geom.*, Colour.*

/** Simple Checkbox with label **/
case class Checkbox(aIsSelected: Boolean = false, labelText:String = "", xLoc: Double = 0, yLoc: Double = 0, aIsEnabled:Boolean = false,
  action: (Checkbox) => Unit = (Checkbox) => {}, myColor:Colour = White)
{ val loc: Pt2 = Pt2(xLoc, yLoc)
  val defaultSize = 12
  var isSelected = aIsSelected
  var isEnabled = aIsEnabled
  var color = myColor

  def toGraphicElems(aIsSelected: Boolean = isSelected, labelText: String = labelText, loc: Pt2 = loc, aIsEnabled:Boolean = isEnabled,
    aAction: (Checkbox) => Unit = action): GraphicElems =
  { isSelected = aIsSelected
    isEnabled = aIsEnabled
    val ink = if (isEnabled) myColor else Grey

    var ret:GraphicElems = RArr(TextFixed(labelText, defaultSize, loc.slateX(defaultSize), ink, LeftAlign))
    if (isSelected) ret = ret ++ RArr(Rect(defaultSize - 4, defaultSize - 4, loc).fill(ink))
    if (isEnabled) ret ++ RArr(Rect(defaultSize, defaultSize, loc).drawActive(ink, 1, this))
    else ret ++ RArr(Rect(defaultSize, defaultSize, loc).draw(1, ink))
  }

  def clicked() =
  { if (isEnabled)
    { isSelected = !isSelected
      action(this)
    }
  }
}