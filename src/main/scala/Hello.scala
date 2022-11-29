
// Scala code for thread creation by extending
// the Thread class
class Hello extends Thread {
  override def run() {
    // Displaying the thread that is running
    println("Thread " + Thread.currentThread().getName() +
      " is running.")
  }
}

class Pi(var start: Int, var end: Int, var width: Double) extends Runnable {
  var result = .0
  var mid:Double = 0
  var height: Double = 0

  override def run(): Unit = {
    for (i <- start until end) {
      mid = (i + 0.5) * width
      height = 4.0 / (1.0 + mid * mid)
      result += height
      print("\n iter:"+i+ " - "+ result+ " \nmid: "+ mid + " height: " + height)

    }
    println(Thread.currentThread.getName + " result =" + result + " start " + start + " end " + end)
  }

  def getResult: Double = result
}

// Creating object
object Hello {
  // Main method
  def main(args: Array[String]) {
    var area = .0
    var width = .0
    var sum = 0.0
    val num_steps = 10
    val num_threads = 2
    val num_values_in_thread = num_steps / num_threads
    var end = num_values_in_thread
    var start = 0
    width = 1.0 / num_steps.asInstanceOf[Double]

    for (i <- 0 until num_threads) {
      val p = new Pi(start, end, width)
      val t = new Thread(p)
      t.setName("thread " + i)
      t.start()
      t.join()
      start = start + end
      end = end + num_values_in_thread
      sum += p.getResult
    }
    area = width * sum
    println("Final result: " + area)
  }
}
