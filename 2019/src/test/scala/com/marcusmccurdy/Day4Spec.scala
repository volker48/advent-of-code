package com.marcusmccurdy

class Day4Spec extends UnitSpec {
  "toDigits" should "work with ints less than 10" in {
    Day4.toDigits(4) shouldBe List(4)
  }
  it should "work with larger ints" in {
    Day4.toDigits(1234) shouldBe List(1, 2, 3, 4)
  }

  "adjacentSame" should "return [true]" in {
    val digits = Day4.toDigits(11)
    Day4.adjacentSame(digits) shouldBe true
  }

  "adjacentSame 12" should "be false" in {
    val digits = Day4.toDigits(12)
    Day4.adjacentSame(digits) shouldBe false
  }

  "adjacentSame 1234" should "return true" in {
    val digits = Day4.toDigits(12344)
    Day4.adjacentSame(digits) shouldBe true
  }

  "pairsIncreasing" should "be true" in {
    val digits = Day4.toDigits(111111)
    Day4.pairsIncreasing(digits) shouldBe true
  }

  "pairsIncreasing 111123" should "be true" in {
    val digits = Day4.toDigits(111123)
    Day4.pairsIncreasing(digits) shouldBe true
  }

  "pairsIncreasing 223450" should "be false" in {
    val digits = Day4.toDigits(223450)
    Day4.pairsIncreasing(digits) shouldBe false
  }

  "pairsIncreasing 2234506" should "be false" in {
    val digits = Day4.toDigits(2234506)
    Day4.pairsIncreasing(digits) shouldBe false
  }

  "111111" should "be valid" in {
    Day4.validPassword(111111) shouldBe true
  }
  "223450" should "be invalid" in {
    Day4.validPassword(223450) shouldBe false
  }

  "123789" should "be invalid" in {
    Day4.validPassword(123789) shouldBe false
  }
  "372037" should "be invalid" in {
    Day4.validPassword(372037) shouldBe false
  }
  "11111" should "be invalid" in {
    Day4.validPassword2(1111) shouldBe false
  }

  "123444" should "be invalid" in {
    Day4.validPassword2(123444) shouldBe false
  }

  "111122" should "be valid pairsIncreasingStrict" in {
    val digits = Day4.toDigits(111122)
    Day4.adjacentSameStrict(digits) shouldBe true
  }

  "1111222" should "be invalid pairsIncreasing" in {
    val digits = Day4.toDigits(1111222)
    Day4.adjacentSameStrict(digits) shouldBe false
  }

  "111224" should "be valid pairsIncreasing" in {
    val digits = Day4.toDigits(111224)
    Day4.adjacentSameStrict(digits) shouldBe true
  }

  "112224" should "be valid pairsIncreasing" in {
    val digits = Day4.toDigits(112224)
    Day4.adjacentSameStrict(digits) shouldBe true
  }

  "223456" should "be valid pairsIncreasing" in {
    val digits = Day4.toDigits(223456)
    Day4.adjacentSameStrict(digits) shouldBe true
  }
}
