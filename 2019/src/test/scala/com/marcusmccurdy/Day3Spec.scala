package com.marcusmccurdy

class Day3Spec extends UnitSpec {
  "updateGrid" should "add a point to the grid" in {
    val grid = Map.empty[Point, Set[String]]
    val result = Day3.updateGrid(Point(1, 1), grid, "1")
    result shouldBe Map(Point(1, 1) -> Set("1"))
  }

  "updateGrid" should "be able to add multiple points" in {
    var grid = Map.empty[Point, Set[String]]
    grid = Day3.updateGrid(Point(1, 1), grid, "1")
    grid = Day3.updateGrid(Point(1, 2), grid, "1")
    grid = Day3.updateGrid(Point(1, 3), grid, "1")
    grid = Day3.updateGrid(Point(1, 3), grid, "2")
    grid shouldBe Map(
      Point(1, 1) -> Set("1"),
      Point(1, 2) -> Set("1"),
      Point(1, 3) -> Set("1", "2")
    )
  }

  "vec toPoints" should "go up" in {
    val vec = new Vec("U", 3)
    val result = vec.toPoints(Point(0, 0), Map.empty[Point, Set[String]], "1")
    result shouldBe Map(
      Point(0, 0) -> Set("1"),
      Point(0, 1) -> Set("1"),
      Point(0, 2) -> Set("1"),
      Point(0, 3) -> Set("1")
    )
  }
  it should "go down" in {
    val down = new Vec("D", magnitude = 2)
    down.toPoints(Point(0, 0), Map.empty[Point, Set[String]], "1") shouldBe Map(
      Point(0, 0) -> Set("1"),
      Point(0, -1) -> Set("1"),
      Point(0, -2) -> Set("1"),
    )
  }
}
