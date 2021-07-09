import org.apache.spark.sql._

object Hello {
  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder().master("local").config("spark.testing.memory", "2147480000").getOrCreate()
    sparkSession.sparkContext.setLogLevel("ERROR")
    val rdd = sparkSession.sparkContext.parallelize(List("yippee", "ki", "yay"))
    print("Number of words " + rdd.count())
    Thread.sleep(600000)


  }
}