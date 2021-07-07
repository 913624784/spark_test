package com.qzw.test.buySpecialTickets


class SpecialPerson(name: String)

class Student(val name: String)

class Older(val name: String)

/**
 * @author Qi zhiwei
 * @date 2019/7/15 19:45
 * @version 1.0
 */
object buySpecialTickets {
  implicit def transformToSpecialPerson(obj: Object): SpecialPerson = {
    if (obj.getClass == classOf[Student]) {
      val student = obj.asInstanceOf[Student]
      new SpecialPerson(student.name)
    } else if (obj.getClass == classOf[Older]) {
      val older = obj.asInstanceOf[Older]
      new SpecialPerson(older.name)
    } else {
      null
    }
  }

  def buyTickets(specialPerson: SpecialPerson) = {
    println("I'm a specialPerson......")
  }

  def main(args: Array[String]): Unit = {
    val student = new Student("hahaha")
    buyTickets(student)
  }
}
