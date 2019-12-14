package com.marcusmccurdy.day7

import akka.actor.typed.ActorSystem

object Day7 extends App {
  // 30940
  val ampsMain: ActorSystem[Amp.AmpMessage] =
    ActorSystem(AmpsMain(), "Amps")
}
