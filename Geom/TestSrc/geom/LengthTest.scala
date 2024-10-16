/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._, pParse._

object LengthTest  extends TestSuite
{
  val tests = Tests {
    val ts1 = "4.3km".toTokens    
    test("Length 1")
    { "4km".asType[Kilometres] ==> Succ(Kilometres(4))
      ts1.isSucc ==> true
    }
  }
}