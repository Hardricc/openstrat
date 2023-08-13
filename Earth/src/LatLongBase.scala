/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

trait LatLongBase
{
  def latMilliSecs: Double

  def longMilliSecs: Double

  def name1: String = "lat"

  def name2: String = "long"

  @inline final def lat: Latitude = Latitude.milliSecs(latMilliSecs)

  @inline final def long: Longitude = Longitude.milliSecs(longMilliSecs)

  @inline final def latDegs: Double = latMilliSecs.milliSecsToDegs

  @inline final def longDegs: Double = longMilliSecs.milliSecsToDegs

  @inline final def latRadians: Double = latMilliSecs.milliSecsToRadians

  @inline final def longRadians: Double = longMilliSecs.milliSecsToRadians

  @inline final def latSecs: Double = latMilliSecs / 1000

  @inline final def longSecs: Double = longMilliSecs / 1000

  /** The sine of the longitude, where East is a positive longitude. */
  @inline final def longSine: Double = longRadians.sine

  /** The cosine of the longitude, where East is a positive longitude. */
  @inline final def longCos: Double = longRadians.cos

  /** The sine of the latitude, where North is a positive latitude. */
  @inline final def latSine: Double = latRadians.sine

  /** The cosine of the latitude, where North is a positive latitude. */
  @inline final def latCos: Double = latRadians.cos

  @inline final def latMins: Double = latSecs / 60

  @inline final def longMins: Double = longSecs / 60

  def latLetter: String = ife(latRadians < 0, "S", "N")

  def longLetter: String = ife(longRadians < 0, "W", "E")

  def latDegStr: String = latDegs.abs.str2 + latLetter

  def longDegStr: String = longDegs.abs.str2 + longLetter

  def polarRadius: Length = EarthPolarRadius

  def equatorialRadius: Length = EarthEquatorialRadius
}