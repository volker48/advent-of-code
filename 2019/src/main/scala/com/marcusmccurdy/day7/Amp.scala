package com.marcusmccurdy.day7

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}

object Amp {
  sealed trait AmpMessage
  final case class InputSignal(value: Int) extends AmpMessage
  final case class OutputSignal(value: Int) extends AmpMessage

  def apply(program: Array[Int],
            next: ActorRef[AmpMessage]): Behavior[AmpMessage] =
    waitingForInputs(program, List.empty, next)

  private def waitingForInputs(
    program: Array[Int],
    inputs: List[Int],
    next: ActorRef[AmpMessage]
  ): Behavior[AmpMessage] =
    Behaviors.receive { (context, message) =>
      (inputs, message) match {
        case (phase :: Nil, InputSignal(input)) =>
          context.log.info(s"phase $phase input signal $input")
          running(program, phase, input, context, next)
        case (Nil, InputSignal(phase)) =>
          waitingForInputs(program, phase :: inputs, next)
        case _ => Behaviors.unhandled
      }
    }

  private def running(program: Array[Int],
                      phase: Int,
                      inputSignal: Int,
                      context: ActorContext[AmpMessage],
                      next: ActorRef[AmpMessage]): Behavior[AmpMessage] = {
    context.log.info("running program")
    val outputFn = Some((v: Int) => {
      context.log.info("sending output signal")
      next ! Amp.InputSignal(v)
    })
    val inputs = List(phase, inputSignal).iterator
    val inputFn = Some(() => {
      val input = inputs.next
      context.log.info(s"getting input: $input")
      input
    })
    Program.execute(program, inputFn = inputFn, outputFn = outputFn)
    Behaviors.stopped
  }
}
