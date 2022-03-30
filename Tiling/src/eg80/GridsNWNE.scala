/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, geom.pglobe._

object GridsNWNE extends HGridMulti {
  val gridMan1 = new HGridMan(EGrid80Km.l0b446, 0)
  { override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  val gridMan2 = new HGridMan(EGrid80Km.l30b446, gridMan1.numTiles)
  { override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  override def gridMans: Arr[HGridMan] = Arr(gridMan1, gridMan1)

  /** Splits [[HCen]] allocation at 0y100 0r 1024. */
  override def unsafeGetMan(r: Int, c: Int): HGridMan = ife(c < t"100", gridMan1, gridMan2)

  override def adjTilesOfTile(tile: HCen): HCens = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???
}