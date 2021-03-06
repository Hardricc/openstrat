/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import geom._, prid._, Colour._

sealed trait Terr extends WithColour
object Water extends Terr { def colour = DarkBlue }
object Woods extends Terr { def colour = Green }
object Plain extends Terr { def colour = Wheat }

trait ThreeScen extends HexGridScen
{ /** tile terrain. */
  def terrs: HCenArr[Terr]
}

trait ThreeScenStart extends ThreeScen
{ override def turn: Int = 0
}

object ThreeScen1 extends ThreeScenStart
{ override implicit val grid: HGrid = HGridReg(2, 6, 2, 10)
  override val terrs: HCenArr[Terr] = grid.newTileArr[Terr](Plain)
  import terrs.{setRow => sr}
  sr(6,2, Water * 2)
  sr(4, 4, Woods * 2)
}

/** 2nd Scenario of Game Three. Has a larger number of hexs. */
object ThreeScen2 extends ThreeScenStart
{ override implicit val grid: HGrid = HGridReg(2, 20, 4, 60)
  override val terrs: HCenArr[Terr] = grid.newTileArr[Terr](Plain)
  import terrs.{setRow => sr}
  sr(6,6, Water * 4)
  sr(8,4, Water * 5)
  sr(10,6, Water * 4)
  sr(16,4, Water * 4)
}

/** 3rd Scenario of Game Three. Has a larger number of hexs. */
object ThreeScen3 extends ThreeScenStart {
  override implicit val grid: HGrid = HGridReg(2, 6, 2, 10)
  override val terrs: HCenArr[Terr] = grid.newTileArr[Terr](Plain)

  import terrs.{setRow => sr}

}