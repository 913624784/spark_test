package com.qzw.test.Etl

/**
  * @author Qi zhiwei
  * @date 2019/8/1 18:15
  * @version 1.0
  */
case class Config(configPath:String = "", date:String = "", env:String = "test")
object Cmd {
  def getParser() = new scopt.OptionParser[Config]("commonEtl") {
    head("scopt", "3.x")
    opt[String]('c', "config") required() action { (x, c) => c.copy(configPath = x) } text("config path")
    opt[String]('d',"date") optional() action { (x, c) => c.copy(date = x)} text("date: yyyy_MM_dd_HH")
    opt[String]('e',"env") optional() action { (x, c) => c.copy(env = x)} text("env: test, online")
  }
}
