/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom._

/** The tile grid package, replacing the old pGrid package. This package works with hexagonal and Square tile grids. There are separate class for the
 * different types of Coordinates [[HCen]], [[SqCen]], [[HSide]], etc. The tile objects themselves will not in the general case the contain grid
 * coordinates, although it may be necessary to include this data for complex tile values interacting with their wider environment. Its fundamental
 * components are the grid data itself. This is just a linear array of tile data. Compile-time typed grid data. So for example a chess board can be
 * represented by a 64 element Arr, its context determines that it is to be interpreted as an 8 by 8 square grid. Grid descriptions that describe the
 * grid representation in the Array and GridFunctions which implement Cood to T. The grid and grid-gui hierarchies currently contain a mix of new and
 * old systems.
 *
 * The package name prid, short for package grid, was chosen to allow you to use the name "grid" in your code. */
package object prid
{
  implicit class GridSlateScaleExtension[T](value: T)(implicit grid: TGrid, evSlate: Slate[T], evScale: Scale[T])
  {
    /** Translates Vec2s relative to Grid centre and then scales. */
    def gridScale(scale: Double): T =
    { val v = - grid.cenVec
      val a = evSlate.SlateXYT(value, v.x, v.y)
      evScale.scaleT(a, scale)
    }

    /** Translates Vec2s relative to focus and then scales. */
    def gridCoordScale(focus: TCoord, scale: Double): T =
    { val v = -focus.toVec
      val a = evSlate.SlateXYT(value, v.x, v.y)
      evScale.scaleT(a, scale)
    }
  }
  val hcStepSomes: Arr[HexStep] = Arr(HexStepUR, HexStepRt, HexStepDR, HexStepDL, HexStepLt, HexStepUL)
  val hcSteps: Arr[HexStepOpt] = HexStepNone +: hcStepSomes

  val scStepSomes: Arr[SqStep] = Arr()//HexStepUR, HexStepRt, HexStepDR, HexStepDL, HexStepLt, HexStepUL)
  val scSteps: Arr[SqStepOpt] = SqStepNone +: scStepSomes

  implicit class IntGridImplicit(thisInt: Int)
  { /** Syntax for succinct [[HCen]] notation. */
    def hc (c: Int): HCen = HCen(thisInt, c)

    /** Syntax for succinct [[HCen]] notation. */
    def hs (c: Int): HSide = HSide(thisInt, c)
  }
}