/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Extension methods for [[SeqSpec]]s sequence specified classes. */
class SeqSpecExtensions[A](val thisSeqSpec : SeqSpec[A])
{
  /** Map this collection of data elements to [[LinePathLike]] class of type BB. */
  def mapLinePath[B <: ValueNElem, BB <: LinePathLike[B]](f: A => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.uninitialised(thisSeqSpec.numElems)
    thisSeqSpec.iForeach((i, a) => build.indexSet(res, i, f(a)))
    res
  }

  /** Map this collection of data elements to [[PolygonLike]] class of type BB. */
  def mapPolygon[B <: ValueNElem, BB <: PolygonLike[B]](f: A => B)(implicit build: PolygonLikeBuilderMap[B, BB]): BB =
  { val res = build.uninitialised(thisSeqSpec.numElems)
    thisSeqSpec.iForeach((i, a) => build.indexSet(res, i, f(a)))
    res
  }

  def toLinePath[AA <: LinePathLike[A]](implicit build: LinePathBuilder[A, AA]): AA =
  { val res = build.uninitialised(thisSeqSpec.numElems)
    thisSeqSpec.iForeach((i, a) => build.indexSet(res, i, a))
    res
  }

  def toPolygon[AA <: PolygonLike[A]](implicit build: PolygonLikeBuilderMap[A, AA]): AA =
  { val res = build.uninitialised(thisSeqSpec.numElems)
    thisSeqSpec.iForeach((i, a) => build.indexSet(res, i, a))
    res
  }
}

class SequExtensions[A](val al : Sequ[A])
{
  /** Map this collection of data elements to [[LinePathLike]] class of type BB. */
  def mapLinePath[B <: ValueNElem, BB <: LinePathLike[B]](f: A => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.uninitialised(al.length)
    al.iForeach((i, a) => build.indexSet(res, i, f(a)))
    res
  }

  /** Maps the elements of tbis sequence to [[PolygonLike]] vertices, returning a [[PolygonLike]] class of type BB. */
  def mapPolygon[B <: ValueNElem, BB <: PolygonLike[B]](f: A => B)(implicit build: PolygonLikeBuilderMap[B, BB]): BB =
  { val res = build.uninitialised(al.length)
    al.iForeach((i, a) => build.indexSet(res, i, f(a)))
    res
  }

  /** FlatMaps the elements of tbis sequence to [[PolygonLike]] vertices, returning a [[PolygonLike]] class of type BB. */
  def flatMapPolygon[B, BB <: PolygonLike[B]](f: A => SeqLike[B])(implicit build: PolygonLikeFlatBuilder[B, BB]): BB =
  { val buff = build.newBuff()
    al.foreach(a => build.buffGrowSeqLike(buff, f(a)))
    build.buffToSeqLike(buff)
  }

  /** FlatMaps with index the elements of tbis sequence to [[PolygonLike]] vertices, returning a [[PolygonLike]] class of type BB. */
  def iFlatMapPolygon[B, BB <: PolygonLike[B]](f: (Int, A) => SeqLike[B])(implicit build: PolygonLikeFlatBuilder[B, BB]): BB = {
    val buff = build.newBuff()
    var i = 0
    al.foreach{ a => build.buffGrowSeqLike(buff, f(i, a)); i += 1 }
    build.buffToSeqLike(buff)
  }

  /** Converts this collection of data elements to [[LinePathLike]] class of type BB. */
  def toLinePath[AA <: LinePathLike[A]](implicit build: LinePathBuilder[A, AA]): AA =
  { val res = build.uninitialised(al.length)
    al.iForeach((i, a) => build.indexSet(res, i, a))
    res
  }

  /** Converts this collection of data elements to [[PolygonLike]] class of type BB. */
  def toPolygon[AA <: PolygonLike[A]](implicit build: PolygonLikeBuilderMap[A, AA]): AA =
  { val res = build.uninitialised(al.length)
    al.iForeach((i, a) => build.indexSet(res, i, a))
    res
  }
}