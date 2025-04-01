/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait RectLen2[+VT <: PtLen2] extends RectangleLen2[VT]
{ override def slate(operand: VecPtLen2): RectLen2[VT]
  override def slate(xOperand: Length, yOperand: Length): RectLen2[VT]
  override def slateX(xOperand: Length): RectLen2[VT]
  override def slateY(yOperand: Length): RectLen2[VT]
  override def scale(operand: Double): RectLen2[VT]
  override def mapGeom2(operand: Length): Rect
}

trait RectLen2Graphic extends RectangleLen2Graphic
{
  override def shape: RectLen2[PtLen2]
}

trait RectLen2Fill extends RectangleLen2Fill