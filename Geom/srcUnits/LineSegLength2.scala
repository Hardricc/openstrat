/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A line segment whose coordinates are specified in [[Length]] units. */
trait LineSegLength2[VT <: PtLength2] extends LineSegLike[VT]
{ def xStart: Length
  def yStart: Length
  def xEnd: Length
  def yEnd: Length
  def startPt: VT
  def endPt: VT

  /** Translates this line segment in 2 [[Length]] dimensions space. */
  def slate(operand: VecPtLength2): LineSegLength2[VT]

  /** Translates this line segment in 2 [[Length]] dimensions space. */
  def slate(xOperand: Length, yOperand: Length): LineSegLength2[VT]

  /** Scales this line segment in 2 [[Length]] dimensions space. */
  def scale(operand: Double): LineSegLength2[VT]

  /** Divides by a [[Length]] to produce a scalar [[LineSeg]]. */
  def / (operand: Length): LineSeg

  def xStartFemtometresNum: Double
  def yStartFemtometresNum: Double
  def xEndFemtometresNum: Double
  def yEndFemtometresNum: Double
  def xStartPicometresNum: Double
  def yStartPicometresNum: Double
  def xEndPicometresNum: Double
  def yEndPicometresNum: Double
  def xStartMetresNum: Double
  def yStartMetresNum: Double
  def xEndMetresNum: Double
  def yEndMetresNum: Double
  def xStartKilometresNum: Double
  def yStartKilometresNum: Double
  def xEndKilometresNum: Double
  def yEndKilometresNum: Double
}

object LineSegLength2
{
  implicit def slateEv: SlateLength[LineSegLength2[PtLength2]] = (obj, delta) => obj.slate(delta)
  implicit def slateXYEv: SlateLengthXY[LineSegLength2[PtLength2]] = (obj, dx, dy) => obj.slate(dx, dy)
  implicit def scaleEv: Scale[LineSegLength2[PtLength2]] = (obj, operand) => obj.scale(operand)
}