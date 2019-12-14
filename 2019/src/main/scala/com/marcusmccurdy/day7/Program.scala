package com.marcusmccurdy.day7

object Program {

  @scala.annotation.tailrec
  def execute(memory: Array[Int],
              inputFn: Option[() => Int] = None,
              outputFn: Option[Int => Unit] = None,
              instPtr: Int = 0): Unit = {
    val (instruction, newPtr) = parseInstruction(memory, instPtr)
    instruction.opcode match {
      case Opcode(99) => // halt
      case _ =>
        instruction(memory, inputFn, outputFn) match {
          case None         => execute(memory, inputFn, outputFn, newPtr)
          case Some(jmpPtr) => execute(memory, inputFn, outputFn, jmpPtr)
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
