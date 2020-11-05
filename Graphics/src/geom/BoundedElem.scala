/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedElem extends GeomElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect

  /** The width of the [[BoundingRect]] of this object. */
  def boundingWidth: Double = boundingRect.width

  def boundingHeight: Double = boundingRect.height
  def boundingTR: Pt2 = boundingRect.topRight
  def boundingBR: Pt2 = boundingRect.bottomRight
  def boundingTL: Pt2 = boundingRect.topLeft
  def boundingBL: Pt2 = boundingRect.bottomLeft
  def xCen: Double
  def yCen: Double
  def cen: Pt2

  def slateTo(newCen: Pt2): BoundedElem
}

/** Type class for performing a 2D translation on an object of type T that moves the centre of the new object to the given position. */
trait SlateTo[T]
{
  /** Translate an object of type T such that the centre of the new object is given by the new position. */
  def slateTTo(obj: T, newCen: Pt2): T
}

class SlateToExtensions[A](thisA: A, ev: SlateTo[A])
{
  def slateTo(newCen: Pt2): A = ev.slateTTo(thisA, newCen)
}