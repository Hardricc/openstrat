/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour._

/** Produces an HTML file documentation for the Geom module. */
object GeomPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Geom Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list, GeomPagePolygons, Ellipses)

  def list: HtmlOlWithLH = HtmlOlWithLH.h2("The Geom module contains",
    geomItme, colourItem, graphicItem, compound, trans, canv, svg, web, geom3, lessons)

  def geomItme: HtmlLi = HtmlLi("Geometry. Immutable classes for points, lines and shapes. These classes build on the Array based collections" +
    " from the Util module.")

  def colourItem: HtmlLi = HtmlLi("Colour class. A 32 bit integer class that can be built from rgba and named values.")

  def graphicItem: HtmlLi = HtmlLi("Graphic primitives. Immutable classes for fills, draws and active elements based on the geometry classes.")

  def compound: HtmlLi = HtmlLi("Compound Graphics. Again immutable classes. Useful for selection and placing.")

  def trans: HtmlLi = HtmlLi("Geometric transformations on both the geometric and graphical elements, preserving maximum type information.")

  def canv: HtmlLi = HtmlLi("An abstract canvas on which to display the graphic elements. Concrete implementations for JavaFx and HtmlCanvas," +
    " allowing applications to be created with minimal platform specific code. The abstract canvas api could be implemented on DirectX or OpenGL," +
    " but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.")

  def svg: HtmlLi = HtmlLi("Conversion of Graphic classes into SVG, gving an alternative target and greater flexibility.")

  def web: HtmlLi = HtmlLi("Web library. Classes for XML, HTML, CSS and simple JavaScript functions. These pages have been generated using this.")

  def geom3: HtmlLi = HtmlLi("3D geometry as well as distance unit classes as opposed to scalars for 1D, 2D and 3D. Basic 3D Graphics will be" +
    " provided, but currently there is no attempt to provide any kind of 3D or physics engine, although a 3D implementation for canvas is entirely" +
    " possible.")

  def lessons: HtmlLi = HtmlLi("Series of lessons / tutorials in geometry and graphics.")

  val svgMargin = 50



  object Ellipses extends HtmlSection
  {
    override def contents: RArr[XCon] = RArr(HtmlH2("Circles and Ellipses"), svgs)

    val circ2: Circle = Circle(200)
    val circ1: Circle = circ2.slateX(-200)
    val circ3: Circle = circ2.slateX(200)
    val cg1: SvgCircle = circ1.fill(Orange).svgElem
    val cg2: RArr[SvgElem] = circ2.fillDraw(Turquoise).svgElems
    val cg3: SvgCircle = circ3.draw(Orchid).svgElem
    val bounds: Rect = circ1.boundingRect.union(circ3.boundingRect).addMargin(svgMargin)
    val svgs = HtmlSvg.bounds(bounds, RArr(cg1, cg3) ++ cg2, RArr(CentreBlockAtt))
  }
}