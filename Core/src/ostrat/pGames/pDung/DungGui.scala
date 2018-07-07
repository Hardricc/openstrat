/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import geom._
import pDisp._
import Colour._
import pGrid._

class DungGui(canv: CanvasPlatform) extends SquareGridGui[DTile, SideBare, DungGrid](canv, Dungeon1)
{  
   mapPanel.backColour = Black
   override def eTop(): Unit = reTop(guButs :+ status)
   
   def fSquare: OfSquareReg[DTile, SideBare, DungGrid] => Disp2 = tog =>
      {
         val tile = tog.tile
         val colour: Colour = tile.colour
         val poly = tog.vertVecs
         val tv = poly.fill(colour)
         val sides = tog.ifScaleCObjs(60, tog.ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
         val tText = tog.ifScaleCObj(60, FillText(tog.cen, tog.xyStr, 14, colour.contrastBW))
         val player = tog.ifScaleIfCObj(60, tile.player, Circle(50).slate(tog.cen).fillDrawFixed(None, Red, 1)    )
         Disp2(List(tv), tText ++ player ++ sides)
      }
   def mapObjs: CanvObjs =  ofTilesDisplayFold[OfSquareReg[DTile, SideBare, DungGrid]](
         fSquare
         //)((t: DTile, gr: DungGrid, gui: TileGridGui[DTile, SideBare, DungGrid]) => new OfSquareReg[DTile, SideBare, DungGrid](t, gr, gui)
               ).collapse//ofSquaresDisplayFold(fTile).collapse   
  
   eTop()
   mapPanel.repaint(mapObjs)
}
