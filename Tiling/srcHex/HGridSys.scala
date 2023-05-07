/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag, pgui._

/** System of hex tile grids. Can be a single [[HGrid]] or a system of multiple hex tile grids. */
trait HGridSys extends Any with TGridSys
{ /** Returns the most appropriate [[HSysProjection]] for this HGridSys. */
  def projection: Panel => HSysProjection = HSysProjectionFlat(this, _)

  final override lazy val numTiles: Int =
  { var i = 0
    foreach(_ => i += 1)
    i
  }

  /** The number of [[HCorner]]s in this [[HGridSys]], 6 for each [[HCen]]. */
  def numCorners: Int = numTiles * 6

  /** The number of sides in the hex grid system. */
  final lazy val numSides: Int =
  { var i = 0
    sidesForeach(_ => i += 1)
    i
  }

  /** The number of inner sides in the hex grid system. */
  final lazy val numInnerSides: Int =
  { var i = 0
    linksForeach(_ => i += 1)
    i
  }

  /** The number of outer sides in the hex grid system. */
  final lazy val numOuterSides: Int =
  { var i = 0
    edgesForeach(_ => i += 1)
    i
  }

  /** The conversion factor for c column tile grid coordinates. 1.0 / sqrt(3). */
  final override def yRatio: Double = 3.sqrt

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCenExists(hc: HCen): Boolean = hCenExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCenExists(r: Int, c:Int): Boolean

  /** If the given [[HCen]] exists within this [[HGridSys]], perform the side effecting function. */
  def hCenExistsIfDo(hc: HCen)(proc: => Unit): Unit = if(hCenExists(hc)) proc

  /** If the given [[HCen]] exists within this [[HGridSys]], perform the side effecting function. */
  def hCenExistsIfDo(r: Int, c: Int)(proc: => Unit): Unit = if(hCenExists(r, c)) proc

  def coordCen: HCoord
  def hCenSteps(hCen: HCen): HStepArr

  /** Unsafe. Gets the destination [[HCen]] from the [[HStep]]. Throws exception if no end point, */
  def stepEndGet(startCen: HCen, step: HStep): HCen

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  final def stepFind(startR: Int, startC: Int, endR: Int, endC: Int): Option[HStep] = stepFind(HCen(startR, startC), HCen(endR, endC))

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  def stepFind(startHC: HCen, endHC: HCen): Option[HStep]

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  def stepEndFind(startHC: HCen, step: HStep): Option[HCen]

  /** Optionally returns the destination of an [[HStep]] if the destination exists in the [[HGridSys]]. */
  def stepEndFind(r: Int, c: Int, step: HStep): Option[HCen] = stepEndFind(HCen(r, c), step)

  final def cenStepEndFind(cenStep: HCenStep): Option[HCen] = stepEndFind(cenStep.startHC, cenStep.step)

  def stepsEndFind(startCen: HCen, steps: HStepArr): Option[HCen] =
  {
    def loop(currCen: HCen, i: Int): Option[HCen] =
      if (i >= steps.length) Some(currCen)
      else stepEndFind(currCen, steps(i)) match
      { case None => None
        case Some(target) => loop(target, i + 1)
      }
    loop(startCen, 0)
  }

  /** Finds step from Start [[HCen]] to target from [[HCen]] if end hex exists, else returns start hex. */
  def stepEndOrStart(startHC: HCen, step: HStep): HCen = stepEndFind(startHC, step).getOrElse(startHC)

  /** Finds the end from an [[HStepLike]]. */
  def stepOptEndFind(startHC: HCen, optStep: HStepLike): Option[HCen] = optStep match
  { case HStepStay => Some(startHC)
    case hs: HStep => stepEndFind(startHC, hs)
  }

  /** Gives a flat projection of [[HCoord]]s to [[Pt2]]s. For a simple singular [[HGrid]] system this is all that is required to translate between
   * grid coordinates and standard 2 dimensional space. For multi grids it provides a simple way to display all the tiles in the grid system, but a
   * more complex projection may be required for fully meaningful display representation. For Example world grid systems and multi layer square tile
   * games will require their own specialist projections. */
  def flatHCoordToPt2(hCoord: HCoord): Pt2

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def layerArrayIndex(hc: HCen): Int = layerArrayIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array SeqDef data. */
  def layerArrayIndex(r: Int, c: Int): Int

  /** Gives the index into the unsafe backing [[Array]] of a [[HCornerLayer]]. */
  def cornerLayerArrayIndex(hc: HCen, vertIndex: Int): Int = layerArrayIndex(hc) * 6 + vertIndex

  /** For each row combine data layer into RArr[HCenRowPair]. May be superceded */
  def rowsCombine[A <: AnyRef](layer: HCenLayer[A], indexingGSys: HGridSys): RArr[HCenRowPair[A]]

  /** Returns a clockwise sequence of adjacent tiles. */
  def adjTilesOfTile(origR: Int, origC: Int): HCenArr

  /** Returns a clockwise sequence of adjacent tiles. */
  final def adjTilesOfTile(origin: HCen): HCenArr = adjTilesOfTile(origin.r, origin.c)

  def sideTileRtOpt(hSide: HSide): Option[HCen]

  /** This method should only be used when you know the side tile exists. */
  def sideTileRtUnsafe(hSide: HSide): HCen

  def sideTileLtOpt(hSide: HSide): Option[HCen]

  /** This method should only be used when you know the side tile exists. */
  def sideTileLtUnsafe(hSide: HSide): HCen

  //def findPathHC(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[LinePathHC] = findPathList(startCen, endCen)(fTerrCost).map(_.toLinePath)

  def sideTileLtAndVertUnsafe(hSide: HSide): (HCen, Int)

  def findPath(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[HCenArr] = findPathList(startCen, endCen)(fTerrCost).map(_.toArr)

  /** Finds path from Start hex tile centre to end tile centre given the cost function parameter. */
  def findPathList(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[List[HCen]] =
  {
    var open: List[HNode] = HNode(startCen, 0, getHCost(startCen, endCen), NoRef) :: Nil
    var closed: List[HNode] = Nil
    var found: Option[HNode] = None

    while (open.nonEmpty & found == None)
    {
      val curr: HNode = open.minBy(_.fCost)
      open = open.filterNot(_ == curr)
      closed ::= curr
      val neighbs: HCenArr = adjTilesOfTile(curr.tile).filterNot(tile => closed.exists(_.tile == tile))
      neighbs.foreach { tile =>
        fTerrCost(curr.tile, tile) match {
          case NoInt =>
          case SomeInt(nc) if closed.exists(_.tile == tile) =>
          case SomeInt(nc) => {
            val newGCost = nc + curr.gCost

            open.find(_.tile == tile) match {
              case Some(node) if newGCost < node.gCost => node.gCost = newGCost; node.parent = OptRef(curr)
              case Some(_) =>
              case None =>
              { val newNode = HNode(tile, newGCost, getHCost(tile, endCen), OptRef(curr))
                open ::= newNode
                if (tile == endCen) found = Some(newNode)
              }
            }
          }
        }
      }
    }
    def loop(acc: List[HCen], curr: HNode): List[HCen] = curr.parent.fld(acc, loop(curr.tile :: acc, _))

    found.map(endNode =>  loop(Nil, endNode))
  }

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   *  departure and the tile of arrival. */
  def getHCost(startCen: HCen, endCen: HCen): Int

  /** foreachs over each [[HCen]] hex tile centre, applying the side effecting function. */
  def foreach(f: HCen => Unit): Unit

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  def iForeach(f: (HCen, Int) => Unit): Unit

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit

  /** Creates a new [[HCenBuffLayer]] An [[HCen] hex tile centre corresponding Arr of empty [[ArrayBuffer]]s of the given or inferred type. */
  final def newHCenArrOfBuff[A <: AnyRef](implicit ct: ClassTag[A]): HCenBuffLayer[A] = HCenBuffLayer(numTiles)

  /** Maps over the [[HCen]] hex centre tile coordinates. B is used rather than A as a type parameter, as this method maps from HCen => B,
   *  corresponding to the standard Scala map function of A => B. */
  final def map[B, ArrB <: Arr[B]](f: HCen => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val res = build.uninitialised(numTiles)
    iForeach((hCen, i) => res.setElemUnsafe(i, f(hCen)))
    res
  }

  /** Maps from all hex tile centre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   * TileGrid structure is lost in the flatMap operation. */
  final def optMap[B, ArrB <: Arr[B]](f: HCen => Option[B])(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff(numTiles)
    foreach { hCen => f(hCen).foreach(build.buffGrow(buff, _)) }
    build.buffToSeqLike(buff)
  }

  final def ifMap[B, ArrB <: Arr[B]](f1: HCen => Boolean)(f2: HCen => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff(numTiles)
    foreach { hCen => if(f1(hCen)) build.buffGrow(buff, f2(hCen)) }
    build.buffToSeqLike(buff)
  }

  def mapPair[B2](f2: HCen => B2)(implicit build: HCenPairArrMapBuilder[B2]): HCenPairArr[B2] =
  { val res = build.uninitialised(numTiles)
    iForeach{ (hc, i) =>
      res.setA1Unsafe(i, hc)
      res.setA2Unsafe(i, f2(hc))
    }
    res
  }

  /** flatMaps from all hex tile centre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   *  TileGrid structure is lost in the flatMap operation. */
  final def flatMap[ArrT <: Arr[_]](f: HCen => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numTiles)
    foreach{ hCen => build.buffGrowArr(buff, f(hCen))}
    build.buffToSeqLike(buff)
  }

  /** flatMaps from all hex tile centre coordinates to an Arr of type ArrT. The normal flatMap functions is only applied if the condtion of the first
   * function is true. */
  final def ifFlatMap[ArrT <: Arr[_]](f1: HCen => Boolean)(f2: HCen => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numTiles)
    foreach { hCen => if(f1(hCen)) build.buffGrowArr(buff, f2(hCen)) }
    build.buffToSeqLike(buff)
  }

  /** Gives the index into an Arr / Array of Tile data from its tile [[HSide]]. Use arrIndex and vertIndex methods to access tile centre and Vertex
   *  Arr / Array data. */
  @inline final def sideLayerArrayIndex(hc: HSide): Int = sideLayerArrayIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of side data from its tile [[HSide]]. Use arrIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array data. */
  def sideLayerArrayIndex(r: Int, c: Int): Int

  /** foreach Hex side's coordinate HSide, calls the effectual function. */
  def sidesForeach(f: HSide => Unit): Unit

  /** foreach hex link / inner side's coordinate HSide, calls the effectual function. */
  def linksForeach(f: HSide => Unit): Unit

  /** foreach hex edge / outer side's coordinate HSide, calls the effectual function. */
  def edgesForeach(f: HSide => Unit): Unit

  /** maps over each Hex Side's coordinate [[HSide]] in the hex grid system. */
  final def sidesMap[B, ArrT <: Arr[B]](f: HSide => B)(implicit build: ArrMapBuilder[B, ArrT]): ArrT =
  { val res: ArrT = build.uninitialised(numSides)
    var i = 0
    sidesForeach{hs => res.setElemUnsafe(i, f(hs)); i += 1 }
    res
  }

  /** maps over each the grid systems link / inner side's coordinate [[HSide]]. */
  final def linksMap[B, ArrT <: Arr[B]](f: HSide => B)(implicit build: ArrMapBuilder[B, ArrT]): ArrT =
  { val res: ArrT = build.uninitialised(numInnerSides)
    var i = 0
    linksForeach{ hs => res.setElemUnsafe(i, f(hs)); i += 1 }
    res
  }

  /** maps over each the grid systems outer side's coordinate [[HSide]]. */
  final def edgesMap[B, ArrT <: Arr[B]](f: HSide => B)(implicit build: ArrMapBuilder[B, ArrT]): ArrT =
  { val res: ArrT = build.uninitialised(numOuterSides)
    var i = 0
    edgesForeach{ hs => res.setElemUnsafe(i, f(hs)); i += 1 }
    res
  }

  /** flatMaps over each Hex Side's coordinate [[HSide]]. */
  final def sidesFlatMap[ArrT <: Arr[_]](f: HSide => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff()
    sidesForeach{hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToSeqLike(buff)
  }

  /** Optionally maps over each Hex Side's coordinate [[HSide]]. */
  final def sidesOptMap[B, ArrB <: Arr[B]](f: HSide => Option[B])(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    sidesForeach { hs => f(hs).foreach(b => build.buffGrow(buff, b)) }
    build.buffToSeqLike(buff)
  }

  /** flatMaps  over each inner hex Side's coordinate [[HSide]].. */
  final def linksFlatMap[ArrT <: Arr[_]](f: HSide => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff()
    linksForeach{ hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToSeqLike(buff)
  }

  /** OptMaps over each inner hex Side's coordinate [[HSide]]. */
  final def linksOptMap[B, ArrB <: Arr[B]](f: HSide => Option[B])(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    linksForeach { hs => f(hs).foreach(build.buffGrow(buff, _)) }
    build.buffToSeqLike(buff)
  }

  /** The [[HSide]] hex side coordinates. */
  final def sides: HSideArr = sidesMap(hs => hs)

  def sideExists(r: Int, c: Int): Boolean = sideExists(HSide(r, c))
  def sideExists(hs: HSide): Boolean = hCenExists(hs.tileLtReg) | hCenExists(hs.tileRtReg)

  /** The line segments of the sides defined in [[HCoord]] vertices. */
  def sideLineSegHCs: LineSegHCArr = sidesMap(_.lineSegHC)

  /** The line segments of the inner sides defined in [[HCoord]] vertices. */
  def innerSideLineSegHCs: LineSegHCArr = linksMap(_.lineSegHC)

  /** The line segments of the outer sides defined in [[HCoord]] vertices. */
  def outerSideLineSegHCs: LineSegHCArr = edgesMap(_.lineSegHC)

  def defaultView(pxScale: Double = 30): HGView

  /** Gives the index into an Arr / Array of Tile data from its tile [[HVert]]. Use arrIndex and sideArrIndex methods to access tile centre and side
   *  Arr / Array data. */
  @inline final def vertArrIndex(hc: HSide): Int = vertArrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of side data from its tile [[HVert]]. Use arrIndex and vertIndex methods to access tile centre and side Arr
   *  / Array data. */
  def vertArrIndex(r: Int, c: Int): Int = 0

  def findSideTiles(hs: HSide ): Option[(HCen, HCen)] = ???

  /** Finds the [[HCoord]] if it exists, by taking the [[HVDirn]] from an [[HVert]]. */
  def vertToCoordFind(hVert: HVert, dirn: HVDirn): Option[HCoord]
}