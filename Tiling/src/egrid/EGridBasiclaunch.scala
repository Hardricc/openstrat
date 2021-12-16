/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg320._

/** object to launch EGrid basic Gui. */
object EGridBasicLaunch extends GuiLaunchStd
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) = (cv => EGridGui(cv, EGrid80Km.scen1), "JavaFx Eath 80KM Grid")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match {
    case 1 => (cv => EGridGui(cv, EGrid80Km.scen1), "JavaFx EGrid80Km.scen1")
    case 2 => (cv => EGridGui(cv, EGrid320Km.scen1), "JavaFx EGrid320Km.scen1")
    case _ => (cv => EGridGui(cv, EGrid80Km.scen1), "JavaFx EGrid80Km.scen1")
  }
}