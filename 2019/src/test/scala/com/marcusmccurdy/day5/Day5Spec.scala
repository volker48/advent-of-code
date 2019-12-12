package com.marcusmccurdy.day5

import com.marcusmccurdy.UnitSpec
import com.marcusmccurdy.intcode.{Instruction, Opcode, Parameter, Program}

class Day5Spec extends UnitSpec {

  "parseInstructions" should "stop on 99" in {
    Program.parseInstruction(Array(99), 0) should equal(
      new Instruction(Opcode(99)),
      1
    )
  }

  "parseInstruction" should "work for add" in {
    val (instruction, _) =
      Program.parseInstruction(Array(1, 1, 2, 3, 99), 0)
    instruction should equal(
      new Instruction(
        Opcode(1),
        new Parameter(1),
        new Parameter(2),
        new Parameter(3)
      )
    )
    instruction.parameters(0).value shouldBe 1
    instruction.parameters(1).value shouldBe 2
    instruction.parameters(2).value shouldBe 3

  }

  "parseInstructions" should "work for opcode 3" in {
    val (instruction, _) = Program.parseInstruction(Array(3, 50, 99), 0)
    instruction.opcode shouldBe Opcode(3)
    instruction.parameters should have length 1
    instruction.parameters.head.value shouldBe 50
  }
  "parseInstruction" should "work for add with immediate mode params" in {
    val (instruction, _) =
      Program.parseInstruction(Array(1101, 2, 2, 3, 99), 0)
    instruction.opcode shouldBe Opcode(1)
    instruction.parameters should have length 3
    instruction.parameters(0).mode shouldBe 1
    instruction.parameters(1).mode shouldBe 1
  }
  "instructions with position mode" should "run" in {
    val memory = Array(1, 5, 6, 0, 99, 15, 15)
    Program.execute(memory)
    memory(0) shouldBe 30
  }

  "instructions with immediate mode" should "run" in {
    val memory = Array(1101, 2, 2, 3, 99)
    Program.execute(memory)
    memory(3) shouldBe 4
  }

  "multiply with immediate mode" should "run" in {
    val memory = Array(1102, 2, 3, 3, 99)
    Program.execute(memory, 0)
    memory(3) shouldBe 6
  }

  "two instructions" should "run" in {
    val memory = Array(1102, 2, 3, 0, 1001, 0, -7, 1, 4, 1, 99)
    Program.execute(memory)
    memory(1) shouldBe -1
  }
}
