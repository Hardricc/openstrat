/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

trait StagingBuild
{
  def stageDocs(path: DirPathAbs): Unit = {
    val docPath = path / "Documentation"
    fileWrite(docPath, "apps.html", AppsPage.out)
    fileWrite(docPath, "util.html", UtilPage.out)
    fileWrite(docPath, "geom.html", geom.GeomPage.out)
    fileWrite(docPath, "tiling.html", prid.TilingPage.out)
    fileWrite(docPath, "earth.html", pEarth.EarthPage.out)
    fileWrite(docPath, "egrid.html", EGridPage.out)
    fileWrite(docPath, "dev.html", pDev.DevPage.out)
    fileWrite(docPath, "newdevs.html", pDev.NewDevsPage.out)
    fileWrite(docPath, "documentation.css", CssDocumentation())
    fileWrite(path, "only.css", OnlyCss())
  }
}

object StagingMill extends StagingBuild
{
  def main(args: Array[String]): Unit =
  {
    deb("Starting StagingMill")
    stagingPathDo { path =>
      debvar(path)
      val res1 = fileCopy("/openstrat/out/AppJs/Diceless/fullLinkJS.dest/main.js", "/CommonSsd/Staging/earthgames/dicelessapp.js")
      debvar(res1)
    }
  }
}