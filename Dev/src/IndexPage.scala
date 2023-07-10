/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

object IndexPage
{
  val head = HtmlHead.titleCss("Openstrat", "only")
  def topMenu: HtmlUl = SubPage.topMenu(SubPage.allPairs)
  def body = HtmlBody.elems(topMenu, XConStr(bodyStr))
  def content = HtmlPage(head, body)

  def iconStrs =
    """<p><a href="https://github.com/Rich2/openstrat"><svg xmlns="http://www.w3.org/2000/svg" width="92" height="20">
      |  <linearGradient id="b" x2="0" y2="100%"><stop offset="0" stop-color="#bbb" stop-opacity=".1"/><stop offset="1" stop-opacity=".1"/>
      |  </linearGradient><mask id="a"><rect width="92" height="20" rx="3" fill="#fff"/></mask><g mask="url(#a)"><path fill="#555" d="M0 0h34v20H0z"/><path
      |    fill="#46BC99" d="M34 0h58v20H34z"/><path fill="url(#b)" d="M0 0h92v20H0z"/></g>
      |    <g fill="#fff" text-anchor="middle" font-family="DejaVu Sans,Verdana,Geneva,sans-serif" font-size="11"><text x="17" y="15" fill="#010101"
      |     fill-opacity=".3">code</text><text x="17" y="14">code</text><text x="62" y="15" fill="#010101" fill-opacity=".3">on github</text><
      |     text x="62" y="14">on github</text>
      |   </g>
      |</svg></a>&nbsp;<a href="https://gitter.im/typestrat/Lobby"><svg xmlns="http://www.w3.org/2000/svg" width="92" height="20">
      |  <linearGradient id="b" x2="0" y2="100%"><stop offset="0" stop-color="#bbb" stop-opacity=".1"/><stop offset="1" stop-opacity=".1"/>
      |  </linearGradient><mask id="a"><rect width="92" height="20" rx="3" fill="#fff"/></mask><g mask="url(#a)"><path fill="#555" d="M0 0h34v20H0z"/>
      |    <path fill="#46BC99" d="M34 0h58v20H34z"/><path fill="url(#b)" d="M0 0h92v20H0z"/></g>
      |    <g fill="#fff" text-anchor="middle" font-family="DejaVu Sans,Verdana,Geneva,sans-serif" font-size="11">
      |      <text x="17" y="15" fill="#010101" fill-opacity=".3">chat</text><text x="17" y="14">chat</text>
      |      <text x="62" y="15" fill="#010101" fill-opacity=".3">on gitter</text><text x="62" y="14">on gitter</text>
      |    </g>
      |</svg></a><br />
      |<b>This project has 2 main focuses:
      |<ul>
      |  <li>A functional Geometry and Vector Graphics library with various supporting utilities</li>
      |  <li>A functional strategy game and historical education library, particularly focused on simultaneous-turn, tile-based games.</li>
      |</ul>
      |</b></p>
      |""".stripMargin

  val bodyStr: String =
    s"""<div class=main>
      |$iconStrs
      |
      |<p>The Strategy games was the original motivation for the project, but the geometry and graphics library have far wider applicability. The geometry
      |  and graphics are far more developed, while the tiling and strategy games are still in a far more expermiental stage. This is in accordance with the
      |  original vision, part of which was to explore the possiblites of an Algebra of Tiling. Out of the needs of these 2 primary focuses, 2 secondary
      |  focuses have developed. If you are new to programming then just ignore these for the time being.
      |  <ul>
      |    <li>RSON A Show, 2D-Show and Persistence Library / Framework.</li>
      |    <li>Heapless Efficent funtional Array based mapping and compound deep-value type collections library. Join the Not[Boxer] Rebellion.</li>
      |  </ul>
      |</p>
      |
      |<p>It currently works on JavaFx and web page. Using canvas on both platforms. See <a href="api/index.html">Scala Docs</a> and See
      |  <a href="apiJs/index.html">Scala Docs for JavaScript target.</a>
      |</p>
      |
      |<p>This project is intended to be accessible and welcoming to programmers of all levels. Indeed it is intended as a vehicle for complete beginners to
      |  learn programming in a fun environment. To be able to begin by what for most games would be described as modding and then move down into programming
      |  as deep as they wish to go, at the pace they wish to. I want to break down the wall between modding and coding. So if you're new to programming and
      |  want to get involved, drop into the gitter channel and say hi. If you are not experienced with Scala, you have found this site and want to
      |  experiment, you will need to install Java JDK8 and sbt. more complete documentation for getting started on Linux / Windows / Mac will come later.
      |  The basic build has been tested on Linux and Windows 7. Jdk 11 preferred.</p>
      |
      |<p>However at the other end, I would welcome input from developers with greater experience and knowledge than my own. One of the goals of the project
      |  is to explore, where it is best to compose with trait / class inheritance and where to use functions. When to use mutation and when to use
      |  immutability. When to use smart, garbage collected heap based objects and when to use dumb data values. Balancing the competing priorities of
      |  elegance, succinctness, readability, run-time performance, compile time performance and accessibility for inexperienced programmers. I feel Scala
      |  is, and in particular Scala 3 will be the ideal language to explore these questions.</p>
      |
      |<p>Scala currently set to 3.3.0. Jdk 11+, 11 prefered. Scala.Js set to 1.13.2. Scala native set to 0.4.14. Sbt currently set to 1.9.1 (uses the
      | build.sbt file). Note (probably due to the JavaFx dependency). Sbt will not work running on Windows in Git Bash. Update your Mill to 0.10.7.</p>
      |
      |<p>Run <code>sbt</code> in bash from project's root folder</p>
      |
      |<p>From within the sbt console run:
      |  <ul>
      |  	<li><code class="sbt">~ Dev/reStart</code> To launch a ScalaFx window. The most useful command for development</li>
      |  	<li><code class="sbt">~ DicelessJs/fastOptJS</code> To rebuild a fast optimised JavaScript file. Use with Dev/DevPages/SbtFastDev.html</li>
      |  	<li><code class="sbt">DicelessJs/fullOptJS</code> To build a full optimised JavaScript file. Use with Dev/DevPages/SbtFullDev.html</li>
      |  	<li><code class="sbt">~ Util/test</code> Rerun tests on Util module.</li>
      |  	<li><code class="sbt">~ Tiling/test</code> Rerun tests on Tiling module.</li>
      |  	<li><code class="sbt">~ Dev/test</code> Rerun tests on, Dev module.</li>
      |  	<li><code class="sbt">~ Util/test; Tiling/test; Dev/test</code> Rerun tests on Util module.</li>
      |  	<li><code class="sbt">DocMain/doc</code> Will produce docs for all the main code in all the modules for the Jvm platform. They can be found in
      |  	  <code class="folder">Dev/SbtDir/DocMain/target/scala-3.2.2/api/</code>
      |    </li>
      |  	<li><code class="sbt">DocJs/doc</code> Will produce docs for all the main code in all the modules for the Javascript platform. They can be found
      |      in <code class="folder">Dev/SbtDir/DocJs/target/DocMain/target/scala-3.2.2/api/</code>
      |    </li>
      |    <li><code class="sbt">bothDoc</code> will perform both the above tasks.</li>
      |  	</ul>
      |  </p>
      |
      |<p>The tilde <code>~</code> tells sbt to rerun the command every time you modify and save a source file. The first command will build and launch a
      |  ScalaFx window. It will rebuild and relaunch so you can immediately see the effects of your changes. Copy the DevSettings.rson file from the
      |  Dev/Misc folder to the  Dev/User folder. Creating the directory and its parents if not already existing. Change the appStr setting in
      |  DevSettings.rson to change the application. All the examples on the richstrat.com website are available plus others. The second command will also
      |  rebuild on source changes in similar manner. However unlike with the reStart command, when you make a source file edit and save it, you will have
      |  to manually refresh the browser window after the fastOptJS command has finished the rebuild.</p>
      |
      |<p><ul>For IntellliJ useful options:
      |  <li>File => Editor => General -> Other -> tick "Show quick documentation on mouse move".</li>
      |  <li>File => "Build, Execution, Deployment" => Compiler -> "Build project automatically"</li>
      |  <li>Project-Pane => Options -> "Flatten packages"</li>
      |</ul></p>
      |
      |<p><b>The Code is currently organised into 4 modules.</b> Each module can be build artifacts for Jvm and JavaFx and for the JavaScript platform and
      |  the Web. Modules can be built for Scala-Native, but code, notably hanging commas may break the Scala-Native build as ScalaNative is still on Scala
      |  2.11.
      |</p>
      |
      |<ol>
      |<li><a href="Documentation/util.html"><b>Util Module</b></a> organised into the following packages: Organised into the following folders and packages:

      |
      |  <li><a href="Documentation/geom.html"><b>Geom Module</b></a> Depends on Util, organised into the following packages Organised into the following folders and packages:      |
      |  </li>
      |
      |  <li><a href="Documentation/Tiling.html"><b>Tiling Module</b></a> Depends on UtilMacros Util and Graphic, just has the ostrat.pGrid package depends on geom and pCanv.
      |  	<ul>
      |  		<li>Abstract regular tile geometry.</li>
      |        <li>Square and hex tile grid geometry.</li>
      |        <li>OfTile classes for the display of tiles.</li>
      |    </ul>
      |  </li>
      |
      |  <li><a href="Documentation/Earth.html"><b>Earth Module</b></a> This package and module is for Earth maps.
      |    <ul>
      |
      |      <li>ostrat.pEarth depends on geom, pCanv and pGrid
      |        <ul>
      |          <li>Earth and sphere geometry.</li>
      |          <li>Grids of Earth terrain.</li>
      |        </ul>
      |      </li>
      |
      |      <li>ostrat.pEarth.pPts large irregular Earth terrain areas. This is mainly a development aid.</li>
      |
      |    </ul>
      |  </li>
      |
      |<li><a href="Documentation/EGrid.html"><b>EGrid Module</b></a>Tiling of the whole world in Hex grids, defining the changes over the course of history.
      |  This will be a data orientated module. It will also include terrain types to model terrain, both real and imagined for local maps and higher scales
      |  right up to 0.5 metres per tile However it won't generally include the data for these. The data for the real world
      |  will be organised according to a number of levels, which are likely to change over increasingly shorter historical time frames.
      |</li>
      |
      |<li><a href="Documentation/Dev.html"><b>Dev Module</b></a> The Module as a whole Depends on all the other modules, although its constiurent parts may
      |  not. This module is for the use of developer tools and settings and for end-user applications, that may eventually end up in their own repositaries.
      |  Unlike the other modules this module has no examples sub modules. The eaxmples in the other modules, should be just that example codes to explain,
      |  illustrate provide tutorials, and to some extent test the modules core code. The examples should not include apps that have nay use in and of
      |  themsleves. Those apps belong in the Dev module.
      |  <ul>
      |    <li>User folder contains developer settings</li>
      |    <li>Developer html pages, linked to sbt target and Mill out folder artifacts.</li>
      |    <li>Documentation web pages.</li>
      |    <li> Collates the lessons in the examples folders from Util, Graphic, World and Strat. This is a number of series of lessons for beginners to
      |     Scala, complete beginners to programming and beginners in geometry, using the graphical API. These lessons are published separately as the
      |     LearnScala project.
      |    </li>
      |
      |    <li>A number of rudimentary games and applications depending on some or all of the above packages. The intention is to factor out common
      |      functionality and classes.
      |      <ul>
      |        <li>ostrat.pWW2 A grand strategy world War 2 game, using the hex tiled world terrain map.</li>
      |        <li>ostrat.p1783 A grand strategy game, also using the world map starting in 1783.</li>
      |        <li>ostrat.p305 A grand strategy game set in 305BC, using part of the world map.</li>
      |        <li>ostrat.pZug A 20th century squad based strategy game using hex tiles.</li>
      |        <li>ostrat.pGames.pCiv A human history 4x development game using hex tiles.</li>
      |        <li>ostrat.pGames.pDung A Square tile based dungeon game.</li>
      |      </ul>
      |    </li>
      |
      |      <li>ostrat.pStrat depends on geom, pCanv and pGrid and pEarth.
      |        <ul>
      |          <li>Flags</li>
      |          <li>DemoCanvas for graphics elements.</li>
      |          <li>Odds and ends.</li>
      |        </ul>
      |      </li>
      |
      |      <li>ostrat.pCloseOrder. Pre modern close order formation based battles, not using tiles.</li>
      |
      |      <li>ostrat.pSpace A solar system app.</li>
      |
      |      <li>ostratpChess. A search for an elegant implementation of Draughts and Chess.</li>
      |
      |  </ul>
      | </li>
      |</ol>
      |
      |<p>The code is organised so if it gains significant traction with other developers, then it can be broken up into separate repositories.
      |<ul>
      |<li><a href="Documentation/GitCommands.html">Useful Git commands</a></li>
      |<li><a href="Documentation/Miscellaneous.html">Miscellaneous notes</a></li>
      |</ul>
      |</p>
      |</div>
      |""".stripMargin


}