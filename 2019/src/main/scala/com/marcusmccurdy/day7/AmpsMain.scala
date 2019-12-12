package com.marcusmccurdy.day7

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Amplifiers {
  final case class Start(name: String)
  def apply(): Behavior[Start] = {
    Behaviors.setup { context =>
      val amp1 = context.spawn
    }
  }
}
