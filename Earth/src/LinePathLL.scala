/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** A latitude-longitude line path. A quasi line path where the points are stored as points of latitude and longitude.Once the points are converted into a
 *  view, ie into pixel positions an actual polygon can be drawn or filled as desired. Do not create line paths that span an arc of greater than 90
 *  degrees as this may break the algorithms. */
final class LinePathLL(val unsafeArray: Array[Double]) extends AnyVal with LatLongSeqSpec with LinePathDbl2[LatLong]
{ override type ThisT = LinePathLL
  override type PolygonT = PolygonLL
  override def typeStr: String = "LinePathLL"
  override def fromArray(array: Array[Double]): LinePathLL = new LinePathLL(array)
  override def polygonFromArray(array: Array[Double]): PolygonLL = new PolygonLL(array)

  /** Aliased by concatReverse. Concatenate the reversed elements of the operand [[LinePathLL]] returning a new [[LinePathLL]]. An immutable
   *  reverse append. The ++ characters indicate concatenate multiple elements. The / character indicates a reverse operation. The purpose of the
   *  concatenate reversed methods is for [[PolygonLL]]s with shared [[LinePathLL]]s. To allow both polygons to keep their points with the clockwise
   *  convention. */
  inline def ++/ (operand: LinePathLL): LinePathLL = concatReverse(operand)

  /** Concatenate the reversed elements of the operand [[LinePathLL]] returning a new [[LinePathLL]]. An immutable append. Aliased by ++/ operator.
   *  The purpose of the concatenate reversed methods is for [[PolygonLL]]s with shared [[LinePathLL]]s. To allow both polygons to keep their points
   *  with the clockwise convention. */
  def concatReverse (operand: LinePathLL): LinePathLL =
  { val res = LinePathLL.uninitialised(ssLength + operand.ssLength)
    ssIForeach{ (i, ll) => res.setElemUnsafe(i, ll) }
    operand.reverse.ssIForeach(ssLength) { (i, ll) => res.setElemUnsafe(i, ll) }
    res
  }

  /** Reverses this [[LinePathLL]] and closes it returning a [[PolygonLL]] */
  def reverseClose: PolygonLL = new PolygonLL(unsafeReverseArray)

  /** Alias for concatReverseTailInitClose. Concatenates the reversed elements of the operand [[LinePathLL]] minus the head and the last element of
   *  the operand. And then closes into a [[PolygonLL]]. */
  def +/--!(operand: LinePathLL): PolygonLL = new PolygonLL(arrayAppendTailInit(operand.reverse))

  /** Concatenates the reversed elements of the operand [[LinePathLL]] minus the head and the last element of the operand. And then closes into a
   *  [[PolygonLL]]. */
  def concatReverseTailInitClose(operand: LinePathLL): PolygonLL = new PolygonLL(arrayAppendTailInit(operand.reverse))

  /** Creates a new backing Array[Double] with the elements of this [[LinePathLL]], with the elements of the operand
   * minus the head and last element of the operand. */
  def arrayAppendTailInit(operand: LinePathLL): Array[Double] =
  { val array = new Array[Double]((ssLength + (operand.ssLength - 2).max(0)) * 2)
    unsafeArray.copyToArray(array)
    iUntilForeach(2, operand.unsafeLength - 2) { i => array(ssLength * 2 + i - 2) = operand.unsafeArray(i) }
    array
  }

  def vertsNum: Int = ssLength

  /** Performs the side effecting function on the [[LatLong]] value of each vertex. */
  def vertsForeach[U](f: LatLong => U): Unit = ssForeach(f)

  def vertsIForeach[U](f: (Int, LatLong) => U): Unit = ssIForeach(f)
}

object LinePathLL extends Dbl2SeqLikeCompanion[LatLong, LinePathLL]
{ override def fromArray(array: Array[Double]): LinePathLL = new LinePathLL(array)

  /** Apply factory method for creating Arrs of [[Dbl2Elem]]s. */
  override def apply(elems: LatLong*): LinePathLL =
  {
    val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.unsafeArray(count * 2) = elems(count).dbl1
      res.unsafeArray(count * 2 + 1) = elems(count).dbl2
      count += 1
    }
    res
  }
}