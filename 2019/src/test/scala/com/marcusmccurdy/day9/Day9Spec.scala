package com.marcusmccurdy.day9

import com.marcusmccurdy.UnitSpec
import com.marcusmccurdy.day9.intcode.Program

class Day9Spec extends UnitSpec {
  "memory" should "expand" in {
    val ea = new Memory()
    ea(5) = 10
    ea(5) shouldEqual 10
    ea(0) shouldEqual 0
  }

  "copy test" should "return itself" in {
    val input = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"
      .split(",")
      .map(_.toLong)
    val memory = new Memory(input)
    Program.execute(memory)
  }

  "16 digit" should "print 16 digit number" in {
    val input = "1102,34915192,34915192,7,4,7,99,0".split(",").map(_.toLong)
    val memory = new Memory(input)
    Program.execute(memory)
  }

  "203" should "work" in {
    val input = "203,15".split(",").map(_.toLong)
    Program.execute(new Memory(input))
  }
}

object blah extends App {
  val input = "203,2,99".split(",").map(_.toLong)
  val memory = new Memory(input)
  Program.execute(memory)
  println(memory.mem(99))
}
