/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** A hexadecimal token with a leading "0x", that can be used for standard 32 bit Ints, 64 bit Longs, as well as less used integer
 *  formats such as BigInteger and Byte. This is in accord with the principle that RSON at the Token and AST (Abstract Syntax Tree) levels stores data not code,
 *  although of course at the higher semantic levels it can be used very well for programming languages. */
case class Nat0xToken(startPosn: TextPosn, digitsStr: String) extends NatStdToken with ValidHexaIntToken
{ override def srcStr: String = "0x" + digitsStr
  override def exprName: String = "IntHexa"
  override def getIntStd: Int = asHexaInt
  override def getNatStd: Int = asHexaInt
}

/** Function for parsing explicit Hexadecimal Token, one that begins with the characters '0x'. */
object Nat0xToken
{
  /** Function for parsing explicit Hexadecimal Token, one that begins with the characters '0x'. */
  def parse(rem: CharsOff, tp: TextPosn)(implicit charArr: CharArr): ErrBi3[ExcLexar, CharsOff, TextPosn, Token] =
  {
    def loop(rem: CharsOff, strAcc: String, intAcc: Long): ErrBi3[ExcLexar, CharsOff, TextPosn, Nat0xToken] = rem match
    { case CharsOff0() => Succ3(rem, tp.right(strAcc.length + 2), Nat0xToken(tp, strAcc))
      case CharsOff1Tail(HexaDigitChar(c, i), tail) => loop(tail, strAcc + c, intAcc * 16 + i)
      case CharsOffHead(LetterChar(_)) => tp.failLexar("Badly formed hexadecimal")
      case _ => Succ3(rem, tp.addStr(strAcc), Nat0xToken(tp, strAcc))
    }

    rem match
    { case CharsOff3Tail('0', 'x', HexaDigitChar(c, i), tail) => loop (tail, c.toString, i)
      case CharsOffHead3('0', 'x', WhitespaceChar(_)) => tp.failLexar("Empty hexademicmal token.")
      case CharsOffHead3('0', 'x', c) => tp.failLexar("Badly formed hexademicmal token.")
      case CharsOff2('0', 'x') => tp.failLexar("Unclosed hexadecimal token")
      case _ => tp.failLexar("Badly formed explicit Hexadecimal literal")
    }
  }
}