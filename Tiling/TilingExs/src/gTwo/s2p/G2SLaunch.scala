/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package s2p
import pgui._, pParse._, prid._, psq._, gPlay._

/** Settings for the sole GUI player. */
case class G2SGuiSettings(view: SGView, counterSet: RArr[Counter])

object G2SLaunch extends GuiLaunchMore
{  override def settingStr: String = "G2Sq"

  override def default: (CanvasPlatform => Any, String) =
    (G2SGui(_, G2SGame(G2SScen1, G2SScen1.counterSet), G2SGuiSettings(G2SScen1.defaultView(), G2SScen1.counterSet)), "JavaFx Game Two Square")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMonOld[Int] = sts.findSettingOld[Int]("scen")
    val num: Int = oScen.getElse(1)
    val scen: G2SqScen = num match
    { case 1 => G2SScen1
      case 2 => G2SScen2
      case 3 => G2SScen3
      case _ => G2SScen1
    }

    val oSetts: EMonOld[AssignMemExpr] = sts.findIntSettingExprOld(num)
    val sts2: EMonOld[RArr[Statement]] = oSetts.map(_.toStatements)
    val pls1 = sts2.findSettingIdentifierArrOld("counters")
    val plAll = scen.counterSet
    val pls2 = pls1.map { arrA => arrA.optMap(st => plAll.find(_.charStr == st)) }
    val pls3 = pls2.getElse(plAll)
    val view: SGView = sts2.findTypeElse(scen.gridSys.defaultView())
    val settings = G2SGuiSettings(view, pls3)
    val game = G2SGame(scen, pls3)
    (G2SGui(_, game, settings), "JavaFx Game Two Squares")
  }
}
