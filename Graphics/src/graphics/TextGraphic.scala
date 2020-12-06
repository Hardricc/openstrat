/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/* The alignment of text can be left right or centre. This may want to extend from a more general alignment trait. If such is useful. */
sealed trait TextAlign
{ def jsStr: String
}

case object CenAlign extends TextAlign { def jsStr = "center" }
case object LeftAlign extends TextAlign { def jsStr = "left" }
case object RightAlign extends TextAlign { def jsStr = "right" }

sealed trait BaseLine
{ def jsStr: String
}

object BaseLine
{ case object Top extends BaseLine { def jsStr = "top" }
  case object Middle extends BaseLine { def jsStr = "middle" }
  case object Alphabetic extends BaseLine { def jsStr = "alphabetic" }
  case object Bottom extends BaseLine { def jsStr = "bottom" }

  /** Implemented as VPos.Top on JavaFx Canvas. */
  case object Hanging extends BaseLine { def jsStr = "hanging" }

  /** Implemented as geometry.VPos.BASELINE on JavaFx Canvas. */
  case object Ideographic extends BaseLine { def jsStr = "ideographic" }
}

/** A Graphical display of Text.
 * @param posn The point to orient from. By default this Vec2 defines the centre but from right or left depending  on alignment. */
case class TextGraphic(str: String, posn: Pt2 = Pt2Z, fontSize: Int = 24, colour: Colour = Black, align: TextAlign = CenAlign,
                       baseLine: BaseLine = BaseLine.Middle) extends GraphicAffineElem with CanvElem
{ type ThisT = TextGraphic
  override def fTrans(f: Pt2 => Pt2) = TextGraphic(str, f(posn), fontSize, colour, align, baseLine)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.textGraphic(this)
}

/** Not sure if this is a good object to have. */
object TextGraphicCen
{ def apply(str: String, fontSize: Int, posn : Pt2 = Pt2Z, colour: Colour = Black, zOrder: Int = 0): TextGraphic =
    new TextGraphic(str, posn, fontSize, colour, CenAlign, BaseLine.Alphabetic)
}

object TextGraphic
{
  def lines(strs: Arr[String], fontSize: Int = 24, posn: Pt2 = Pt2Z, fontColour: Colour = Black, lineSpacing: Double = 1,
            align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Alphabetic): Arr[TextGraphic] =
  { val len = strs.elemsLen
    if(len == 0) Arr()
      else strs.iMap((str, i) => TextGraphic(str, posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontSize, fontColour, align, baseLine))
  }
}

case class TextOutline(str: String, fontSize: Int = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, lineWidth: Double = 1.0,
                       align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Alphabetic, zOrder: Int = 0) extends GraphicAffineElem
{ override type ThisT = TextOutline
  override def fTrans(f: Pt2 => Pt2) = TextOutline(str, fontSize, f(posn), colour, lineWidth, align, baseLine)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.textOutline(this)

  override def negY: TextOutline = ???
}