/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Australia. Depends on nothing. */
object Australia extends EArea2("Australia", -24.45 ll 134.47, sahel)
{ val capeLeeuwin: LatLong = -34.36 ll 115.13
  val wAustralia: LatLong = -22.58 ll 113.95
  val eightyMile: LatLong = -19.41 ll 121.24
  val couloumbPoint: LatLong = -17.30 ll 122.12
  val drysdaleRiver: LatLong = -13.77 ll 126.95
  val victoriaMouth: LatLong = -15.13 ll 129.65
  val thamarrurr: LatLong = -14.42 ll 129.36
  val coxPeninsular: LatLong = -12.41 ll 130.64
  val nAustralia: LatLong = -12.01 ll 133.56
  val eastArnhem: LatLong = -12.31 ll 136.92
  val limmen: LatLong = -14.73 ll 135.36
  val karumba: LatLong = -17.52 ll 140.8
  val nQueensland: LatLong = -11 ll 142.43

  val nKennedy: LatLong = -14.49 ll 143.95
  val capeMelville: LatLong = -14.17 ll 144.51
  val coolbie: LatLong = -18.86 ll 146.27
  val harveyBay: LatLong = -25.29 ll 152.89
  val brisbane: LatLong = -27.05 ll 153.03
  val byronBay: LatLong = -28.64 ll 153.62
  val seAustralia: LatLong = -37.4 ll 149.58

  val wilsonsProm: LatLong = -39.12 ll 146.38
  val barwonHeads: LatLong = -38.27 ll 144.53
  val capeOtway: LatLong = -38.85 ll 143.51
  val portMacdonnell: LatLong = -38.06 ll 140.66
  val carpenterRocks: LatLong = -37.89 ll 140.28
  val capeJaffa: LatLong = -36.96 ll 139.67
  val hardwicke: LatLong = -34.91 ll 137.46
  val portAugusta: LatLong = -32.53 ll 137.77
  val sleaford: LatLong = -34.92 ll 135.64
  val smokyBay: LatLong = -32.52 ll 133.86
  val yalata: LatLong = -31.35 ll 131.21
  val nuytsland1: LatLong = -32.96 ll 124.33
  val nuytsland2: LatLong = -33.86 ll 123.63
  val windyHarbour: LatLong = -34.84 ll 116

  override val polygonLL: PolygonLL = PolygonLL(capeLeeuwin, wAustralia, eightyMile, couloumbPoint, drysdaleRiver, victoriaMouth, thamarrurr,
    coxPeninsular, nAustralia, eastArnhem, limmen, karumba, nQueensland, nKennedy, capeMelville, coolbie, harveyBay, brisbane, byronBay, seAustralia,
    wilsonsProm, barwonHeads, capeOtway, portMacdonnell, carpenterRocks, carpenterRocks, hardwicke, portAugusta, sleaford, smokyBay, yalata,
    nuytsland1, nuytsland2, windyHarbour)
}
/** [[polygonLL]] graphical representation of the North Ilsand of New Zealand. Depends on nothing. */
object NZNorthIsland extends EArea2("NewZealandNIsland", -38.66 ll 176, land)
{ val capeReinga: LatLong = -34.42 ll 172.68
  val teHapua: LatLong = -34.41 ll 173.05
  val aukland: LatLong = -36.83 ll 174.81
  val eCape: LatLong = -37.69 ll 178.54
  val capePalliser: LatLong = -41.61 ll 175.29
  val makara: LatLong = -41.29 ll 174.62
  val himtangi: LatLong = -40.36 ll 175.22
  val capeEgmont: LatLong = -39.28 ll 173.75

  override val polygonLL: PolygonLL = PolygonLL(capeReinga, teHapua, aukland, eCape, capePalliser, makara, himtangi, capeEgmont)
}

/** [[polygonLL]] graphical representation of the South Island of New Zealand. Depends on nothing. */
object NZSouthIsland extends EArea2("NewZealandSIsland", -43.68 ll 171.00, land)
{ val swNewZealand: LatLong = -45.98 ll 166.47
  val puponga: LatLong = -40.51 ll 172.72
  val capeCambell: LatLong = -41.73 ll 174.27
  val slopePoint: LatLong = -46.67 ll 169.00

  override val polygonLL: PolygonLL = PolygonLL(swNewZealand, puponga, capeCambell, slopePoint)
}