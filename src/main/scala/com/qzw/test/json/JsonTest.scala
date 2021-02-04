package com.qzw.test.json

import org.apache.spark.sql.SparkSession

/**
 * @author : qizhiwei 
 * @date : 2021/2/4
 * @Description : ${Description}
 */
object JsonTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("Parquet")
      .getOrCreate()
    val df = spark.read.json("src/main/resources/people.json")
    //第一个参数为显示行数 第二个为是否完整显示列数据 ，默认 true 不完整显示列数据
    df.show(10, false)
    //    +----+-------+
    //    |age |name   |
    //    +----+-------+
    //    |null|Michael|
    //    |30  |Andy   |
    //    |19  |Justin |
    //    +----+-------+
    spark.stop()
  }
}
