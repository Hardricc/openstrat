/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A line segment whose coordinates are specified in [[Length]] units. */
trait LineSegLength2[VT <: PtLength2] extends LineSegLike[VT]
{ def xStart: Length
  def yStart: Length
  def xEnd: Length
  def yEnd: Length
  def startPt: VT
  def endPt: VT

  /** Divides by a [[Length]] to produce a scalar [[LineSeg]]. */
  def / (operand: Length): LineSeg

  def xStartMetresNum: Double
  def yStartMetresNum: Double
  def xEndMetresNum: Double
  def yEndMetresNum: Double
  def xStartKilometresNum: Double
  def yStartKilometresNum: Double
  def xEndKilometresNum: Double
  def yEndKilometresNum: Double
}