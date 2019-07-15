package com.qzw.test.myAccum

/**
  * @author Qi zhiwei
  * @date 2019/7/15 19:48
  * @version 1.0
  */
class Pen(){
  def write(str:String): Unit ={
    println(str+"================")
  }
}

object implicitContext{
  implicit val pen = new Pen
}

object userPen {

  def userForExam(name:String)(implicit pen:Pen) = {
    pen.write(name)
  }

  def userForExercise(name:String)(implicit pen:Pen) = {
    pen.write(name)
  }

  def main(args: Array[String]): Unit = {
    import implicitContext._

    userForExam("hahaha")
    userForExercise("heiheihei")
  }

}
