package com.qzw.test.testParquet

import org.apache.spark.sql.SparkSession
/**
  * @author Qi zhiwei
  * @date 2019/7/15 19:21
  * @version 1.0
  */
object readParquet {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("parquet").master("local[2]").getOrCreate()
    val path="C:\\Users\\qizhiwei\\Desktop\\part-11626-2034c1b8-97c9-4ecd-934b-ee0b759cd483.c000.snappy.parquet"
    val df=spark.read.parquet(path).rdd.map(line => line.toString()).foreach(println)

  }
}
