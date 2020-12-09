/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pGrid
import geom._

sealed class SqFace(val ordNum: Int, val angle: AngleVec, val xTile: Int, val yTile: Int)
object SFaceUp extends SqFace(0, 0.degs, 0, 2)
object SFaceUR extends SqFace(1, -45.degs, 1, 1)
object SFaceRt extends SqFace(2, -90.degs, 2, 0)
object SFaceDR extends SqFace(3, -135.degs, 1, -1)
object SFaceDn extends SqFace(4, 180.degs, 0, -2)
object SFaceDL extends SqFace(5, 135.degs, -1, -1)
object SFaceLt extends SqFace(6, 90.degs, -2, 0)
object SFaceUL extends SqFace(7, 45.degs, -1, 1)

object SqFace
{
  /** Needs to be renamed. */
  @deprecated def optFaceOld(orig: Cood, dirn: Cood): Option[SqFace] = dirn - orig match
  { case Cood(0, 2) => Some(SFaceUp)
    case Cood(2, 2) => Some(SFaceUR)
    case Cood(2, 0) => Some(SFaceRt)
    case Cood(2, -2) => Some(SFaceDR)
    case Cood(0, -2) => Some(SFaceDn)
    case Cood(-2, -2) => Some(SFaceDL)
    case Cood(-2, 0) => Some(SFaceLt)
    case Cood(-2, 2) => Some(SFaceUL)
    case _ => None
  }
}