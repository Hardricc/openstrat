/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCanv

trait GuiLaunch
{
  def launch(s2: Int, s3: String): (CanvasPlatform => Any, String)
}