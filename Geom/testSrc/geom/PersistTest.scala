/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._

object PersistTest extends TestSuite
{
  val tests = Tests {
    val p1 = Pt2(2.5, -4)

    test("Pt2") {
      p1.str ==> "Pt2(2.5; -4)"
      p1.strSemi ==> "2.5; -4"
      p1.strComma ==> "2.5, -4"
      p1.str.asType[Pt2] ==> Good(p1)
      p1.strSemi.asType[Pt2] ==> Good(p1)
      p1.strComma.asType[Pt2] ==> Good(p1)
    }

    val p3 = Pt3(2.5, -4, -20.01)
    test("Pt3") {
      p3.str ==> "Pt3(2.5; -4; -20.01)"
      p3.strSemi ==> "2.5; -4; -20.01"
      p3.strComma ==> "2.5, -4, -20.01"
      p3.str.asType[Pt3] ==> Good(p3)
      p3.strSemi.asType[Pt3] ==> Good(p3)
      p3.strComma.asType[Pt3] ==> Good(p3)
      p3.showFields ==> "Pt3(x = 2.5; y = -4; z = -20.01)"
      //p3.showTypedFields ==> "Pt3(x: DFLoat = 2.5; y: DFloat = -4; z: DFloat = -20.01)"
    }
  }
}
