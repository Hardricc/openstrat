/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.2.2snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor = "3.0"
val scalaMinor = "0"
//lazy val jarVersion = "_" + scalaMajor + "-" + versionStr + ".jar"
//ThisBuild/scalaVersion := scalaMajor + "." + scalaMinor
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

//scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
// "-feature", "-language:implicitConversions", "UTF-8", "-deprecation", "-explaintypes"),// "-Xsource:3"),//, "-Ywarn-value-discard", "-Xlint"),

lazy val root = (project in file(".")).aggregate(GraphicsJvm3, TilingJvm3, EarthJvm3, DevJvm3)
lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
ThisBuild/baseDir := (ThisBuild/baseDirectory).value


def baseProj(srcsStr: String, nameStr: String) = Project(nameStr, file("Dev/SbtDir/" + nameStr)).settings(
  moduleDir := baseDir.value / srcsStr,  
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
  resourceDirectory := moduleDir.value / "res",
)

def jvm2Proj(srcsStr: String) = baseProj(srcsStr, srcsStr + "Jvm2").settings(
  scalaVersion := "2.13.6",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-encoding", "UTF-8"),
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx", "src2", "srcExs", "srcExsJvm", "srcExsFx").map(moduleDir.value / _),
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
  Test/scalaSource := moduleDir.value / "testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/resourceDirectory :=  moduleDir.value / "testRes",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
)

def jvm3Proj(srcsStr: String) = baseProj(srcsStr, srcsStr + "Jvm3").settings(
  scalaVersion := "3.0.0",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
  testFrameworks += new TestFramework("utest.runner.Framework"), 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test",
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx", "src3", "srcExs", "srcExsJvm", "srcExsFx").map(moduleDir.value / _),
  Test/scalaSource := moduleDir.value / "testSrcExs",
  Test/unmanagedSourceDirectories := List(moduleDir.value / "testSrc", (Test/scalaSource).value),
  Test/resourceDirectory :=  moduleDir.value / "testResExs",
  Test/unmanagedResourceDirectories := List(moduleDir.value / "testRes", (Test/resourceDirectory).value),
)

lazy val MacrosJvm2 = jvm2Proj("Macros")
lazy val MacrosJvm3 = jvm3Proj("Macros")

lazy val GraphicsJvm2 = jvm2Proj("Graphics").dependsOn(MacrosJvm2).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GraphicsJvm3 = jvm3Proj("Graphics").dependsOn(MacrosJvm3).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val TilingJvm2 = jvm2Proj("Tiling").dependsOn(GraphicsJvm2)
lazy val TilingJvm3 = jvm3Proj("Tiling").dependsOn(GraphicsJvm3)
lazy val EarthJvm2 = jvm2Proj("Earth").dependsOn(TilingJvm2)
lazy val EarthJvm3 = jvm3Proj("Earth").dependsOn(TilingJvm3)

lazy val DevJvm3 = jvm3Proj("Dev").dependsOn(EarthJvm3).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  Test/scalaSource := moduleDir.value / "testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/resourceDirectory :=  moduleDir.value / "testRes",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

/*val libModules =  List("Util", "Graphics", "Tiling", "Earth")

lazy val StratLib = Project("StratLib", file("Dev/SbtDir/StratLib"))/*.dependsOn(UtilMacros)*/.settings(commonSett).settings(
  scalaSource := baseDir.value / "Util/src",
  Compile/scalaSource := baseDir.value / "Util/src",

  Compile/unmanagedSourceDirectories := libModules.flatMap(nameStr => List("src", "srcJvm").
    map(endStr => baseDir.value / nameStr / endStr)),

  Compile/unmanagedResourceDirectories := libModules.map(str => (ThisBuild/baseDirectory).value / str / "res"), 
  Test/scalaSource := baseDir.value / "Util/testSrc",
  Test/unmanagedSourceDirectories := List(),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15",
)

lazy val StratExs = Project("StratExs", file("Dev/SbtDir/StratExs")).dependsOn(StratLib).settings(commonSett).settings(
  scalaSource := baseDir.value / "Util/srcExs",
  Compile/scalaSource := baseDir.value / "Util/srcExs",

  Compile/unmanagedSourceDirectories := libModules.flatMap(nameStr => List("srcExs", "srcExsJvm", "srcExsFx").
    map(endStr => baseDir.value / nameStr / endStr)),

  Compile/unmanagedResourceDirectories := libModules.map(str => baseDir.value / str / "resExs"), 
  Test/scalaSource := baseDir.value / "Util/testSrcExs",
  Test/unmanagedSourceDirectories := List(),
)*/

val docDirs: List[String] = List("Util", "Graphics", "Tiling", "Earth", "Dev")

lazy val custDoc = taskKey[Unit]("Aims to be a task to aid buiding ScalaDocs")
custDoc :=
{ val t1 = (DocMain/Compile/doc).value
  val t2 = (DocJs/Compile/doc).value
  println("Main docs and Js docs built")
}

lazy val DocMain = (project in file("Dev/SbtDir/DocMain"))/*.dependsOn(UtilMacros)*/.settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJvm", el + "/srcExs", el + "srcFx")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
  Compile/doc/scalacOptions ++= Seq("-groups"),
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs"))/*.dependsOn(UtilMacrosJs)*/.settings(
  name := "OpenStrat",
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJs", el + "/srcExs")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  Compile/doc/scalacOptions ++= Seq("-groups"),
)

def jsProj(name: String) = Project(name + "Js", file("Dev/SbtDir/" + name + "Js")).enablePlugins(ScalaJSPlugin).settings(
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
  //libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0",
  //libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.1.0").cross(CrossVersion.for3Use2_13),
  scalaSource := (ThisBuild/baseDirectory).value / name / "src",
  Test/scalaSource := (ThisBuild/baseDirectory).value / name / "testSrc",

  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.7.10" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),  
)

lazy val GraphicsJs = jsProj("Graphics")/*.dependsOn(UtilMacrosJs)*/.settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs", "src3").map(s => baseDir.value / "Graphics" / s)
)

lazy val TilingJs = jsProj("Tiling").dependsOn(GraphicsJs).settings(
  Compile/unmanagedSourceDirectories := List("Tiling/src", "Tiling/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val EarthJs = jsProj("Earth").dependsOn(TilingJs).settings(  
  Compile/unmanagedSourceDirectories := List("Earth/src", "Earth/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val DevJs = jsProj("Dev").dependsOn(EarthJs).settings(
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/srcJs", "Util/srcExs", "Graphics/srcExs", "Tiling/srcExs", "Earth/srcExs").
    map(s => (ThisBuild/baseDirectory).value / s),
)

def dottySettings = List(
	scalaVersion := "3.0.0",
	resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns),
	scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

lazy val GraphicsDot = Project("GraphicsDot", file("Dev/SbtDir/GraphicsDot")).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Graphics/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Graphics/src",

  Compile/unmanagedSourceDirectories := List(scalaSource.value, (ThisBuild/baseDirectory).value / "Graphics/srcExs",
    (ThisBuild/baseDirectory).value / "Graphics/src3", (ThisBuild/baseDirectory).value / "Graphics/srcJvm", (ThisBuild/baseDirectory).value / "Graphics/srcFx"),

  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Graphics/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15",
  Compile/mainClass := Some("ostrat.DotMain"),
)

lazy val TilingDot = Project("TilingDot", file("Dev/SbtDir/TilingDot")).dependsOn(GraphicsDot).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Tiling/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Tiling/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value, (ThisBuild/baseDirectory).value / "Tiling/srcExs"),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Tiling/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val EarthDot = Project("EarthDot", file("Dev/SbtDir/EarthDot")).dependsOn(TilingDot).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Earth/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Earth/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Earth/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val DevDot = Project("DevDot", file("Dev/SbtDir/DevDot")).dependsOn(EarthDot).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Dev/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Dev/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value, (ThisBuild/baseDirectory).value / "Dev/srcFx"),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Dev/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Compile/unmanagedResourceDirectories := List((ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

lazy val UtilMacrosNat = Project("UtilMacrosNat", file("Dev/SbtDir/UtilMacrosNat")).enablePlugins(ScalaNativePlugin).settings(  
  scalaSource := (ThisBuild/baseDirectory).value / "Util/srcMacros",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/srcMacros",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  //libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
)

lazy val UtilNat = Project("UtilNat", file("Dev/SbtDir/UtilNat")).dependsOn(UtilMacrosNat).enablePlugins(ScalaNativePlugin).settings(  
  scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
 )

lazy val GraphicsNat = Project("GraphicsNat", file("Dev/SbtDir/GraphicsNat")).dependsOn(UtilNat).enablePlugins(ScalaNativePlugin).settings(  
  scalaSource := (ThisBuild/baseDirectory).value / "Graphics/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Graphics/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value, (ThisBuild/baseDirectory).value / "Graphics/srcNat"),
 )

lazy val DevNat = Project("DevNat", file("Dev/SbtDir/DevNat")).dependsOn(GraphicsNat).enablePlugins(ScalaNativePlugin).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Dev/srcNat",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Dev/srcNat",
  Compile/unmanagedSourceDirectories := List((Compile/scalaSource).value),
  resourceDirectory := (ThisBuild/baseDirectory).value / "Dev/resNat",
  Compile/resourceDirectory := (ThisBuild/baseDirectory).value / "Dev/resNat",
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value),
)  