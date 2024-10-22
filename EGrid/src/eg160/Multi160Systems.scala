/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid.phex._, egrid._

/** 160km scenario for 2 Grid system for 0°E and 30°E */
object Scen160Europe extends EScenLongMulti
{ override val title: String = "160km °0E - 30°E"
  override implicit val gridSys: EGrid160LongMulti = EGrid160.multi(2, 0, 256)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 160km scenario for 2 Grid system for 120°E and 150°E */
object Scen160ChinaJapan extends EScenLongMulti
{ override val title: String = "160km 120°E - 150°E"
  override implicit val gridSys: EGrid160LongMulti = EGrid160.multi(2, 4, 252, 272)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}


/** 320km terrain only scenario for North America. 4 320km grid system for 150°W, 120°W, 90°W and 60°W */
object Scen160NorthAmerica extends EScenLongMulti
{ override val title: String = "320km North America 150°W - 60°W"
  override implicit val gridSys: EGrid160LongMulti = EGrid160.multi(3, 8, 314)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 160km scenario for 3 Grid system for 30°W, 0°E and 30°E */
object Scen160Africa extends EScenLongMulti
{ override val title: String = "160km 30°W - 60°E"
  override implicit val gridSys: EGrid160LongMulti = EGrid160.multi(3, 11, 280)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}