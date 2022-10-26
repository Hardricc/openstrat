/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class SeqDefExtensions[A](val al : SeqSpec[A])
{
  /** Map this collection of data elements to [[LinePathLike]] class of type BB. */
  def mapLinePath[B <: ElemValueN, BB <: LinePathLike[B]](f: A => B)(implicit build: LinePathBuilder[B, BB]): BB =
  {
    val res = build.newLinePath(al.ssLength)
    al.ssIForeach((i, a) => build.arrSet(res, i, f(a)))
    res
  }

  /** Map this collection of data elements to [[PolygonLike]] class of type BB. */
  def mapPolygon[B <: ElemValueN, BB <: PolygonLike[B]](f: A => B)(implicit build: PolygonLikeMapBuilder[B, BB]): BB =
  { val res = build.newPolygonT(al.ssLength)
    al.ssIForeach((i, a) => build.arrSet(res, i, f(a)))
    res
  }

  def toLinePath[AA <: LinePathLike[A]](implicit build: LinePathBuilder[A, AA]): AA =
  { val res = build.newLinePath(al.ssLength)
    al.ssIForeach((i, a) => build.arrSet(res, i, a))
    res
  }

  def toPolygon[AA <: PolygonLike[A]](implicit build: PolygonLikeMapBuilder[A, AA]): AA =
  { val res = build.newPolygonT(al.ssLength)
    al.ssIForeach((i, a) => build.arrSet(res, i, a))
    res
  }
}

class SequExtensions[A](val al : Sequ[A])
{
  /** Map this collection of data elements to [[LinePathLike]] class of type BB. */
  def mapLinePath[B <: ElemValueN, BB <: LinePathLike[B]](f: A => B)(implicit build: LinePathBuilder[B, BB]): BB =
  {
    val res = build.newLinePath(al.length)
    al.iForeach((i, a) => build.arrSet(res, i, f(a)))
    res
  }

  /** Map this collection of data elements to [[PolygonLike]] class of type BB. */
  def mapPolygon[B <: ElemValueN, BB <: PolygonLike[B]](f: A => B)(implicit build: PolygonLikeMapBuilder[B, BB]): BB =
  { val res = build.newPolygonT(al.length)
    al.iForeach((i, a) => build.arrSet(res, i, f(a)))
    res
  }

  def toLinePath[AA <: LinePathLike[A]](implicit build: LinePathBuilder[A, AA]): AA =
  { val res = build.newLinePath(al.length)
    al.iForeach((i, a) => build.arrSet(res, i, a))
    res
  }

  def toPolygon[AA <: PolygonLike[A]](implicit build: PolygonLikeMapBuilder[A, AA]): AA =
  { val res = build.newPolygonT(al.length)
    al.iForeach((i, a) => build.arrSet(res, i, a))
    res
  }
}