/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._

object LsRson2 extends LessonGraphics
{ override def title: String = "RSON Lesson 2"

  override def bodyStr: String = """Lesson C3. Pointer in object."""

  override def canv: CanvasPlatform => Any = LsD2Canv(_)


  /** D Series lessons deal with persistence */
  case class LsD2Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D2") {
    val tStr =
      """2.0;
     "Hello";
      7;
      Vec2(2.3; 3.2);
      "Goodbye" """
    val t1 = tStr.intAtStsIndex(2)

    /** Gives the same result as t1 as the Int value ar index 2 is unique. */
    val t2 = tStr.findTypeOld[Int]

    val t3 = tStr.findTypeOld[String]

    /** Indexes start at 0. */
    val t4 = tStr.typeAtStsIndexOld[String](0)

    val t5 = tStr.typeAtStsIndexOld[String](1)

    /** Because Indexes start at 0. There is no element 2 of type String. */
    val t6 = tStr.typeAtStsIndexOld[String](2)

    val t7 = tStr.findTypeOld[Pt2]
    val t8 = tStr.dblAtStsIndex(0)

    val topStrs = RArr(t1, t2, t3, t4, t5, t6, t7, t8).map(_.toString)
    val topBlock = MText(200, topStrs)

    val arr = Array(4, 5, 6)
    val as = arr.str
    //  val r1 = as.findType[Seq[Int]]//The default constructor for a Seq is List
    val r2 = as.findTypeOld[List[Int]]
    val r3 = as.findTypeOld[Vector[Int]]
    //  val a4 = as.findType[Array[Int]]
    // val r4 = a4//toString method on Array not very helpful
    //val r5 = a4.map(_(1))
    //  val r6: EMon[Int] = a4.map[Int](arr => arr(2))//This is the long explicit result.

    // val strs = Arr(/*r1,*/ r2, r3, r4, r5).map(_.toString)
    //  val bottomBlock  = MText(-100, strs)

    repaint(topBlock) // ++ bottomBlock)
  }
}