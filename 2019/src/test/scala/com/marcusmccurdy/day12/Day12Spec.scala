package com.marcusmccurdy.day12

import com.marcusmccurdy.UnitSpec

class Day12Spec extends UnitSpec {
  "pairs" should "work with 1" in {
    Day12.pairs(List("A")) should equal(List.empty)
  }

  "pairs" should "work with 2" in {
    Day12.pairs(List("A", "B")) should equal(List(("B", "A")))
  }

  "pairs" should "work with 3" in {
    val pairs = Day12.pairs(List("A", "B", "C"))
    pairs should contain(("B", "A"))
    pairs should contain(("C", "A"))
    pairs should contain(("C", "B"))
    pairs.length should equal(3)
  }

  "moon" should "clone" in {
    val m1 = new Moon(1, 2, 3, 4, 5, 6)
    val m1Clone = m1.clone().asInstanceOf[Moon]
    m1Clone.x = 42
    m1.x should equal(1)
  }

}
