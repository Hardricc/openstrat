/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMed
import geom._, pglobe._

/** [[PolygonLL]] graphic for Crete depends on nothing. */
object Cephalonia extends EArea2("Cephalonia", 38.22 ll 20.59, Hill)
{ val north: LatLong = 38.50 ll 20.66
  val southEast: LatLong = 38.11 ll 20.82
  val southWest: LatLong = 38.18 ll 20.34
  val northWest: LatLong = 38.36 ll 20.40

  override val polygonLL: PolygonLL = PolygonLL(north, southEast, southWest, northWest)
}

/** [[PolygonLL]] graphic for Crete depends on nothing. */
object Crete extends EArea2("Crete", 35.23 ll 24.92, Hill)
{ val northEast: LatLong = 35.32 ll 26.31
  val southEast: LatLong = 35.02 ll 26.19
  val p10: LatLong = 34.92 ll 24.73
  val p15: LatLong = 35.09 ll 24.72
  val p20: LatLong = 35.23 ll 23.59
  val p30: LatLong = 35.29 ll 23.52
  val capeGramvousa: LatLong = 35.62 ll 23.60

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p10, p15, p20, p30, capeGramvousa)
}

/** [[PolygonLL]] graphic for Crete depends on nothing. */
object Lesbos extends EArea2("Lesbos", 38.19 ll 26.30, Hill)
{ val north: LatLong = 39.39 ll 26.34
  val northEast: LatLong = 39.34 ll 26.42
  val southEast: LatLong = 39.02 ll 26.61
  val south: LatLong = 38.96 ll 26.40
  val p70: LatLong = 39.19 ll 25.83
  val p75: LatLong = 39.26 ll 25.86
  val poseidonsLungs: LatLong = 39.37 ll 26.17

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, south, p70, p75, poseidonsLungs)
}

/** [[PolygonLL]] graphic for Cyprus depends on nothing. */
object Cyprus extends EArea2("Cyprus", 34.98 ll 33.15, Hill)
{ val northEast: LatLong = 35.69 ll 34.58
  val southEast: LatLong = 34.96 ll 34.09
  val p30: LatLong = 34.57 ll 33.04
  val pontiBaba: LatLong = 35.10 ll 32.28
  val korucamBurnu: LatLong = 35.40 ll 32.92

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p30, pontiBaba, korucamBurnu)
}

/** [[PolygonLL]] graphic for Rhodes depends on nothing. */
object Rhodes extends EArea2("Rhodes", 36.22 ll 27.95, Hill)
{ val north: LatLong = 36.46 ll 28.22
  val akraLindos: LatLong = 36.05 ll 28.09
  val p40: LatLong = 35.93 ll 27.86
  val south: LatLong = 35.88 ll 27.76
  val p50: LatLong = 35.94 ll 27.72
  val p65: LatLong = 36.15 ll 27.69
  val p70: LatLong = 36.27 ll 27.81

  override val polygonLL: PolygonLL = PolygonLL(north, akraLindos, p40, south, p50, p65, p70)
}

/** [[PolygonLL]] graphic for the Sinai peninsular depends on nothing. */
object Sinai extends EArea2("Sinai", 29.88 ll 33.75, Deserts)
{ val eGaza: LatLong = 31.32 ll 34.22
  val eilat: LatLong = 29.54 ll 34.98
  val south: LatLong = 27.73 ll 34.25
  val suez: LatLong = 29.93 ll 32.56
  val portSaid: LatLong = 31.27 ll 32.32

  override val polygonLL: PolygonLL = PolygonLL(eGaza, eilat, south, suez, portSaid)
}

/** [[PolygonLL]] graphic for north east Africa. Depends on [[SaharaWest]] and [[Sinai]]. */
object SaharaEast extends EArea2("Sahara\neast", 24 ll 25, Deserts)
{ val elAgheila: LatLong = 30.12 ll 19.08
  val benghazi: LatLong = 32.12 ll 20.05
  val derna: LatLong = 32.93 ll 22.15
  val p90: LatLong = 30.82 ll 29.09
  val baltim: LatLong = 31.60 ll 31.01
  val p5: LatLong = 29.59 ll 32.34
  val southEast: LatLong = 17 ll 39.4

  val polygonLL: PolygonLL = PolygonLL(SaharaWest.southEast, SaharaWest.northEast, elAgheila, benghazi, derna, p90, baltim, Sinai.portSaid,
    Sinai.suez, p5, southEast)
}