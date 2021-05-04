/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

class HSideArr[A <: AnyRef](val unsafeArr: Array[A])
{

}

/** Boolean data corresponding to the sides of a hex grid, stored using an underlying Array[Boolean]. Thhese classes should be created, initalised and
 *  used using an [HGrid]] class. For convenience the [[HGrid]] is passed as an implicit parameter. */
class HSideBooleans(val unsafeArray: Array[Boolean]) extends AnyVal
{
  def setTrues(hSides: HSide*)(implicit grid: HGrid): Unit = hSides.foreach(r => unsafeArray(grid.sideArrIndex(r)) = true)
}