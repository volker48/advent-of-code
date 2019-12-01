package com.marcusmccurdy

object Day1 extends App {
  val input = """119341
                |141179
                |104964
                |90174
                |106547
                |78708
                |128438
                |84980
                |66768
                |106894
                |82394
                |95489
                |52669
                |95631
                |66849
                |135461
                |88795
                |77228
                |123981
                |72391
                |103823
                |63982
                |64997
                |145184
                |69311
                |117874
                |106314
                |101353
                |114745
                |88889
                |83875
                |85051
                |101949
                |118449
                |65252
                |135144
                |68497
                |132390
                |105856
                |121098
                |144838
                |87829
                |141579
                |140516
                |126377
                |55911
                |89261
                |76610
                |135578
                |110154
                |147648
                |106639
                |143854
                |91637
                |84297
                |117449
                |75041
                |58141
                |132983
                |114681
                |75817
                |116453
                |56544
                |65230
                |90622
                |133929
                |92089
                |134197
                |104271
                |127204
                |149761
                |128253
                |132253
                |109273
                |88734
                |136243
                |122128
                |119246
                |121118
                |128448
                |137797
                |66767
                |67488
                |90616
                |97823
                |51642
                |98789
                |94130
                |128350
                |101600
                |85570
                |145174
                |127257
                |141772
                |144415
                |82959
                |58548
                |129474
                |125838
                |68864
                |""".stripMargin
  val part1 = input.split("\n").map(_.toInt).map(calcFuel).sum

  println(part1)

  val part2 = input.split("\n").map(_.toInt).map(calcRecursive(_)).sum

  println(part2)

  def calcFuel(mass: Int): Int = {
    Math.floor(mass / 3).toInt - 2
  }

  @scala.annotation.tailrec
  def calcRecursive(mass: Int, acc: Int = 0): Int = {
    val fuel = calcFuel(mass)
    fuel match {
      case x if x <= 0 => acc
      case _           => calcRecursive(fuel, acc + fuel)
    }
  }

}
