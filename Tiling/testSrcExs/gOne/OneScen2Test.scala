/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import utest._

object OneScen2Test  extends TestSuite
{
  val g = OneScen2.grid
  val tests = Tests
  {
    "test1" -
    { g.rCenMin ==> 2
      g.rCenMax ==> 10
      g.cTileMin ==> 4
      g.cTileMax ==> 8
      g.numOfRow0s ==> 2
      g.rRow2sMin ==> 2
      g.rRow2sMax ==> 10
      g.numOfRow2s ==> 3
      g.numCenRows ==> 5
      g.cRow2sMin ==> 6
      g.cRow0sMin ==> 4
//      g. cRowStart(2) ==> 2
//      g.cRowLen(2) ==> 3
//      g.cRowEnd(2) ==> 10
//      g.cRowLen(4) ==> 2
//      g.cRowLen(6) ==> 1
//      g.cRowEnd(6) ==> 6
//      g.cRowLen(8) ==> 2
//      g.cRowEnd(8) ==> 8
//      g.numOfTiles ==> 8
    }

    "Sides" -
    {
      //g.sideRoords.elemsLen ==> 36
    }
   }
}