package com.qzw.test.testParquet

import java.util
import java.util.Date

import org.apache.commons.lang3.text.StrSubstitutor
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.spark.sql.SparkSession

/**
  * @author Qi zhiwei
  * @date 2019/7/15 19:21
  * @version 1.0
  */
object readParquet {
  def getValue1(line: String) = {
    val strs = new StringBuffer()
    val fields = line.split("\t")
    for (i <- 0 to fields.length - 1) {
      if (i != 11 && i != 12)
        strs.append(fields(i) + ",")
    }
    strs.substring(0, strs.length() - 1)
  }

  def getValue(line: String) = {
    val strs = new StringBuffer()
    val fields = line.split("\t")
    for (i <- 0 to fields.length - 1) {
      if (i == 1 || i == 2 || i == 3 || i == 5 || i == 25 || i == 29)
        strs.append(fields(i) + ",")
    }
    strs.append(fields(7) + ",").append(fields(42) + ",").append(fields(6) + ",").append(fields(0) + ",").append(fields(28)).toString
  }

  def testStrSubstitutor(): Unit ={
    val map =new util.HashMap[String,String]()
    map.put("name","${x}")
    map.put("x","y")
    val str=new StrSubstitutor(map)
    val hello2 = "${name}"
    println(str.replace(hello2))
  }

  def testStrSubstitutor1(): Unit ={
    val valuesMap = new util.HashMap[String,String]()
    valuesMap.put("animal", "quick brown fox")
    valuesMap.put("target", "lazy dog")
    val templateString = "The ${animal} jumped over the ${target}."
    val sub = new StrSubstitutor(valuesMap)
    val resolvedString = sub.replace(templateString)
    println(resolvedString)
  }

  def readparquet(testPath:String,onlinePath:String): Unit ={
    val spark = SparkSession.builder().appName("parquet").master("local[2]").getOrCreate()
    val df=spark.read.parquet(testPath)
    val dff=spark.read.parquet(onlinePath)
    val showtest=df.rdd.map(line =>line.toString()).foreach(println)
    val showonline=dff.rdd.map(line =>line.toString()).foreach(println)
    println(df.rdd.map(_.toString).subtract(dff.rdd.map(_.toString)).count)
    println(df.rdd.subtract(dff.rdd).count())
    println(df.count)
    println(dff.count)
  }

  def strSubstitutor(date:Date,prefix: String)={
    val res: util.Map[String, String] = new util.HashMap[String, String]
    res.put(prefix + "year", DateFormatUtils.format(date, "yyyy"))
    res.put(prefix + "month", DateFormatUtils.format(date, "MM"))
    res.put(prefix + "day", DateFormatUtils.format(date, "dd"))
    res.put(prefix + "hour", DateFormatUtils.format(date, "HH"))
    res.put(prefix + "dt", DateFormatUtils.format(date, "yyyy-MM-dd"))
    new StrSubstitutor(res, "{", "}")
  }

  def main(args: Array[String]): Unit = {
    val test = "service\ttype\tcode\tcupid_user_id\tplatform_id\trequest_id\tuser_ip\tepisode_id\tuaa_user_id\tvideo_event_id\tis_vip\tad_type_id\torder_item_id"
//    println(getValue1(test))
    val online = "video_event_id\tservice\ttype\tcode\terror_message\tcupid_user_id\tuaa_user_id\tuser_ip\tcountry_id\tprovince_id\tcity_id\tisp_id\tisp_province_id\tisp_city_id\tchannel_id\tnetwork_type\tad_info\tad_player_id\tuser_agent\trequest_duration\trequest_count\tcustom_info\tlog_time\tapp_version\tsdk_version\tplatform_id\twebsite_id\tis_offline\tis_vip\trequest_id\tdevice_idfa\tdevice_open_udid\tdevice_imei\tdevice_android_id\tdevice_android_advertising_id\tdevice_mac_address\tis_vip_movie\tplay_source\ttraffic_partition_type\tuser_vip_type\tvideo_vip_type\talbum_id\tepisode_id\tmobile_key\tchannel_filter_tags\tfrom_type\tfrom_sub_type\tepisode_type"
//    println(getValue(online))
    val testPath="C:\\Users\\qizhiwei\\Desktop\\part-11571-2034c1b8-97c9-4ecd-934b-ee0b759cd483.c000.snappy.parquet"
    val onlinePath="C:\\Users\\qizhiwei\\Desktop\\part-11571-2034c1b8-97c9-4ecd-934b-ee0b759cd483.c000.snappy (2).parquet"
//    readparquet(testPath,onlinePath)
//    testStrSubstitutor()
//    testStrSubstitutor1()
    val str="/data/{year}{month}{day}{hour}/*.log.gz,/data/{year}/{month}/{day}/{hour}/*.gz"
    val s = strSubstitutor(new Date(),"")
    println(s.replace(str,""))



  }
}
