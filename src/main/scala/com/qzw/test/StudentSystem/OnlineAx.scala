package com.qzw.test.StudentSystem

import java.io.StringReader
import java.text.SimpleDateFormat

import com.opencsv.CSVReader
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object OnlineAx {


  //声明文件的字段标题
  val hourOnlineCount = {
    for (i <- 0 to 24) yield {
      i + "hourOnlineCount,"
    }
  }
  val userNetHourAnalysisFileTitle = "userId," + hourOnlineCount.mkString + "onlineYearMonth," + "semester"

  def transformDataList(line: String) = {
    val reader = new CSVReader(new StringReader(line));
    val rn = reader.readNext()
    //学生id
    val userId = rn(0)
    //每小时上网时长默认值
    val hourOnlineCounts = {
      val buf = mutable.Buffer[Int]()
      for (i <- 1 to 24) {
        buf.append(0)
      }
      buf
    }
    //时间格式化
    val sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm")
    //开始上网时间
    val beginTime = sdf.parse(rn(2))
    //结束上网时间
    val endTime = sdf.parse(rn(3))

    //统计每条上网记录的上网小时数
    if (beginTime.getDay == endTime.getDay) { //上网时间为同一天，没有跨天
      for (hour <- beginTime.getHours to endTime.getHours) {
        hourOnlineCounts(hour) += 1
      }
    } else { //上网时间不在同一天
      for (hour <- beginTime.getHours to 23) {
        hourOnlineCounts(hour) += 1
      }
      for (hour <- 0 to endTime.getHours) {
        hourOnlineCounts(hour) += 1
      }
    }

    //把开始时间转成字符串格式，赋予变量 在线的年月
    val onlineYearMonth = new SimpleDateFormat("yyyy/MM").format(beginTime) + "/01"


    //学生入学年份
    val year = userId.take(4).toInt
    //上网的年份
    val onlineYear = onlineYearMonth.take(4).toInt
    //上网的月份
    val onlineMonth = onlineYearMonth.slice(5, 7).toInt

    //通过年份和月份计算出学期
    var semester = (onlineYear - year) * 2
    if (onlineMonth > 6) {
      semester += 1
    }

    //返回键值对
    ((userId + "#" + onlineYearMonth + "#" + semester) -> hourOnlineCounts.toList)

  }

  //把处理后的数据分解为要写入文件的形式
  def transformUserNetHourList(s: (String, List[Int])) = {
    val userInfo = s._1.split("#")
    val userId = userInfo(0)
    val onlineYearMonth = userInfo(1)
    val semester = userInfo(2)

    userId + "," + s._2.mkString(",") + "," + onlineYearMonth + "," + semester + "," + s._2.indexOf(s._2.max) + "," + s._2.indexOf(s._2.min)
  }


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("app").setMaster("local[3]")
    val sc = new SparkContext(conf)

    val file = sc.textFile("D://file/washedNetDataFile.csv")
    val hourCounts = file.map(transformDataList).reduceByKey { (x, y) =>
      val buffer = mutable.Buffer[Int]()
      for (i <- 0 to 23) {
        buffer.append(x(i) + y(i))
      }
      buffer.toList
    }.map(transformUserNetHourList).collect()

    for (i <- hourCounts) {
      WriteFile.writeInfo("D://file/userNetHourAnalysisFile.csv", i + "\n")
    }
  }


}
