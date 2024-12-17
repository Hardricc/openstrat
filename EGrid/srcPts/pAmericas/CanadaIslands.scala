/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for Devon Island and Axal Heiberg Island. Depends on nothing. */
object RingnesIslands extends EarthPoly("RingnesIslands", 78.473 ll -100.940, hillyTundra)
{ val ellefNorth: LatLong = 79.367 ll -103.886
  val amundNorth: LatLong = 78.813 ll -97.866
  val haigThomas: LatLong = 78.184 ll -94.308
  val cornwallEast: LatLong = 77.657 ll -93.085
  val cornwallSE: LatLong = 77.446 ll -93.530
  val cornwallSW: LatLong = 77.501 ll -96.060
  val amundSW: LatLong = 77.798 ll -97.131
  val ellefSW: LatLong = 77.747 ll -102.377
  val ellefWest: LatLong = 78.488 ll -104.945

  override val polygonLL: PolygonLL = PolygonLL(ellefNorth, amundNorth, haigThomas, cornwallEast, cornwallSE, cornwallSW, amundSW, ellefSW, ellefWest)
}

/** [[polygonLL]] graphical representation for Ellesmere Island and Axel Heiberg Island. Depends on nothing. */
object EllesmereIsland extends EarthPoly("Ellsesmere Island", 80.24 ll -79.37, mtainIce)
{ val east: LatLong = 82.27 ll -61.23
  val p10: LatLong = 81.440 ll -64.604
  val p30: LatLong = 79.57 ll -73.19
  val southEast: LatLong = 76.51 ll -78.33

  val northKentSW: LatLong = 76.486 ll -90.022
  val p55: LatLong = 77.128 ll -88.283
  val p58: LatLong = 77.845 ll -88.203
  val p62: LatLong = 78.166 ll -88.888
  val p64: LatLong = 78.213 ll -92.051

  val west: LatLong = 80.038 ll -100.150
  val p70: LatLong = 80.377 ll -96.693
  val p75: LatLong = 81.345 ll -94.118
  val p78: LatLong = 81.730 ll -91.680
  val p85: LatLong = 82.895 ll -80.027
  val north: LatLong = 83.08 ll -70.18
  val p5: LatLong = 82.788 ll -63.585

  val northCoast: LinePathLL = LinePathLL(west, p70, p75, p78, p85,north, p5, east)

  override val polygonLL: PolygonLL = LinePathLL(east, p10, p30, southEast, northKentSW, p55, p58, p62, p64) |+-+| northCoast
}

/** [[polygonLL]] graphical representation for Devon Island. Depends on nothing. */
object DevonIsland extends EarthPoly("Devon Island", 75.15 ll -87.1, tundra)
{ val p10: LatLong = 75.77 ll -81.09
  val p20: LatLong = 75.46 ll -79.62
  val p30: LatLong = 74.60 ll -80.30
  val p35: LatLong = 76.40 ll -90.46
  val p40: LatLong = 74.77 ll -91.96
  val p50: LatLong = 76.22 ll -96.93
  val p60: LatLong = 77.03 ll -96.44

  override val polygonLL: PolygonLL = PolygonLL(p10, p20, p30, p40, p50, p60, p35)
}

/** [[polygonLL]] graphical representation for Banks Island. Depends on nothing. */
object BanksIsland extends EarthPoly("Banks Island", 73.12 ll -121.13, tundra)
{ val p0: LatLong = 74.28 ll -118
  val p10: LatLong = 73.54 ll -115.34
  val p18: LatLong = 72.66 ll -119.15
  val p30: LatLong = 71.58 ll -120.50
  val south: LatLong = 71.11 ll -123.06
  val southWest: LatLong = 71.96 ll -125.81
  val northWest: LatLong = 74.35 ll -124.70
  val north: LatLong = 74.56 ll -121.49

  override val polygonLL: PolygonLL = PolygonLL(p0, p10, p18, p30, south, southWest, northWest, north)
}

/** [[polygonLL]] graphical representation for Meckenzie Island, Borden Island and Brock Island. Depends on nothing. */
object MackenzieIslands extends EarthPoly("Mackenzie Islands", 75.43 ll -110.86, tundra)
{ val bordenNorth: LatLong = 78.759 ll -110.423
  val bordenNE: LatLong = 78.559 ll -109.335
  val southEast: LatLong = 77.573 ll -110.077
  val south: LatLong = 77.335 ll -112.012
  val west: LatLong = 77.958 ll -115.077
  override val polygonLL: PolygonLL = PolygonLL(bordenNorth, bordenNE, southEast, south, west)
}

/** [[polygonLL]] graphical representation for Melville Island and Prince Patrick Island. Depends on nothing. */
object MelvilleIsland extends EarthPoly("Melville Island", 75.43 ll -110.86, tundra)
{ val northWest: LatLong = 75.86 ll -105.44
  val southWest: LatLong = 75.06 ll -105.99
  val south: LatLong = 74.41 ll -113.00
  val patricSouthWest: LatLong = 75.98 ll -122.63
  val patricNorthWest: LatLong = 77.32 ll -119.17

  override val polygonLL: PolygonLL = PolygonLL(northWest, southWest, south, patricSouthWest, patricNorthWest)
}

/** [[polygonLL]] graphical representation for Victoria Island. Depends on nothing. */
object VictoriaIsland extends EarthPoly("Victoria Island", 70.65 ll -109.36, tundra)
{ val stefanssonN: LatLong = 73.75 ll -105.29
  val vic5: LatLong = 71.12 ll -104.60
  val vic10: LatLong = 70.21 ll -101.34
  val southEast: LatLong = 69.00 ll -101.79
  val southWest: LatLong = 68.46 ll -113.21
  val vic30: LatLong = 69.22 ll -113.69
  val pointCaen: LatLong = 69.30 ll -115.95
  val vic40: LatLong = 71.60 ll -118.90
  val northWest: LatLong = 73.36 ll -114.57
  val p10: LatLong = 73.00 ll -110.49

  override val polygonLL: PolygonLL = PolygonLL(stefanssonN, vic5, vic10, southEast, southWest, vic30, pointCaen, vic40, northWest, p10)
}

/** [[polygonLL]] graphical representation for Prince Wales Island. Depends on nothing. */
object PrinceWalesIsland extends EarthPoly("Prince of Wales Island", 72.87 ll -99.13, tundra)
{ val northEast: LatLong = 73.86 ll -97.20
  val east: LatLong = 72.43 ll -96.28
  val south: LatLong = 71.30 ll -98.71
  val west: LatLong = 72.81 ll -102.71
  val p10: LatLong = 73.81 ll -100.99

  override val polygonLL: PolygonLL = PolygonLL(northEast, east, south, west, p10)
}

/** [[polygonLL]] graphical representation for Southampton Island. Depends on nothing. */
object SouthamptonIsland extends EarthPoly("Southampton Island", 64.5 ll -84.35, tundra)
{ val north: LatLong = 66.02 ll -85.08
  val p20: LatLong = 65.27 ll -84.26
  val east: LatLong = 63.78 ll -80.16
  val p30: LatLong = 63.45 ll -81.00
  val p32: LatLong = 63.44 ll -80.97
  val south: LatLong = 63.11 ll -85.46
  val southEast: LatLong = 63.56 ll -87.13
  val p52: LatLong = 64.09 ll -86.18
  val p80: LatLong = 65.73 ll -85.96

  override val polygonLL: PolygonLL = PolygonLL(north, p20, east, p30, p32, south, southEast, p52, p80)
}

/** [[polygonLL]] graphical representation for Baffin Island. Depends on nothing. */
object BaffinIsland extends EarthPoly("Baffin Island", 69.55 ll -72.64, tundra)
{ val p3: LatLong = 73.75 ll -84.94
  val p4: LatLong = 72.65 ll -86.70
  val p12: LatLong = 73.73 ll -82.82
  val bylotNE: LatLong = 73.67 ll -78.13
  val p14: LatLong = 72.071 ll -74.249
  val p20: LatLong = 70.54 ll -68.31
  val p22: LatLong = 69.253 ll - 66.677
  val p24: LatLong = 68.714 ll -67.689
  val p26 = 68.042 ll -64.938

  val east: LatLong = 66.67 ll -61.29
  val p28: LatLong = 64.927 ll -63.627
  val p29: LatLong = 66.276 ll -67.132
  val p31: LatLong = 64.732 ll -65.543
  val lemieux: LatLong = 63.598 ll -63.970

  val southEast: LatLong = 61.88 ll -65.96
  val p35: LatLong = 64.37 ll -74.67
  val p40: LatLong = 64.43 ll -78.02
  val p47: LatLong = 66.17 ll -74.43
  val p60: LatLong = 69.76 ll -77.61
  val p70: LatLong = 70.50 ll -88.72
  val p80: LatLong = 73.58 ll -88.21

  override def polygonLL: PolygonLL = PolygonLL(p3, p4, p12, bylotNE, p14, p20, p22, p24, p26, east, p28, p29, p31, lemieux, southEast, p35, p40, p47, p60, p70,
    p80)
}