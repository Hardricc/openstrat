/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg640._, prid.phex._

object EG640AppJs
{ @JSExport def main(args: Array[String]): Unit = { EGTerrOnlyGui(CanvasJs, Scen640All, HGView(115, 512, 25), false, false); () }
} 