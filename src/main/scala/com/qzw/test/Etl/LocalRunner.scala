package com.qzw.test.Etl

import java.io.FileReader
import java.util.Map

import org.apache.commons.io.IOUtils
import org.yaml.snakeyaml.Yaml

/**
 * @author Qi zhiwei
 * @date 2019/8/2 9:36
 * @version 1.0
 */
object LocalRunner {
  private def getComplexFieldType(other: String) = {
    var index, gap = 0
    var stop = false
    while (!stop) {
      if (other(index) == '<')
        gap = gap + 1
      if (other(index) == '>') {
        gap = gap - 1
        if (gap == 0)
          stop = true
      }
      index = index + 1
    }
    other.substring(0, index)
  }

  def main(args: Array[String]): Unit = {
    val ts = System.currentTimeMillis()
    val cmdParser = Cmd.getParser()
    cmdParser.parse(args, Config()) match {
      case Some(config) => {
        val (configMap, date) = ParseYaml.handleCmd(config)
        println(configMap)
        val reader = new FileReader("C:\\Users\\qizhiwei\\Desktop\\qisheng_pht_spark_test.yml")
        val configContent = IOUtils.toString(reader)
        val Proto = new Yaml().load(configContent).asInstanceOf[Map[String, Any]]
        println(Proto)

      }
      case None =>
    }
    println(getComplexFieldType("array<struct<key:string,value:string>>"))
  }
}
