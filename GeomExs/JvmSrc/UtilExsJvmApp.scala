/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utiljvm._

object UtilExsJvmApp
{
  def main(args: Array[String]): Unit =
  {
    deb("Starting ExsJvmApp")
    val oDir = args.headOption
    println(oDir)
    oDir.foreach{dirStr =>
      fileWrite(dirStr, "documentation.css", CssDocmentation())
      fileWrite(dirStr, "only.css", OnlyCss())
    }
  }
}