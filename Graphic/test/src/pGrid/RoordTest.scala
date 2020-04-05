package ostrat
package pGrid
import utest._

object RoordTest extends TestSuite
{
  val r1 = 5 rr -96
  val r2 = r1 + Roord(-5, 96)

  val ll = EGrid80Km.roordToLatLong0(464 rr 4)
  debvar(ll.degStr)
  val tests = Tests
  {
    "test1" -
      {
        r1 ==> Roord(5, -96)
        r2 ==> Roord(0, 0)
      }
  }
}
