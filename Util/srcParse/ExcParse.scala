/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Parser [[Exception]]. */
trait ExcParse extends Exception

/** AST [[Exception]]. */
case class ExcAst(tp: TextPosn, detail: String) extends Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail) with
  ExcParse

object FailAst
{
  def apply[A](tp: TextPosn, detail: String): Fail[ExcAst, A] = new Fail[ExcAst, A](ExcAst(tp, detail))
}
case class ExcLexar(tp: TextPosn, detail: String) extends Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail) with
  ExcParse

object FailLexar
{
  def apply[A](tp: TextPosn, detail: String): Fail[ExcLexar, A] = new Fail[ExcLexar, A](ExcLexar(tp, detail))
}