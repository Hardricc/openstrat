/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWW2
import geom._, pEarth._, pCanv._, pStrat._

case class WWIIGuiOld(canv: CanvasPlatform, scen: WWIIScen) extends EarthAllGuiOld("World War II")
{
  statusText --= "Left click on unit to select, right click to move."
  focusUp = true
  override def saveNamePrefix = "WW2"

  val fHex: OfETile[W2TileAncient, W2SideAncient] => GraphicElems = etog =>
    {
      import etog._         
      val colour: Colour = tile.colour
      val poly = etog.vertDispVecs.fillActive(colour, tile)
      //val sides = etog.ifScaleCObjs(60, ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
      val textOrUnit: GraphicElems = ifScaleCObjs(110, tile.lunits match
        { case s if tScale > 68 & s.nonEmpty => Arr(UnitCounters.infantry(30, s.head, s.head.colour,tile.colour).slate(cen))
          case _ =>
          { val strs: Strings = Strings(cood.base32, cenLL.degStr)
            TextGraphic.lines(strs, 10, cen, colour.contrastBW)//.toArraySeq
          }
        }
      )
      Arr(poly) ++ textOrUnit
    }
    
  def fSide: OfESide[W2TileAncient, W2SideAncient] => GraphicElems = ofs =>
    {
      import ofs._
      ifScaleCObjs(60, side.terr match
        { case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour, (t1, _) => vertDispLine.draw(t1.colour.contrastBW, 1))
          case Straitsold => Arr(vertDispLine.draw(Colour.Blue, 6))
        }
      )
   } 
   
  //def dSides: GraphicElems = ofSidesDisplayFold(fSide)//(OfHexSideReg.implicitBuilder(_, _, _))
      
  def ls: GraphicElems =
  { val gs: GraphicElems = scen.grids.flatMap(_.eGraphicElems(this, fHex, fSide))
    val as: GraphicElems = scen.tops.flatMap(a => a.disp2(this))
    as ++ gs
  }   
  
  mapPanel.mouseUp = (button: MouseButton, clickList, _) => button match
    {      
      case LeftButton =>
      { selected = clickList//.fHead(Arr(), Arr(_))
        statusText = selected.headToStringElse("Nothing Clicked")
        eTop()
      }

      case RightButton => (selected, clickList) match
      { case (List(army: Army), List(newTile: W2TileAncient)) =>
        { army.tile.lunits = army.tile.lunits.removeFirst(_ == army)
          val newArmy = army.copy(newTile)
          newTile.lunits +-= newArmy
          selected = List(newArmy)
          repaintMap()
        }
        case (List(army: Army), as) => debvar(as.length)
        case _ =>
      }

      case mb => deb(mb.toString)
    }
  scale = 1.08.km
  eTop()
  loadView()
  repaintMap()
}