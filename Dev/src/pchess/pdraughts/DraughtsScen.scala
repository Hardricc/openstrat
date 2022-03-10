/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import prid._, psq._

trait DraughtsScen extends ChessLikeScen
{ def draughts: SqCenArrOpt[Draught]
}

object DraughtsStart extends DraughtsScen
{ val turn = 0
  val draughts: SqCenArrOpt[Draught] = grid.newTileArrOpt[Draught]
  //def rowRoords(y: Int): SqCens = iToMap(ife(y.div4Rem2, 2, 4), 16, 4){c => y rr c}
//  iToForeach(2, 6, 2){y => rowRoords(y).foreach{r => draughts.mutSetSome(r, WhiteMan) } }
//  iToForeach(16, 12, -2){y => rowRoords(y).foreach{r => draughts.mutSetSome(r, BlackMan) } }
}
