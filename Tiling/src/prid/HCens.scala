/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An efficient array[Int] based collection for [[HCen]]s hex grid centre coordinates. */
class HCens(val arrayUnsafe: Array[Int]) extends AnyVal with Int2sSeq[HCen]
{ type ThisT = HCens

  override def newElem(i1: Int, i2: Int): HCen = HCen(i1, i2)

  override def unsafeFromArray(array: Array[Int]): HCens = new HCens(array)

  override def typeStr: String = "HCens"

  override def fElemStr: HCen => String = _ => "Not implemented"
}

/** Companion object for [[HCens]] trait efficient array[Int] based collection for [[HCen]]s hex grid centre coordinates, contains factory apply and uninitialised methods.. */
object HCens extends Int2sArrCompanion[HCen, HCens]
{
  //override def buff(initialSize: Int): RoordBuff = new RoordBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): HCens = new HCens(array)

  implicit object PersistImplicit extends Int2sArrPersist[HCen, HCens]("HCens")
  { override def fromArray(value: Array[Int]): HCens = new HCens(value)

    override def showT(obj: HCens, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ???
  }

  //implicit val arrArrayImplicit: ArrTFlatBuilder[HCens] = HCen.roordsBuildImplicit
}