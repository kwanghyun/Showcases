package functions

/**
 * @author kwjang
 */
object test {
  def main(args: Array[String]): Unit = {
    var startFrom = "";
    if (args.size > 1) {
      startFrom = args(1)
      println(startFrom);
    }
  }
}