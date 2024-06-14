/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import java.net.*, java.io.{ BufferedReader, InputStreamReader }

object ServRaw extends App
{ deb("Starting")
  val servSock: ServerSocket = new ServerSocket(8080)
  var numConns: Int = 0
  while(true)
  { val sock: Socket = servSock.accept()
    val conn = new ConnSesh(numConns, sock)
    conn.run()
    numConns += 1
  }
  servSock.close()
  deb("Finishing")
}

class ConnSesh(val cNum: Int, val sock: Socket) extends Runnable
{
  override def run(): Unit =
  { val readbuf: BufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(sock.getInputStream()))
    var line: String = null
    while ( { line = readbuf.readLine; line != null && line != "" }) {
      println(line)
    }
    sock.getOutputStream.write(s"HTTP/1.1 200 OK\nContent-Type: text/plain\n\nHello, Server with Http! Connection: $cNum".getBytes)
    readbuf.close
  }
}