package com.qzw.test.match_filter

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor


import scala.beans.BeanProperty


class pro {
  @BeanProperty var database = ""
  @BeanProperty var tablename = ""
  @BeanProperty var dt = ""
  @BeanProperty var hour = ""
  @BeanProperty var quarter = ""
  @BeanProperty var matchfile = ""
  @BeanProperty var notfields = ""

  override def toString: String = s"/hive/warehouse/$database.db/$tablename/dt=$dt/hour=$hour/quarter=$quarter/$matchfile"
}

object filter {
  def main(args: Array[String]): Unit = {
    val oldfilename = Thread.currentThread().getContextClassLoader.getResourceAsStream("old_table.yml")
    val newfilename = Thread.currentThread().getContextClassLoader.getResourceAsStream("new_table.yml")
    val yaml = new Yaml(new Constructor(classOf[pro]))
    val oldurl = yaml.load(oldfilename).asInstanceOf[pro]
    val newurl = yaml.load(newfilename).asInstanceOf[pro]
    println("oldurl=" + oldurl)
    println("newurl=" + newurl)

    //    val conf = new SparkConf().setMaster("local[3]").setAppName("app")
    //    val sc = new SparkContext(conf)
    //    val rdd = sc.textFile("E://a.txt").map(line => getString(line).hashCode)
    //    val newRdd = sc.textFile("E://b.txt").map(line => getString(line).hashCode)
    //    val list = rdd.subtract(newRdd).take(5)
    //    val rddnew = sc.textFile("E://a.txt").map(line => (line, getString(line).hashCode))
    //    rddnew.filter(t => t._2.equals(list(0))).take(5).foreach(println)

//    val logFilePath = this.getClass.getResource("/old_pingback.log")
//    val reader = new BufferedReader(new FileReader(new File(logFilePath.getFile())))

    println(getString("anknsj iknsaon snaknis san s","san,iknsaon"))
  }


  def getString(line: String, notfields: String): String = {
    var linestr = ""
    var s=notfields.replace(",","|")
    linestr=line.replaceAll("("+s+")","")
    linestr
  }
}

