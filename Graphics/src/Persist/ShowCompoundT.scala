/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Show trait for Compound types contain elements, requiring the Show class or classes for the type or types of the constituent elements. */
trait ShowCompoundT[R] extends ShowT[R]
{ final override def strT(obj: R): String = showT(obj, Show.Standard, -1)
}

/** Persistence base trait for PersistCase and PersistSeqLike. Some methods probably need to be moved down into sub classes. */
trait PersistCompound[R] extends ShowCompoundT[R] with Persist[R]
{
  override def fromExpr(expr: ParseExpr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? //fromParameterStatements(sts)
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

trait ShowSeqLike[A, R] extends ShowCompoundT[R]
{ def evA: ShowT[A]
  override def typeStr = "Seq" + evA.typeStr.enSquare
  //override def syntaxDepth(obj: R): Int = obj.evA.syntaxDepth() + 1
}