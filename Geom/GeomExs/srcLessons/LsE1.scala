/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._

object LsE1 extends LessonGraphics
{ override def title: String = "Turn app 1"

  override def bodyStr: String = """Lesson C3. Pointer in object."""

  override def canv: CanvasPlatform => Any = LsE1Canv(_)

  /** E Series lessons deal with games. E1 is a super simple single player turn game. */
  case class LsE1Canv(canv: CanvasPlatform) extends CmdBarGui
  { override def title: String = "Lesson E1"
    import e1._

    var state: GameState = GameState.start
    var cmd: TurnCmd = NoMove
    statusText = "Right click to set action to Move. Left to set action to CycleColour. Press Turn button or middle click for next turn."

    def cmdDisp: RArr[GraphicElem] = cmd match {
      case Move(v) => Arrow.paint(state.posn, v) //Returns Arr[GraphicElem]
      case CycleColour => RArr(state.drawNextColour)
      case _ => RArr()
    }

    /** frame refers to the screen output. In the same way that a movie is constructed from a number of still frames. So we create the "action" in a
     * graphical application through a series of frames. Unlike in the movies our display may not change for significant periods of time. Where we can
     * it is simpler to create the whole screen out, to create each from a blank slate so to speak rather than just painting the parts of the dsplay
     * that have been modified. */
    def frame(): Unit = {
      reTop(RArr(StdButton.turn(state.turnNum + 1)))
      mainRepaint(state.fillRect %: cmdDisp)
    }

    def newTurn(): Unit = {
      state = state.turn(cmd); cmd = NoMove; frame()
    }

    frame()

    topBar.mouseUp = (b, s, v) => s match {
      case RArr1(Turn) => newTurn()
      case _ =>
    }

    mainMouseUp = (b, s, v) => b match {
      case RightButton => {
        cmd = Move(v); frame()
      }
      case LeftButton => {
        cmd = CycleColour; frame()
      }
      case _ => newTurn()
    }
  }
}