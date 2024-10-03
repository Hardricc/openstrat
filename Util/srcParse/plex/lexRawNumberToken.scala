/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex

/** Function object for parsing a raw number, could be a normal decimal, hexadecimal or trigdual number. Not all natural numbers are
 * parsed with this function object. Raw hex and trigdual numbers can be encoded as alphanumeric identity tokens. */
object lexRawNumberToken
{
  def apply(rem: CharsOff, tp: TextPosn, str: String, isNeg: Boolean)(implicit charArr: CharArr): Ebbf3[ExcLexar, CharsOff, TextPosn, Token] = rem match
  { case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp, str + d.toString, isNeg)
    case CharsOff2Tail('.', d, tail) if d.isDigit => parseDeciFrac(tail, tp, str, d.toString, isNeg)
    case CharsOff1Tail(HexaUpperChar(l), tail) => parseHexaToken(tail, tp, str + l.toString, isNeg)
    case CharsOff1Tail(l, tail) if (l <= 'N' && l >= 'G') | (l <= 'W' && l >= 'P') => parseBase32(tail, tp, str + l.toString, isNeg)
    case CharsOff1Tail(LetterOrUnderscoreChar(l), tail) => lexDigitHeadToken(tail, tp, str, isNeg, l.toString)
    case _ if isNeg => Succ3(rem, tp.addStr(str).right1, NegBase10Token(tp, str))
    case _ => Succ3(rem, tp.addStr(str), NatBase10Token(tp, str))
  }
}

object parseHexaToken
{
  def apply(rem: CharsOff, tp: TextPosn, str: String, isNeg: Boolean)(implicit charArr: CharArr): Ebbf3[ExcLexar, CharsOff, TextPosn, Token] = rem match
  { case CharsOff1Tail(d, tail) if d.isDigit | (d <= 'F' && d >= 'A') => parseHexaToken(tail, tp, str + d.toString, isNeg)
    case CharsOff1Tail(l, tail) if (l <= 'G' && l >= 'G') | (l <= 'W' && l >= 'P') => parseBase32(tail, tp, l.toString, isNeg)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.failLexar("Badly formed raw hexadecimal token.")
    case _ if isNeg => Succ3(rem, tp.addStr(str), RawHexaNegToken(tp, str))
    case _ => Succ3(rem, tp.addStr(str), RawHexaNatToken(tp, str))
  }
}

object parseBase32
{
  def apply(rem: CharsOff, tp: TextPosn, str: String, isNeg: Boolean)(implicit charArr: CharArr): Ebbf3[ExcLexar, CharsOff, TextPosn, ValidRawBase32IntToken] = rem match
  { case CharsOff1Tail(l, tail) if l.isDigit | (l <= 'A' && l >= 'G') | (l <= 'W' && l >= 'P') => parseBase32(tail, tp, l.toString, isNeg)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.failLexar("Badly formed raw Base 32 token.")
    case _ if isNeg => Succ3(rem, tp.addStr(str), RawBase32NegToken(tp, str))
    case _ => Succ3(rem, tp.addStr(str), RawBase32NatToken(tp, str))
  }
}

object parseDeciFrac
{
  def apply(rem: CharsOff, tp: TextPosn, seq1: String, seq2: String, isNeg: Boolean)(implicit charArr: CharArr): Ebbf3[ExcLexar, CharsOff, TextPosn, Token] =
  rem match
  {
    case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp, seq1, seq2 + d.toString, isNeg)
    case _ =>
    { val token = ife(isNeg, DeciFracNegToken(tp, seq1, seq2, ""), DeciFracPosToken(tp, seq1, seq2, ""))
      Succ3(rem, tp.addStr(seq1).addStr(seq2).right1, token)
    }
  }
}

object lexDigitHeadToken
{
  def apply(rem: CharsOff, tp: TextPosn, digitsStr: String, isNeg: Boolean, alphaStr: String)(implicit charArr: CharArr): Ebbf3[ExcLexar, CharsOff, TextPosn, Token] =
    rem match
    { case CharsOff1Tail(LetterOrUnderscoreChar(l), tail) => apply(tail, tp, digitsStr, isNeg, alphaStr + l)
      case _ => Succ3(rem, tp.addStr(digitsStr).addStr(alphaStr), DigitHeadAlphaTokenGen(tp, digitsStr, alphaStr))
    }
}