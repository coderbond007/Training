import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.col;

public class Main {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().master("local").config("spark.testing.memory", "2147480000").getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
        Dataset<Row> dataset = sparkSession.read().option("header", "true").option("delimiter", "\t").csv("/Users/pradyumn.ag/IdeaProjects/platform_training/src/main/resources/input.csv");

        task1(dataset);
        task2(dataset);
    }

    private static void task2(Dataset<Row> dataset) {
        Map<String, String> aggFunc = new HashMap<String, String>();
        aggFunc.put("Ad_Clicks", "sum");
    }

    public static void task1(Dataset<Row> dataset) {
        dataset = dataset.select("Day").where(col("Ad_Clicks").$greater$eq("5000"));
        dataset.show(20);
    }
}