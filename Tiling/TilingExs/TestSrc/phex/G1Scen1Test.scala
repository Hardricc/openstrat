/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest.{Show => *, *}, gOne.h1p.*, gPlay.*

object G1Scen1Test extends TestSuite
{ val os1: G1HScen1.type = G1HScen1
  val gs = os1.gridSys
  val g1: HGridRect = os1.gridSys
  val g1Str = "HGridRect(2; 6; 2; 62)"
  val cs1: LayerHcOptSys[Counter] = os1.counters
  val hr1 = LayerHcOptRow[Counter](4, CounterA, None * 2, CounterB)
  val hr2 = LayerHcOptRow[Counter](4, CounterA, CounterB, CounterC)

  val tests = Tests {
    val lr1 = LayerHcOptRow(8, CounterA, CounterB)
    test("os1")
    { g1.str ==> g1Str
      g1Str.asType[HGridRect] ==> Succ(g1)
      lr1.numTiles ==> 2
      lr1.arrayUnsafe(0) ==> CounterA
      lr1.str ==> "HRow(8; CounterA; CounterB)"
      hr1.str ==> "HRow(4; CounterA; ; ; CounterB)"
      LayerHcOptRow[Counter](4, CounterA, None * 2, CounterB, None).str ==> "HRow(4; CounterA; ; ; CounterB; ;)"
      "HRow(4; CounterA; CounterB; CounterC)".asType[LayerHcOptRow[Counter]] ==> Succ(hr2)
      assert("HRow(4; CounterA; CounterB; CounterD)".asType[LayerHcOptRow[Counter]] != Succ(hr2))
      //"HRow(4; CounterA; ; ; CounterB)".asType[LayerHcOptRow[Counter]] ==> Good(hr1)
    }
  }
}