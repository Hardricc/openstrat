/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

/** Also tests the srcToETokens function object. */
object DoubleTokenTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val Sp2 = StrPosn(1, 2)
  val Sp3 = StrPosn(1, 3)
  val Sp5 = StrPosn(4, 5)
  val Sp44 = StrPosn(4, 4)

  val xeqStr = "x = 0.4"
  val xSts: EArr[Statement] = xeqStr.parseStatementsOld
  val xDbl: ErrBi[Exception, Double] = xeqStr.findDblSetting("x")
  val s51 = "51.1"

  val tests = Tests {
    test("Test1")
    { assertMatch("4.5".parseTokensOld){ case Good(Arr1(DeciFracPosToken(Sp1, "4", "5", ""))) => }
      assertMatch("0.5".parseTokensOld){ case Good(Arr1(DeciFracPosToken(Sp1, "0", "5", ""))) => }
      "0.4".asDbl ==> Succ(0.4)
      "543.012".asDbl ==> Succ(543.012)
      "543.012".asPosDbl ==> Good(543.012)
      "-543.012".asDbl ==> Succ(-543.012)
      "-543.012".asPosDbl.isBad ==> true
        "-0.4".asDbl ==> Succ(-0.4)
      "-4".asDbl ==> Succ(-4)
      assertMatch(s51.parseTokensOld){ case Good(Arr1(DeciFracPosToken(Sp1, "51", "1", ""))) => }
      "51".unsafeDigitsToLong ==> 51l
      s51.findTypeOld[Double] ==> Good(51.1)
      assertMatch(xeqStr.parseTokensOld){ case Good(Arr3(IdentLowerOnlyToken(_, "x"), AsignToken(_), DeciFracPosToken(_, _, _, _))) => }
      assertMatch("271.562".parseTokensOld){ case Good(Arr1(DeciFracPosToken(sp1, "271", "562", ""))) => }
      //Note this not a legal AST but it doesn't matter for the purpose of lexical tests
      assertMatch("4.5 4.5".parseTokensOld){ case Good(Arr2(DeciFracPosToken(Sp1, "4", "5", ""), DeciFracPosToken(sp5, "4", "5", ""))) => }
    }

    test("Test 2")
    { assertMatch(xSts) { case Good(Arr1(_)) => }
      assertMatch(xDbl) { case Succ(0.4) => }
    }
  }

}
