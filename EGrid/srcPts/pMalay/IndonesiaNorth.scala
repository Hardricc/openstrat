/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMalay
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of the island of Borneo. 743330km². Depends on nothing. */
object Borneo extends EarthAreaIsland("Borneo", 0.63 ll 114.132, jungle)
{ override val area: KilometresSq = 743330.kilometresSq

  val nBorneo: LatLong = 6.99 ll 117.12
  val northEast: LatLong = 5.382 ll 119.241
  val borderEast: LatLong = 4.165 ll 117.906
  val cenEast: LatLong = 1.022 ll 118.986
  val p20: LatLong =  -0.761 ll 117.613
  val p25: LatLong = -2.177 ll 116.589
  val southEast: LatLong = -4.03 ll 116.09
  val p45: LatLong = -4.172 ll 114.650
  val southWest: LatLong = -2.96 ll 110.29
  val p55: LatLong = -1.259 ll 109.398
  val p60: LatLong = 0.816 ll 108.841
  val nwSarawak: LatLong = 2.08 ll 109.64
  val batangLuparMouth: LatLong = 1.512 ll 110.988
  val p70: LatLong = 2.798 ll 111.333
  val p75: LatLong = 3.268 ll 113.058
  val kulalaBaram: LatLong = 4.598 ll 113.973

  override val polygonLL: PolygonLL = PolygonLL(nBorneo, northEast, borderEast, cenEast, p20, p25, southEast, p45, southWest, p55, p60, nwSarawak,
    batangLuparMouth, p70, p75, kulalaBaram)
}

/** [[polygonLL]] graphical representation of the island of Sulawesi 186216.16km². Depends on nothing. */
object Sulawesi extends EarthArea("Sulawesi", -2.16 ll 120.58, jungle)
{ val seSulawesi: LatLong = -5.41 ll 119.38
  val nwSulawesi: LatLong = 0.72 ll 120.06
  val neSulawesi: LatLong = 1.67 ll 125.15
  val ambesia: LatLong = 0.52 ll 120.62
  val poso: LatLong = -1.42 ll 120.68
  val teku: LatLong = -0.76 ll 123.45
  val swSulawesi: LatLong = -5.66 ll 122.78
  val nGulfBoni: LatLong = -2.61 ll 120.81

  override val polygonLL: PolygonLL = PolygonLL(seSulawesi, nwSulawesi, neSulawesi, ambesia, poso, teku, swSulawesi, nGulfBoni)
}

/** [[polygonLL]] graphical representation of the island of New Guinea. Depends on nothing. */
object GuineaWest extends EarthArea("West Guinea", -5.19 ll 141.03, hillyJungle)
{ val waigeoWest: LatLong = -0.113 ll 130.295
  val waigeoNorth: LatLong = -0.007 ll 130.814
  val manokwari: LatLong = -0.73 ll 133.98
  val sCenderawasih: LatLong = -3.39 ll 135.33
  val tebe: LatLong = -1.46 ll 137.93
  val northEast: LatLong = -2.606 ll 141

  val southEast: LatLong = -9.126 ll 141
  val p55: LatLong = -9.231 ll 141.135
  val p60: LatLong = -8.113 ll 139.951
  val southWest: LatLong = -8.431 ll 137.655
  val p70: LatLong = -7.518 ll 138.145
  val heilwigMouth: LatLong = -5.359 ll 137.866
  val aindua: LatLong = -4.46 ll 135.21
  val p85: LatLong = -4.083 ll 132.915
  val wNewGuinea: LatLong = -0.82 ll 130.45

  override val polygonLL: PolygonLL = PolygonLL(waigeoWest, waigeoNorth, manokwari, sCenderawasih, tebe, northEast, southEast, p55, p60, southWest, p70,
    heilwigMouth, aindua, p85, wNewGuinea)
}

/** [[polygonLL]] graphical representation for Papua New Guinea. Depends on nothing. */
object PapuaNewGuinea extends EarthArea("Papua New Guinea", -5.448 ll 143.578, hillyJungle)
{ val madang: LatLong = -4.85 ll 145.78
  val saidor: LatLong = -5.614 ll 146.473
  val p10: LatLong = -5.918 ll 147.339
  val p15: LatLong = -6.402 ll 147.843
  val p22: LatLong = -6.642 ll 147.856
  val markhamMouth: LatLong = -6.745 ll 146.970
  val deboinMission: LatLong = -8.056 ll 148.123
  val gavida: LatLong = -9.019 ll 149.292
  val east: LatLong = -10.23 ll 150.87

  val hulaBlackSand: LatLong = -10.103 ll 147.726
  val p53: LatLong = -8.067 ll 146.031
  val morigo: LatLong = -7.83 ll 143.98
  val saibai: LatLong = -9.32 ll 142.63

  override val polygonLL: PolygonLL = PolygonLL(GuineaWest.northEast, madang, saidor, p10, p15, p22, markhamMouth, deboinMission, gavida, east,
    hulaBlackSand, p53, morigo, saibai, GuineaWest.southEast
  )
}

/** [[polygonLL]] graphical representation of New Britain 35144.6km². Depends on nothing. */
object NewBritain extends EarthAreaIsland("New Britain", -5.251 ll 151.402, hillyJungle)
{ override val area: KilometresSq = 35144.6.kilometresSq

  val north: LatLong = -4.133 ll 152.166
  val northEast: LatLong = -4.336 ll 152.404
  val baronga: LatLong = -6.246 ll 150.463
  val umbolWest: LatLong = -5.502 ll 147.754
  val p75: LatLong = -5.480 ll 150.908
  val takis: LatLong = -4.213 ll 151.489

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, baronga, umbolWest, p75, takis)
}

/** [[polygonLL]] graphical representation 8990km² of New Ireland 7404km² + 1186km² + 400km². Depends on nothing. */
object NewIreland extends EarthAreaIsland("New Ireland", -5.251 ll 151.402, hillyJungle)
{ override val area: KilometresSq = 8990.kilometresSq

  val newHanoverIsland: LatLong = -2.360 ll 150.190
  val newIreland20: LatLong = -3.977 ll 152.926
  val south: LatLong = -4.840 ll 152.882
  val p60: LatLong = -4.182 ll 152.685

  override val polygonLL: PolygonLL = PolygonLL(newHanoverIsland, newIreland20, south, p60)
}