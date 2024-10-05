/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, collection.immutable._, reflect.ClassTag

/** This the base trait for all efficient sequence collections based on Array like classes, Arrays, ArrayBuffers etc. The final classes compile time
 *  wrap the platform Array and buffer classes. So currently there are just two classes for each type A, An ArrImut that wraps a standard immutable
 *  Array to produce an immutable array, and a ArrBuff that wraps an ArrayBuffer. Currently this just in a standard ArrayBuffer. Where A is a compound
 *  value types or an AnyVal type. */
trait Sequ[+A] extends Any with SeqLike[A @uncheckedVariance]
{ /** The final type of this object. */
  type ThisT <: Sequ[A]

  /** The length of this Sequence. This will have the same value as the dataLength property inherited from [[SeqLike]][A]. */
  def length: Int

  /** Just a handy short cut to give the length of this collection as a string. */
  def lenStr: String = length.toString

  /** Method for keeping the typer happy when returning this as an instance of ThisT. */
  @inline def returnThis: ThisT = this.asInstanceOf[ThisT]

  /** Accesses the defining sequence element by a 0 based index. */
  @inline def apply(index: Int): A

  /** like the apply method accesses the defining sequence element by a 0 based index, but cycles around for indexes less that 0 and equal or greater than index length. */
  def indexCycle(index: Int): A = apply(index %% length)

  /** The first element of this sequence. */
  @inline def head: A = apply(0)

  /** Returns the [[Some]] of the first element unless the sequence is empty in which case it returns [[None]]. */
  def headFind: Option[A] = ife(length > 0, Some(apply(0)), None)

  /** The last element of this sequence. */
  @inline def last: A = apply(length - 1)

  /** Returns the [[Some]] of the last element unless the sequence is empty in which case it returns [[None]]. */
  def lastFind: Option[A] = ife(length > 0, Some(last), None)

  /** Is this sequence empty? */
  @inline def empty: Boolean = length <= 0

  /** Is this sequence non empty? */
  @inline def nonEmpty: Boolean = length > 0

  /** Folds over the non existence / existence of a head element. The first parameter is a value for an empty sequence, the second parameter passed as a separate parameter list is a function on the head element. */
  def headFold[B](noHead: => B)(ifHead: A => B): B = ife(length >= 1, ifHead(head), noHead)

  /** Folds over the non existence / existence of a head element. If the sequence is nonEmpty applies toString to head element else returns the noHead parameter string. */
  def headFoldToString[B](noHead: => String): String = ife(length >= 1, apply(0).toString, noHead)

  /** Folds over the non existence / existence of a last element. The first parameter is a value for an empty sequence, the second parameter passed as a separate parameter list is a function on the last element. */
  def lastFold[B](noLast: => B)(ifLast: A => B): B = ife(length >= 1, ifLast(last), noLast)

  /** if this [[Sequ]] is nonEmpty performs the side effecting function on the head. If empty procedure is applied. */
  def ifHead[U](f: A => U): Unit = if(length >= 1) f(apply(0))

  /** Applies an index to this ArrayLike collection which cycles back to element 0, when it reaches the end of the collection. Accepts even negative
   * integers as an index value without throwing an exception. */
  @inline def cycleGet(index: Int): A = apply(index %% length)

  /** Performs a side effecting function on each element of this sequence in order. The function may return Unit. If it does return a non Unit value
   *  it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method users. */
  def foreach[U](f: A => U): Unit =
  { var count = 0
    while(count < length)
    { f(apply(count))
      count = count + 1
    }
  }

  /** Performs a side effecting function on each element of the specifying sequence in order. */
  inline final override def ssForeach[U](f: A => U): Unit = foreach(f)

  /** Index with foreach. Performs a side effecting function on the index and each element of this sequence. It takes a function as a parameter. The
   *  function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about
   *  discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for the index is 0 if just
   *  the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set the start value
   *  of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example
   *  in fold methods' (accumulator, element) => B signature. */
  def iForeach[U](f: (Int, A) => U): Unit =
  { var count = 0
    var i: Int = 0
    while(count < length )
    { f(i, apply(count))
      count+= 1
      i += 1
    }
  }

  /** Index with foreach. Performs a side effecting function on the index and each element of this sequence. It takes a function as a parameter. The
   *  function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about
   *  discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for the index is 0 if just
   *  the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set the start value
   *  of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example
   *  in fold methods' (accumulator, element) => B signature. */
  def iForeach[U](startIndex: Int)(f: (Int, A) => U): Unit =
  { var count = 0
    var i: Int = startIndex
    while(count < length )
    { f(i, apply(count))
      count+= 1
      i += 1
    }
  }

  /** Specialised map to an immutable [[Arr]] of B. Applies the supplied function to every element of this sequence. */
  def map[B, ArrB <: Arr[B]](f: A => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val res = build.uninitialised(length)
    iForeach((i, a) => build.indexSet(res, i, f(a)))
    res
  }

  /** Takes a map function from A to Option[B] but only returns the [[Arr] of B if all the elements map to a [[Some]]. Hence the ArrB if returned will
   *  be the same length as this sequence. */
  def optAllMap[B, ArrB <: Arr[B]](f: A => Option[B])(implicit build: BuilderArrMap[B, ArrB]): Option[ArrB] =
  { val res = build.uninitialised(length)
    var good = true
    var i = 0
    while(i < length & good) f(apply(i)) match
    { case Some(b) => { build.indexSet(res, i, b); i += 1 }
      case None => good = false
    }
    ife(good, Some(res), None)
  }

  /** Specialised opt map to an immutable [[Arr]] of B. Applies the supplied function to every element of this sequence. */
  def optMap[B, ArrB <: Arr[B]](f: A => Option[B])(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    foreach(a => f(a).foreach(b => buff.grow(b)))
    build.buffToSeqLike(buff)
  }

  /** A map operation where the return type of the [[SeqLike]] is explicitly given by the the second type parameter. */
  def mapTo[B, BB <: SeqLike[B]](build: BuilderSeqLikeMap[B, BB])(f: A => B): BB =
  { val res = build.uninitialised(length)
    var i = 0
    foreach{ el => res.setElemUnsafe(i, f(el)); i += 1 }
    res
  }

  /** Performs a side effecting function on each element of the range of index values for this sequence in order. The function may return Unit. If it does return a non Unit value
   * it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method users. */
  def indexToForeach[U](iFrom: Int, iTo: Int, iStep: Int = 1)(f: A => U): Unit = {
    if (iTo != iFrom & iStep == 0) throw excep("Loop step can not be 0.")
    var i: Int = iFrom
    if (iStep > 0) while (i <= iTo) {
      f(apply(i)); i += iStep
    }
    else while (i >= iTo) {
      f(apply(i)); i += iStep
    }
  }

  /** A map operation on the range of indexed values, where the return type of the [[SeqLike]] is explicitly given by the the first parameter. */
  def indexMapTo[B, BB <: SeqLike[B]](iFrom: Int, iTo: Int, iStep: Int = 1)(f: A => B)(implicit build: BuilderSeqLikeMap[B, BB]): BB =
  { val res = build.uninitialised(length)
    var ti = 0
    indexToForeach(iFrom, iTo, iStep) { el => res.setElemUnsafe(ti, f(el)); ti += 1 }
    res
  }

  /** Specialised map to an immutable [[PairArrFinalA1]] of B. Applies the supplied function to every element of this sequence. */
  def mapPair[B1, ArrB1 <: Arr[B1], B2, B <: PairFinalA1Elem[B1, B2], ArrB <: PairArrFinalA1[B1, ArrB1, B2, B]](f1: A => B1)(f2: A => B2)(
  implicit build: BuilderArrPairMap[B1, ArrB1, B2, B, ArrB]): ArrB =
  { val b1Res = map(f1)(build.b1ArrBuilder)
    val b2Array = mapArray(f2)(build.b2ClassTag)
    build.arrFromArrAndArray(b1Res, b2Array)
  }

  /** Index with element map. Applies the parameter function to the index and each respective element of this sequence. The function returns an
   * element of type B and the method as a whole returns the specialised [[Arr]] of type B. The method has 2 versions / name overloads. The
   * default start for the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first
   * parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of putting the collection
   * element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. This method should be overridden in sub
   * classes. */
  def iMap[B, ArrB <: Arr[B]](f: (Int, A) => B)(implicit ev: BuilderArrMap[B, ArrB]): ArrB =
  { val res = ev.uninitialised(length)
    iForeach((i, a) => ev.indexSet(res, i, f(i, a)))
    res
  }

  /** Index with element map. Applies the parameter function to the index and each respective element of this sequence. The function returns an
   * element of type B and the method as a whole returns the specialised [[Arr]] of type B. The method has 2 versions / name overloads. The
   * default start for the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first
   * parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of putting the collection
   * element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. Ideally this method should be overridden in sub
   * classes. */
  def iMap[B, ArrB <: Arr[B]](startindex: Int)(f: (Int, A) => B)(implicit ev: BuilderArrMap[B, ArrB]): ArrB =
  { val res = ev.uninitialised(length)
    iForeach(startindex)((i, a) => ev.indexSet(res, i, f(i, a)))
    res
  }

  /** Specialised flatMap to a [[Arr]]. */
  def flatMap[ArrB <: Arr[?]](f: A => ArrB)(implicit ev: BuilderArrFlat[ArrB]): ArrB =
  { val buff: ev.BuffT = ev.newBuff()
    foreach{ a =>
      val newVals = f(a)
      ev.buffGrowArr(buff, newVals)
    }
    ev.buffToSeqLike(buff)
  }

  /** Index with element flatMap. Applies the parameter function to the index and each respective element of this sequence. The function returns a
   * [[Arr]] of elements of type B and the method as a whole flattens and then returns the specialised [[Arr]] of type B. The method has 2
   * versions / name overloads. The default start for the index is 0 if just the function parameter is passed. The second version name overload takes
   * an [[Int]] for the first parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of
   * putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. Ideally this method should
   * be overridden in sub classes. */
  def iFlatMap[ArrB <: Arr[?]](f: (Int, A) => ArrB)(implicit build: BuilderArrFlat[ArrB]): ArrB =
  { val buff: build.BuffT = build.newBuff()
    var i: Int = 0
    while (i < length)
    { val newArr = f(i, apply(i));
      build.buffGrowArr(buff, newArr)
      i += 1
    }
    build.buffToSeqLike(buff)
  }

  /** Index with element flatMap. Applies the parameter function to the index and each respective element of this sequence. The function returns a
   * [[Arr]] of elements of type B and the method as a whole flattens and then returns the specialised [[Arr]] of type B. The method has 2
   * versions / name overloads. The default start for the index is 0 if just the function parameter is passed. The second version name overload takes
   * an [[Int]] for the first parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of
   * putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. Ideally this method should
   * be overridden in sub classes. */
  def iFlatMap[ArrB <: Arr[?]](iInit: Int)(f: (Int, A) => ArrB)(implicit build: BuilderArrFlat[ArrB]): ArrB =
  { val buff: build.BuffT = build.newBuff()
    var count: Int = 0
    while (count < length)
    { val newElems = f(count + iInit, apply(count))
      build.buffGrowArr(buff, newElems)
      count += 1
    }
    build.buffToSeqLike(buff)
  }

  /** Takes a second collection as a parameter and zips the elements of this collection and the operand collection and applies the specialised map
   * function from type A and type B to type C. */
  def zipMap[B, C, ArrC <: Arr[C]](operator: Sequ[B])(f: (A, B) => C)(implicit ev: BuilderArrMap[C, ArrC]): ArrC =
  { val newLen = length.min(operator.length)
    val res = ev.uninitialised(newLen)
    var count = 0
    while(count < newLen)
    { val newElem = f(apply(count), operator.apply(count))
      ev.indexSet(res, count, newElem)
      count += 1
    }
    res
  }

  /** Takes a second collection and third collections as parameters and zips the elements of this collection and the operand collections and applies
   *  the specialised map function from type A and type B and type C to type D. */
  def zipMap2[B, C, D, ArrD <: Arr[D]](operator1: Sequ[B], operator2: Sequ[C])(f: (A, B, C) => D)(implicit ev: BuilderArrMap[D, ArrD]): ArrD =
  { val newLen = length.min(operator1.length).min(operator2.length)
    val res = ev.uninitialised(newLen)
    var count = 0
    while(count < newLen)
    { val newElem = f(apply(count), operator1.apply(count), operator2.apply(count))
      ev.indexSet(res, count, newElem)
      count += 1
    }
    res
  }

  /* Maps from A to B like normal map,but has an additional accumulator of type C that is discarded once the traversal is completed. Note the function
   * signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'
   *  (accumulator, element) => B signature. */
  def mapWithAcc[B, ArrB <: Arr[B], C](initC: C)(f: (C, A) => (B, C))(implicit ev: BuilderArrMap[B, ArrB]): ArrB =
  { val res = ev.uninitialised(length)
    var accC: C = initC
    iForeach({ (i, a) =>
          val (newB, newC) = f(accC, a)
          res.setElemUnsafe(i, newB)
          accC = newC
        })
    res
  }

  /** Map from A => [[EMon]][B]. implicitly takes a [[BuilderArrMap]]. There is a name overload that explicitly takes a more flexible [[BuilderCollMap]] as the
   * first parameter list. */
  def mapEMon[B, ArrB <: Arr[B]](f: A => EMon[B])(implicit ev: BuilderArrMap[B, ArrB]): EMon[ArrB] =
  { val acc = ev.newBuff()
    var continue = true
    var count = 0
    var errs: StrArr = StrArr()
    while(count < length & continue == true)
      f(apply(count)).foldErrs { g => ev.buffGrow(acc, g); count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(ev.buffToSeqLike(acc)), Bad(errs))
  }

  /** Map from A => [[ErrBi]][E, B]. Returns a successful [[Arr]] of B as long as the function produces no errors, in whihc case it returns a [[Fail]] of the
   * first error encountered implicitly takes a [[BuilderArrMap]]. There is a name overload that explicitly takes a more flexible [[BuilderCollMap]] as the
   * first parameter list. */
  def mapErrBi[E <: Throwable, B, ArrB <: Arr[B]](f: A => ErrBi[E, B])(implicit ev: BuilderArrMap[B, ArrB]): ErrBi[E, ArrB] =
  { val acc = ev.newBuff()
    var count = 0
    var optErr: Option[E] = None
    while (count < length & optErr == None)
      f(apply(count)).fold { g => ev.buffGrow(acc, g); count += 1 } { newErr => optErr = Some(newErr) }
    optErr match
    { case Some(err) => Fail (err)
      case None => Succ (ev.buffToSeqLike(acc))
    }
  }

  /** Map from A => [[EMon]][B]. There is a name overload that implicitly takes a narrower [[BuilderArrMap]] as the second parameter list. */
  def mapEMon[B, BB](ev: BuilderCollMap[B, BB])(f: A => EMon[B]): EMon[BB] =
  { val acc = ev.newBuff()
    var continue = true
    var count = 0
    var errs: StrArr = StrArr()
    while (count < length & continue == true)
      f(apply(count)).foldErrs { g => ev.buffGrow(acc, g); count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(ev.buffToSeqLike(acc)), Bad(errs))
  }

  /** Map from A => [[ErrBi]][E, B]. There is a name overload that implicitly takes a narrower [[BuilderArrMap]] as the second parameter list. */
  def mapErrBi[E <: Throwable, B, BB](ev: BuilderCollMap[B, BB])(f: A => ErrBi[E, B]): ErrBi[E, BB] =
  { val acc = ev.newBuff()
    var count = 0
    var optErr: Option[E] = None
    while (count < length & optErr == None)
      f(apply(count)).fold{ g => ev.buffGrow(acc, g); count += 1 } { newErr => optErr = Some(newErr) }
    optErr match
    { case Some(err) => Fail(err)
      case None => Succ(ev.buffToSeqLike(acc))
    }
  }

  /** Maps to an Array. */
  def mapArray[B](f: A => B)(implicit ct: ClassTag[B]): Array[B] =
  { val res = new Array[B](length)
    var i = 0
    foreach{ a => res(i) = f(a); i += 1 }
    res
  }

  /** Map from A => B, retuening an [[EMon]] of [[List]]. */
  def mapEMonList[B](f: A => EMon[B]): EMon[List[B]] =
  { var acc: List[B] = Nil
    var continue = true
    var count = 0
    var errs: StrArr = StrArr()
    while(count < length & continue == true)
      f(apply(count)).foldErrs { g => acc ::= g; count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(acc.reverse), Bad(errs))
  }

  /** map 2 elements of A to 1 element of B. Ignores the last element on a collection of odd numbered length. */
  def map2To1[B, ArrB <: Arr[B]](f: (A, A) => B)(implicit ev: BuilderArrMap[B, ArrB]): ArrB =
  { val res = ev.uninitialised(length)
    var count = 0
    while (count + 1  < length)
    {  ev.indexSet(res, count, f(apply(count), apply(count + 1)))
      count += 2
    }
    res
  }

  def bestOfGet(init: A @uncheckedVariance)(f1: A => Boolean)(f2: (A, A) => Boolean): A =
  { var res = init
    foreach{a => if (f1(a) && f2(res, a)) res = a}
    res
  }

  def filter[ArrA <: Arr[A] @uncheckedVariance](f: A => Boolean)(implicit ev: BuilderArrMap[A, ArrA] @uncheckedVariance): ArrA =
  { val buff = ev.newBuff()
    foreach(a => onlyIf(f(a), ev.buffGrow(buff, a)))
    ev.buffToSeqLike(buff)
  }

  def filterNot[ArrA <: Arr[A] @uncheckedVariance](f: A => Boolean)(implicit ev: BuilderArrMap[A, ArrA] @uncheckedVariance): ArrA =
  { val buff = ev.newBuff()
    foreach(a => onlyIf(!f(a), ev.buffGrow(buff, a)))
    ev.buffToSeqLike(buff)
  }

  def filterToList(f: A => Boolean): List[A] =
  { var acc: List[A] = Nil
    foreach{ a => if (f(a)) acc ::= a }
    acc.reverse
  }

  /** Maps over a function from A to any Iterable and flattens the result into an [[RArr]][A]. */
  def flatToIterableMap[B, ArrB <: Arr[B]](f: A => Iterable[B])(implicit ev: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = ev.newBuff(length)
    foreach(a => ev.buffGrowIter(buff, f(a)))
    ev.buffToSeqLike(buff)
  }

  /** Folds over this sequence starting with the initial value */
  def foldLeft[B](initial: B)(f: (B, A) => B): B =
  { var acc: B = initial
    foreach(a => acc = f(acc, a))
    acc
  }

  def foldLeft[B](f: (B, A) => B)(implicit ev: DefaultValue[B]): B =
  { var acc: B = ev.default
    foreach(a => acc = f(acc, a))
    acc
  }

  def indexOf(elem: A @uncheckedVariance): Int =
  { var result = -1
    var count  = 0
    while (count < length & result == -1)
    { if (elem == apply(count)) result = count
    else count += 1
    }
    result
  }

  /** Return the index of the first element where predicate is true, or -1 if predicate not true forall. */
  def indexWhere(f: A => Boolean): Int =
  { var count = 0
    var result = -1
    while(count < length & result == -1)
    { if(f(apply(count))) result = count
      count += 1
    }
    result
  }

  /** Foreachs over the tail of this sequence. Performs a side effecting function on each element of this sequence excluding the first. */
  def tailForeach[U](f: A => U): Unit =
  { var count = 1
    while(count < length) { f(apply(count)); count += 1 }
  }

  /** Performs a side effecting function on each element of this sequence excluding the last. The function may return Unit. If it does return a non
   *  Unit value it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method users. */
  def initForeach[U](f: A => U): Unit =
  { var count = 0
    while(count < length - 1)
    { f(apply(count))
      count += 1
    }
  }

  /** foldLeft over the tail of this sequence. */
  def tailfold[B](initial: B)(f: (B, A) => B): B =
  { var acc: B = initial
    tailForeach(a => acc = f(acc, a))
    acc
  }

  def foldHeadTail[B](initVal: B)(f: A => B, fAcc: (B, B) => B): B =
  { var acc = initVal
    if (this.nonEmpty)
    { acc = fAcc(acc, f(head))
      tailForeach { a => acc = fAcc(acc, f(a)) }
    }
    acc
  }

  /** Performs a side effecting function on each element of this sequence in reverse order. The function may return Unit. If it does return a non Unit
   *  value it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method users. */
  def reverseForeach[U](f: A => U): Unit =
  { var count = length
    while(count > 0) { count -= 1; f(apply(count)) }
  }

  /** Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold
   *  methods' (accumulator, element) => B signature. */
  def reverseIForeach[U](f: (Int, A) => U): Unit =
  { var count = length
    while(count > 0) { count -= 1; f(count, apply(count)) }
  }

  /** Returns true if the predicate holds true for all values of this sequence, else retruns false. */
  def forAll(p: (A) => Boolean): Boolean =
  { var acc: Boolean = true
    var count = 0
    while (acc & count < length) if (p(apply(count))) count += 1 else acc = false
    acc
  }

  /**  Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold
   *  methods' (accumulator, element) => B signature. */
  def iForAll(p: (Int, A) => Boolean): Boolean =
  { var acc: Boolean = true
    var count = 0
    while (acc & count < length) if (p(count, apply(count))) count += 1 else acc = false
    acc
  }

  /** Returns true if this sequence contains a value equal to the parameter value. The passed vvalue for equivlence testing  an be a super type of the
   * collection type. */
  def contains[A1 >: A](elem: A1): Boolean =
  { var count = 0
    var res = false
    while (res == false & count < length){ if (elem == apply(count)) res = true; count += 1 }
    res
  }

  /** maps ValueProduct collection to List */
  def mapList[B <: AnyRef](f: A => B): List[B] =
  { var res: List[B] = Nil
    reverseForeach(res ::= f(_))
    res
  }

  /** Folds left to a [[String]] accumulator with an initial value of the empty string. The first parameter is a function from A tp String. The second
   * parameter is a separator [[String]] the 2nd and subsequent A => String values. */
  def foldStr(f: A => String, seperator: String = ""): String =
  { var acc: String = ""
    var start = true
    foreach{ a => if(start == true) { acc = f(a); start = false } else acc = acc + seperator + f(a) }
    acc
  }

  /** Counts the number of elements that fulfil the condition A => Boolean */
  def existsCount(f: A => Boolean): Int =
  { var count = 0
    foreach(el => if (f(el)) count += 1)
    count
  }

  /** Not sure about this method. */
  def mkString(separator: String): String = ife(length == 0, "",
    { var acc = head.toString
      var count = 1
      while(count < length)
      { acc += separator + apply(count).toString
        count += 1
      }
      acc
    }
  )

  /** Converts this SeqGen to a [[list]]. */
  def toList: List[A] =
  { var acc: List[A] = Nil
    reverseForeach(acc ::= _)
    acc
  }

  def toVector: Vector[A] = toList.toVector

  /** Sums aaccumulating the results of the A => Int function. */
  def sumBy(f: A => Int): Int =
  { var acc = 0
    foreach(acc += f(_))
    acc
  }

  /** Collects values of B by applying partial function to only those elements of A, for which the PartialFunction is defined. */
  def collect[B, BB <: Arr[B]](pf: PartialFunction[A, B])(implicit ev: BuilderArrMap[B, BB]): BB =
  { val acc = ev.newBuff()
    foreach{a => if (pf.isDefinedAt(a)) ev.buffGrow(acc, pf(a)) }
    ev.buffToSeqLike(acc)
  }

  /** Takes a function from A to EMon[B]. If the function applied to eqch element produces a single Good, it is returned else returns [[Bad]]. */
  def mapUniqueGood[B](f: A => EMon[B]): EMon[B] =
  { var count = 0
    var acc: EMon[B] = badNone("No elem of type found")
    foreach{a =>
      val eb: EMon[B] = f(a)
      if(eb.isGood){ count += 1; acc = eb }
    }
    ife(count < 2, acc, badNone(s"$count values found"))
  }

  /** maps from A to EMon[B], collects the good values. */
  def mapCollectGoods[B, BB <: Arr[B]](f: A => EMon[B])(implicit ev: BuilderArrMap[B, BB]): BB =
  { val acc = ev.newBuff()
    foreach(f(_).forGood(ev.buffGrow(acc, _)))
    ev.buffToSeqLike(acc)
  }

  /** maps from A to ErrBi[B], collects the successful values. */
  def mapCollectSuccs[B, BB <: Arr[B]](f: A => ErrBi[?, B])(implicit ev: BuilderArrMap[B, BB]): BB =
  { val acc = ev.newBuff()
    foreach(f(_).forSucc(ev.buffGrow(acc, _)))
    ev.buffToSeqLike(acc)
  }

  /** Gives the maximum value of this sequence according to the implicit ordering type class instance, which can be passed explicitly. */
  def max[B >: A](implicit ord: math.Ordering[B]): A =
  { var acc = apply(0)
    tailForeach{ el => acc = ord.max(acc, el) }
    acc
  }

  /** Gives the minimum value of this sequence according to the implicit ordering type class instance, which can be passed explicitly. */
  def min[B >: A](implicit ord: math.Ordering[B]): A =
  { var acc = apply(0)
    tailForeach{ el => acc = ord.min(acc, el) }
    acc
  }

  /** Gives the maximum value of type B, produced by applying the function from A to B on each element of this collection, or the default value if the
   *  collection is empty. */
  def fMax[B](defaultValue: B)(f: (A) => B)(implicit cmp: math.Ordering[B]): B = if (empty) defaultValue else
  { var acc = f(head)
    tailForeach{ el => acc = cmp.max(acc, f(el)) }
    acc
  }

  /** Gives the minimum value of type B, produced by applying the function from A to B on each element of this collection, or the default value if the
   *  collection is empty. */
  def fMin[B](defaultValue: B)(f: (A) => B)(implicit cmp: math.Ordering[B]): B =
  { var acc = f(head)
    tailForeach{ el => acc = cmp.min(acc, f(el)) }
    acc
  }

  def toStrsCommaFold(fToStr: A => String): String = foldStr(fToStr, ", ")
  def toStrsCommaNoSpaceFold(fToStr: A => String): String = foldStr(fToStr, ",")
  def toStrsSemiFold(fToStr: A => String): String = foldStr(fToStr, "; ")
  def toStrsCommaParenth(fToStr: A => String): String = toStrsCommaFold(fToStr).enParenth
  def toStrsSemiParenth(fToStr: A => String): String = toStrsSemiFold(fToStr).enParenth

  def sum(implicit ev: Sumable[A] @uncheckedVariance): A = foldLeft[A](ev.identity)(ev.sum(_, _))

  /** Tries to find te first element of this sequence conforming to the predicate. */
  def find(f: A => Boolean): Option[A] =
  { var count = 0
    var res: Option[A] = None
    while (count < length & res.isEmpty)
    {
      val el = apply(count)
      if (f(el)) res = Some(el)
      else count += 1
    }
    res
  }

  /** Tests if the condition exists for any element of this sequence. */
  def exists(f: A => Boolean): Boolean =
  { var count = 0
    var res = false
    while (count < length & res == false)  if (f(apply(count))) res = true else count += 1
    res
  }

  def partition[ArrA <: Arr[A] @uncheckedVariance](f: A => Boolean)(implicit build: BuilderArrMap[A, ArrA] @uncheckedVariance): (ArrA, ArrA) =
  { val buff1: build.BuffT = build.newBuff()
    val buff2: build.BuffT = build.newBuff()
    foreach{a => if (f(a)) build.buffGrow(buff1, a) else build.buffGrow(buff2,a) }
    (build.buffToSeqLike(buff1), build.buffToSeqLike(buff2) )
  }

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  override def elemsStr: String = map(fElemStr).mkString("; ").enParenth

  /** Takes a function that returns an [[EMon]] and returns the first [[Good]]. */
  def findGood[B](f: A => EMon[B]): EMon[B] =
  { var res: EMon[B] = ENone
    var i = 0
    while(i < length && res == ENone)
    { val b = f(apply(i))
      if (b.isGood) res = b
      i += 1
    }
    res.noneMap1("No Good returned when function applied to this Sequ.")
  }

  /** Takes a function that returns an [[ErrBi]] and returns the first [[Succ]]. */
  def findSucc[E <: Throwable, B](f: A => ErrBi[E, B]): ErrBi[ExcNotFound.type, B] =
  { var res: ErrBi[ExcNotFound.type, B] = NotFound()
    var i = 0
    while (i < length && res.isFail)
    { val bi = f(apply(i))
       bi match
       { case Succ(b) => res = Succ[B](b)
         case _ =>
       }
      i += 1
    }
    res
  }
}