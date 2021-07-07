package com.qzw.test.netAnalysis

import java.io.StringReader

import com.opencsv.CSVReader
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

object washData {

  //判断当前行的列是否有空值
  def delNullOrNone(s: String) = {
    val para = s.split(",").toList
    var state = true
    for (data <- para) {

      if (None == data) {
        state = false
      }
    }
    state
  }

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("app").setMaster("local[3]")
    val sc = new SparkContext(conf)

    //    val title = "stuId,sex,onTime,offTime\n"

    //从性别表中读数据
    val sexList = Source.fromFile("F://file/sexDictFile.csv").getLines().toList
    //声明一个存放学号和性别的map
    var sexMap = Map[String, String]().toBuffer
    for (i <- sexList) {
      val info = i.toString.split(",").toList
      sexMap += (info(0) -> info(1))
    }
    val imSexMap = sexMap.toMap

    val input = sc.textFile("F://file/netClean.csv").filter(delNullOrNone)
    val result = input.map { line =>
      val reader = new CSVReader(new StringReader(line));
      val rn = reader.readNext()

      rn(0) + "," + imSexMap(rn(0)) + "," + rn(1) + "," + rn(2)
    }

    val washedData = result.collect().toList

    //    writeFile(title)
    for (i <- washedData) {
      println(i)
      writeFile.writeInfo("F://file/washedNetDataFile.csv", i + "\n")
    }


  }

}
