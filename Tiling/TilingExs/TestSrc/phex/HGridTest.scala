/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest.*

object HGridTest extends TestSuite
{
  val g1: HGridRect = HGridRect(3, 5)
 // val g2: HGridsDuo = HGridsDuo(2, 8, 2, 6, 100, 104)

  val tests = Tests {
    test("test1") {
      g1.stepFind(2, 2, 4, 4) ==> Some(HexUR)
      g1.stepFind(2, 2, 2, 6) ==> Some(HexRt)
      g1.stepFind(2, 2, 4, 0) ==> None
      g1.stepFind(2, 2, 0, 4) ==> None
    }

    val ig1: HGridGen = HGridGen.fromTop(6, (2, 10), (4, 8), (6, 6))
    val ig1Str = "HGridIrr(2, 6, 6; 4, 4, 8; 6, 2, 10)"
    val eg1 = ig1Str.asType[HGridGen]

    test("test HGrid Irr")
    { ig1.str ==> ig1Str
      assert(eg1.isSucc)
      eg1.map(_.bottomCenR) ==> Succ(2)
      eg1.map(_.rowRightCenC(6)) ==> Succ(10)
      eg1 ==> Succ(ig1)
    }
  }
}