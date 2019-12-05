package com.marcusmccurdy

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

object Day4 extends App {

  @scala.annotation.tailrec
  def toDigits(v: Int, l: List[Int] = List.empty[Int]): List[Int] = {
    val mod = v % 10
    val rem = v / 10
    rem match {
      case 0 => mod :: l
      case _ => toDigits(rem, mod :: l)
    }
  }
  @scala.annotation.tailrec
  def adjacentSame(password: List[Int],
                   acc: List[Boolean] = List.empty[Boolean]): Boolean = {
    def getBool(l: List[Boolean]) = {
      l.dropWhile(p => !p).headOption match {
        case None    => false
        case Some(p) => p
      }
    }
    password match {
      case Nil      => getBool(matches)
      case _ :: Nil => getBool(matches)
      case l :: r :: rest =>
        val same = l == r
        adjacentSame(r :: rest, same :: matches)
    }
  }
  def pairsIncreasing(digits: List[Int]): Boolean = {
    @scala.annotation.tailrec
    def increasing(digits: List[Int], acc: Boolean = true): Boolean = {
      digits match {
        case Nil      => acc
        case _ :: Nil => acc
        case l :: r :: rest =>
          increasing(r :: rest, acc && l <= r)
      }
    }
    increasing(digits)
  }

  def validPassword(password: Int): Boolean = {
    val digits = toDigits(password)
    adjacentSame(digits) && pairsIncreasing(digits)
  }

  val passwords = 372037 to 905157
  // bad guess 533121
  println(passwords.map(validPassword).size)

}
