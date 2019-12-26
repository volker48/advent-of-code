package com.marcusmccurdy.day12

object Day12 extends App {
  val input = """<x=-16, y=15, z=-9>
                |<x=-14, y=5, z=4>
                |<x=2, y=0, z=6>
                |<x=-3, y=18, z=9>""".stripMargin

  var moons = List(
    new Moon(-16, 15, -9, 0, 0, 0),
    new Moon(-14, 5, 4, 0, 0, 0),
    new Moon(2, 0, 6, 0, 0, 0),
    new Moon(-3, 18, 9, 0, 0, 0)
  )

  val moonPairs = pairs(moons)
  for (_ <- 0 until 1000) {
    moonPairs
      .foreach({ case (l, r) => l.applyGravity(r) })
    moons.foreach(_.applyVelocity())
  }

  val energy = moons.map(_.energy()).sum
  // 909000 too high
  // 908091 too high
  // 302697 too high
  // 939
  // 344
  println(energy)

  @scala.annotation.tailrec
  def pairs[A](m: List[A], acc: List[(A, A)] = List.empty): List[(A, A)] = {
    m match {
      case Nil | _ :: Nil => acc
      case head :: next =>
        val newAcc = acc.appendedAll(next.zip(Iterator.continually(head)))
        pairs(next, newAcc)
    }
  }
}
