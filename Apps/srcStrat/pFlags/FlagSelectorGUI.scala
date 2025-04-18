/* Copyright 2021-25 w0d, Rich Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom._, pgui._, Colour._

/**    NB: Assumes Flag.ratio is always <=2. :NB: From Left | Right TODO: drag bar, click base, spring scroll, touch, pixel, clip, effects
 *  separate scrollbar, vertical scrollbar */
case class FlagSelectorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Flags Are Ace")
{ //val #_less = "Less".intern()
  //val #_more = "More".intern()
  //val #_pageLess = "PageLess".intern()
  //val #_pageMore = "PageMore".intern()
  //val #_first = "First".intern()
  //val #_last = "Last".intern()
  //var selectedFocused = false

  var viewIndex, itemsPerUnitScroll, iScrollStep, jScrollStep: Int = 0
  var selectedIndex = -1
/**/
  var listOfFlags: RArr[Flag] = RArr(PapuaNewGuinea, Eritrea, India, Iraq, CCCP, CzechRepublic, Colombia, Chile, Cyprus, Armenia, Austria, Belgium,
   Chad, China, England, France, Germany, Germany1871, Italy, Ireland, Japan, Russia, USSR, Swastika, UnitedKingdom, UnitedStates, WhiteFlag,
   CommonShapesInFlags)

  val itemCount: Int = listOfFlags.length // 224 //
  val itemsPerRow: Int = 5  //  columns
  val itemsPerCol: Int = 3  //  rows
  val itemsPerPage: Int = itemsPerRow * itemsPerCol
  val pages: Int = 1 + (itemCount - 1) / itemsPerPage

  // listOfFlags = Arr[Flag](); for(i <- 0 to itemCount - 1) { val thisColor = Colour.fromInts(scala.util.Random.nextInt(200) + 55, scala.util.Random.nextInt(200) + 55, scala.util.Random.nextInt(200) + 55);
  // listOfFlags = listOfFlags ++ Arr(TextFlagMaker(i.toString, thisColor)) }

  /** viewport width */
  val vWidth = 750

  /** viewport height */
  val vHeight = 310

  /** viewport Header size */
  val vHeaderSize = 50

  /** viewport cell width */
  val vCellWidth = 150

  /** viewport cell height */
  val vCellHeight = 100

  /** viewport common scale */
  val vCommonScale = 100

  //val scrollport = Map(
  val maxBarWidth = vWidth  - 80
  val minBarWidth =  20
  val isScrollHorizontal = true
  val scrollYpos = vHeight / 2 + vHeaderSize / 2
  
  val firstFlagsPosition = Pt2(-(vWidth - vCellWidth) / 2, (vHeight - vCellHeight) / 2)
  val barBackground =  Rectangle.curvedCorners(maxBarWidth + 2, 32, 10, Pt2(0, scrollYpos)).fill(Black)
  val background = Rectangle.curvedCorners(vWidth, vHeight, 10).fill(Gray)
  val btnMore = simpleButton(">") { scrollMore() }.slate(+20 + maxBarWidth / 2, scrollYpos)
  val btnLess = simpleButton("<") { scrollLess() }.slate(-20 - maxBarWidth / 2, scrollYpos)
  val scrollBar = RArr(btnMore, btnLess, barBackground)

  if (isScrollHorizontal) { itemsPerUnitScroll = itemsPerCol; iScrollStep = itemsPerCol; jScrollStep = 1 }
  else                                     { itemsPerUnitScroll = itemsPerRow; iScrollStep = 1; jScrollStep = itemsPerRow }
// set itemsPerUnitScroll = itemsPerPage >>> scroll by page rather than by line

  val scrollStep = Math.max(iScrollStep, jScrollStep)
  val barMaxAvailable = maxBarWidth - minBarWidth
  val barWidth = (minBarWidth + Math.min(barMaxAvailable, 1.0 * barMaxAvailable * itemsPerPage / itemCount))
  val barAvailable = maxBarWidth - barWidth
  val barStartX = -barAvailable / 2
  val maxIndexOfFirstItemInView = scrollStep * ((Math.max(0, itemCount - itemsPerPage + scrollStep - 1)) / scrollStep)
  def scrollMore(): Unit = { showGridView(viewIndex + itemsPerUnitScroll) }
  def scrollLess(): Unit = { showGridView(viewIndex - itemsPerUnitScroll) }
  var viewableItems:RArr[Graphic2Elem] = RArr()

  var bar = Rectangle.curvedCorners(barWidth, 30, 10).fill(Pink)
  var barOffsetX = 0.0
  var dragStartBarOffsetX = 0.0
  var dragStartX = 0.0
  var isDragging = false
  
 def showGridView(indexOfFirstItemInView:Int = 0): Unit =
  { val firstIndex = Math.min(Math.max(indexOfFirstItemInView, 0), maxIndexOfFirstItemInView)
    viewableItems = RArr()
    for(j <- 0 to itemsPerCol - 1; i <- 0 to itemsPerRow - 1 if firstIndex + i * iScrollStep + j * jScrollStep < itemCount)
    { val thisIndex = firstIndex + i * iScrollStep + j * jScrollStep
      val thisFlag = listOfFlags(thisIndex).compound(thisIndex.toString).scale(vCommonScale / Math.sqrt(listOfFlags(thisIndex).ratio))
      viewableItems = viewableItems +% thisFlag.slate(i * vCellWidth, -j * vCellHeight).slate(firstFlagsPosition)
    }
    viewIndex = firstIndex
    if (selectedIndex == -1) positionBar() else showSelected()
  }

  def positionBar(): Unit = 
  { barOffsetX = if (maxIndexOfFirstItemInView != 0) barAvailable * viewIndex * 1.0 / maxIndexOfFirstItemInView else 0
    bar = Rectangle.curvedCorners(barWidth, 30, 10, Pt2(barStartX + barOffsetX, scrollYpos)).fill(Pink)
    repaint(RArr(background) ++ scrollBar ++ viewableItems ++ RArr(bar))
  }

  def showSelected(): Unit =
  {
    val item = listOfFlags(selectedIndex).compound(selectedIndex.toString)
    val item2 = item.scale(3 * vCommonScale / Math.sqrt(listOfFlags(selectedIndex).ratio))
    viewableItems = RArr(item2)
    positionBar()
  }

  showGridView(viewIndex)

  def dragging(pixelDelta: Double): Unit =
  {
    barOffsetX = dragStartBarOffsetX + pixelDelta
    barOffsetX = Math.min(barAvailable, Math.max(0, barOffsetX))
    val currentScroll = ((barOffsetX * maxIndexOfFirstItemInView) / barAvailable).toInt
    showGridView(((currentScroll + itemsPerUnitScroll - 1) / itemsPerUnitScroll * itemsPerUnitScroll))//
  }

  mouseUp = (mouseButton: MouseButton, clickList, mousePosition) => isDragging match
  { case true => isDragging = false
    case false => mouseButton match
    { case LeftButton => clickList match
      { case RArrHead(MouseButtonCmd(cmd)) => cmd.apply(mouseButton)
        case RArrHead(flagIndex) =>
        { selectedIndex = if (selectedIndex != -1) -1 else flagIndex.toString.toInt
          showGridView(viewIndex)
        } 
        case l => { selectedIndex = -1; showGridView(viewIndex) }
      }
      case _ =>
    }
  }
   
  canv.mouseDragged = (mousePosition:Pt2, mouseButton:MouseButton) => if (mouseButton == LeftButton & isDragging == true) dragging(mousePosition.x - dragStartX)

  canv.mouseDown = (mousePosition:Pt2, mouseButton:MouseButton) => mouseButton match
  { case LeftButton if (bar.boundingRect.ptInside(mousePosition) == true) =>
    { dragStartBarOffsetX = barOffsetX
      dragStartX = mousePosition.x
      isDragging = true
    }
    case LeftButton if (barBackground.boundingRect.ptInside(mousePosition) == true) =>
    { if (mousePosition.x > barStartX + barOffsetX) showGridView(viewIndex + itemsPerPage)
      else showGridView(viewIndex - itemsPerPage)
    }
    case _ =>
  }

//** NB below is for scroll ~> need focus to handle keys also for selected etc **//
  canv.keyDown = (thekey: String) => thekey match
  {// case ("ArrowUp" | "ArrowLeft") => theSelectionFocus ? showSelected(#_less) : showGridView(#_less)//viewIndex - itemsPerUnitScroll)
   // case ("ArrowDown" | "ArrowRight") => theSelectionFocus ? showSelected(#_more) : showGridView(#_more)//viewIndex + itemsPerUnitScroll)
   // case ("PageDown") => theSelectionFocus ? showSelected(#_pageMore) : showGridView(#_pagemore)//viewIndex + itemsPerPage)
   // case ("PageUp") => theSelectionFocus ? showSelected(#_pageLess) : showGridView(#_pageless)//viewIndex - itemsPerPage)
   // case ("End") => theSelectionFocus ? showSelected(#_last) : showGridView(#_last)//maxIndexOfFirstItemInView)
   // case ("Home") => theSelectionFocus ? showSelected(#_first) : showGridView(#_first)//0
    case _ => deb(thekey)
  }
  
  canv.onScroll = (isScrollLess: Boolean) => if (isScrollLess) scrollLess() else scrollMore()
}
