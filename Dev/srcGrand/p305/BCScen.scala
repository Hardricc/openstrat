/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import prid._, phex._, egrid._, eg80._, pEarth._

trait BCScen extends EScenBasic with HSysTurnScen
{
}

object BCScen1 extends BCScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid80LongFull = EGrid80.e0(446)
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override val sTerrs: HSideOptLayer[WSide] = Terr80E0.sTerrs
  override val corners: HCornerLayer = Terr80E0.corners
}

object BCScen2 extends BCScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid80LongFull = EGrid80.e0(446)
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override val sTerrs: HSideOptLayer[WSide] = Terr80E0.sTerrs
  override val corners: HCornerLayer = Terr80E0.corners
}