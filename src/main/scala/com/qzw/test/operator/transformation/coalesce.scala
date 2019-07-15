package com.qzw.test.operator.transformation

import org.apache.spark.{SparkConf, SparkContext}

object coalesce {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[3]").setAppName("app")
    val sc = new SparkContext(conf)

    val datas = List("hi", "hello", "how", "are", "you")
    val datasRDD = sc.parallelize(datas, 4)
    println("RDD的分区数: " + datasRDD.partitions.length)

    //用于将RDD进行重分区，使用HashPartitioner。
    // 且该RDD的分区个数等于numPartitions个数。
    // 如果shuffle设置为true，则会进行shuffle。
    val datasRDD2 = datasRDD.coalesce(8,false)
    println("RDD的分区数: " + datasRDD2.partitions.length)

  }
}
