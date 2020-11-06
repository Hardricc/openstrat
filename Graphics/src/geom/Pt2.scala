/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._, collection.mutable.ArrayBuffer

/** A 2 dimensional point. It can be used to for translations of 2 dimensional points. Thanks to Rene Descarte for this. */
final class Pt2(val x: Double, val y: Double) extends Vec2Like with ProdDbl2
{
  override def toString: String = Pt2.persistImplicit.show(this, 0)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Pt2]
  @inline override def _1: Double = x
  @inline override def _2: Double = y
  override def productPrefix: String = "Pt2"

  /** Adds this Vector to a second 2 dimensional vector. */
  def +(operand: Vec2Like): Pt2 = Pt2(x + operand.x, y + operand.y)

  /** Subtracts the operand [[Vec2]] 2D vector from this 2D point. Returns a [[Pt2]]. */
  def -(operand: Vec2): Pt2 = Pt2(x - operand.x, y - operand.y)

  /** Subtracts the operand 2-idmensianl vecotr from this 2-dimensional vector. */
  @deprecated def -*-(operand: Vec2Like): Pt2 = Pt2(x - operand.x, y - operand.y)

  /** Subtracts the operand 2D point from this 2D point to get the relative Vector. Returns a [[Vec2]]. */
  def vecFrom(operand: Pt2): Vec2 = Vec2(x - operand.x, y - operand.y)

  /** Subtracts this 2D point from the operand 2D point to get the relative Vector. Returns a [[Vec2]]. */
  def vecTo(operand: Pt2): Vec2 = Vec2(operand.x - x, operand.y - y)

  /** Gives the positive scalar distance between this and the operand Vec2. */
  def distTo(operand: Pt2): Double = (operand -*- this).magnitude

  /** The average of this and the operand Pt2. The mid point between this point and the operand second point. */
  def mid(point2: Pt2): Pt2 = (this + point2) / 2

  def strMod(f: Double => String): String = "Pt2".appendParenthSemis(f(x), f(y))
  def str0: String = strMod(_.str0)
  def str1: String = strMod(_.str1)
  def str2: String = strMod(_.str2)
  def str3: String = strMod(_.str3)

  override def equals(other: Any): Boolean = other match
  { case Pt2(px, py) => (x =~ px) && (y =~ py)
    case _ => false
  }

  def addXY (otherX: Double, otherY: Double): Pt2 = Pt2(x + otherX, y + otherY)
  def subXY (otherX: Double, otherY: Double): Pt2 = Pt2(x - otherX, y - otherY)


  @inline def *(factor: Double): Pt2 = Pt2(x * factor, y * factor)
  @inline def /(divisor: Double): Pt2 = Pt2(x / divisor, y / divisor)

  def addX(adj: Double): Pt2 = Pt2(x + adj, y)
  def addY(adj: Double): Pt2 = Pt2(x, y + adj)
  def subX(adj: Double): Pt2 = Pt2(x - adj, y)
  def subY(adj: Double): Pt2 = Pt2(x, y - adj)

  def yScale(factor: Double): Pt2 = Pt2(x, y * factor)
  def xScale(factor: Double): Pt2 = Pt2(x * factor, y)
  def xyScale(xOperand: Double, yOperand: Double): Pt2 = Pt2(x * xOperand, y * yOperand)

  /** Reflects or mirrors this Vec2 across a line, returning new Vec2. */
  def reflect(lineLike: LineLike): Pt2 = lineLike match {
    case xl: XLine => reflectXLine(xl)
    case yl: YLine => reflectYLine(yl)
    case r: Ray => ???
    case lineSeg: LineSeg => {
      val v1 = lineSeg.pStart
      val v2 = lineSeg.pEnd
      val lineDelta = v2 -*- v1
      val lineUnitVector = lineDelta / lineDelta.magnitude
      2 * v1 -*- this -*- 2 * (v1 -*- this).dot(lineUnitVector) * lineUnitVector
    }
  }

  /** Reflects, mirrors this Vec2 across an XLine. */
  def reflectXLine(line: XLine): Pt2 = ???

  /** Reflects, mirrors this Vec2 across a YLine. */
  def reflectYLine(line: YLine): Pt2 = ???

  /** Reflects ,mirrors along the X axis by negating Y. */
  def negY: Pt2 = Pt2(x, -y)

  /** Reflects, mirrors along the Y axis by negating X. */
  def negX: Pt2 = Pt2(-x, y)

  /** Reflects, mirrors along the y = yOffset line that is parallel to the  X axis. */
  def reflectXParallel(yOffset: Double): Pt2 = Pt2(x, -y + yOffset * 2)

  /** Reflects, mirrors along the x = xOffset line, that is parallel to the Y axis by negating X. */
  def reflectYParallel(xOffset: Double): Pt2 = Pt2(-x + xOffset * 2, y)

  def xShear(operand: Double): Pt2 = Pt2(x, y * operand)
  def yShear(operand: Double): Pt2 = Pt2(x * operand, y)

  @inline def prolign(m: ProlignMatrix): Pt2 = m.vecTrans(this)

  /** Where xnd y is a map on the surface ofa sphere. Currently not working for angles greater than Pi / 2 */
  def toLatLong(radius: Double): LatLong = LatLong.radians(math.acos(y / radius), math.acos(x / radius))

  /** Reverses the y coordinate. Useful for translating between canvases where the y axis measures down and coordinate systems where y is up */
  def inverseY: Pt2 = Pt2(x, -y)

  def toTuple: Tuple2[Double, Double] = (x, y)
  def pp(z: Double): Pt3 = Pt3(x, y, z)

  /** rotates the vector 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotate90: Pt2 = Pt2(-y, x)

  /** Rotates the vector 180 degrees or Pi radians. */
  @inline def rotate180: Pt2 = Pt2(-x, -y)

  /** rotates the vector 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotate270: Pt2 = Pt2(y, -x)

  /** Line segment from this point to the parameter point. */
  def lineTo(pt2: Pt2): LineSeg = LineSeg(this, pt2)

  /** Line segment from this point to along the given angle for the given magnitude to point 2. */
  def lineAlong(angle: Angle, magnitude: Double): LineSeg = LineSeg(this, this + angle.toVec2(magnitude))

  /** Rotates this vector through the given angle around the origin. */
  def rotate(a: Angle): Pt2 = a match
  { case Deg0 => this
    case Deg90 => rotate90
    case Deg180 => rotate180
    case Deg270 => rotate270
    case a => Pt2(x * a.cos - y * a.sin, x * a.sin + y * a.cos)
  }

  def rotateRadians(r: Double): Pt2 = Pt2(x * cos(r) - y * sin(r),
    { val ya = x * sin(r)
      val yb =y * cos(r)
      ya + yb
    })

  /** Rotates this vector through the given angle around the centre of rotation passed as the first parameter. */
  def rotateAbout(centre: Pt2, a: Angle): Pt2 =
  {
    val rel: Vec2 = this vecFrom centre
    val rel2: Vec2 = a match {
      case Deg0 => rel
      case Deg90 => rel.rotate90
      case Deg180 => rel.rotate180
      case Deg270 => rel.rotate270
      case a => Vec2(rel.x * a.cos - rel.y * a.sin, rel.x * a.sin + rel.y * a.cos)
    }
    centre + rel2
  }

  def centreSquare(length: Double): PolygonImp =
  { val r = length / 2.0
    PolygonImp(-r pp r, r pp r, r pp -r, -r pp -r).slate(x, y)
  }

  def textAt(str: String, fontSize: Int, fontColour: Colour = Colour.Black): TextGraphic = TextGraphic(str, this, fontSize, fontColour)
  def toText(fontSize: Int = 10, fontColour: Colour = Colour.Black): TextGraphic = TextGraphic(str1, this, fontSize, fontColour)

  /*def arcControlPoint(pt2: Pt2, arcCentre: Pt2): Pt2 =
  { val angle1 = (this - arcCentre).angle
    val angle2 = (pt2 - arcCentre).angle
    val resultAngle =  angle1.bisect(angle2)
    val alphaAngle =  resultAngle / 2
    val radius = (pt2 - arcCentre).magnitude
    arcCentre + resultAngle.toVec2(radius / alphaAngle.cos)
  }*/

  def linesCross(armLength: Double = 5): Seq[LineSeg] = Seq( new LineSeg(x - armLength, y , x + armLength, y),
    new LineSeg(x, y - armLength, x, y + armLength))

  /** Not sure about this method */
  def drawCross(armLength: Double, lineColour: Colour, lineWidth: Double): LinesDraw =
    LineSegs.doubles(x - armLength, y, x + armLength, y,
    x, y - armLength, x, y + armLength).draw(lineWidth, lineColour)

  def alignMatrix(matrix: AlignMatrix): Pt2 = Pt2(x * matrix.xFactor, y * matrix.yFactor) + matrix.vDelta
}

/** Companion object for Vec2. contains Apply factory method. */
object Pt2
{ def apply(x: Double, y: Double): Pt2 = new Pt2(x, y)
  def unapply(orig: Pt2): Option[(Double, Double)] = Some((orig.x, orig.y))
  def fromAngle(angle: Angle, scalar: Double = 1.0): Pt2 = angle.toVec2(scalar)

  implicit class Pt2Implicit(thisPt: Pt2)
  { def * (operand: Dist): Dist2 = Dist2(thisPt.x * operand, thisPt.y * operand)


  }

  def circlePt(angle: Double): Pt2 = Pt2(cos(angle), sin(angle))
  def circlePtClockwise(angle: Double): Pt2 = Pt2(cos(angle), - sin(angle))

  implicit class Vec2IterableExtension(thisIter: Iterable[Pt2])
  { def toPolygon: PolygonImp = thisIter.toArrProdHomo
  }

  implicit val persistImplicit: PersistD2[Pt2] = new PersistD2[Pt2]("Vec2", "x", _.x, "y", _.y, apply)

  implicit val vec2sBuildImplicit: ArrProdDbl2Build[Pt2, Pt2s] = new ArrProdDbl2Build[Pt2, Pt2s]
  { type BuffT = Vec2Buff
    override def fromDblArray(array: Array[Double]): Pt2s = new Pt2s(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): Vec2Buff = new Vec2Buff(inp)
  }
}