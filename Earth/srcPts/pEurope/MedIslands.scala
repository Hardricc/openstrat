/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

/** [[PolygonLL]] graphic for Majorca depends on nothing. */
object Majorca extends EArea2("Majorca", 39.59 ll 3.01, plain)
{ val south = 39.26 ll 3.05
  val palma = 39.56 ll 2.63
  val portalsVells = 39.45 ll 2.51
  val santElm = 39.59 ll 2.34
  val capFormentor = 39.96 ll 3.21
  val east = 39.71 ll 3.47

  override val polygonLL: PolygonLL = PolygonLL(south, palma, portalsVells, santElm, capFormentor, east)
}

/** [[PolygonLL]] graphic for Sicily depends on nothing. */
object Sicily extends EArea2("Sicily", cen = 37.58 ll 14.27, plain)
{ val sSicily = 36.66 ll 15.08
  val kartibubbo = 37.56 ll 12.67
  val marsala = 37.80 ll 12.42
  val calaRossa = 38.18 ll 12.73
  val mondello = 38.22 ll 13.32
  val n1 =37.97 ll 13.74
  val torreFaro = 38.26 ll 15.65
  val contradoFortino = 38.24 ll 15.58
  val messina = 38.18 ll 15.56
  val catania = 37.48 ll 15.08

  override val polygonLL: PolygonLL = PolygonLL(sSicily, kartibubbo, marsala, calaRossa, mondello, n1, torreFaro, contradoFortino, messina, catania)
}

/** [[PolygonLL]] graphic for Canaries depends on nothing. */
object Canarias extends EArea2("Canarias", 27.96 ll -15.60, plain)
{ val elHierro = 27.72 ll -18.15
  val laPalma = 28.85 ll -17.92
  val lanzarote = 29.24 ll -13.47
  val fuerteventura = 28.24 ll -13.94
  val granCanaria = 27.74 ll -15.60

  val polygonLL: PolygonLL = PolygonLL(elHierro, laPalma, lanzarote, fuerteventura, granCanaria)
}

/** [[PolygonLL]] graphic for Crete depends on nothing. */
object Crete extends EArea2("Crete", 35.23 ll 24.92, hills)
{ val northEast = 35.32 ll 26.31
  val southEast = 35.02 ll 26.19
  val p10 = 34.92 ll 24.73
  val p15 = 35.09 ll 24.72
  val p20 = 35.23 ll 23.59
  val p30 = 35.29 ll 23.52
  val capeGramvousa = 35.62 ll 23.60
  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p10, p15, p20, p30, capeGramvousa)
}

/** [[PolygonLL]] graphic for Cyprus depends on nothing. */
object Cyprus extends EArea2("Cyprus", 34.98 ll 33.15, hills)
{ val northEast = 35.69 ll 34.58
  val southEast = 34.96 ll 34.09
  val p30 = 34.57 ll 33.04
  val pontiBaba = 35.10 ll 32.28
  val korucamBurnu = 35.40 ll 32.92

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p30, pontiBaba, korucamBurnu)
}

/** [[PolygonLL]] graphic for Corsica depends on nothing. */
object Corsica extends EArea2("Corsica", 42.18 ll 9.17, hills)
{ val nCorsica = 43.00 ll 9.42
  val bastia = 42.70 ll 9.45
  val olmuccia = 41.69 ll 9.40
  val sCorsica = 41.37 ll 9.21
  val swCorsica = 41.56 ll 8.79
  val scandola = 42.37 ll 8.54
  val nwCalvi = 42.57 ll 8.71
  val pointeMignola = 42.73 ll 9.16
  val fromontica = 42.67 ll 9.29

  val polygonLL = PolygonLL(nCorsica, bastia, olmuccia, sCorsica, swCorsica, scandola, nwCalvi, pointeMignola, fromontica)
}

/** [[PolygonLL]] graphic for Sardinia depends on nothing. */
object Sardina extends EArea2("Sardina", 40.12 ll 9.07, hills)
{ val calaCaterina = 39.10 ll 9.51
  val perdaLonga = 38.87 ll 8.84
  val capoTeulada = 38.86 ll 8.64
  val portscuso = 39.21 ll 8.36
  val capoFalcone = 40.97 ll 8.20
  val platamona = 40.81 ll 8.46
  val north = 41.25 ll 9.23
  val east = 40.52 ll 9.82

  val polygonLL = PolygonLL(calaCaterina, perdaLonga, capoTeulada, portscuso, capoFalcone, platamona, north, east)
}