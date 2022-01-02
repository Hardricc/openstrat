/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This class needs replacing. */
class CurveSegDists(val unsafeArray: Array[Double]) extends AnyVal with ArrDbl7s[DistCurveTail]
{ type ThisT = CurveSegDists
  override def unsafeFromArray(array: Array[Double]): CurveSegDists = new CurveSegDists(array)
  override def typeStr: String = "CurvedSegDists"
  override def dataElem(iMatch: Double, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): DistCurveTail =
    new DistCurveTail(iMatch, d1, d2, d3, d4, d5, d6)
  override def fElemStr: DistCurveTail => String = _.toString
}

object CurveSegDists extends DataDbl7sCompanion[DistCurveTail, CurveSegDists]
{ /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  override def fromArrayDbl(array: Array[Double]): CurveSegDists = new CurveSegDists(array)
}