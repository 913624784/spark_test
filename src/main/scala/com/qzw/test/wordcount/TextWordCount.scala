package com.qzw.test.wordcount

import org.apache.spark.sql.SparkSession

/**
 * @author : qizhiwei
 * @date : 2021/2/4
 * @Description : ${Description}
 */
object TextWordCount {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("WordCount")
      .config("master", "local")
      .getOrCreate()
    val rdd = spark.sparkContext.textFile("src/main/resources/people.csv")
    rdd.flatMap(_.split(";")).map((_, 1)).reduceByKey(_ + _).foreach(println)
    //    (job,1)
    //    (Jorge,1)
    //    (name,1)
    //    (32,1)
    //    (age,1)
    //    (30,1)
    //    (Developer,2)
    //    (Bob,1)
    spark.stop()
  }
}