package learn
import ostrat._, geom._, pCanv._, Colour._

/** This lesson displays an interactive Bezier curve whose points can be dragged and also displays the syntax required to draw it */
case class LsC7(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C7: Exploring Beziers")
{ /** defines the size of the circles that represent the points of the bezier */
  val circleRadius = 10
  case class DragCircle(var loc: Pt2, color: Colour)
  
  /** start point bezier. */
  val startPoint = DragCircle(-250 pp 0, Red)

  /** End point of bezier curve. */
  val endPoint = DragCircle(-50 pp  0, Red)

  /** control point for start point */
  val controlStart = DragCircle(-250 pp -250, Gray)

  var controlStartOffset = 0 vv -250

  /** control point for end point */
  val controlEnd = DragCircle(-50 pp 150, Gray)

  var controlEndOffset = 0 vv 150
  
  val bezierPoints = Arr(startPoint, endPoint, controlStart, controlEnd)

  val quadraticStart = DragCircle(50 pp  0, Red)
  val quadraticEnd = DragCircle(150 pp  0, Red)
  val quadraticControl = DragCircle(100 pp -150, Gray)
  val quadraticBezierPoints = Arr(quadraticStart, quadraticEnd, quadraticControl)

  /** when one of the bezier points is being dragged, this will indicate which */
  var theDragee: Option[DragCircle] = None 
  
  val allBezierPoints = Arr(startPoint, endPoint, controlStart, controlEnd, quadraticStart, quadraticEnd, quadraticControl)

  drawBezier()

  def drawBezier():Unit =
  { val dragCircles = bezierPoints.map(dc => Circle(circleRadius, dc.loc).fill(dc.color))

    /** line between the start point and its control point */
    val startControlLine = LineSegDraw(startPoint.loc, controlStart.loc, Grey, 1)

    /** line between the end point and its control point */
    val endControlLine = LineSegDraw(endPoint.loc, controlEnd.loc, Grey, 1)

    /** the bezier to be displayed */
    val bezier = Bezier(startPoint.loc, controlStart.loc, controlEnd.loc, endPoint.loc).draw(Green, 2)

    /** this holds the syntax required to draw the current bezier  (NB: replace ; with , ) */
    val txt = TextGraphic("BezierDraw(" + startPoint.loc + ", " + controlStart.loc + ", " + controlEnd.loc + ", " + endPoint.loc + ", 2, Green)", 18, 0 pp 300, Green)
    
    val elementsToPaint = dragCircles ++ Arr(txt, startControlLine, endControlLine, bezier)

    val quadraticBezier = Bezier(quadraticStart.loc, quadraticControl.loc, quadraticControl.loc, quadraticEnd.loc).draw(Blue, 2)
    val quadraticDragCircles = quadraticBezierPoints.map(dc => Circle(circleRadius, dc.loc).fill(dc.color))
    val quadraticStartControlLine = LineSegDraw(quadraticStart.loc, quadraticControl.loc, Grey, 1)
    val quadraticEndControlLine = LineSegDraw(quadraticEnd.loc, quadraticControl.loc, Grey, 1)
    val txtQuad = TextGraphic("BezierDraw(" + quadraticStart.loc + ", " + quadraticControl.loc + ", " + quadraticControl.loc + ", " + quadraticEnd.loc + ", 2, Blue)", 18, 0 pp -300, Blue)

    repaint(elementsToPaint +- txtQuad ++ quadraticDragCircles +- quadraticBezier +- quadraticStartControlLine +- quadraticEndControlLine )
  }

  /* test to see if drag operation has started. if the mouseDown is on one of the represented bezier points then set theDragee to its corresponding
   option */
  canv.mouseDown = (position, _) => theDragee = allBezierPoints.find(i => i.loc.distTo(position) <= circleRadius)

  // When a point is being dragged update the corresponding bezier point with its new position and then redraw the screen.
  canv.mouseDragged = (position, button) => theDragee match
  { case Some(drag) if (drag == startPoint) => drag.loc = position; controlStart.loc = drag.loc +  controlStartOffset; drawBezier()
    case Some(drag) if (drag == endPoint) => drag.loc = position; controlEnd.loc = drag.loc +  controlEndOffset; drawBezier()
    case Some(drag) if (drag == controlStart) => drag.loc = position; controlStartOffset = drag.loc << startPoint.loc; drawBezier()
    case Some(drag) if (drag == controlEnd) => drag.loc = position; controlEndOffset = drag.loc << endPoint.loc; drawBezier()
    case Some(drag) => drag.loc = position; drawBezier() 
    case _ => theDragee = None
  }

  /** dragging has finished so reset theDragee */
  mouseUp = (button, clickList, position) => theDragee = None 
}

