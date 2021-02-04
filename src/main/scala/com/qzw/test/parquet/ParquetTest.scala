package com.qzw.test.parquet

import org.apache.spark.sql.SparkSession

/**
 * @author : qizhiwei 
 * @date : 2021/2/4
 * @Description : ${Description}
 */
object ParquetTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("ParquetTest")
      .getOrCreate()
    val df = spark.read.parquet("src/main/resources/users.parquet")
    //false 代表输出完整的列数据，默认为 true 截断显示
    df.show(false)
    //    +------+--------------+----------------+
    //    |name  |favorite_color|favorite_numbers|
    //    +------+--------------+----------------+
    //    |Alyssa|null          |[3, 9, 15, 20]  |
    //    |Ben   |red           |[]              |
    //    +------+--------------+----------------+
    spark.stop()

  }
}
