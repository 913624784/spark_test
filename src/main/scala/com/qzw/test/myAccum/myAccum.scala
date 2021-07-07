package com.qzw.test.myAccum


import org.apache.spark.util.AccumulatorV2
import java.util.{HashSet, Set}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * @author Qi zhiwei
 * @date 2019/7/15 19:46
 * @version 1.0
 */
class myAccum extends AccumulatorV2[String, Set[String]] {
  val result: Set[String] = new HashSet[String]()

  override def isZero: Boolean = {
    result.isEmpty
  }

  override def copy(): AccumulatorV2[String, Set[String]] = {
    val myaccum = new myAccum()
    myaccum
  }

  override def reset(): Unit = {
    result.clear()
  }

  override def add(v: String): Unit = {
    result.add(v)
  }

  override def merge(other: AccumulatorV2[String, Set[String]]): Unit = {
    result.addAll(other.value)
  }

  override def value: Set[String] = {
    this.result
  }
}

object myAccum {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[3]").setAppName("app")
    val sc = new SparkContext(conf)

    val myaccum = new myAccum

    sc.register(myaccum, "myaccum")

    val rdd = sc.parallelize(List("1", "2", "3", "4", "5")).map { line =>
      val num = line.toInt
      myaccum.add(line)
      num
    }.reduce(_ + _)
    println(rdd)
    println(myaccum.value)
  }
}