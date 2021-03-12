/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An immutable Arr of Opt Tile data for a specific hex tile grid [[HGrid]]. This is specialised for OptRef[A]. The tileGrid can map the [[HCen]]
 * coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[HGrid]] hex grid parameter. */
class HCenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length
  def clone: HCenArrOpt[A] = new HCenArrOpt[A](unsafeArr.clone)

  /** Sets the Some value of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSome(y: Int, c: Int, value: A)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(y, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[HCen]] coordinate. This is an imperative mutating operation. */
  def setSome(hc: HCen, value: A)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSomes(triples: (Int, Int, A)*)(implicit grid: HGridReg): Unit = triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  def setNone(hc: HCen)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = null.asInstanceOf[A]

  def setAll(value: A): Unit = iUntilForeach(0, length)(unsafeArr(_) = value)

  def setSomeNew(hc: HCen, value: A)(implicit grid: HGrid): HCenArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(hc)) = value
    new HCenArrOpt[A](newArr)
  }

  /** Moves the object in the array location given by HCen1 to HCen2, by setting H2 to the value of h1 and setting H1 to null. */
  def mutMove(h1: HCen, h2: HCen)(implicit grid: HGrid): Unit =
  { unsafeArr(grid.arrIndex(h2)) = unsafeArr(grid.arrIndex(h1))
    unsafeArr(grid.arrIndex(h1)) = null.asInstanceOf[A]
  }

/** coordinate-foreach-Some. Foreach Some element and its associated [[HCen]] coordinate applies the side effecting parameter function. It ignores
 *  the None values. */
def cForeachSome(f: (A, HCen) => Unit)(implicit grid: HGrid): Unit = grid.foreach { hc => f(unsafeArr(grid.arrIndex(hc)), hc) }

/** Coordinate-map. Maps the this Arr of Opt values, with their respective [[HCen]] coordinates to an Arr of type B. */
def cMap[B, ArrT <: ArrImut[B]](fNone: => HCen => B)(fSome: (A, HCen) => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
{
  val buff = build.newBuff()
  grid.foreach { hc =>
    val a = unsafeArr(grid.arrIndex(hc))
    if (a != null) build.buffGrow(buff, fSome(a, hc))
    else { val newVal = fNone(hc); build.buffGrow(buff, newVal) }
  }
  build.buffToArr(buff)
}


/** Maps the this Arr of Opt values, without their respective Hcen coordinates to an Arr of type B. This method treats the [[HCenArrOpt]] class like
 *  a standard Arr or Array. It does not utilise the grid [[HGrid]] from which this [[HcenArr]] was created. */
def map[B, ArrT <: ArrImut[B]](noneValue: => B)(f: A => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
{
  val buff = build.newBuff()
  grid.foreach { r =>
    val a = unsafeArr(grid.arrIndex(r))
    if (a != null) build.buffGrow(buff, noneValue)
    else { val newVal = f(a); build.buffGrow(buff, newVal) }
  }
  build.buffToArr(buff)
}


/** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
def apply(hc: HCen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))

/** Maps the Some values to type B by the parameter function. It ignores the None values. This method treats the [[HcenArr]] class like a standard
 *  Arr or Array. It does not utilise the grid [[HGrid]] from which this [[HCenArrOpt]] was created. */
def mapSomes[B, ArrT <: ArrImut[B]](f: A => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
{
  val buff = build.newBuff()
  grid.foreach { r =>
    val a = unsafeArr(grid.arrIndex(r))
    if(a != null)
    { val newVal = f(a)
      build.buffGrow(buff, newVal)
    }
 }
 build.buffToArr(buff)
}

/** Coordinate map Somes. map the some values of this HcenArrOpt, with the respective Hcen coordinate to type B, the first type parameter B. Returns
 *  an immutable Array based collection of type ArrT, the second type parameter. */
def cMapSomes[B, ArrT <: ArrImut[B]](f: (A, HCen) => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
{
  val buff = build.newBuff()
  grid.foreach { r =>
    val a: A = unsafeArr(grid.arrIndex(r))
    if(a != null)
    { val newVal = f(a, r)
      build.buffGrow(buff, newVal)
    }
  }
  build.buffToArr(buff)
}

/** Coordinate map Nones. Map the None values respective [[HCen]] coordinates of this [[HCenArrOpt]] to type B, the first type parameter. Returns an
 * immutable Array based collection of type ArrT, the second type parameter. */
def cMapNones[B, ArrT <: ArrImut[B]](f: HCen => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
{
  val buff = build.newBuff()
  grid.foreach { r =>
    val a: A = unsafeArr(grid.arrIndex(r))
    if(a == null)
    { val newVal = f(r)
      build.buffGrow(buff, newVal)
    }
  }
  build.buffToArr(buff)
}

/** Coordinate flatMap Somes. Maps and flattens each Some element with its associated [[HCen]] coordinate. It ignores the None values. */
def cFlatMapSomes[ArrT <: ArrImut[_]](f: (A, HCen) => ArrT)(implicit grid: HGrid, build: ArrTFlatBuilder[ArrT]): ArrT =
{
  val buff = build.newBuff()
  grid.foreach { hc =>
    val a = unsafeArr(grid.arrIndex(hc))
    if(a != null)
    { val newVal = f(a, hc)
      build.buffGrowArr(buff, newVal)
    }
  }
  build.buffToArr(buff)
}
}