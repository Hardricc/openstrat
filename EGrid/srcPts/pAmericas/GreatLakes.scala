/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphical representation for central Canada. Depends on [[Quebec]]. */
object CanadaCentral extends EarthPoly("Canada\n central", 52.37 ll -86.94, taiga)
{ val manitoba20: LatLong = 57.26 ll -90.89
  val jamesBayNW: LatLong = 55.07 ll -82.31
  val attapiskatMouth: LatLong = 52.97 ll -82.26
  val moosoneeMouth: LatLong = 51.36 ll -80.40

  override def polygonLL: PolygonLL = LinePathLL(Manitoba.nelsonMouth, manitoba20, jamesBayNW, attapiskatMouth, moosoneeMouth, Quebecia.jamesBayS) ++<
    LakeHuron.centralCanadaCoast ++ LakeSuperior.northCoast.reverse |++<| LakeWinnipeg.eastCoast
}

/** [[PolygonLL]] graphical representation for Lake Superior. Depends on nothing. */
object LakeSuperior extends LakePoly("Lake Superior", 47.5 ll -88, Lake)
{ override val area: Kilares = 82100.kilares

  val east: LatLong = 46.52 ll -84.61
  val grandMarais = 47.748 ll -90.347
  val michipicoten: LatLong = 47.96 ll -84.86
  val north: LatLong = 48.80 ll -87.31
  val northEast: LatLong = 48.751 ll -86.420
  val p10: LatLong = 47.963 ll -85.811
  val west48: LatLong = 48.00 ll -89.57
  val west: LatLong = 46.77 ll -92.11

  /** The north coast of Lake Superior in a clockwise direction, shares both west and east [[LatLong]] points with southCoast. */
  val northCoast: LinePathLL = LinePathLL(west, grandMarais, west48, north, northEast, p10, michipicoten, east)

  val p35: LatLong = 46.96 ll -90.86
  val montrealMouth: LatLong = 46.57 ll -90.42
  val p50: LatLong = 46.966 ll -88.144
  val highRock : LatLong = 47.42 ll -87.71
  val chocolayMouth: LatLong = 46.50 ll -87.35
  val whitefishPoint = 46.770 ll -84.954
  val p52: LatLong = 46.485 ll -85.030

  /** The south coast of Lake Superior in a clockwise direction, shares both east and west [[LatLong]] points with northCoast. */
  val southCoast: LinePathLL = LinePathLL(east, p52, whitefishPoint, chocolayMouth, p50, highRock, montrealMouth, p35, west)

  override def polygonLL: PolygonLL = northCoast |-++-| southCoast
}

/** [[PolygonLL]] graphical representation for Lake Huron. Depends on nothing. */
object LakeHuron extends LakePoly("Lake Huron", 44.80 ll -82.4, Lake)
{ override val area: Kilares = 59590.kilares
  
  val pineMouth: LatLong = 46.05 ll -84.66
  val borderNorth: LatLong = 45.91 ll -83.50
  val fitzwilliam: LatLong = 45.45 ll -81.79
  val killarney: LatLong = 45.99 ll -81.43
  val northEast: LatLong = 45.89 ll -80.75
  val centralCanadaCoast: LinePathLL = LinePathLL(pineMouth, borderNorth, fitzwilliam, killarney, northEast)

  val east: LatLong = 44.86 ll -79.89
  val geogianSouth: LatLong = 44.47 ll -80.11
  val owenSound: LatLong = 44.58 ll -80.94
  val tobermory: LatLong = 45.26 ll -81.69
  val saubleBeach: LatLong = 44.63 ll -81.27
  val pointClarke: LatLong = 44.073 ll -81.758
  val grandBend: LatLong = 43.313 ll -81.766
  val south: LatLong = 43.00 ll -82.42
  val eastCanadaCoast: LinePathLL = LinePathLL(northEast, east, geogianSouth, owenSound, tobermory, saubleBeach,pointClarke, grandBend, south)

  val turnipRock: LatLong = 44.07 ll -82.96
  val tobicoLagoon: LatLong = 43.67 ll -83.90
  val pesqueIsle: LatLong = 45.27 ll -83.38
  val usCoastSouth: LinePathLL = LinePathLL(south, turnipRock, tobicoLagoon, pesqueIsle)

  override def polygonLL: PolygonLL = usCoastSouth ++ LinePathLL(LakeMichigan.mouthSouth, LakeMichigan.mouthNorth) ++ centralCanadaCoast |-++-| eastCanadaCoast
}

/** [[PolygonLL]] graphical representation for Lake Erie. Depends on nothing. */
object LakeErie extends LakePoly("Lake Erie", 42.24 ll -81.03, Lake)
{ override val area: Kilares = 25700.kilares

  val detroitMouth: LatLong = 42.05 ll -83.15
  val theTip: LatLong = 41.90 ll -82.50
  val portStanley: LatLong = 42.66 ll -81.24
  val longPoint: LatLong = 42.58 ll -80.44
  val portDover: LatLong = 42.784 ll -80.220
  val niagraMouth: LatLong = 42.89 ll -78.91
  val eastCanadaCoast: LinePathLL = LinePathLL(detroitMouth, theTip, portStanley, longPoint, portDover, niagraMouth)

  val east: LatLong = 42.78 ll -78.85
  val edgeWater: LatLong = 41.488 ll -81.739
  val south: LatLong = 41.38 ll -82.49
  val maumeeMouth: LatLong = 41.70 ll -83.47
  val southCoast: LinePathLL = LinePathLL(niagraMouth, east, edgeWater, south, maumeeMouth)

  override def polygonLL: PolygonLL = eastCanadaCoast |+-+| southCoast
}

/** [[PolygonLL]] graphical representation for Lake Ontario. Depends on nothing. */
object LakeOntario extends LakePoly("Lake Ontario", 43.65 ll -77.84, Lake)
{ override val area: Kilares = 18970.kilares

  val wolfeSW: LatLong = 44.10 ll -76.44
  val tibbettsPoint = 44.10 ll -76.37
  val southEast: LatLong = 43.53 ll -76.22
  val irondequoitMout = 43.236 ll -77.534
  val niagraMouth: LatLong = 43.26 ll -79.07
  val usCoast: LinePathLL = LinePathLL(wolfeSW, tibbettsPoint, southEast, irondequoitMout, niagraMouth)

  val southWest: LatLong = 43.30 ll -79.82
  val frenchmansBay: LatLong = 43.81 ll -79.09
  val northEast: LatLong = 44.20 ll -76.51
  val canadaCoast: LinePathLL = LinePathLL(niagraMouth, southWest, frenchmansBay, northEast, wolfeSW)

  override def polygonLL: PolygonLL = usCoast |-++-| canadaCoast
}

/** [[PolygonLL]] graphical representation for Lake Michigan. Depends on nothing. */
object LakeMichigan extends LakePoly("Lake Michigan", 43.82 ll -87.1, Lake)
{ override val area: Kilares = 58030.kilares

  val mouthSouth: LatLong = 45.78 ll -84.75
  val pointBetsie: LatLong = 44.69 ll -86.26
  val macatawaMouth: LatLong = 42.773 ll -86.212
  val bridgeman: LatLong = 41.946 ll -86.578
  val south: LatLong = 41.62 ll -87.25
  val coastEast: LinePathLL = LinePathLL(mouthSouth, pointBetsie, macatawaMouth, bridgeman, south)

  val chicagoMouth: LatLong = 41.888 ll -87.612
  val west: LatLong = 43.04 ll -87.89
  val northWest: LatLong = 45.91 ll -86.97
  val north: LatLong = 46.10 ll -85.42
  val mouthNorth: LatLong = 45.84 ll -84.75
  val coastWest: LinePathLL = LinePathLL(south, chicagoMouth, west, northWest, north, mouthNorth)

  override def polygonLL: PolygonLL = coastEast |++| coastWest
}