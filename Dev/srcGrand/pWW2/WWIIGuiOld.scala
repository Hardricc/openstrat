/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWW2
import geom._, pglobe._, pEarth._, pgui._, pStrat._, pGrid._

/** Gui for World War 2 scenarios, uses the deprecated ancient tile system. */
case class WWIIGuiOld(canv: CanvasPlatform, scen: WWIIScenOld, startScale: Option[Length] = None, startFocus: Option[LatLong] = None) extends
  EarthAllGuiOld("World War II")
{
  statusText --= "Left click on unit to select, right click to move."
  focusUp = true
  focus = startFocus.getOrElse(50 ll 10)
  startScale.foreach{scale = _ }

  override def saveNamePrefix = "WW2"

  val fHex: OfETile[W2TileAncient, W2SideAncient] => GraphicElems = etog =>
    {
      import etog._         
      val colour: Colour = tile.colour
      val poly = etog.vertDispVecs.fillActive(colour, tile)
      //val sides = etog.ifScaleCObjs(60, ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
      val textOrUnit: GraphicElems = ifScaleCObjs(110, tile.lunits match
        { case s if tScale > 68 & s.nonEmpty => RArr(UnitCounters.infantry(32, s.head, s.head.colour,tile.colour).slate(cen))
          case _ =>
          { val strs: StrArr = StrArr(cood.base32, cenLL.degStr, cood.yxStr)
            TextGraphic.lines(strs, 10, cen, colour.contrastBW)
          }
        }
      )
      RArr(poly) ++ textOrUnit
    }
    
  def fSide: OfESide[W2TileAncient, W2SideAncient] => GraphicElems = ofs =>
    {
      import ofs._
      ifScaleCObjs(60, side.terr match
        { case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour, (t1, _) => vertDispLine.draw(t1.colour.contrastBW, 1))
          case Straitsold => RArr(vertDispLine.draw(Colour.Blue, 6))
        }
      )
   } 
   
  //def dSides: GraphicElems = ofSidesDisplayFold(fSide)//(OfHexSideReg.implicitBuilder(_, _, _))
      
  def ls: GraphicElems =
  { val seas = earth2DEllipse(scale).fill(Ocean.colour)
    val gs: GraphicElems = scen.grids.flatMap(_.eGraphicElems(this, fHex, fSide))
    val as: GraphicElems = scen.tops.flatMap(a => a.disp2(this))
    seas %: as ++ gs
  }   
  
  mapPanel.mouseUp = (button: MouseButton, clickList, _) => button match
    {      
      case LeftButton =>
      { selected = clickList//.fHead(Arr(), Arr(_))
        statusText = selected.headFoldToString("Nothing Clicked")
        eTop()
      }

      case RightButton => (selected, clickList) match
      { case (AnyArrHead(army: ArmyOld), AnyArrHead(newTile: W2TileAncient)) =>
        { army.tile.lunits = army.tile.lunits.removeFirst(_ == army)
          val newArmy = army.copy(newTile)
          newTile.lunits +%= newArmy
          selected = AnyArr(newArmy)
          repaintMap()
        }
        case (AnyArrHead(army: ArmyOld), as) => debvar(as.length)
        case _ =>
      }

      case mb => deb(mb.toString)
    }
 
  eTop()
  loadView()
  repaintMap()
}