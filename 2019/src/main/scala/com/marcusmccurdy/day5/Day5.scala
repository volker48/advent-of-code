package com.marcusmccurdy.day5

import scala.io.StdIn.readLine

sealed case class Opcode(code: Int)
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

class Instruction(val opcode: Opcode, val parameters: Parameter*) {
  def apply(memory: Array[Int]): Option[Int] = {
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
        memory(parameters(0).value) = readLine("opcode 3 input: ").toInt
        None
      case Opcode(4) =>
        val value = parameters(0)(memory)
        println(value)
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
object Program {

  @scala.annotation.tailrec
  def execute(memory: Array[Int], instPtr: Int = 0): Unit = {
    val (instruction, newPtr) = parseInstruction(memory, instPtr)
    instruction.opcode match {
      case Opcode(99) => // halt
      case _ =>
        instruction(memory) match {
          case None         => execute(memory, newPtr)
          case Some(jmpPtr) => execute(memory, jmpPtr)
        }
    }
  }

  def parseInstruction(raw: Array[Int], instPtr: Int): (Instruction, Int) = {
    raw(instPtr) match {
      case 99 => (new Instruction(Opcode(99)), 1)
      case opParams =>
        val op = opParams % 10
        val param1Mode = (opParams / 100) % 10
        val param2Mode = opParams / 1000
        op match {
          case 1 | 2 | 7 | 8 =>
            (
              new Instruction(
                Opcode(op),
                new Parameter(raw(instPtr + 1), param1Mode),
                new Parameter(raw(instPtr + 2), param2Mode),
                new Parameter(raw(instPtr + 3))
              ),
              instPtr + 4
            )
          case 3 | 4 =>
            (
              new Instruction(
                Opcode(op),
                new Parameter(raw(instPtr + 1), param1Mode)
              ),
              instPtr + 2
            )
          case 5 | 6 =>
            (
              new Instruction(
                Opcode(op),
                new Parameter(raw(instPtr + 1), param1Mode),
                new Parameter(raw(instPtr + 2), param2Mode)
              ),
              instPtr + 3
            )
          case invalid =>
            throw new IllegalArgumentException(
              s"$invalid is not a valid instruction"
            )
        }
    }

  }
}
object Day5 extends App {
  val input =
    "3,225,1,225,6,6,1100,1,238,225,104,0,1101,69,55,225,1001,144,76,224,101,-139,224,224,4,224,1002,223,8,223,1001,224,3,224,1,223,224,223,1102,60,49,225,1102,51,78,225,1101,82,33,224,1001,224,-115,224,4,224,1002,223,8,223,1001,224,3,224,1,224,223,223,1102,69,5,225,2,39,13,224,1001,224,-4140,224,4,224,102,8,223,223,101,2,224,224,1,224,223,223,101,42,44,224,101,-120,224,224,4,224,102,8,223,223,101,3,224,224,1,223,224,223,1102,68,49,224,101,-3332,224,224,4,224,1002,223,8,223,1001,224,4,224,1,224,223,223,1101,50,27,225,1102,5,63,225,1002,139,75,224,1001,224,-3750,224,4,224,1002,223,8,223,1001,224,3,224,1,223,224,223,102,79,213,224,1001,224,-2844,224,4,224,102,8,223,223,1001,224,4,224,1,223,224,223,1,217,69,224,1001,224,-95,224,4,224,102,8,223,223,1001,224,5,224,1,223,224,223,1102,36,37,225,1101,26,16,225,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1107,677,677,224,102,2,223,223,1006,224,329,1001,223,1,223,1108,677,677,224,1002,223,2,223,1006,224,344,1001,223,1,223,107,226,226,224,1002,223,2,223,1006,224,359,101,1,223,223,1008,226,226,224,102,2,223,223,1005,224,374,1001,223,1,223,1107,226,677,224,1002,223,2,223,1006,224,389,1001,223,1,223,1008,677,226,224,1002,223,2,223,1005,224,404,1001,223,1,223,7,677,226,224,102,2,223,223,1005,224,419,1001,223,1,223,1008,677,677,224,1002,223,2,223,1006,224,434,1001,223,1,223,108,226,226,224,102,2,223,223,1006,224,449,1001,223,1,223,108,677,677,224,102,2,223,223,1006,224,464,1001,223,1,223,107,226,677,224,1002,223,2,223,1005,224,479,101,1,223,223,1108,226,677,224,1002,223,2,223,1006,224,494,1001,223,1,223,107,677,677,224,1002,223,2,223,1006,224,509,101,1,223,223,7,677,677,224,102,2,223,223,1006,224,524,1001,223,1,223,1007,226,677,224,1002,223,2,223,1005,224,539,1001,223,1,223,8,226,677,224,1002,223,2,223,1005,224,554,101,1,223,223,8,677,677,224,102,2,223,223,1005,224,569,101,1,223,223,7,226,677,224,102,2,223,223,1006,224,584,1001,223,1,223,1007,226,226,224,102,2,223,223,1006,224,599,1001,223,1,223,1107,677,226,224,1002,223,2,223,1006,224,614,1001,223,1,223,1108,677,226,224,1002,223,2,223,1005,224,629,1001,223,1,223,1007,677,677,224,102,2,223,223,1006,224,644,1001,223,1,223,108,226,677,224,102,2,223,223,1005,224,659,101,1,223,223,8,677,226,224,1002,223,2,223,1006,224,674,1001,223,1,223,4,223,99,226"
  val memory = input.split(",").map(_.toInt)

  Program.execute(memory)
  // 223 is wrong

  // 1450890
}
