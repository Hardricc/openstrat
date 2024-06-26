/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for nunavut territory Canada. Depends on Nothing. */
object Nunavut extends EarthArea("Nunavut", 67.00 ll -96.58, tundra)
{ val northWest = 68.27 ll -108.77
  val nunavut15: LatLong = 67.68 ll -101.53
  val nunavut17: LatLong = 68.55 ll -97.80
  val nunavut25: LatLong = 68.05 ll -94.76
  val nunavut30: LatLong = 69.32 ll -94.23
  val nunavut40: LatLong = 71.39 ll -96.17
  val somersetNW: LatLong = 73.99 ll -95.28
  val somersetNE: LatLong = 73.91 ll -90.18
  val somersetSE: LatLong = 72.73 ll -92.27
  val nunavut45 = 72.72 ll -93.79
  val nunavut47 = 71.76 ll -93.69
  val nunavut49 = 71.35 ll -92.97
  val nunavut54 = 70.17 ll -91.57
  val nunavut56: LatLong = 69.70 ll -92.48
  val nunavut60: LatLong = 69.50 ll -91.83
  val nunavut64 = 68.45 ll -90.59
  val nunavut65 = 68.24 ll -90.23
  val nunavut67 = 69.26 ll -89.13
  val nunavut70: LatLong = 68.80 ll -88.02
  val nunavut80: LatLong = 67.21 ll -87.19
  val nunavut83 = 68.72 ll -85.59
  val nunavut85 = 69.85 ll -85.55
  val nunavut87 = 69.65 ll -82.13
  val nunavut88: LatLong = 69.20 ll -81.35
  val navut20 = 67.45 ll -81.21
  val naujaat10 = 66.35 ll -83.41
  val naujaat12 = 66.18 ll -84.44
  val nunavut90 = 66.27 ll -85.23
  val nunavut92 = 66.54 ll -86.76
  val naujaat15 = 66.20 ll -85.90
  val naujaat17 = 64.00 ll -88.76
  val rockHouseIsland = 63.44 ll -90.63

  override def polygonLL: PolygonLL = PolygonLL(northWest, nunavut15, nunavut17, nunavut25, nunavut30, nunavut40, somersetNW, somersetNE, somersetSE,
    nunavut45, nunavut47, nunavut49, nunavut54, nunavut56, nunavut60, nunavut65, nunavut67, nunavut70, nunavut80, nunavut83, nunavut85, nunavut87,
    nunavut88, navut20, naujaat10, naujaat12, nunavut90, nunavut92, naujaat15, naujaat17, rockHouseIsland)
}

/** [[polygonLL]] graphical representation for Reindeer Lake. Depends on nothing. */
object ReindeerLake extends EarthArea("Reindeer\nLake", 66.00 ll -120.25, Lake)
{  val north: LatLong = 58.136 ll -101.994
  val beaver: LatLong = 57.437 ll -102.192
  val southEast: LatLong = 56.359 ll -102.954
  val southWest: LatLong = 56.363 ll -103.056
  val p55: LatLong = 56.445 ll -103.069
  val p75: LatLong = 57.478 ll -102.618

  override def polygonLL: PolygonLL = PolygonLL(north, beaver, southEast, southWest, p55, p75)
}

/** [[polygonLL]] graphical representation for Great Bear Lake. Depends on nothing. */
object GreatBearLake extends EarthArea("Great Bear\nLake", 66.00 ll -120.25, Lake)
{ val north: LatLong = 67.04 ll -119.80
  val p20: LatLong = 66.86 ll -119.45
  val p30: LatLong = 66.40 ll -120.51
  val p36: LatLong = 66.29 ll -117.97
  val p40: LatLong = 65.60 ll -119.92
  val p55: LatLong = 64.93 ll -122.06
  val p70: LatLong = 66.06 ll -121.25
  val northWest: LatLong = 66.26 ll -125.87

  override def polygonLL: PolygonLL = PolygonLL(north, p20, p30, p36, p40, p55, p70, northWest)
}

/** [[polygonLL]] graphical representation for Great Bear Lake. Depends on nothing. */
object GreatSlaveLake extends EarthArea("Great Slave\nLake", 61.66 ll -113.83, Lake)
{ val north: LatLong = 62.96 ll -110.42
  val east: LatLong = 62.77 ll -108.91
  val south: LatLong = 60.83 ll -115.58
  val bigIslandEast: LatLong = 61.13 ll -116.44
  val p70: LatLong = 61.88 ll -114.59
  val northWest: LatLong = 62.76 ll -116.00
  val p80: LatLong = 61.99 ll -113.50

  override def polygonLL: PolygonLL = PolygonLL(north, east, south, bigIslandEast, p70, northWest, p80)
}

  /** [[polygonLL]] graphical representation for north west Canada. Depends on [[AlaskaNorth]]. */
object CanadaNorthWest extends EarthArea("Canada\n north west", 64.051 ll -129.98, taiga)
{ val yakut50: LatLong = 60 ll -139.60
  val yakut10: LatLong = 68.90 ll -136.53
  val inuvik10: LatLong = 70.56 ll -128.00
  val nunavut10: LatLong = 69.00 ll -115.80
  val raeMouth: LatLong = 67.92 ll -115.34
  val hudsonBay60W: LatLong = 60 ll -94.82

  override def polygonLL: PolygonLL = PolygonLL(yakut50, AlaskaSouth.p5, AlaskaNorth.southEast, AlaskaNorth.northEast, yakut10, inuvik10, nunavut10, raeMouth, Nunavut.northWest,
    Nunavut.rockHouseIsland, hudsonBay60W, GreatSlaveLake.east, GreatSlaveLake.north, GreatSlaveLake.p80, GreatSlaveLake.northWest)
}

object CanadaSouthWest extends EarthArea("Canada\n south west", 55 ll -110, taiga)
{ val montanaNE: LatLong = 49 ll -104
  val w49th: LatLong = 49 ll -122.754
  val vancouverIslandSouth: LatLong = 48.310 ll -123.563
  val vancouverIslandWest: LatLong = 50.776 ll -128.428
  val priceIslandSouth: LatLong = 52.290 ll -128.677
  val kunghitIslandSouth: LatLong = 51.944 ll -131.027
  val p50: LatLong = 54.71 ll -132.81
  val eggIsland : LatLong= 59.91 ll -94.85

  val churchillMouth = 58.79 ll -94.20
  val manitoba10 = 58.75 ll -93.24
  val nelsonMouth: LatLong = 57.09 ll -92.47

  override def polygonLL: PolygonLL = PolygonLL(CanadaNorthWest.yakut50, GreatSlaveLake.northWest, GreatSlaveLake.p70, GreatSlaveLake.bigIslandEast,
    GreatSlaveLake.south, GreatSlaveLake.east, CanadaNorthWest.hudsonBay60W, eggIsland, churchillMouth, manitoba10, nelsonMouth,
    LakeWinnipeg.northWest, LakeWinnipeg.p75, LakeWinnipeg.redMouth, montanaNE, w49th, vancouverIslandSouth, vancouverIslandWest, priceIslandSouth,
    kunghitIslandSouth, p50)
}