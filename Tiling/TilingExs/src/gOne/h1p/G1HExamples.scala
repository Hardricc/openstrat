/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package h1p
import prid.*, phex.*, gPlay.*

/** 1st example Turn 0 scenario state for Game One hex. */
object G1HScen1 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridRect = GSys.g1
  val counters: LayerHcOptSys[Counter] = LayerHcOptSys()
  counters.setSomeMut(4, 4, CounterA)
  counters.setSomesMut((4, 8, CounterB), (6, 10, CounterC))
  val gl: LayerHcOptGrid[Counter] = LayerHcOptGrid(gridSys, counters.arrayUnsafe)
}

/** 2nd example Turn 0 scenario state for Game One hex. */
object G1HScen2 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridRect = GSys.g2
  val counters: LayerHcOptSys[Counter] = LayerHcOptSys()
  counters.setSomeMut(6, 38, CounterA)
  counters.setSomesMut((4, 40, CounterB), (6, 42, CounterC), (6, 50, CounterD), (10, 34, CounterE))
}

/** 3rd example Turn 0 scenario state for Game One hex. */
object G1HScen3 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridGen = GSys.g3
  val counters: LayerHcOptSys[Counter] = LayerHcOptSys()
  counters.setSomesMut((4, 4, CounterA), (10, 6, CounterB), (8, 8, CounterC))
}

/** 3rd example Turn 0 scenario state for Game One. */
object G1HScen7 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridGen.fromTop(10, (6, 6), (4, 8), (2, 10), (4, 8), (6, 6))
  val counters: LayerHcOptSys[Counter] = LayerHcOptSys()
  counters.setSomesMut((4, 4, CounterA), (10, 6, CounterB), (8, 8, CounterC))
}

/** 2nd example Turn 0 scenario state for Game One. */
object G1HScen4 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridRect = HGridRect.minMax(2, 10, 4, 8)
  val counters: LayerHcOptSys[Counter] = LayerHcOptSys()
  counters.setSomesMut((4, 4, CounterA), (8, 4, CounterB), (6, 6, CounterC))
}

/** 8th example Turn 0 scenario state for Game One. An empty regular grid containing no hex tiles. */
object G1HScen8 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridRect = HGridRect.minMax(4, 2, 4, 2)
  val counters: LayerHcOptSys[Counter] = LayerHcOptSys()
}

/** 9th example Turn 0 scenario state for Game One. An empty irregular grid containing no hex tiles. */
object G1HScen9 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridGen.fromTop(2)
  val counters: LayerHcOptSys[Counter] = LayerHcOptSys()
}

/** 10th example Turn 0 scenario state for Game One. Uses an [[HGridGen]] */
object G1HScen10 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridGen.fromTop(12, (4, 48), (6, 18), (8, 8), (2, 14), (4, 14), (6, 6))
  val counters: LayerHcOptSys[Counter] = LayerHcOptSys()
  counters.setSomesMut((4, 4, CounterA), (10, 6, CounterB), (8, 8, CounterC))
}