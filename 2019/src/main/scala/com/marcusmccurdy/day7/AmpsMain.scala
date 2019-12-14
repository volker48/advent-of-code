package com.marcusmccurdy.day7

import akka.actor.typed.{Behavior, Terminated}
import akka.actor.typed.scaladsl.Behaviors
import com.marcusmccurdy.day7.Amp.{AmpMessage, InputSignal, OutputSignal}

object AmpsMain {
  val program: Array[Int] =
    "3,8,1001,8,10,8,105,1,0,0,21,34,43,64,85,98,179,260,341,422,99999,3,9,1001,9,3,9,102,3,9,9,4,9,99,3,9,102,5,9,9,4,9,99,3,9,1001,9,2,9,1002,9,4,9,1001,9,3,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,102,3,9,9,101,4,9,9,102,3,9,9,4,9,99,3,9,101,2,9,9,1002,9,3,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99"
      .split(",")
      .map(_.toInt)

  def apply(): Behavior[AmpMessage] = {
    Behaviors.setup[AmpMessage] { context =>
      val remaining = Array(0, 1, 2, 3, 4).permutations.length
      for ((perm, i) <- Array(0, 1, 2, 3, 4).permutations.zipWithIndex) {
        val e = context.spawn(Amp(program.clone, context.self), s"AmpE$i")
        val d = context.spawn(Amp(program.clone, e), s"AmpD$i")
        val c = context.spawn(Amp(program.clone, d), s"AmpC$i")
        val b = context.spawn(Amp(program.clone, c), s"AmpB$i")
        val a = context.spawn(Amp(program.clone, b), s"AmpA$i")
        a ! Amp.InputSignal(perm(0))
        b ! Amp.InputSignal(perm(1))
        c ! Amp.InputSignal(perm(2))
        d ! Amp.InputSignal(perm(3))
        e ! Amp.InputSignal(perm(4))
        a ! Amp.InputSignal(0)

      }
      collectResults(0, 0)
    }
  }

  private def collectResults(recvd: Int,
                             maxOutput: Int): Behavior[AmpMessage] = {
    Behaviors.receive { (context, message) =>
      val n = recvd + 1
      message match {
        case InputSignal(value) =>
          val newMax = value max maxOutput
          if (n == 120) {
            context.log.info(s"Max output is $newMax")
            Behaviors.stopped
          } else {
            context.log.info(s"Max output is $newMax received $recvd")
            collectResults(n, newMax)
          }
      }
    }
  }
}
