package com.marcusmccurdy.day16

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Day16 extends App {
  var input =
    "59767332893712499303507927392492799842280949032647447943708128134759829623432979665638627748828769901459920331809324277257783559980682773005090812015194705678044494427656694450683470894204458322512685463108677297931475224644120088044241514984501801055776621459006306355191173838028818541852472766531691447716699929369254367590657434009446852446382913299030985023252085192396763168288943696868044543275244584834495762182333696287306000879305760028716584659188511036134905935090284404044065551054821920696749822628998776535580685208350672371545812292776910208462128008216282210434666822690603370151291219895209312686939242854295497457769408869210686246"
      .split("")
      .map(_.toInt)
  @scala.annotation.tailrec
  def genPattern(length: Int,
                 outputPos: Int,
                 input: Array[Int],
                 output: ArrayBuffer[Int] = ArrayBuffer.empty): Array[Int] = {
    outputPos match {
      case x if x == length => output.toArray
      case _ =>
        val pattern = go(length, outputPos)
        output += input.zip(pattern).map({ case (l, r) => l * r }).sum.abs % 10
        genPattern(length, outputPos + 1, input, output)
    }

  }

  val basePattern = Array(0, 1, 0, -1)
  @scala.annotation.tailrec
  def go(length: Int,
         outputPos: Int,
         output: ArrayBuffer[ArrayBuffer[Int]] = ArrayBuffer.empty,
         count: Int = 0): ArrayBuffer[Int] = {

    count match {
      case x if x == length + 1 => output.flatten
      case _ =>
        val idx = count % 4
        val number = basePattern(idx)
        var x = ArrayBuffer.fill(outputPos + 1)(number)
        if (count == 0) {
          x = x.drop(1)
        }
        go(length, outputPos, output :+ x, count + 1)
    }
  }
  val length = input.length
  for (i <- 0 to 99) {
    println(s"Phase $i")
    input = genPattern(length, 0, input)
  }
  println(input.take(8).mkString(""))

  //53541724 too low
}
