/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

/** Optional data layer for Hex grid systems. */
class HCenOptHStepLayer[A](val arrayInt: Array[Int], val arrayA: Array[A])(implicit val ct: ClassTag[A], val gridSys: HGridSys)
{
  def numCens: Int = arrayA.length
  def step(hc: HCen): HStepOpt = HStepOpt.fromInt(arrayInt(gridSys.layerArrayIndex(hc)))
  def index(hc: HCen): Int = gridSys.layerArrayIndex(hc)

  def setSome(hc: HCen, step: HStepOpt, value: A): Unit =
  { arrayInt(index(hc)) = step.int1
    arrayA(index(hc)) = value
  }

  def mapAcc: HCenAccLayer[A] =
  {
    val acc = HCenAccLayer[A]()
    gridSys.foreach{origin =>
      val index = gridSys.layerArrayIndex(origin)
      val optA = arrayA(index)
      if (optA != null)
      { val optTarget: Option[HCen] = gridSys.stepOptEndFind(origin, step(origin))
        optTarget.foreach{ target => acc.append(target, origin, arrayA(index)) }
      }
    }
    acc
  }
}

object HCenOptHStepLayer
{ /** Factory apply method for [[HCenOptHStepLayer]]. */
  def apply[A](gSys: HGridSys)(implicit ct: ClassTag[A]): HCenOptHStepLayer[A] = new HCenOptHStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))(ct, gSys)

  /** Factory apply method for [[HCenOptHStepLayer]]. */
  def apply[A]()(implicit ct: ClassTag[A], gSys: HGridSys): HCenOptHStepLayer[A] = new HCenOptHStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))
}