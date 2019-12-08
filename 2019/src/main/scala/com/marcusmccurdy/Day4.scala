package com.marcusmccurdy

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
  def adjacentSame(password: List[Int], acc: Boolean = false): Boolean = {
    password match {
      case Nil      => acc
      case _ :: Nil => acc
      case l :: r :: rest =>
        adjacentSame(r :: rest, acc || (l == r))
    }
  }

  @scala.annotation.tailrec
  def adjacentSameStrict(password: List[Int], acc: Boolean = false): Boolean = {
    password match {
      case Nil           => acc
      case _ :: Nil      => acc
      case l :: r :: Nil => acc || (l == r)
      case l :: r :: rr :: rest =>
        adjacentSameStrict(
          (l :: r :: rr :: rest).dropWhile(p => p == l),
          acc || (l == r) && (l != rr)
        )
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

  def validPassword2(password: Int): Boolean = {
    val digits = toDigits(password)
    adjacentSameStrict(digits) && pairsIncreasing(digits)
  }

  val passwords = 372037 to 905157
  // bad guess 533121
  println(passwords.map(validPassword).count(p => p))
  // bad guess 413
  // bad guess 222
  println(passwords.map(validPassword2).count(p => p))

}
