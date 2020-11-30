package com.marcusmccurdy.day19.intcode

class Parameter(val value: Long, val relBase: Long, val mode: Int = 0) {
  def apply(memory: Memory): Long = {
    mode match {
      case 0 => memory(value.toInt)
      case 1 => value
      case 2 => memory((relBase + value).toInt)
      case default =>
        throw new IllegalArgumentException(s"Invalid parameter mode: $default")
    }
  }

  override def toString = s"Parameter($value, $relBase, $mode)"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Parameter]

  override def equals(other: Any): Boolean = other match {
    case that: Parameter =>
      (that canEqual this) &&
        value == that.value &&
        mode == that.mode
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(value, mode)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
