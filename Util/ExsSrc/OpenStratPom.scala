/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import ostrat.pWeb._

class OpenStratPom(val artifactStr: String, versionStr: String) extends PomProject
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = GroupId("com.richstrat")
  override def version: XmlVersion = XmlVersion(versionStr)
  override def dependencies: RArr[PomDep] = RArr(ScalaLibDependency("3.4.1"))
}