package com.qzw.test.wordcount

import org.apache.spark.{SparkConf, SparkContext}
import java.security.MessageDigest
import java.util
import com.google.common.base.Strings
import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang3.StringUtils

object WordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[3]").setAppName("app")
    val sc = new SparkContext(conf)
//    val lineRdd = sc.textFile("E://a.txt").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
//    lineRdd.foreach(println)
    val a=sc.textFile("E://a.txt")//12345
    val b=sc.textFile("E://b.txt")//34567
//    println(a.foreach(print))
    a.subtract(b).foreach(println)
//    println(b.foreach(print))
//    b.subtract(a).foreach(println)
//    val decode = new String(Base64.decodeBase64("MTgyLjg0LjIwNy4xMzM="))
//    println(decode)
    val realUserIp="MTgyLjg0LjIwNy4xMzM="

  }
}