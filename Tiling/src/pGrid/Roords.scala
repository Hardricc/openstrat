/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid

/** An array[Int] based collection for Roord. */
class Roords(val arrayUnsafe: Array[Int]) extends AnyVal with Int2sArr[Roord]
{ type ThisT = Roords
  override def fElemStr: Roord => String = _.toString
  override def unsafeFromArray(array: Array[Int]): Roords = new Roords(array)
  override def typeStr: String = "Roords" + foldLeft("")(_ + "; " + _.ycStr)
  override def newElem(i1: Int, i2: Int): Roord = Roord.apply(i1, i2)

  /*def filter(f: Roord => Boolean): Roords =
  { val tempArr = new Array[Int](array.length)
    var count = 0
    var lengthCounter = 0
    while (count < length)
    {
      if (f(this.apply(count)))
      { tempArr(lengthCounter * 2) = array(count * 2)
        tempArr(lengthCounter * 2 + 1) = array(count * 2 + 1)
        lengthCounter += 1
      }
      count += 1
    }
    val finalArr = new Array[Int](lengthCounter * 2)
    count = 0
    while (count < lengthCounter * 2){ finalArr(count) = tempArr(count); count += 1 }
    new Roords(finalArr)
  }

  def flatMapNoDuplicates(f: Roord => Roords): Roords =
  {
    val buff = new RoordBuff()
    foreach{ el =>
      val newVals = f(el)
      newVals.foreach{ newVal => if( ! buff.contains(newVal)) buff.grow(newVal) }
    }
    new Roords(buff.toArray)
  }*/
}

object Roords extends Int2sArrCompanion[Roord, Roords]
{
  //override def buff(initialSize: Int): RoordBuff = new RoordBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): Roords = new Roords(array)

  implicit object PersistImplicit extends Int2sArrPersist[Roord, Roords]("Roords")
  { override def fromArray(value: Array[Int]): Roords = new Roords(value)

    override def showT(obj: Roords, way: Show.Way, decimalPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: ArrTFlatBuilder[Roords] = Roord.roordsBuildImplicit
}

class RoordBuff(val buffer: Buff[Int] = buffInt()) extends AnyVal with Int2sBuffer[Roord, Roords]
{ type ArrT = Roords
  override def intsToT(i1: Int, i2: Int): Roord = Roord(i1, i2)
}