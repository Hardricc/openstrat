/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Test App for [[EMonOld]]s. */
object HelloEmon extends App
{ println("Welcome to Hello Emon. This will printout the first number but not the second.")
  val mi1: ErrBi[Exception, Int] = "4".asInt
  mi1.forSucc(i => println(i.str))//Something happens
  val mi2: ErrBi[Exception, Int] = "2.2".asInt
  mi2.forSucc(i => println(i.str))//Nothing happens.
  val i1: Int = mi2.getElse(0)
  println("You asked for " + i1.str)
  val s1: String = mi2.fold("This really is an Int: " + _)(_ => "This is not an Int")
  println(s1)
  var counter: Int = 10
  println("Counter value = " + counter.str)
  mi1.fold{ err => println("The counter was not changed.") }{ counter += _}
  println("Counter value is now: " + counter.str)
  mi2.fold{ err => println("The counter was not changed.") }{ counter += _ }
  println("Counter value is now: " + counter.str)
  println("MyId".parseTokens)
  import geom._
  val h13 = HexParrX(640)
  val h13a = h13.area
  debvar(h13a)
  val h13ta = h13a / 6
  debvar(h13ta)
}