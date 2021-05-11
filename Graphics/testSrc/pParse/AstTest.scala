/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

object AstTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val s1 = "x = y;"
  val t1 = srcToETokens.str(s1)
  val w1 = stringToStatements(s1)
  val t3= Arr(IdentLowerOnlyToken(Sp1, "x"), AsignToken(StrPosn(1, 3)), IdentLowerOnlyToken(StrPosn(1, 5), "y"), SemicolonToken(StrPosn(1, 6)))
  val a1: ERefs[Statement] = astParse(t3)
  deb(a1.get.lenStr)
 // deb(w1.toString)

  val tests = Tests
  {
    "Test1" -
    {
      //t1 ==> Good(t3)
      //Good(Arr(4)).eq(Good(Arr(4))
      assertMatch(a1){case Good(Arr1(_)) => }
    }
  }
}