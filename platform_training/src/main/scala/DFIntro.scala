import org.apache.spark.sql.{SparkSession, functions}

/**
  * Created by Anks on 30/07/19
  */
object DFIntro {
  
  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder().master("local").config("spark.testing.memory", "2147480000").getOrCreate()
    sparkSession.sparkContext.setLogLevel("ERROR")
    val df = sparkSession.read.option("header","true").option("delimiter","\t").csv("src/main/resources/input.csv")
  }
}
