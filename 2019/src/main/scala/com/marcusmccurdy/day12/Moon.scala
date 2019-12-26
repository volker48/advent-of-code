package com.marcusmccurdy.day12

class Moon(var x: Int,
           var y: Int,
           var z: Int,
           var velX: Int,
           var velY: Int,
           var velZ: Int) {

  def applyGravity(other: Moon): Unit = {
    val (dxl, dxr) = Moon.compare(x, other.x)
    val (dyl, dyr) = Moon.compare(y, other.y)
    val (dzl, dzr) = Moon.compare(z, other.z)
    updateVelocity(dxl, dyl, dzl)
    other.updateVelocity(dxr, dyr, dzr)
  }

  def updateVelocity(dx: Int, dy: Int, dz: Int): Unit = {
    velX += dx
    velY += dy
    velZ += dz
  }

  def applyVelocity(): Unit = {
    x += velX
    y += velY
    z += velZ

  }

  def energy(): Int = {
    (x.abs + y.abs + z.abs) * (velX.abs + velY.abs + velZ.abs)
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Moon]

  override def equals(other: Any): Boolean = other match {
    case that: Moon =>
      (that canEqual this) &&
        x == that.x &&
        y == that.y &&
        z == that.z &&
        velX == that.velX &&
        velY == that.velY &&
        velZ == that.velZ
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x, y, z, velX, velY, velZ)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = s"Moon($x, $y, $z, $velX, $velY, $velZ)"

  override def clone(): AnyRef = {
    new Moon(x, y, z, velX, velY, velZ)
  }
}

object Moon {

  def compare(left: Int, right: Int): (Int, Int) = {
    if (left < right) {
      (1, -1)
    } else if (left > right) {
      (-1, 1)
    } else {
      (0, 0)
    }
  }
}
