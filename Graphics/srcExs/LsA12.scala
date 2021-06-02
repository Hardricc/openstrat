/* Copyright 2018-21 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** Lesson A12. */
case class LsA12(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A12")
{
  val c1 = Circle(200).draw()
  val c2 = Circle(231).draw(DarkBlue)

  val h1: HexParrX = HexParrX(200)
  val hd = h1.draw()
  val htv = h1.vertsMap(v => Circle(25, v).fill(Pink))
  val hts = h1.vertsMap(v => TextGraphic(v.str0, 15, v))
  val h2: HexParrX = h1.slateX(-400)
  val hc = h2.sidesIMap(){ (s, i) => s.draw(Colour.rainbow.cycleGet(i), 2) }
  val h3d = HexParrY(231, 231, 0).draw(DarkBlue)

  def hexGraphics(hr: HexReg, colour: Colour): GraphicElems =
  { val verts = hr.vertsIFlatMap(1){(pt, i) => pt.textArrowToward(hr.cen, "V" + i.str)}
    val sides = hr.sidesIFlatMap(1){ (side, i) => side.midPt.textArrowAwayFrom(hr.cen, "Side" + i.str) }
    verts ++ sides +- hr.draw(colour) +- TextGraphic(hr.str, 12, hr.cen, colour)
  }

  val h4 = HexParrX(250, 200, 290)
  val h4d = hexGraphics(h4, Green)

  val h5 = HexParrY(250, -200, 290)
  val h5d = hexGraphics(h5, DarkMagenta)

  val gap = 290

  val h6 = HexReg(220, Deg0, -gap, -270)
  val h6d = hexGraphics(h6, IndianRed)

  val h7 = HexReg(220, Deg45, 0, -270)
  val h7d = hexGraphics(h7, Turquoise)

  val h8 = HexReg(220, Deg90, gap, -270)
  val h8d = hexGraphics(h8, Colour.GoldenRod)

  repaint(htv ++ hts +- hd +- c1 +- c2 ++ hc +- h3d ++ h4d ++ h5d ++ h6d ++ h7d ++ h8d)
}