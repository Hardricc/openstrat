package learn
import ostrat._, geom._, pCanv._, Colour._, scala.math.{Pi}

/** This is a temporary lesson: whilst Arcs get fixed. */
case class ArcTest(canv: CanvasPlatform) extends CanvasNoPanels("Arc Test")
{
  var x = -650.0 // x coordinate
  var y = 325.0 // y coordinate
  var radius = 20.0
  //var arcOrigin = Pt(0,0)
  val longArcOffset = 0.025
  var myStuff:GraphicElems = Arr()
  for (index <- 0 to 374) {
    x += 50;
    if (x>=650) {x = -600; y -= 45}
    val theta = Pi1/180*index+longArcOffset; // angle of arc
    val startPoint = x+radius*math.cos(longArcOffset) pp y+radius*math.sin(longArcOffset)
    val apex = x+radius*math.cos(theta/2+longArcOffset) pp y+radius*math.sin(theta/2+longArcOffset)
    val endPoint = x+radius*math.cos(theta+longArcOffset) pp y+radius*math.sin(theta+longArcOffset)
    val myArc = CArc3(startPoint, apex, endPoint)
    deb(index.toString+" s="+myArc.startDegs.toString+" a="+myArc.angle.toString)
    //deb(index.toString+" theta =" + theta + " startAngle="+myArc.startAngle)
    //deb(index.toString+" theta =" + theta + " angle="+myArc.angle)
    myStuff = myStuff ++ Arr(myArc.draw(DeepSkyBlue), TextGraphic(index.toString, x pp y, 12, Black))
  }
  val delta = Pi1/8
  x = -650.0 // x coordinate
  y = 325.0 // y coordinate
  radius = 15.0
  val shortArcOffset = Pi1/360
  for (index <- 0 to 374) {
    x += 50;
    if (x>=650) {x = -600; y -= 45}
    val theta = shortArcOffset+Pi1/180*index; // angle of arc
    val startPoint = x+radius*math.cos(theta) pp y+radius*math.sin(theta)
    val apex = x+radius*math.cos(theta+delta) pp y+radius*math.sin(theta+delta)
    val endPoint = x+radius*math.cos(theta+delta+delta) pp y+radius*math.sin(theta+delta+delta)
    val myArc = CArc3(startPoint, apex, endPoint)
    deb(index.toString+" s="+myArc.startDegs.toString+" a="+myArc.angle.toString)
    myStuff = myStuff ++ Arr(myArc.draw(Orange))
  }
  repaint(myStuff)
}

//def getPointOnCircle(origin: pt2, radius: Angle)
// CArcDrawOld(x pp y, 0 pp 0, 0 pp 250)
// CArc3(x pp y, -141.421356237 pp 141.421356237, 0 pp 200).draw(Crimson)
// Arr(CArcDraw(CArc(x pp y, x+radius pp y, endAngle), 2, Blue))
//   var myStuff: GraphicElems = ijToMap(1, 1)(0,15) { (i, j) =>
//     val x = -400 + 25 + j * 50; // x coordinate
//     val y = 25 + i * 50; // y coordinate
//     val arcAngle = 0.1 + Pi/8*j; // angle of arc
//     //val origin = x pp y
//     val startPoint = x+radius pp y
//     val apex = x+radius*math.cos(arcAngle/2) pp y+radius*math.sin(arcAngle/2)
//     val endPoint = x+radius*math.cos(arcAngle) pp y+radius*math.sin(arcAngle)

//     CArc(startPoint, apex, endPoint).draw(DeepSkyBlue)
