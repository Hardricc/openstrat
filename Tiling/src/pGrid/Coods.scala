/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pGrid

/** An array[Int] based collection for Cood. To be replaced by [[Roords]]. */
class Coods(val arrayUnsafe: Array[Int]) extends AnyVal with Int2sArr[Cood]
{ type ThisT = Coods
  override def fElemStr: Cood => String = _.str
  override def unsafeFromArray(array: Array[Int]): Coods = new Coods(array)
  override def typeStr: String = "Coods"
  override def newElem(i1: Int, i2: Int): Cood = Cood.apply(i1, i2)

  def filter(f: Cood => Boolean): Coods =
  { val tempArr = new Array[Int](arrayUnsafe.length)
    var count = 0
    var lengthCounter = 0
    while (count < elemsLen)
    {
      if (f(this.apply(count)))
      { tempArr(lengthCounter * 2) = arrayUnsafe(count * 2)
        tempArr(lengthCounter * 2 + 1) = arrayUnsafe(count * 2 + 1)
        lengthCounter += 1
      }
      count += 1
    }
    val finalArr = new Array[Int](lengthCounter * 2)
    count = 0
    while (count < lengthCounter * 2){ finalArr(count) = tempArr(count); count += 1 }
    new Coods(finalArr)
  }

  def flatMapNoDuplicates(f: Cood => Coods): Coods =
  {
    val buff = new CoodBuff()
    foreach{ el =>
      val newVals = f(el)
      newVals.foreach{ newVal => if( ! buff.contains(newVal)) buff.grow(newVal) }
    }
    new Coods(buff.toArray)
  }
}

object Coods extends Int2sArrCompanion[Cood, Coods]
{
  //override def buff(initialSize: Int): CoodBuff = new CoodBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): Coods = new Coods(array)

  implicit object PersistImplicit extends Int2sArrPersist[Cood, Coods]("Coods")
  { override def fromArray(value: Array[Int]): Coods = new Coods(value)

    override def showT(obj: Coods, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: ArrTFlatBuilder[Coods] = new Int2sArrFlatBuilder[Cood, Coods]
  { type BuffT = CoodBuff
    override def fromIntArray(array: Array[Int]): Coods = new Coods(array)
    override def fromIntBuffer(inp: Buff[Int]): CoodBuff = new CoodBuff(inp)
  }
}

class CoodBuff(val buffer: Buff[Int] = buffInt()) extends AnyVal with Int2sBuffer[Cood, Coods]
{ type ArrT = Coods
  override def intsToT(i1: Int, i2: Int): Cood = Cood(i1, i2)
}