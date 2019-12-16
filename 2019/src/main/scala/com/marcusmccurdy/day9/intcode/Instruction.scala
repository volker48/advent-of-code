package com.marcusmccurdy.day9.intcode

import com.marcusmccurdy.day9.Memory

import scala.io.StdIn.readLine

sealed case class InstructionResult(jmpPtr: Option[Long] = None,
                                    relBaseOffset: Option[Long] = None)

class Instruction(val opcode: Opcode, val parameters: Parameter*) {
  def apply(memory: Memory): InstructionResult = {
    opcode match {
      case Opcode(1) =>
        val location = if (parameters(2).mode == 2) {
          parameters(2).value + parameters(2).relBase
        } else {
          parameters(2).value
        }
        memory(location.toInt) = parameters(0)(memory) + parameters(1)(memory)
        InstructionResult()
      case Opcode(2) =>
        val location = if (parameters(2).mode == 2) {
          parameters(2).value + parameters(2).relBase
        } else {
          parameters(2).value
        }
        memory(location.toInt) = parameters(0)(memory) * parameters(1)(memory)
        InstructionResult()
      case Opcode(3) =>
        val location = if (parameters(0).mode == 2) {
          parameters(0).value + parameters(0).relBase
        } else {
          parameters(0).value
        }
        memory(location.toInt) = readLine("opcode 3 input: ").toInt
        InstructionResult()
      case Opcode(4) =>
        val value = parameters(0)(memory)
        println(value)
        InstructionResult()
      case Opcode(5) =>
        if (parameters(0)(memory) != 0) {
          return InstructionResult(Option(parameters(1)(memory)))
        }
        InstructionResult()
      case Opcode(6) =>
        if (parameters(0)(memory) == 0) {
          return InstructionResult(Option(parameters(1)(memory)))
        }
        InstructionResult()
      case Opcode(7) =>
        var result = 0
        if (parameters(0)(memory) < parameters(1)(memory)) {
          result = 1
        }
        val location = if (parameters(2).mode == 2) {
          parameters(2).value + parameters(2).relBase
        } else {
          parameters(2).value
        }
        memory(location.toInt) = result
        InstructionResult()
      case Opcode(8) =>
        var result = 0
        if (parameters(0)(memory) == parameters(1)(memory)) {
          result = 1
        }
        val location = if (parameters(2).mode == 2) {
          parameters(2).value + parameters(2).relBase
        } else {
          parameters(2).value
        }
        memory(location.toInt) = result
        InstructionResult()
      case Opcode(9) =>
        val offset = parameters(0)(memory)
        InstructionResult(relBaseOffset = Some(offset))
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
