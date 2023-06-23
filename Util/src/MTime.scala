/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import reflect.ClassTag//java.util.{GregorianCalendar => JGreg}

/** An instant of time specified to the nearest minute. By default uees Gregorian Calender */
class MTime(val int1: Int) extends AnyVal with Ordered[MTime] with Int1Elem
{
  def minute: Int = int1 %% 60
  def hour: Int = (int1 %% 1440) / 60
  def day: Int = (int1 %% 44640) / 1440
  def monthNum: Int = (int1 %% 535680) / 44640
  def year: Int = int1 / 535680
  override def compare(that: MTime): Int = int1 match
  { case i if i > that.int1 => 1
    case i if i == that.int1 => 0
    case _ => -1
  }

  private def monthStr: String = monthNum match
  { case 1 => "January"
    case 2 => "Febuary"
    case 3 => "March"
    case 4 => "April"
    case 5 => "May"
    case 6 => "June"
    case 7 => "July"
    case 8 => "August"
    case 9 => "September"
    case 10 => "October"
    case 11 => "Novemeber"
    case 12 => "December"
    case _ => "Unknown Month"
  }

  override def toString: String = year.str -- monthStr -- day.str
}

object MTime
{
  def apply(year: Int, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0): MTime =
    new MTime(minute + hour * 60 + day * 1440 + month * 44640 + year * 535680)
}

class MTime2(val int1: Int, val int2: Int)
{
  def time1: MTime = new MTime(int1)
  def time2: MTime = new MTime(int2)
}

object MTime2{
  def apply(time1: MTime, time2: MTime):MTime2 = new MTime2(time1.int1, time2.int1)
}

class MTimeSeries[A](val arrayInt: Array[Int], arrayA: Array[A])
{
  def seriesNum: Int = arrayA.length
  def find(time: MTime): Option[A] = if (time.int1 < arrayInt(0) | time.int1 > arrayInt(seriesNum)) None
  else
  { def loop(i: Int): Option[A] = if (time.int1 >= arrayInt(i)) Some(arrayA(i)) else loop(i + 1)
    loop(0)
  }
}

object MTimeSeries
{
  def apply[A](startTime: MTime, pairs: (MTime, A)*)(implicit ct: ClassTag[A]): MTimeSeries[A] =
  { val len = pairs.length
    val intArray = new Array[Int](len + 1)
    intArray(0) = startTime.int1
    val arrayA = new Array[A](len)
    var i = 0
    pairs.foreach{ pair =>
      intArray(i + 1) = pair._1.int1
      arrayA(i) = pair._2
      i += 1
    }
    new MTimeSeries[A](intArray, arrayA)
  }

  def apply[A](a1: A, pairs: (MTime, A)*)(implicit startEnd: MTime2, ct: ClassTag[A]): MTimeSeries[A] = {
    val len = pairs.length + 1
    val intArray = new Array[Int](len + 1)
    intArray(0) = startEnd.int1
    val arrayA = new Array[A](len)
    arrayA(0) = a1
    var i = 1
    pairs.foreach { pair =>
      intArray(i) = pair._1.int1
      arrayA(i) = pair._2
      i += 1
    }
    intArray(len) = startEnd.int2
    new MTimeSeries[A](intArray, arrayA)
  }
}

/*
object TimeSpan
{
   def apply(ticsInMilliSeconds: Long): TimeSpan = TimeSpan(ticsInMilliSeconds) 
}
class TimeSpan(val tics: Long) extends AnyVal
{
   def +(operand: TimeSpan): TimeSpan = TimeSpan(tics + operand.tics)
   def -(operand: TimeSpan): TimeSpan = TimeSpan(tics - operand.tics)
   def *(operand: Long): TimeSpan = TimeSpan(tics * operand)
   def *(operand: Double): TimeSpan = TimeSpan((tics * operand).toLong)
   def /(operand: Long): TimeSpan = TimeSpan(tics / operand)
   def /(operand: Double): TimeSpan = TimeSpan((tics / operand).toLong)
   def toSecsonds: Long = tics / 1000
   def toMinutes: Long = tics / 60000
   def toHours: Long = tics / 3600000
}
object TimeDate
{
   def apply(ticsInMilliSecondsRelativeTo1970: Long): TimeDate = new TimeDate(ticsInMilliSecondsRelativeTo1970)
   def apply(year: Int, month: Int, day: Int) = new TimeDate(new JGreg(year, month, day).getTimeInMillis())   
}

class TimeDate(val tics: Long) extends AnyVal
{
   private def mutT: JGreg = new JGreg(1, 1, 1)
   def +(operand: TimeSpan): TimeDate = TimeDate(tics + operand.tics)
   def -(operand: TimeSpan): TimeDate = TimeDate(tics - operand.tics)
   def --(operand: TimeDate): TimeSpan = TimeSpan(tics - operand.tics)
   def date: String =
   {
      val m = mutT
      m.setTimeInMillis(tics)
      m.get(1).toString + " " + getJGEra(m) + " " + getJGMonth(m) + " " + m.get(4).toString      
   }
   private def getJGMonth(jg: JGreg): String = jg.get(2) match
   {
      case 1 => "January"
      case 2 => "Febuary"
      case 3 => "March"
      case 4 => "April"
      case 5 => "May"
      case 6 => "June"
      case 7 => "July"
      case 8 => "August"
      case 9 => "September"
      case 10 => "October"
      case 11 => "Novemeber"
      case 12 => "December"   
      case _ => "Unknown Month"   
   }
   private def getJGEra(jg: JGreg): String = jg.get(0) match
   {
      case 1 => "AD"
      case v => v.toString   
   }      
}

object Hour
{
   def apply: TimeSpan = new TimeSpan(3600000)
   def apply(numberOfWholeHours: Long): TimeSpan = new TimeSpan(numberOfWholeHours * 3600000)
   def inDou(value: Double): TimeSpan = new TimeSpan((value * 3600000).toLong)
}
object Minute
{
   def apply: TimeSpan = new TimeSpan(60000)
   def apply(numberOfWholeMinutes: Long): TimeSpan = new TimeSpan(numberOfWholeMinutes * 60000)
   def inDou(value: Double): TimeSpan = new TimeSpan((value * 60000).toLong)
}
object Second
{ 
   def apply: TimeSpan = new TimeSpan(1000)
   def apply(numberOfWholeSeconds: Long): TimeSpan = new TimeSpan(numberOfWholeSeconds * 1000)
   def inDou(value: Double): TimeSpan = new TimeSpan((value * 1000).toLong)
}

object WholeHours
{
	def apply(numberOfWholeHours: Int): WholeHours = new WholeHours(numberOfWholeHours)
}
class WholeHours(val value: Int) extends AnyVal
{
   def +(operand: WholeHours): WholeHours = new WholeHours(value + operand.value)
   def toTimeSpan: TimeSpan = new TimeSpan(value * 3600000)
}*/
