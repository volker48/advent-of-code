package com.marcusmccurdy.day9.intcode

import com.marcusmccurdy.day9.Memory

object Program {

  @scala.annotation.tailrec
  def execute(memory: Memory, instPtr: Int = 0, relBase: Long = 0): Unit = {
    val (instruction, newPtr) = parseInstruction(memory, instPtr, relBase)
    instruction.opcode match {
      case Opcode(99) => // halt
      case _ =>
        instruction(memory) match {
          case InstructionResult(None, None) => execute(memory, newPtr, relBase)
          case InstructionResult(None, Some(offset)) =>
            execute(memory, newPtr, relBase + offset)
          case InstructionResult(Some(jmpPtr), None) =>
            execute(memory, jmpPtr.toInt, relBase)
          case default =>
            throw new IllegalArgumentException(s"invalid $default")
        }
    }
  }

  def parseInstruction(raw: Memory,
                       instPtr: Int,
                       relBase: Long): (Instruction, Int) = {
    raw(instPtr) match {
      case 99 => (new Instruction(Opcode(99)), 1)
      case opParams =>
        val op = (opParams % 10).toInt
        val param1Mode = ((opParams / 100) % 10).toInt
        val param2Mode = ((opParams / 1000) % 10).toInt
        val param3Mode = (opParams / 10000).toInt
        op match {
          case 1 | 2 | 7 | 8 =>
            (
              new Instruction(
                Opcode(op),
                new Parameter(raw(instPtr + 1), relBase, param1Mode),
                new Parameter(raw(instPtr + 2), relBase, param2Mode),
                new Parameter(raw(instPtr + 3), relBase, param3Mode)
              ),
              instPtr + 4
            )
          case 3 | 4 | 9 =>
            (
              new Instruction(
                Opcode(op),
                new Parameter(raw(instPtr + 1), relBase, param1Mode)
              ),
              instPtr + 2
            )
          case 5 | 6 =>
            (
              new Instruction(
                Opcode(op),
                new Parameter(raw(instPtr + 1), relBase, param1Mode),
                new Parameter(raw(instPtr + 2), relBase, param2Mode)
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
