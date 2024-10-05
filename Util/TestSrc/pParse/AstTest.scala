/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._, plex._

object AstTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val s1 = "x = y;"
  val t1 = lexSrc.str(s1)
  val w1 = stringToStatementsOld(s1)
  val t3: RArr[StatementMem & BlockMemToken] = RArr(IdentLowerOnlyToken(Sp1, "x"), AsignToken(StrPosn(1, 3)),
    IdentLowerOnlyToken(StrPosn(1, 5), "y"), SemicolonToken(StrPosn(1, 6)))
  val a1: EArr[Statement] = tokensToStatementsOld(t3)

  val tests = Tests {
    test("Test1")
    { assertMatch(a1){case Good(Arr1(_)) => }
    }
  }
}