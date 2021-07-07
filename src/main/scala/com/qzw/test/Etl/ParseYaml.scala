package com.qzw.test.Etl

import java.io.{FileReader, IOException, InputStreamReader}
import java.text.SimpleDateFormat
import java.util.{Comparator, Date, Map}

import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.yaml.snakeyaml.Yaml


/**
 * @author Qi zhiwei
 * @date 2019/8/2 9:32
 * @version 1.0
 */
object ParseYaml {
  private val sdf = new SimpleDateFormat("yyyy_MM_dd_HH")
  private val msdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm")

  /**
   * 解析cmd参数 并配置
   *
   * @param cmd
   * @return YAML MAP和DATE
   */
  def handleCmd(cmd: Config) = {
    val reader = new FileReader(cmd.configPath)
    val configContent = IOUtils.toString(reader)
    val configMap = new Yaml().load(configContent).asInstanceOf[Map[String, Any]]
    val date = if (StringUtils.isBlank(cmd.date))
      new Date()
    else {
      if (cmd.date.split("_").length == 4)
        sdf.parse(cmd.date)
      else
        msdf.parse(cmd.date)
    }

    (configMap, date)
  }
}
