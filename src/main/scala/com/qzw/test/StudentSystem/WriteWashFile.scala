package com.qzw.test.StudentSystem

import java.io.StringReader

import com.opencsv.CSVReader
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

object WriteWashFile {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("app").setMaster("local[3]")
    val sc = new SparkContext(conf)
    val washList = Source.fromFile("D://file/washedNetDataFile.csv").getLines().toList

    val input = sc.textFile("D://file/washedNetDataFile.csv")
    val list = List[String]().toBuffer
    for (i <- 1 to 24) {
      list.append(i + "")
    }
    val result = input.map {
      line =>
        val reader = new CSVReader(new StringReader(line))
        val rn = reader.readNext()
        rn(0) + "," + rn(1) + "," + rn(2) + "," + rn(4) + "," + list
    }

    val washedData = result.collect().toList
    for (i <- washedData) {
      println(i)
      WriteFile.writeInfo("D://file/washedNetDataFile.csv", i + "\n")
    }


  }
}
