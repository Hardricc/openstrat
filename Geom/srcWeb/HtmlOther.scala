/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML A anchor element. */
class HtmlA(val link: String, val contents: RArr[XCon], otherAttribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "a"
  override val attribs: RArr[XmlAtt] = RArr(HrefAtt(link)) ++ otherAttribs
}

object HtmlA
{ /** Factory apply method for [[HtmlA]] class. */
  def apply(link: String, label: String): HtmlA = new HtmlA(link, RArr(label.xCon))
}

/** HTML P paragraph element. */
trait HtmlP extends HtmlUnvoid
{ def tag = "p"
}

/** Copied from old needs checking. */
object HtmlP
{ /** Factory apply method for for creating HTML paragraphs. */
  def apply(strIn: String, attsIn: XmlAtt*): HtmlP = new HtmlP
  { def str: String = strIn
    def con1: XConText = str.xCon
    override val attribs: RArr[XmlAtt] = attsIn.toArr
    override def contents: RArr[XCon] = RArr(con1)

    override def out(indent: Int, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String = con1.outLines(indent + 2, openUnclosed.length) match
    { case TextIn1Line(text, _) => indent.spaces + openUnclosed + text + closeTag
      case TextIn2Line(text, _) => indent.spaces + openUnclosed + text + closeTag
      case TextInMultiLines(text, _) => indent.spaces + openUnclosed + text --- indent.spaces + closeTag
      case a => excep(a.toString + "in out method not implemented.")
    }
  }
}

/** HTML noscript element. */
case class HtmlNoScript(contents: RArr[XCon], attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "noscript"
}

object HtmlNoScript
{
  def apply(): HtmlNoScript = new HtmlNoScript(RArr("This page will not function properly without Javascript enabled".xCon))
}

/** HTML script element. */
case class HtmlScript(contents: RArr[XCon], attribs: RArr[XmlAtt]) extends HtmlInline
{ override def tag: String = "script"
}

/** Companion object for [[HtmlScript]] class, HTML script element Contains factory methods for creating the src and function call elements. */
object HtmlScript
{ /** Sets the link for a Javascript script file. */
  def jsSrc(src: String): HtmlScript = HtmlScript(RArr(), RArr(TypeAtt.js, SrcAtt(src)))

  /** Sets the function for an external JavaScript call. */
  def main(stem: String): HtmlScript = HtmlScript(RArr(XConText(stem + ".main()")), RArr(TypeAtt.js))
}

/** HTML style element. */
case class HtmlStyle(rules: RArr[CssRuleLike], attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "style"
  override def contents: RArr[XCon] = RArr(rules.foldStr(_.out(), "; ").xCon)
}

object HtmlStyle
{
  def apply(rules: CssRuleLike*): HtmlStyle = new HtmlStyle(rules.toArr)
}

/** HTML bold element. */
case class HtmlB(str: String) extends HtmlInline
{ override def tag: String = "b"

  /** The attributes of this XML / HTML element. */
  override def attribs: RArr[XmlAtt] = RArr()

  /** The content of this XML / HTML element. */
  override def contents: RArr[XCon] = RArr(str.xCon)
}

/** Html H1 header element. */
case class HtmlH1(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ override def tag = "h1"
}

/** Html H2 header element. */
case class HtmlH2(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h2"
}

/** Html H3 header element. */
case class HtmlH3(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h3"
}

/** Html H4 header element. */
case class HtmlH4(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h4"
}