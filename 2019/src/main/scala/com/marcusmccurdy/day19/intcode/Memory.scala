package com.marcusmccurdy.day19.intcode

import scala.collection.mutable.ArrayBuffer

class Memory(val initial: IterableOnce[Long] = Iterable.empty) {
  val mem: ArrayBuffer[Long] =
    scala.collection.mutable.ArrayBuffer.from(initial)

  def apply(n: Int): Long = {
    if (n >= mem.length) {
      mem.padToInPlace(mem.length + n + 1, 0)
    }
    mem.apply(n)
  }

  def update(index: Int, elem: Long): Unit = {
    if (index >= mem.length) {
      mem.padToInPlace(index + mem.length + 1, 0)
    }
    mem.update(index, elem)
  }

}
