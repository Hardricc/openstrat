/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, peri._, prid.phex._

@JSExportTopLevel("PeriJsApp")
object PeriJsApp
{
  @JSExport def main(): Unit = { PeriGui(CanvasJs, PeriScen1, HGView(102, 1536, 24)); () }
} 