# Lessons learned

### Day 1
1. Read the problem closely. I initially forgot to do the subtraction step.

### Day 2
1. Pattern matching Arrays can be annoying. I attempted to match `Array(1, x, y, index, tail @ _*)`, but this causes
tail to become a `Seq` and I could no longer recursively call my process function. As a work around I just converted the
instructions to a `List`, but there is probably a better way.
    2. I hadn't used for comprehensions before and used it to generate all the noun, verb combinations.
    3. It isn't as easy to "break" in Scala. I wanted to break out of the for comprehension when the correct result is 
    found, but I would need to restructure the code so I gave up on it.
    
### Day 3
Day 3 took me a long time to get working. I initially started with a geometric approach and parsed the input in to
Vectors -> Points -> Lines, but then I realized it would be much easier to use a `Map[Point, Set[String]]` to track the
points as keys and the values of each line that "touches" that point. Once that map is created, the map needs to be
filtered by values where the size of the set is > 1 and then calculate the L1 distance from each intersection point to
the origin and find the minimum distance.

I spent a great deal of time trying to figure out how to effectively pattern match on the direction to populate the map.
I knew I wanted to match on the strings `"U"` etc., but I didn't want to repeat a bunch of code in each match. Based on
the direction either X or Y needs to be incremented or decremented. I wanted a way to return the range of values to
use for incrementing / decrementing and somehow also return a Point that has either X or Y fixed depending on the direction.
When going up or down the X is static and when going left or right the Y is static. Currying saved the day!

```scala
  def curriedPoint: Int => Int => Point = Point.curried

  def toPoints(prev: Point, grid: Grid, lineId: String): Grid = {
    val pointValGen = direction match {
      case "U" =>
        (curriedPoint(prev.x)(_), prev.y to (prev.y + magnitude))
      case "D" =>
        (curriedPoint(prev.x)(_), prev.y to (prev.y - magnitude) by -1)
      case "R" =>
        (curriedPoint(_: Int)(prev.y), prev.x to (prev.x + magnitude))
      case "L" =>
        (curriedPoint(_: Int)(prev.y), prev.x to (prev.x - magnitude) by -1)
    }
    pointValGen._2
      .map(value => pointValGen._1(value))
      .foldLeft(grid)((g, p) => updateGrid(p, g, lineId))
  }
```

### Day 4
Day 4 seemed much easier than Day 3. I ended up writing quite a few unit tests
for this day to check some edge cases I was hitting causing me to get incorrect
answers.

### Day 8
I had the order of the tuple for `zipWithIndex` reversed, but it makes sense
that the index is the second element of the tuple since the method is named 
`zipWITHINDEX`.
