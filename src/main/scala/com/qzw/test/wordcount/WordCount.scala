package com.qzw.test.wordcount

import org.apache.spark.{SparkConf, SparkContext}
import java.security.MessageDigest
import java.util

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[3]").setAppName("app")
    val sc = new SparkContext(conf)
    val lineRdd = sc.textFile("E://a.txt").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    lineRdd.foreach(println)
  }
}