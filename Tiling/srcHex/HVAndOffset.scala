/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** An [[HVert]] and an offset. The Offset of from the [[HVert]] measured in an offset towards a neighbouring [[HCen]] or [[HVert]]. */
class HVAndOffset(val int1: Int, val int2: Int, val int3: Int) extends Int3Elem
{ /** The r or row component of the [[HVert]]. */
  inline def r: Int = int1

  /** The c or column component of the [[HVert]]. */
  inline def c: Int = int2

  override def toString: String = "HVAndOffset".appendParenthSemis(int1.str, int2.str, hvDirn.toString, magnitude.toString)

  /** The [[HVert]]. */
  def vert: HVert = HVert(r, c)

  def hvOffset: HVOffset = HVOffset.fromInt(int3)

  def magnitude: Int = hvOffset.magnitude

  def hvDirn: HVDirnOpt = hvOffset.hvDirn

  def vHigh: Boolean = None match
  { case _ if r.div4Rem1 & c.div4Rem0 => true
    case _ if r.div4Rem3 & c.div4Rem2 => true
    case _ if r.div4Rem1 & c.div4Rem2 => false
    case _ if r.div4Rem3 & c.div4Rem0 => false
    case _ => excep(s"r = $r, c = $c Invalid valuse for HVert.")
  }

  def target: HCoord = hvDirn match
  { case HVUp if vHigh => HVertLow(r + 2, c)
    case HVUp => HCen(r + 1, c)
    case HVUR if vHigh => HCen(r + 1, c + 2)
    case HVUR => HVertHigh(r, c + 2)
    case HVDR if vHigh => HVertLow(r, c + 2)
    case HVDR => HCen(r - 1, c + 2)
    case HVDn if vHigh => HCen(r - 1, c)
    case HVDn => HVertHigh(r - 2, c)
    case HVDL if vHigh => HVertLow(r, c - 2)
    case HVDL => HCen(r - 1, c - 2)
    case HVUL if vHigh => HCen(r + 1, c - 2)
    case HVUL => HVertHigh(r, c - 2)
    case HVExact => vert
  }

  /** The [[HCen]] the [[HVDirn]] points to if it is coming from the correct type of [[HVert]]. */
  def hCen: HCen = HCen(r + hvDirn.dCenR, c + hvDirn.dCenC)

  /** Not sure what this is. */
  def hVert2: HVert = HVert(r + hvDirn.dVertR, c + hvDirn.dVertC)

  /** Not sure what this is. */
  def hVert3: HVert = HVert(r - hvDirn.dVertR, c - hvDirn.dVertC)

  def isCenDirn: Boolean = hvDirn match {
    case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
    case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2  => true
    case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
    case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem2 => true
    case _ => false
  }

  /** Converts this offset [[HVert]] to [[Pt2]]. */
  def toPt2(f: HCoord => Pt2)(implicit hSys: HGridSys): Pt2 = hvDirn match
  { case HVExact => f(vert)
    case hd: HVDirn => hSys.vertToCoordFind(vert, hd) match
    { case Some(hc2) =>
      { val p2 = f(hc2)
        val x = ((16 - magnitude) * f(vert).x + magnitude * p2.x) / 16
        val y = ((16 - magnitude) * f(vert).y + magnitude * p2.y) / 16
        Pt2(x, y)
      }
      case _ => f(vert)
    }
  }
}

  /** Companion object for [[HVAndOffset]] class contains factory apply and none methods. End users should rarely need to use the class constructor
 * directly. */
object HVAndOffset
{
  def apply(hVert: HVert, hvDirn: HVDirnOpt, offset: Int): HVAndOffset = apply(hVert.r, hVert.c, hvDirn, offset)

  def apply(r: Int, c: Int, hvDirn: HVDirnOpt, magnitude: Int): HVAndOffset =
  { val magnitude2 = ife(magnitude < 0, -magnitude, magnitude)
    val dirn2 = ife(magnitude < 0, hvDirn.opposite, hvDirn)

    val isCenDirn: Boolean = hvDirn match {
      case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
      case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2 => true
      case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
      case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem0 => true
      case _ => false
    }

    val magnitude3 = magnitude match
    { case m if /*isCenDirn & */ m > 7 => { deb(s"$m > 7 ofr HCen dirn"); m.min(7) }
     // case m if !isCenDirn & m > 3 => { deb(s"$m > 3 for HVert dirn"); m.min(3) }
      case m => m
    }

    val hVertOffset = HVOffset(dirn2, magnitude3)
    new HVAndOffset(r, c, hVertOffset)
  }

  def none(r: Int, c: Int) = new HVAndOffset(r, c, 0)
  def none(hVert: HVert) = new HVAndOffset(hVert.r, hVert.c, 0)

  implicit val sarrMapBuilderImplicit: Int3ArrMapBuilder[HVAndOffset, HVAndOffsetArr]  = new Int3ArrMapBuilder[HVAndOffset, HVAndOffsetArr]
  { type BuffT = HVAndOffsetBuff
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVAndOffsetBuff = new HVAndOffsetBuff(buffer)
    override def fromIntArray(array: Array[Int]): HVAndOffsetArr = new HVAndOffsetArr(array)
  }

  /** Implicit type class instance / evidence for the [[HVAndOffset]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonBuildEv: PolygonHVAndOffsetMapBuilder = new PolygonHVAndOffsetMapBuilder

  /** Implicit type class instance / evidence for the [[HVAndOffset]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonFlatBuildEv: PolygonHVAndOffsetFlatBuilder = new PolygonHVAndOffsetFlatBuilder
}

trait HVAndOffsetSeqLike extends Int3SeqLike[HVAndOffset]
{
  final override def newElem(int1: Int, int2: Int, int3: Int): HVAndOffset = new HVAndOffset(int1, int2, int3)
  final override def fElemStr: HVAndOffset => String = _.toString
}

class HVAndOffsetArr(val unsafeArray: Array[Int]) extends HVAndOffsetSeqLike with Int3Arr[HVAndOffset]
{ override type ThisT = HVAndOffsetArr
  override def typeStr: String = "HVAndOffsetArr"
  override def fromArray(array: Array[Int]): HVAndOffsetArr = new HVAndOffsetArr(array)
}

object HVAndOffsetArr extends Int3SeqLikeCompanion [HVAndOffset, HVAndOffsetArr]
{ override def fromArray(array: Array[Int]): HVAndOffsetArr = new HVAndOffsetArr(array)
}

/** Specialised [[Buff]] class for [[HVAndOffset]]s. The [[HVert]] with offset class. */
class HVAndOffsetBuff(val unsafeBuffer: ArrayBuffer[Int]) extends Int3Buff[HVAndOffset]
{ override type ThisT = HVAndOffsetBuff
  override type ArrT = HVAndOffsetArr
  override def typeStr: String = "HVAndoffsetBuff"
  override def newElem(int1: Int, int2: Int, int3: Int): HVAndOffset = new HVAndOffset(int1, int2, int3)
}