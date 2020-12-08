/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** The typeclass trait for transforming an object in 2d geometry. Note overrides necessary to preserve type. */
trait AffineTrans[T] extends TransSim[T]
{ def trans(obj: T, f: Pt2 => Pt2):  T
  override def slate(obj: T, offset: Vec2Like): T = trans(obj, _.slate(offset))
  override def scale(obj: T, operand: Double): T = trans(obj, _.scale(operand))
  def shear(obj: T, xScale: Double, yScale: Double): T = trans(obj, v => Pt2(v.x * yScale, v.y * xScale))
  override def rotate(obj: T, angle: AngleVec): T = trans(obj, _.rotate(angle))

  def mirrorYOffset(obj: T, xOffset: Double): T = trans(obj, _.reflectYParallel(xOffset))
  def mirrorXOffset(obj: T, yOffset: Double): T = trans(obj, _.reflectXParallel(yOffset))
  override def reflectSegT(obj: T, line: LineSeg): T = trans(obj, _.reflect(line))
  override def reflectT(obj: T, line: Line): T = trans(obj, _.reflect(line))
}

/** The companion object for the Trans[T] typeclass, containing instances for common classes. */
object AffineTrans
{
  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: AffineTrans[A]): AffineTrans[AA] =
    (obj, f) => obj.map(el => ev.trans(el, f))

  implicit def fromTranserAllImplicit[T <: AffinePreserve]: AffineTrans[T] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T]

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: AffineTrans[A]): AffineTrans[F[A]] =
    (obj, f) => evF.mapT(obj, el => evA.trans(el, f))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: AffineTrans[A]): AffineTrans[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
}