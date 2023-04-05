/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._

/** A Civ scenario turn state. */
trait CivScen  extends HSysTurnScen
{ override def title: String = "Civ Scenario"

  /** tile terrain. */
  val terrs: HCenLayer[VTile]
  val sTerrs: HSideOptionalLayer[VSide, VSideSome]
  val corners: HCornerLayer
  val lunits: HCenArrLayer[Warrior]
}

/** A Civ scenario state at turn 0. */
trait CivScenStart extends CivScen
{ override def turn: Int = 0
}

/** Civ scenario 1. */
object Civ1 extends CivScenStart
{ override implicit val gridSys: HGrid = HGridReg(2, 12, 4, 40)
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Plain)
  terrs.setRowEnd(12, 20, Hill, Mountain * 2, Plain * 3)
  terrs.setRowEnd(4, 4, Hill * 3, Plain * 7)
  override val sTerrs: HSideOptionalLayer[VSide, VSideSome] = gridSys.newSideOptionalLayer[VSide, VSideSome]
  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(10, 18, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))

  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
}

/** Civ scenario 2. */
object Civ2 extends CivScenStart
{ override val title: String = "CivRise Scen 2"
  override implicit val gridSys: HGrid = HGridReg(2, 14, 4, 42)
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Sea)

//  terrs.setRow(10, Plain, Head2Land(5), Sea * 4, IsPlain * 2, Sea * 2)
//  terrs.setRow(8, Land(Plain) * 4, Head4Land(5), Sea * 3, Plain, Sea)
//  terrs.setRow(6, Land(Plain) * 3, Sea * 2, Head3Land(4, Mountain), Sea, Head1Land(4), Plain * 2)
  terrs.setRowEndUnchecked(4, Mountain * 3, Plain * 2)
  terrs.setRowSame(2, Plain)

  override val sTerrs: HSideOptionalLayer[VSide, VSideSome] =
  { val res: HSideOptionalLayer[VSide, VSideSome] = gridSys.newSideOptionalLayer[VSide, VSideSome]
    //res.setSomeInts(VSideMid(River), 3,39,  4,38,  5,39,  6,40,  3,37)
    //res.setSomeInts(VSideMid(), 4, 30)
    //res.setSomeInts(VSideLt(), 7,27,  6,28,  9,21,  8,22,  7,21,  6,28,  10,12,  11,11,  9,31,  11,31,  11,35,  10,36,  9,35)
    //res.setSomeInts(VSideRt(), 7,19,  9,19,  6,24,  7,25,  11,9,  9,29,  10,28,  11,29,  9,33,  11,33,  6,32,  7,33)
    //res.setSomeInts(VSideLtRt(),  10,32)
    res
  }

  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(8, 16, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))

  override val corners: HCornerLayer =
  { val res = gridSys.newHVertOffsetLayer
    res.setMouth3(8, 40)
    res.setVert2In(6, 38)
    res.setVert5In(4, 40)
    res.setMouth5(2, 42)
    res.setMouth1(2, 34)
    res.setTJunction(3, 38)

    res.setMouth3(6, 30)
    res.setMouth0(2, 30)

    res.set2CornersIn(10, 10, 0, 7)
    res.set3CornersIn(6, 26, 5, 7)
    res.set4CornersIn(8, 20, 0, 7)
    res.set6CornersIn(10, 30, 7)
    res.set6CornersIn(10, 34, 7)
    res.setCornerIn(6, 34, 5, 7)

    res
  }
}