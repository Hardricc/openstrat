/* Copyright 2024 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pszio
import zio._, Console._, http._, ostrat._, geom._, prid.phex._

object HelloWorld extends ZIOAppDefault
{
  val handHome = handler(Response.html("Blah"))//pDev.IndexPage.out))
  val pt = ostrat.geom.Pt2(4, 5)
  val hex1 = HCen(4, 4)

  val routes: Routes[Any, Response] = Routes(
    Method.GET / "" -> handHome,
    Method.GET / "index.html" -> handHome,
    Method.GET / "index.htm" -> handHome,
    Method.GET / "index" -> handHome,
    Method.GET / "geom.html" -> handler(Response.html(s"This is a pt: $pt"))
  )
  val app: HttpApp[Any] = routes.toHttpApp

  override val run = Server.serve(app).provide(Server.default)
}