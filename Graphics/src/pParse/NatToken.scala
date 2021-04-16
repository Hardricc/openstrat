/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** The base trait for all integer tokens. A Natural (non negative) number Token. It contains a single property, the digitStr. The digitStr depending
* on the class may be interpreted in 1 to 3 ways, as a normal decimal number, a hexadecimal number, or a trigdual (base 32) number. */
trait NatToken extends ExprToken
{ def digitsStr: String
}

/** A raw valid natural number Token. All of these tokens are valid raw Base 32 tokens. */
trait NatRawToken extends NatBase32Token
{ override def digitsStr: String = srcStr
}

/** A raw natural number token. */
trait DigitsRawToken extends NatRawToken

/** A 64 bit integer token in standard decimal format, but which can be inferred to be a raw Hexadecimal. It can be used for standard 32 bit Ints and
 *  64 bit Longs, as well as less used integer formats such as Byte. This is in accord with the principle that RSON at the Token and AST (Abstract
 *  Syntax Tree) levels stores data not code, although of course at the higher semantic levels it can be used very well for programming languages. */
case class NatDeciToken(startPosn: TextPosn, srcStr: String) extends NatHexaToken with DigitsRawToken
{ override def subTypeStr: String = "Decimal"
  override def digitsStr: String = srcStr

  /** gets the natural integer value from this token interpreting it as a standard Base10 notation. */
  def getInt: Int =
  { var acc = 0
    implicit val chars: Chars = srcStr.toChars
    def loop(rem: CharsOff): Int = rem match
    {
      case CharsOff0() => acc
      case CharsOff1Tail(DigitChar(_, i), tail)  => { acc = acc * 10 + i; loop(tail) }
    }
    loop(chars.offsetter0)
  }
}