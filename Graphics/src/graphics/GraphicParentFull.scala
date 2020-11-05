/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicParentFull extends GraphicAffineElem with GraphicActiveAffine
{ override type ThisT <: GraphicParentFull
  def cen: Pt2

  /** The type of children can probably be widened in the future. */
  def children: Arr[GraphicAffineElem]
  
  def topLeft: ThisT = this.slate(- boundingRect.topLeft)
  def topRight: ThisT = this.slate(- boundingRect.topRight)
  def bottomLeft: ThisT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: ThisT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[GraphicAffineElem]): ThisT
  def addElem(newElem: GraphicAffineElem): ThisT = addElems(Arr(newElem))
  def mutObj(newObj: Any): ThisT
}