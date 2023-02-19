/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** 20North, 0 East */
object EuropeNW extends EArea1("EuropeNW", 20 ll 0)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(Ireland, England, Scotland, Wales, Orkneys, OuterHebrides, Shetland, Faroe, JanMayen, Frankia, BelgLux, Alsace,
    Netherlands, Jutland, Zealand, Funen, Germania, Alpsland, Polandia, Baltland, Ukraine, SwedenSouth, SwedenNorth, Oland)
}

object EuropeEast extends EArea1("EuropeEast", 60 ll 60)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(RussiaSouth, Greece, BalkansWest, BalkansEast, Finlandia, Gotland, Saaremaa, Hiiumaa, Crimea, MarmaraSea, Peloponnese)
}

object EuropeSW extends EArea1("EuropeSw", 20 ll 0)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(FranceSouth, Iberia, Italy, ItalySouth)
}

object Mediterreanean extends EArea1("Mediterranean", 35.78 ll 18.73)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(Canarias, Sicily, Rhodes, Majorca, Crete, Cyprus, Sardina, Corsica)
}

object AfricaNorth extends EArea1("AfricaNorth", 18.19 ll 15)
{ import pAfrica._
  override val a2Arr: RArr[EArea2] = RArr(SaharaEast, EastAfricaSouth, SaharaWest, WestAfricaSouth)
}

/** The southern part of Africa below approximately 5 degrees north, roughly triangular in shape. */
object AfricaSouth extends EArea1("AfricanSouth", -16.14 ll 24.36)
{ import pAfrica. _
  override val a2Arr: RArr[EArea2] = RArr(LakeVictoria, LakeTanganyika, SouthAfrica, CentralAfricaWest, centralAfricaEast, Madagascar)
}

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object MiddleEast  extends EArea1("MiddleEast", 20.18 ll -0.65)
{ import middleEast._
  override val a2Arr = RArr(Sinai, Anatolia, Kurdistan, LakeVan, Armenia, Levant, Arabia, Persia, Iraq, LakeTuz)
}

object AmericasNorth extends EArea1("North America", 49 ll -100)
{
  import pAmericas._
  val ensenada: LatLong = 31.74 ll -116.73

  val lakes: RArr[EArea2] = RArr(LakeSuperior, LakeHuron, LakeMichigan, LakeErie, LakeOntario)
  override val a2Arr: RArr[EArea2] = lakes ++
    RArr(UsaWest, UsaEast, Alaska, NorthWestCanada, SouthWestCanada, CentralCanada, BanksIsland, MelvilleIsland, VictoriaIsland, PrinceWalesIsland, SouthamptonIsland,
      EastCanada, DevonIsland, BaffinIsland, NewFoundland, Baja, CentralAmerica, Cuba)
}

/** Asia East. North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object Asia extends EArea1("Asia", 60 ll 100)
{ import pAsia._
  override val a2Arr = RArr(seAsia, Korea, CEAsia, NeAsia, FeAsia, sakhalin, Hokkaido, japan, Taiwan, Kazak, RusNorth, India, SriLanka, Himalayas,
    CentralAsia, SevernyIsland)
}
object PolarNorth extends EArea1("NPole", 89.5 ll 0)
{ import pOceans._
  override val a2Arr: RArr[EArea2] = RArr(Greenland, Artic, Svalbard, Nordauslandet)
}

object AmericasSouth extends EArea1("SAmericas", -6.52 ll -62.28)
{ import pAmericas._
  override val a2Arr: RArr[EArea2] = RArr(SouthAmericaSouth, SouthAmericaWest, SouthAmericaEast)
}

object Australasia extends EArea1("Australasia", -23 ll 130)
{ import pOceans._
  override val a2Arr: RArr[EArea2] = RArr(Ssumatra, Borneo, Sulawesi, javaIsland, NewGuinea, Australia, NewZealandNIsland, NewZealandSIsland)
}

object PacificTop extends EArea1("Pacific", 0 ll 175)
{ import pOceans._
  override val a2Arr: RArr[EArea2] = RArr(Hawaii)
}