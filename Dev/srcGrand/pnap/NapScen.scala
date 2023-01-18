/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import prid._, phex._, egrid._, eg160._, pEarth._

trait NapScen extends EScenBasic with HSysTurnScen
{
  override def title: String = "AD1783"
}

object NapScen2 extends NapScen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid160LongFull = EGrid160.e0(276)
  override val terrs: HCenLayer[WTile] = Terr160E0.terrs
  override def sTerrs: HSideOptLayer[WSide] = Terr160E0.sTerrs
//  override def sTerrsDepr: HSideBoolLayer = Terr160E0.sTerrsDepr
  override val corners: HCornerLayer = Terr160E0.corners
}
