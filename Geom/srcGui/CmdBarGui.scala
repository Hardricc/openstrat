/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._, Colour._

/** A simple 2 panel display, a man panel and a command bar. */
trait CmdBarGui extends CanvasPanelled
{ val barThickness = 30
  val topBar = addPanel(Rect.tl(canv.width, barThickness, canv.topLeft), true)
  topBar.backColour = Colour.Cornsilk

  /** The text for the status field of the command bar. */
  var statusText: String = ""

  def textButton(str: String, cmd: AnyRef): PolygonCompound = Rect(40, 25).fillActiveDrawText(Gray, cmd, str, 15, 2, White, Colour.Black, LeftAlign)

  val mainPanel: Panel = addPanel(Rect.bl(canv.width, canv.height - barThickness, canv.bottomLeft))
  def mainRepaint(els: RArr[Graphic2Elem]): Unit = mainPanel.repaint(els)
  def mainRepaints(els: Graphic2Elem*): Unit = mainPanel.repaints(els*)
  def mainWidth: Double = mainPanel.width
  def mainHeight: Double = mainPanel.height

  /**  repaints the top command bar */
  def reTop(commands: RArr[GraphicBounded]): Unit =
  { val a = displayRowGraphics(topBar.cenLeft, commands)
    val sp = ife(a.empty, topBar.cenLeft,a.last.boundingRect.cen.addX(80))
    val st = TextFixed(statusText, 15, sp, Black, LeftAlign)
    topBar.repaint(a +% st)
  }

  canv.onScroll = b => mainPanel.onScroll(b)
  def mainMouseUp: (MouseButton, RArr[Any], Pt2) => Unit = mainPanel.mouseUp
  def mainMouseUp_= (f: (MouseButton, RArr[Any], Pt2) => Unit): Unit = { mainPanel.mouseUp = f }

  /** Selected takes the type Any, but the value None means nothing selected. */
  var selectStack: RArr[Any] = RArrAny()

  /** Selected takes the type Any, but the value None means nothing selected. */
  var selected: Any = None

  def selectedStr: String = selected match
  { case None => "Nothing selected"
//    case sel: Selectable => {deb("Selectable"); sel.selectStr }
    case sw: Tell => sw.str
    case a => a.toString
  }

  topBar.mouseUp =
  { case (b, RArrHead(MouseButtonCmd(cmd)), _) => cmd.apply(b)
    case (_, l, _) => deb(l.toString)
  }
}

object StdButton
{
  def apply(str: String, cmd: AnyRef) =
      Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5).parentAll(cmd, White, 3, Black, 25, str)

  def turn(num: Int) = apply("Turn" -- num.toString, Turn)    
}

object Turn extends TellSimple
{ override def typeStr: String = "Turn.type"
  override def str: String = "Turn"
}