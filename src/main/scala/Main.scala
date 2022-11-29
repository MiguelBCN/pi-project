import java.util.Calendar
// Scala code for thread creation (Similar to Java)

/**
 * This class extends the class Runnable therefore when this function is called will create a new thread
 * To parallize the Riemman Algorithm to estimated PI we define that each thread can calculated
 * a certain amount of values and the add them together
 *
 * @param start Define the start postion of the chunk of values we´ll calculated
 * @param end   Define the end position of the chunk of values we´ll calculted
 * @param width value of width need it in each operation
 */
class Pi(var start: Int, var end: Int, var width: Double) extends Runnable {
  var result = .0
  var mid: Double = 0
  var height: Double = 0

  /**
   * This is the main function in each thread its job is to calculated PI
   * from a chunk of data given by start and end
   */
  override def run(): Unit = {
    //println("Thread " + Thread.currentThread().getName() + " is running.")
    for (i <- start until end) {
      mid = (i + 0.5) * width
      height = 4.0 / (1.0 + mid * mid)
      result += height

      //print("\n iter:"+i+ " - "+ result+ " \nmid: "+ mid + " height: " + height)

    }
    //println(Thread.currentThread.getName + " result =" + result + " start " + start + " end " + end)
  }

  /**
   * This function will return the value calculated in this thread
   *
   * @return Sum of all values
   */
  def getResult: Double = result
}


/**
 * This object is the core of the application, particularly the method main is the one
 * which we´ll be process when executed
 */
object Main {
  // Main method
  def main(args: Array[String]): Unit = {

    // Parallel method
    var pi_value = .0
    var width = .0
    var sum = 0.0
    val num_steps = 450000000
    val num_threads = 10

    val num_values_in_thread = num_steps / num_threads
    var end = num_values_in_thread
    var start = 0
    width = 1.0 / num_steps.asInstanceOf[Double]
    val t_ini = System.nanoTime()

    for (i <- 0 until num_threads) {
      val p = new Pi(start, end, width)
      val t = new Thread(p)
      t.setName("thread " + i)
      t.start()
      t.join() // Wait for thread to die

      start = start + num_values_in_thread
      end = end + num_values_in_thread
      sum += p.getResult // we acuumulated the results of each thread
    }
    // PI calculation
    pi_value = width * sum
    val duration = (System.nanoTime - t_ini) / 1e9d
    println("Result Parallel: " + pi_value+ " Duration: "+  duration + "ns" + " Num Threads: " + num_threads + " Num step:" + num_steps)


    // SECUENCIAL METHOD
    var mid2,height2,result2,sum2 : Double = 0
    val t_ini2 = System.nanoTime()
    for (i <- 0 until num_steps){
      mid2 = (i + 0.5) * width;
      height2 = 4.0 / (1.0 + mid2 * mid2);
      sum2 += height2;
    }
    result2 = width * sum2;
    val duration2 = (System.nanoTime - t_ini2) / 1e9d
    println("Result Secuencial: " + result2+ " Duration: "+  duration2 + "ns" + " Num step:" + num_steps)

    println("Solo se ve una mejora en num_steps mayores a 450000000")

  }
}
