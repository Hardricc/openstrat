/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

case class GitProj(path: String, projName: String)
{
  def fullPath = path / projName
  def scalaVersionStr: String = "3.1.0"

  def ignoreStr =
  """project/project/
    |target/
    |DocProj/
    |DevData/
    |bin/
    |out/
    |SbtDir/
    |.*/""".stripMargin

  def ignoreWrite: Unit = fileWrite(fullPath,".gitignore", ignoreStr)

  def sbtStr: String =
    s"""
      |name := "$projName"
      |scalaVersion := $scalaVersionStr
      |Compile/scalaSource := baseDirectory.value / "src"
      |scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8")""".stripMargin

  def sbtWrite: Unit = fileWrite(fullPath,projName + ".sbt", sbtStr)

  def mainStr =
  s"""
      |object ${projName}App extends App{
      |println("Hello from $projName")
      |}""".stripMargin

  def mainWrite: Unit = fileWrite(fullPath / "src",projName + "App.scala", mainStr)

  def millStr: String =
    """
      |// build.sc
      |import mill._, scalalib._
      |
      |object Common extends ScalaModule
      |{ def scalaVersion = $scalaVersionStr
      |  def sources = T.sources(millSourcePath / "../src")
      |}
      |""".stripMargin

  def millWrite: Unit = fileWrite(fullPath,"build.sc", millStr)

  def apply: Unit =
  { ignoreWrite
    sbtWrite
    millWrite
    mainWrite
  }
}
