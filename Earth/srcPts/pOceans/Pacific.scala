/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, WTile._

object Hawaii extends EArea2("Hawaii", 20.85 ll -156.92, plain)
{
  val sHawaii = 18.91 ll -155.68
  val nwHawaii = 21.57 ll -158.28
  val nHawaii = 21.71 ll -157.97
  val hana = 20.75 ll -155.98
  val eHawii = 19.51 ll -154.80

  override val polygonLL: PolygonLL = PolygonLL( sHawaii, nwHawaii, nHawaii, hana, eHawii)
}