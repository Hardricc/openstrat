/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Array[Double] based collection class for a LinePath. Conversion to and from the Vec2s class and Polygon class should not entail a runtime
 *  cost. */
final class LinePath(val unsafeArray: Array[Double]) extends AffinePreserve with Pt2SeqSpec with LinePathDbl2[Pt2]
{ override type ThisT = LinePath
  override type PolygonT = Polygon
  override def typeStr: String = "LinePath"
  def fromArray(array: Array[Double]): LinePath = new LinePath(array)
  override def polygonFromArray(array: Array[Double]): Polygon = new PolygonGen(array)
  @inline def xStart: Double = unsafeArray(0)
  @inline def yStart: Double = unsafeArray(1)
  @inline def pStart: Pt2 = Pt2(xStart, yStart)

  def ptsTrans(f: Pt2 => Pt2): LinePath =  new LinePath(arrTrans(f))

  def vertsTailForeach(f: (Double, Double) => Unit): Unit =
  { var count = 1
    while (count < numVerts)
    { f(unsafeArray(count *2), unsafeArray( count * 2 + 1))
      count += 1      
    }
  }
  
  def draw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinePathDraw = LinePathDraw(this, lineWidth, colour)

  /** Closes the line Path into a Polygon, by mirroring across the yAxis. This is useful for describing symmetrical across the y Axis polygons, with
   * the minimum number of points. The implementation is efficient, but is logical equivalent of myVec2s ++ myVec2s.reverse.negX. */
  def yMirrorClose: PolygonGen =
  { val acc: Array[Double] = appendArray(ssLength)
    var count = unsafeLength

    ssReverseForeach { orig =>
      acc(count) = - orig.x
      acc(count + 1) = orig.y
      count += 2
    }
    new PolygonGen(acc)
  }
}

/** Companion object for LinePath contains apply factory object and Persist type class instance. */
object LinePath extends Dbl2SeqLikeCompanion[Pt2, LinePath]
{ override def fromArray(array: Array[Double]): LinePath = new LinePath(array)

  implicit val persistImplicit: Dbl2SeqDefPersist[Pt2, LinePath] = new Dbl2SeqDefPersist[Pt2, LinePath]("LinePath")
  { override def fromArray(value: Array[Double]): LinePath = new LinePath(value)
  }
}