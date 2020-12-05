package learn
import ostrat._, geom._, pCanv._, Colour._

/* These lessons are intended to be accessible to people who haven't programmed before and have limited geometry knowledge. This is a comment. It
 *  doesn't do anything. Everything between the forward-slash star at the beginning of the comment and the star forward-slash at the end is a comment.
 *  Hopefully in your
 *   editor or IDE (integrated Developer Environment) the comments will appear in a different colour. */

// This is also a comment. Everything after two forward-slashes to the end of line. You can add and remove //s from the beginning of the commands,
// Assuming you are running the "mill -w name.runBackground" when you do a save mill will automatically rebuild and you can see the result of your changes.
// The associated commands will appear / disappear from the screen.
 
case class LsA4(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A4")
{
  val c3 = CArc3(-100 pp 0, -71 pp 71, 0 pp 100)
  val r1 = 0.1
  val r2 = 0.2
  val rot = AngleVec(r1 + r2)
  deb(rot.toString)
  debvar(c3.height)
  debvar(c3.radius)
  val stuff = Arr(
    LineSegDraw(0 pp 0, 160 pp 100),//This line starts at the centre of the screen and goes to point 160 right of centre and 100 up form centre.
    LineSegDraw(0 pp 50, 150 pp 200, lineWidth = 3),
    LineSegDraw(50 pp -50, 200 pp -50, Red, 2),//Note if you don't include a Colour you get Black

    LinePath(0 pp -50, 50 pp -100, -25 pp -75, 200 pp -60).draw(2, Orange),

    c3.draw(Crimson),

    Bezier(200 pp -350, -500 pp -300, -600 pp -300, -450 pp -200).draw(Green, 2),
  )
  repaint(stuff)
}

/** There are three types of values above. Numbers, text and Colours. Try changing the numbers, save the file and you should things move around the
  * screen. Congratulations! you are now a programmer. But, but, you respond, there's loads of magic going on here that I don't understand. All
  * programmers rely on magic, and of course the first thing we should know about magic is it doesn't always work. I will try and explain how some of
  * the magic works, but in programming there's always more magic to decode.
  *
  * The second type is text. Programmers refer to text as Strings, for some reason. I shall use the term string from now on. Strings start with a "
  * quotation mark and end with a quotation mark. Change the text in on one of the strings, save the file and you should see the text change on the
  * screen. If you try replacing a string with a number or replacing a number with a String, the compiler will complain and you will get an error
  * message. The third type is Colour. Note Colours must have the correct capital letters. You can just try guessing the colours or you can google /
  * duckduck web colours to see what is available. Again put a Colour where a number or a string is expected or vice versa and the compiler will
  * complain. */