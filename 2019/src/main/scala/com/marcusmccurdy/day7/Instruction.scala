package com.marcusmccurdy.day7

import scala.io.StdIn.readLine

class Instruction(val opcode: Opcode, val parameters: Parameter*) {
  def apply(memory: Array[Int],
            inputFn: Option[() => Int] = None,
            outputFn: Option[Int => Unit] = None): Option[Int] = {
    opcode match {
      case Opcode(1) =>
        memory(parameters(2).value) = parameters(0)(memory) + parameters(1)(
          memory
        )
        None
      case Opcode(2) =>
        memory(parameters(2).value) = parameters(0)(memory) * parameters(1)(
          memory
        )
        None
      case Opcode(3) =>
        val input = inputFn match {
          case None     => readLine("opcode 3 input: ").toInt
          case Some(fn) => fn()
        }
        memory(parameters(0).value) = input
        None
      case Opcode(4) =>
        val value = parameters(0)(memory)
        outputFn match {
          case None     => println(value)
          case Some(fn) => fn(value)
        }
        None
      case Opcode(5) =>
        if (parameters(0)(memory) != 0) {
          return Option(parameters(1)(memory))
        }
        None
      case Opcode(6) =>
        if (parameters(0)(memory) == 0) {
          return Option(parameters(1)(memory))
        }
        None
      case Opcode(7) =>
        var result = 0
        if (parameters(0)(memory) < parameters(1)(memory)) {
          result = 1
        }
        memory(parameters(2).value) = result
        None
      case Opcode(8) =>
        var result = 0
        if (parameters(0)(memory) == parameters(1)(memory)) {
          result = 1
        }
        memory(parameters(2).value) = result
        None
    }

  }

  override def toString = s"Instruction($opcode, $parameters)"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Instruction]

  override def equals(other: Any): Boolean = other match {
    case that: Instruction =>
      (that canEqual this) &&
        opcode == that.opcode &&
        parameters == that.parameters
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(opcode, parameters)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
