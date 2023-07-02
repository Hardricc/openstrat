/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._

object Polandia extends EArea2("Polandia", 50.07 ll 20.13, Plain)
{ val mielno: LatLong = 54.26 ll 16.06
  val jaroslawiec: LatLong = 54.54 ll 16.53
  val jastrzebia: LatLong = 54.83 ll 18.33
  val wladyslawowo: LatLong = 54.79 ll 18.42
  val danzig: LatLong = 54.39 ll 18.65
  val p10 = 54.46 ll 19.64
  val capeTaran = 54.96 ll 19.98

  /** 54.93 ll 21.26 */
  val kaliningrad: LatLong = 54.93 ll 21.26

  val cenEast = 52 ll 24

  val polygonLL = PolygonLL(Germania.swinoujscie, mielno, jaroslawiec, jastrzebia, wladyslawowo, danzig, p10, capeTaran, kaliningrad, cenEast,
    BalkansEast.odessa, BalkansWest.northEast, Alpsland.zagreb, Alpsland.vienna)

  val stettin = 53.43 ll 14.55
}

object Baltland extends EArea2("BaltLand", 56.46 ll 27.83, Plain)
{ val klaipeda = 55.73 ll 21.08
  val ziemupe = 56.83 ll 21.06
  val ovsi = 57.57 ll 21.71
  val kolka = 57.75 ll 22.60
  val kesterclems = 57.11 ll 23.24
  val jurmala = 56.96 ll 23.66
  val saulkrasti = 57.28 ll 24.41
  val parnu = 58.37 ll 24.47
  val lao = 58.23 ll 24.12
  val virtsu = 58.56 ll 23.50
  val noarootsi = 59.2 ll 23.5
  val paldiski = 59.40 ll 24.04
  val udria = 59.40 ll 27.92
  val krasnoselsky = 59.86 ll 30.14
  val piterland = 59.97 ll 30.21
  val nevaMouth = 59.95 ll 31.04
  val ladogaEast = 60.66 ll 32.96
  val svirMouth = 61.01 ll 35.49
   
  val onegaSouth = 60.88 ll 35.67
  val ustye = 61.19 ll 36.43
  val onegaEast = 61.39 ll 36.47
  val peschanoyeSouth = 62.04 ll 35.68
  val pudozhgorskiy = 62.32 ll 35.86
  val chelmuzhiEast = 62.53 ll 35.75
   
  /** Start of North coast. */
  val onezhsky: LatLong = 63.79 ll 37.35
  val onegaRiver = 63.93 ll 37.99
  val pushlakhtaNorth = 64.92 ll 36.44
  val letniyNavolok = 65.16 ll 37.04
  val uyma = 64.55 ll 39.71
  val niznyayaWest = 65.58 ll 39.79
  val intsy = 65.98 ll 40.75
  val koyda = 66.51 ll 42.25
  val koydaEast = 66.41 ll 43.24
  val mezenMouth: LatLong = 66.07 ll 44.10
  val southEast: LatLong = 52 ll 45
   
  val polygonLL = PolygonLL(Polandia.kaliningrad, klaipeda, ziemupe, ovsi, kolka, kesterclems, jurmala, saulkrasti, parnu, lao, virtsu, noarootsi, paldiski, udria,
     krasnoselsky, piterland, nevaMouth, ladogaEast, svirMouth, onegaSouth, ustye, onegaEast, peschanoyeSouth,pudozhgorskiy, chelmuzhiEast,
     /** North coast */onezhsky, onegaRiver, pushlakhtaNorth, letniyNavolok, uyma, niznyayaWest, intsy, koyda, koydaEast, mezenMouth,southEast,
     Polandia.cenEast)

  val konigsberg = 54.71 ll 20.45
}

object Gotland extends EArea2("Gotland", 57.46 ll 18.47, Plain)
{ val southWest = 56.90 ll 18.12
  val west = 57.26 ll 18.09
  val tofta = 57.53 ll 18.10
  val hallshuk = 57.92 ll 18.73
  val east = 57.96 ll 19.35
   
  val polygonLL = PolygonLL(southWest, west, tofta, hallshuk, east)
}

object Saaremaa extends EArea2("Saaremaa", 58.43 ll 22.52, Plain)
{ val south = 57.91 ll 22.03
  val uudibe = 58.15 ll 22.21
  val west = 58.03 ll 21.82
  val northWest = 58.51 ll 21.91
  val nommkula = 58.68 ll 23.19
  val loetsa = 58.64 ll 23.34
  val east = 58.55 ll 23.40
  val tehumardi = 58.18 ll 22.25
   
  val polygonLL = PolygonLL(south, uudibe, west, northWest, nommkula, loetsa, east, tehumardi)
}

object Hiiumaa extends EArea2("Hiiumaa", 58.90 ll 22.63, Plain)
{ val west = 58.92 ll 22.04
  val north = 59.08 ll 22.65
  val sarve = 58.83 ll 23.05
  val southEast = 58.70 ll 22.67
  val southWest = 58.7 ll 22.49
  val polygonLL = PolygonLL(west, north, sarve, southEast, southWest)
}