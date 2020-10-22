/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import geom._

/** An experiment. This package works with hexagonal and Square tile grids. The tile objects themselves will not in the general case the contain grid coordinates, although
 * it may be necessary to include this data for complex Tile values interacting with their wider environment. Its fundamental components are the grid data itself.
 * This is just a linear array of tile data. Compile-time typed grid data. So for example a chess board can be represented by a 64 element Arr, its context
 * determines that it is to be interpreted as an 8 by 8 square grid. Grid descriptions that describe the grid representation in the Array and GridFunctions
 * which implement Cood to T. The grid and grid-gui hierarchies currently contain a mix of new and old systems.
 *
 * The package name pGrid was chosen to allow you to use the name "grid" in your code. */
package object prid
{
  def holding = "Holding methos.Silly"
  implicit class GridSlateScaleExtension[T](value: T)(implicit grid: TGrid, evSlate: Slate[T], evScale: Scale[T]) {
    /** Translates Vec2s relative to Grid centre and then scales. */
    /*def gridScale(scale: Double): T =
    { val a = evSlate.slateT(value, - grid.cen)
      evScale.scaleT(a, scale)
    }*/
    /** Translates Vec2s relative to focus and then scales. */
    /*def gridRoordScale(focus: Roord, scale: Double): T =
    { val a = evSlate.slateT(value, - focus.gridVec2)
      evScale.scaleT(a, scale)
    }*/
  }
}