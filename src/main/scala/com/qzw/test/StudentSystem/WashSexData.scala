package com.qzw.test.StudentSystem

import java.io.StringReader

import com.opencsv.CSVReader
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

object WashSexData { //写一个过滤器函数 判断是否有空值 传入每一行
  def isornull(s: String) = {
    val para = s.split(",").toList
    var state = true
    for (data <- para) {
      if (None == data) { //为空 设为false
        state = false
      }
    }
    state
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("app").setMaster("local[3]")
    val sc = new SparkContext(conf)

    //处理性别表
    val sexList = Source.fromFile("D://file//sexDictFile.csv").getLines().toList
    //声明一个存放学号和性别的map
    var sexmap = Map[String, String]().toBuffer
    for (i <- sexList) { //读取性别表每一行
      val info = i.toString.split(",").toList
      //key 0 学号 value 1 性别
      sexmap += (info(0) -> info(1))
    }
    val imSexMap = sexmap.toMap

    val input = sc.textFile("D://file//netClean.csv").filter(isornull) //设置过滤器 名字
    val result = input.map {
      line =>
        val reader = new CSVReader(new StringReader(line))
        val rn = reader.readNext()
        //给imsexmap传一个key 就返回一个值
        rn(0) + "," + imSexMap(rn(0)) + "," + rn(1) + "," + rn(2)
    }

    val washedData = result.collect().toList
    //声明清洗后数据表中的列
    val title = "stuId,sex,onTime,offTime\n"
    //WriteFile.writeInfo("D://file/washedNetDataFile.csv",title)
    for (i <- washedData) {
      println(i)
      WriteFile.writeInfo("D://file/washedNetDataFile.csv", i + "\n")
    }
  }
}
