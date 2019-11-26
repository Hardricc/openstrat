package ostrat
package pParse

class IdentNum {

}

trait IdentUpperToken extends ExprToken
trait IdentLowerToken extends ExprToken

/** An Alphanumeric Token. It contains a symbol rather than a String to represent the AlphaNumeric token as commonly used Symbols have better
 *  better performance than the equivalent Strings. */
case class IdentLowerOnlyToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "AlphaTokenExpr"
  override def toString: String =  "AlphaToken".appendParenthSemis(srcStr, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def tokenTypeStr: String = "AlphaToken"
}

case class IdentHexaToken(startPosn: TextPosn, srcStr: String) extends IdentUpperToken
{
  def exprName: String = "IdentHexaExpr"
  override def tokenTypeStr: String = "IdentHexaToken"
}

case class IdentLowerTrigToken(startPosn: TextPosn, srcStr: String) extends IdentLowerToken
{
  def exprName: String = "IdentLowerTrigExpr"
  override def tokenTypeStr: String = "IdentLowerTrigToken"
}

/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "HashAlphaTokenExpr"
  override def tokenTypeStr: String = "HashAlphaToken"
}

case class UnderscoreToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def srcStr = "_"
  override def exprName: String = "EmptyClauseExpr"
  override def tokenTypeStr: String = "CommaToken"
}