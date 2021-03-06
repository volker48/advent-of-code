package com.marcusmccurdy

import scala.collection.mutable.ListBuffer

sealed case class Opcode(code: Int)

class Instruction(opcode: Opcode,
                  xIndex: Int = 0,
                  yIndex: Int = 0,
                  destIndex: Int = 0) {
  def apply(memory: ListBuffer[Int]): Unit = {
    val x = memory(xIndex)
    val y = memory(yIndex)
    val result = opcode match {
      case Opcode(1) => x + y
      case Opcode(2) => x * y
    }
    memory(destIndex) = result
  }
}
object Program {

  @scala.annotation.tailrec
  def execute(instructions: List[Instruction], memory: ListBuffer[Int]): Int = {
    instructions match {
      case instruction :: Nil =>
        instruction(memory)
        memory(0)
      case instruction :: xs =>
        instruction(memory)
        execute(xs, memory)
    }
  }

  @scala.annotation.tailrec
  def fromRaw(
    raw: List[Int],
    acc: ListBuffer[Instruction] = ListBuffer.empty
  ): List[Instruction] = {
    raw match {
      case 99 :: _ =>
        acc.toList
      case op :: xIndex :: yIndex :: destIndex :: rest =>
        acc += new Instruction(Opcode(op), xIndex, yIndex, destIndex)
        fromRaw(rest, acc)
    }
  }

}
object Day2 extends App {
  val input =
    "1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,13,19,1,9,19,23,1,6,23,27,2,27,9,31,2,6,31,35,1,5,35,39,1,10,39,43,1,43,13,47,1,47,9,51,1,51,9,55,1,55,9,59,2,9,59,63,2,9,63,67,1,5,67,71,2,13,71,75,1,6,75,79,1,10,79,83,2,6,83,87,1,87,5,91,1,91,9,95,1,95,10,99,2,9,99,103,1,5,103,107,1,5,107,111,2,111,10,115,1,6,115,119,2,10,119,123,1,6,123,127,1,127,5,131,2,9,131,135,1,5,135,139,1,139,10,143,1,143,2,147,1,147,5,0,99,2,0,14,0"
  val instructions = input.split(",").map(_.toInt).toList

  val memory = ListBuffer.from(instructions)
  val part1 = Program.execute(Program.fromRaw(instructions), memory)
  println(part1)
  val nums = 0 to 99
  val pairs = for (noun <- nums; verb <- nums) yield (noun, verb)
  for ((noun, verb) <- pairs) {
    val trial = instructions.updated(1, noun).updated(2, verb)
    val result = Program.execute(Program.fromRaw(trial), ListBuffer.from(trial))
    if (result == 19690720) {
      println(s"noun $noun verb $verb")
      val answer = 100 * noun + verb
      println(answer)

    }
  }

}
