package com.qzw.test.netAnalysis

import java.io.{File, FileWriter, PrintWriter}

object writeFile {

  //把清洗后的数据写入文件
  def writeInfo(fileName:String,str:String) = {
    val file = new File(fileName)
    val fw = new FileWriter(file,true)
    val pw = new PrintWriter(fw)
    pw.write(str)
    pw.close()
  }

}
