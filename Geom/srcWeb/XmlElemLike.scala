/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML / HTML just stored as a [[String]]. This is not desirable, except as a temporary expedient. */
case class XmlAsString(value: String) extends XCon
{ override def out(indent: Int, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String = value
}

/** An XML or an HTML element */
trait XmlElemLike extends XCon
{ /** The XML /HTML tag String. A tag is a markup construct that begins with < and ends with > */
  def tag: String
  
  def tagLen: Int = tag.length
  
  /** The length of the < character plus the tag. */
  def openTagOpenLen: Int = tagLen + 1

  /** The attributes of this XML / HTML element. */
  def attribs: RArr[XmlAtt]

  /** The content of this XML / HTML element. */
  def contents: RArr[XCon]

  def attribsOut: String = ife(attribs.empty, "", " " + attribs.mkStr(_.str, " "))
  def openAtts: String = "<" + tag + attribsOut
  def openUnclosed: String = openAtts + ">"
  def openTag: String = openAtts + ">"
  def closeTag: String = "</" + tag + ">"
  def n1CloseTag: String = "\n" + closeTag
  def n2CloseTag: String = "\n\n" + closeTag
}

trait XmlLikeMulti extends XmlElemLike
{
  override def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String =
    if (contents.empty) openAtts + "/>"
    else openUnclosed.nli(indent + 2) + contents.mkStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

trait XmlLike1Lineable extends XmlElemLike
{
  override def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String = contents match
  { case RArr0() => openAtts + "/>"
    case RArr1(_) => openUnclosed + contents(0).out(0, 150) + closeTag
    case _ => openUnclosed.nli(indent + 2) + contents.mkStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
  }
}

trait XmlConInline extends XmlElemLike
{
  override def outEither(indent: Int, maxLineLen: Int = lineLenDefault): (Boolean, String) = (true, out(indent, maxLineLen))

  override def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String =
  { val cons = contents.map(_.outEither(indent, maxLineLen))
    val middle = cons.length match
    { case 0 => ""
      case 1 if cons.head._1 => cons.head._2
      case n => cons.foldLeft("") { (acc, el) => acc --- el._2 } + "\n"
    }
    openTag + middle + closeTag
  }
}