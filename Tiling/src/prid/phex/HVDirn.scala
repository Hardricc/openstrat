/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** [[HVert]] direction of offset towards [[HCen]]. These objects should not be confused with [[HStep]]s */
sealed trait HVDirn extends Int1Elem
{ /** The delta in R to the [[HCen]] from an [[HCoord]]. */
  def dCenR: Int

  /** The delta in C to the [[HCen]] from an [[HCoord]]. */
  def dCenC: Int

  def dVertR: Int
  def dVertC: Int

  def int1: Int

  def opposite: HVDirn

  def corner(hv: HVert): Int
}

object HVDirn
{
  def fromInt(inp: Int): HVDirn = inp match
  { case 0 => HVExact
    case 1 => HVUR
    case 2 => HVDR
    case 3 => HVDn
    case 4 => HVDL
    case 5 => HVUL
    case 6 => HVUp
    case n => excep(s"$n is an invalid Int value for an HVDirn.")
  }
}

/** Hex Vert offset of none. */
object HVExact extends HVDirn
{ def dCenR: Int = 0
  def dCenC: Int = 0
  override def int1: Int = 0
  override def opposite: HVDirn = HVExact
  override def dVertR: Int = 0
  override def dVertC: Int = 0

  override def corner(hv: HVert): Int = ife(hv.hexIsUp, 3, 3)
}

/** Hex Vert Up offset. */
object HVUp extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = 0
  override def int1: Int = 6
  override def opposite: HVDirn = HVDn
  override def dVertR: Int = 2
  override def dVertC: Int = 0
  override def corner(hv: HVert): Int = ife(hv.hexIsUp, 3, 0)
}

/** Hex Vert Up Right offset. */
object HVUR extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = 2
  override def int1: Int = 1
  override def opposite: HVDirn = HVDL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
  override def corner(hv: HVert): Int = ife(hv.hexIsUp, 4, 1)
}

object HVDR extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = 2
  override def int1: Int = 2
  override def opposite: HVDirn = HVUL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
  override def corner(hv: HVert): Int = ife(hv.hexIsUp, 2, 5)
}

object HVDn extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = 0
  override def int1: Int = 3
  override def opposite: HVDirn = HVUp
  override def dVertR: Int = -2
  override def dVertC: Int = 0
  override def corner(hv: HVert): Int = ife(hv.hexIsUp, 3, 0)
}

object HVDL extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = -2
  override def int1: Int = 4
  override def opposite: HVDirn = HVUR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
  override def corner(hv: HVert): Int = ife(hv.hexIsUp, 4, 1)
}

object HVUL extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = -2
  override def int1: Int = 5
  override def opposite: HVDirn = HVDR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
  override def corner(hv: HVert): Int = ife(hv.hexIsUp, 3, 3)
}

class HVDirnArr(val unsafeArray: Array[Int]) extends Int1Arr[HVDirn]
{ override type ThisT = HVDirnArr
  override def typeStr: String = "HDirnArr"
  override def newElem(intValue: Int): HVDirn = HVDirn.fromInt(intValue)
  override def fromArray(array: Array[Int]): HVDirnArr = new HVDirnArr(array)
  override def fElemStr: HVDirn => String = _.toString
}

object HVDirnArr extends Int1SeqLikeCompanion[HVDirn, HVDirnArr]
{ override def fromArray(array: Array[Int]): HVDirnArr = new HVDirnArr(array)
}