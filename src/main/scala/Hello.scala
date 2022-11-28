object Hello {

  def main(args: Array[String]): Unit = {
    var k:Float = 1
    var s:Float = 0

    var x: Unit = (0 to 100).foreach(i => {
      if (i % 2 == 0 ) {
        s += 4 / k
      }
      else {
        s -= 4 / k
      }
      k += 2
    })

    println("Resultado: " + s )
  }

}
