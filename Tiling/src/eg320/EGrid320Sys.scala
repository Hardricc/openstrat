/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, prid._, phex._

trait EGrid320Sys extends EGridSys
{ override val cScale: Length = 80.kMetres
}

trait EGrid320MainMulti extends EGridMainMulti with EGrid320Sys
{ override def hcDelta: Int = 1024

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}