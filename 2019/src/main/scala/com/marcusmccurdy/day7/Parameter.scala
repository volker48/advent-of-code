package com.marcusmccurdy.day7

class Parameter(val value: Int, val mode: Int = 0) {
  def apply(memory: Array[Int]): Int = {
    mode match {
      case 0 => memory(value)
      case 1 => value
    }
  }

  override def toString = s"Parameter($value, $mode)"

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
