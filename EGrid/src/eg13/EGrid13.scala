/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import egrid._

/** object for creating earth grids with 640km hexs, with a c scale of 80km. */
object EGrid13
{ /** Returns an [[RArr]] sequence of 13km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 114): RArr[EGrid13LongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGrid13LongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 114): EGrid13LongMulti = new EGrid13LongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid13.grids(numGridsIn, headInt, bottomR, topR)

    override def headGridInt: Int = headInt

    override def gridsXSpacing: Double = 40

    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))
  }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 0)
  def e30(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 114): EGrid13LongFull = EGrid13LongFull(rBottomCen, rTopCen, 11)

  def scen0: EScenBasic = EScenBasic(Terr13E0.grid, Terr13E0.terrs, Terr13E0.sTerrs, Terr13E0.corners, Terr13E0.hexNames, "1300km 0°E")
  def scen1: EScenBasic = EScenBasic(Terr13E30.grid, Terr13E30.terrs, Terr13E30.sTerrs, Terr13E30.corners, Terr13E30.hexNames, "1300km 30°E")
  def scen2: EScenBasic = EScenBasic(Terr13E60.grid, Terr13E60.terrs, Terr13E60.sTerrs, Terr13E60.corners, Terr13E60.hexNames, "1300km 60°E")
  def scen3: EScenBasic = EScenBasic(Terr13E90.grid, Terr13E90.terrs, Terr13E90.sTerrs, Terr13E90.corners, Terr13E90.hexNames, "1300km 90°E")
  def scen4: EScenBasic = EScenBasic(Terr13E120.grid, Terr13E120.terrs, Terr13E120.sTerrs, Terr13E120.corners, Terr13E120.hexNames, "1300km 120°E")
  def scen5: EScenBasic = EScenBasic(Terr13E150.grid, Terr13E150.terrs, Terr13E150.sTerrs, Terr13E150.corners, Terr13E150.hexNames, "1300 150°E")
  def scen6: EScenBasic = EScenBasic(Terr13E180.grid, Terr13E180.terrs, Terr13E180.sTerrs, Terr13E180.corners, Terr13E180.hexNames, "1300 180°E")
  def scen7: EScenBasic = EScenBasic(Terr13W150.grid, Terr13W150.terrs, Terr13W150.sTerrs, Terr13W150.corners, Terr13W150.hexNames, "1300 150°W")
  def scen8: EScenBasic = EScenBasic(Terr13W120.grid, Terr13W120.terrs, Terr13W120.sTerrs, Terr13W120.corners, Terr13W120.hexNames, "1300 120°W")
  def scen9: EScenBasic = EScenBasic(Terr13W90.grid, Terr13W90.terrs, Terr13W90.sTerrs, Terr13W90.corners, Terr13W90.hexNames, "1300 90°W")
  def scen10: EScenBasic = EScenBasic(Terr13W60.grid, Terr13W60.terrs, Terr13W60.sTerrs, Terr13W60.corners, Terr13W60.hexNames, "1300 60°W")
  def scen11: EScenBasic =EScenBasic(Terr13W30.grid, Terr13W30.terrs, Terr13W30.sTerrs, Terr13W30.corners, Terr13W30.hexNames, "1300 30°W")
}