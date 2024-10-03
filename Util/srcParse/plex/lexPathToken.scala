/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex

object lexPathToken
{
  def apply(remOff: CharsOff, tpStart: TextPosn)(implicit charArr: CharArr): EMon3[CharsOff, TextPosn, Token] =
  { var buff = StringBuff ()
   def loop(remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
   {
     case CharsOffHead2('/', LetterOrUnderscoreChar(_)) => lexIdentifierToken(remOff.drop1, tp.right1).toEMon3.flatMap3{(co, tp, token) =>
       buff.grow(token.srcStr)
       loop(co, tp)
     }
     case _ => Good3(remOff, tp, PathToken(tp, buff.toArray))
   }
   loop(remOff, tpStart)
  }

  def alt(remOff: CharsOff, tpStart: TextPosn)(implicit charArr: CharArr): EEMon3[CharsOff, TextPosn, Token] = ???
  /*{ var buff = StringBuff()

    def loop(remOff: CharsOff, tp: TextPosn): EEMon3[CharsOff, TextPosn, Token] = remOff match
    {
      case CharsOffHead2('/', LetterOrUnderscoreChar(_)) => lexIdentifierToken(remOff.drop1, tp.right1).flatMap3 { (co, tp, token) =>
        buff.grow(token.srcStr)
        loop(co, tp)
      }
      case _ => Good3(remOff, tp, PathToken(tp, buff.toArray))
    }

    loop(remOff, tpStart)
  }*/
}
