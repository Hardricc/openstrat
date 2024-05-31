/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._, Colour._

object CssDocmentation extends CssOpenstrat
{
  override def rules: RArr[CssRuleLike] = RArr(
    CssBody(CssBGColour(Ivory), DecFontSize(18.px)), CssH1(DecTextCentre, DecFontSize(44.px)),
    CssClassesRule("central", DecMaxWidth(68.em), DecMargLeftRight(CssAuto)),
    CssClassesRule("main", DecMaxWidth(68.em), DecMargLeftRight(CssAuto)),
    CssOl(DecPadLeft(1.em)), CssRule("ol li", CssMargTopBot(2.em)), CssRule("ul li", DecMarg(0.25.em)), CssRule("ol > li", CssMargTopBot(1.em)),
    CssClassesRule("lexical", DecColour(DarkBlue)),
    CssCode(DecColour(DarkRed)), CssClassesRule("sbt", DecColour(DarkGreen)), CssClassesRule("folder", DecColour(DarkBlue)),
    CssClassesRule("path", DecColour(DarkBlue), DecNoWrap), CssClassesRule("bash", DecColour(DarkRed), DecNoWrap),
    CssClassesRule("scala", DecColour(Black), DecNoWrap, DecFontSize(10.px)), CssObjectRule("centreBlock", DispBlock, DecMargLeftRightAuto),
    CssRule("td th", DecPadRight(2.em), DecAlignStart),
    minMed
  )
}