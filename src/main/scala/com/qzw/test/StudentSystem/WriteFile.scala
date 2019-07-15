package com.qzw.test.StudentSystem

import java.io.{File, FileWriter, PrintWriter}

object WriteFile {
  def writeInfo(filename:String,str:String){
        val file=new File(filename)
        val fw=new FileWriter(file,true)
        val pw=new PrintWriter(fw)
        pw.write(str)
        pw.close()
  }
}
