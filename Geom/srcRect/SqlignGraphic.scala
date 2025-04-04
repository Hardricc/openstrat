/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait SqlignGraphic extends RectGraphic, SquareGraphic
{ override def shape: Sqlign
}

trait SqlignGraphicSimple extends SqlignGraphic, RectGraphicSimple, SquareGraphicSimple

/** A fill graphic for a square aligned to the X and Y axes. */
class SqlignFill(val shape: Sqlign, val fillFacet: FillFacet) extends SqlignGraphicSimple, RectFill, SquareFill
{ override def slate(operand: VecPt2): SqlignFill = SqlignFill(shape.slate(operand), fillFacet)
  override def slate(xDelta: Double, yDelta: Double): SqlignFill = SqlignFill(shape.slate(xDelta, yDelta), fillFacet)
  override def scale(operand: Double): SqlignFill = SqlignFill(shape.scale(operand), fillFacet)
  override def negX: SqlignFill = SqlignFill(shape.negX, fillFacet)
  override def negY: SqlignFill = SqlignFill(shape.negY, fillFacet)
  override def rotate90: SqlignFill = SqlignFill(shape.rotate90, fillFacet)
  override def rotate180: SqlignFill = SqlignFill(shape.rotate180, fillFacet)
  override def rotate270: SqlignFill = SqlignFill(shape.rotate270, fillFacet)
  override def prolign(matrix: ProlignMatrix): SqlignFill = SqlignFill(shape.prolign(matrix), fillFacet)
}

object SqlignFill
{
  def apply(shape: Sqlign, fillFacet: FillFacet): SqlignFill = new SqlignFill(shape, fillFacet)
}

class SqlignCompound(val shape: Sqlign, val facets: RArr[GraphicFacet], val childs: RArr[Sqlign => GraphicElems], val adopted: GraphicElems) extends
  SqlignGraphic, RectCompound, ParentGraphic2[Sqlign]
{
  override def children: RArr[Graphic2Elem] = childs.flatMap(ch => ch(shape)) ++ adopted
  override def slate(operand: VecPt2): SqlignCompound = SqlignCompound(shape.slate(operand), facets, childs, children.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): SqlignCompound =
    SqlignCompound(shape.slate(xOperand, yOperand), facets, childs, children.slate(xOperand, yOperand))

  override def scale(operand: Double): SqlignCompound = SqlignCompound(shape.scale(operand), facets, childs, children.scale(operand))
  override def negX: SqlignCompound = SqlignCompound(shape.negX, facets, childs, children.negX)
  override def negY: SqlignCompound = SqlignCompound(shape.negY, facets, childs, children.negY)
}

object SqlignCompound
{
  def apply(shape: Sqlign, facets: RArr[GraphicFacet], childs: RArr[Sqlign => GraphicElems], children: GraphicElems = RArr()): SqlignCompound =
    new SqlignCompound(shape, facets, childs, children)
}