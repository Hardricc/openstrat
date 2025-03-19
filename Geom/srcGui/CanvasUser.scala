/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._, collection.mutable.ArrayBuffer

/** So the descendant classes need to set the canv.mouseup field to use the mouse and its equivalents. */
trait CanvasUser
{ def title: String
  val canv: CanvasPlatform

  /** This reverses the order of the GraphicActive List. Method paints objects to screen as side effect. */
  def paintObjs(movedObjs: RArr[Graphic2Elem]): RArr[GraphicActive] =
  {
    val activeBuff: ArrayBuffer[GraphicActive] = Buffer()

    movedObjs.foreach {
      case el: GraphicActiveOld => activeBuff += el
      case _ =>
    }

    movedObjs.foreach(processGraphic)

    def processGraphic(el: Graphic2Elem): Unit = el match
    {
      case el: GraphicClickable => activeBuff += el
      case sc: ShapeCompound => { sc.rendToCanvas(canv); sc.children.foreach(processGraphic) }
      //case cs: GraphicParentOld => canv.rendElems(cs.children)
      case cpf: GraphicParentFull => canv.rendElems(cpf.children)
      case ce: Graphic2Elem => ce.rendToCanvas(canv)
      //s case nss: UnScaledShape => canv.rendElems(nss.elems.slate(nss.referenceVec))
      //case v =>
    }
    activeBuff.toReverseRefs
  }
   
  def refresh(): Unit   
  canv.resize = () => refresh()   
}