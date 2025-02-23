/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg120._, eg160._, eg220._, eg320._, eg460._, eg640._, egmega._, eg13._, prid._, phex._, pParse._

/** object to launch EGrid basic Gui. */
object EGridLaunch extends GuiLaunchMore
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) =
    (cv => EGTerrOnlyGui(cv, EGrid80.scen0, EGrid80.scen0.gridSys.coordCen.view(), false), "JavaFx Eath 80KM Grid")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val scenNum: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)
    val isSepDraw: Boolean = sts.findSettingElse("sepDraw", false)
    val oview: ExcMon[HGView] = sts.findKeySetting[Int, HGView](scenNum)
    
    val scen: EScenBasic = scenNum match
    { case 0 => EGrid13.scen0
      case 1 => EGrid13.scen1
      case 2 => EGrid13.scen2
      case 3 => EGrid13.scen3
      case 4 => EGrid13.scen4
      case 5 => EGrid13.scen5
      case 6 => EGrid13.scen6
      case 7 => EGrid13.scen7
      case 8 => EGrid13.scen8
      case 9 => EGrid13.scen9
      case 10 => EGrid13.scen10
      case 11 => EGrid13.scen11

      case 12 => Scen13Europe
      case 13 => Scen13ChinaJapan
      case 14 => Scen13NorthAmerica
      case 15 => Scen13Africa
      case 16 => Scen13India
      case 17 => Scen13Atlantic
      case 18 => Scen13DateLine
      case 19 => Scen13All

      case 20 => EGridMega.scen0
      case 21 => EGridMega.scen1
      case 22 => EGridMega.scen2
      case 23 => EGridMega.scen3
      case 24 => EGridMega.scen4
      case 25 => EGridMega.scen5
      case 26 => EGridMega.scen6
      case 27 => EGridMega.scen7
      case 28 => EGridMega.scen8
      case 29 => EGridMega.scen9
      case 30 => EGridMega.scen10
      case 31 => EGridMega.scen11

      case 32 => ScenMegaEurope
      case 33 => ScenMegaChinaJapan
      case 34 => ScenMegaNorthAmerica
      case 35 => ScenMegaAfrica
      case 36 => ScenMegaIndia
      case 37 => ScenMegaAtlantic
      case 38 => ScenMegaDateLine
      case 39 => ScenMegaAll

      case 40 => EGrid640.scen0
      case 41 => EGrid640.scen1
      case 42 => EGrid640.scen2
      case 43 => EGrid640.scen3
      case 44 => EGrid640.scen4
      case 45 => EGrid640.scen5
      case 46 => EGrid640.scen6
      case 47 => EGrid640.scen7
      case 48 => EGrid640.scen8
      case 49 => EGrid640.scen9
      case 50 => EGrid640.scen10
      case 51 => EGrid640.scen11

      case 52 => Scen640Europe
      case 53 => Scen640ChinaJapan
      case 54 => Scen640NorthAmerica
      case 55 => Scen640Africa
      case 56 => Scen640India
      case 57 => Scen640Atlantic
      case 58 => Scen640DateLine
      case 59 => Scen640All

      case 60 => EGrid460.scen0
      case 61 => EGrid460.scen1
      case 62 => EGrid460.scen2
      case 63 => EGrid460.scen3
      case 64 => EGrid460.scen4
      case 65 => EGrid460.scen5
      case 66 => EGrid460.scen6
      case 67 => EGrid460.scen7
      case 68 => EGrid460.scen8
      case 69 => EGrid460.scen9
      case 70 => EGrid460.scen10
      case 71 => EGrid460.scen11

      case 72 => Scen460Europe
      case 73 => Scen460ChinaJapan
      case 74 => Scen460NorthAmerica
      case 75 => Scen460Africa
      case 76 => Scen460India
      case 77 => Scen460Atlantic
      case 78 => Scen460DateLine
      case 79 => Scen460All

      case 80 => EGrid320.scen0
      case 81 => EGrid320.scen1
      case 82 => EGrid320.scen2
      case 83 => EGrid320.scen3
      case 84 => EGrid320.scen4
      case 85 => EGrid320.scen5
      case 86 => EGrid320.scen6
      case 87 => EGrid320.scen7
      case 88 => EGrid320.scen8
      case 89 => EGrid320.scen9
      case 90 => EGrid320.scen10
      case 91 => EGrid320.scen11

      case 92 => Scen320Europe
      case 93 => Scen320ChinaJapan
      case 94 => Scen320NorthAmerica
      case 95 => Scen320Africa
      case 96 => Scen320India
      case 97 => Scen320Atlantic
      case 98 => Scen320DateLine
      case 99 => Scen320All

      case 100 => EGrid220.scen0
      case 101 => EGrid220.scen1
      case 102 => EGrid220.scen2

      case 105 => EGrid220.scen5

      case 108 => EGrid220.scen8
      case 109 => EGrid220.scen9
      case 110 => EGrid220.scen10
      case 111 => EGrid220.scen11

      case 112 => Scen220Europe

      case 114 => Scen220NorthAmerica
      case 115 => Scen220Africa
      case 116 => Scen220NorthAmerica2

      case 117 => Scen220Atlantic
      case 118 => BritReg220.regScen

      case 120 => EGrid160.scen0
      case 121 => EGrid160.scen1

      case 124 => EGrid160.scen4
      case 125 => EGrid160.scen5

      case 128 => EGrid160.scen8
      case 129 => EGrid160.scen9
      case 130 => EGrid160.scen10
      case 131 => EGrid160.scen11

      case 132 => Scen160Europe
      case 133 => Scen160ChinaJapan
      case 134 => Scen160NorthAmerica
      case 135 => Scen160Africa

      case 138 => Brit160.britScen

      case 140 => EGrid120.scen0
      case 141 => EGrid120.scen1
      case 142 => EGrid120.scen2

      case 151 => EGrid120.scen11

      case 152 => Scen120Europe

      case 155 => Scen120Africa

      case 160 => EGrid80.scen0
      case 161 => EGrid80.scen1
      case 172 => Scen80Europe
      case 173 => WesternFront.wFrontScen
      case _ => Scen460All
    }
    val view = oview.flatMap(v => ife(scen.gridSys.hCoordExists(v.hCoord), Succ(v), FailNotFound)).getElse(scen.gridSys.coordCen.view())
    (EGTerrOnlyGui(_, scen, view, isFlat, true, isSepDraw), scen.title --"Experimental" -- ife(isFlat, "Flat", "Globe") -- "JavaFx")
  }
}