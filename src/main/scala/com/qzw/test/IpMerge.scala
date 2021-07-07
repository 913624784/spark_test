package com.qzw.test

import java.io.{FileInputStream, InputStreamReader}
import java.util
import java.util.{HashMap, LinkedHashMap, List, Map}

import com.google.common.base.Strings
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.validator.routines.InetAddressValidator
import scala.reflect.io.Path

/**
 * @author Qi zhiwei
 * @date 2019/7/18 17:14
 * @version 1.0
 */
object IpMerge {
  def isValidValue(value: Any) = {
    if (value == null)
      false
    else
      true
  }

  def isValidString(value: Any) = {
    if (value == null || Strings.isNullOrEmpty(value.toString))
      false
    else
      true
  }

  def merge(user_ip: String, xip: String, real_user_ip: String): Unit = {
    if (!isValidValue(user_ip))
      println("user ip ")
    else {
      val ipAddress = user_ip
      val convertedUserIp = extractIp(ipAddress, xip);

      val realUserIp = real_user_ip
      val finalUserIp =
        if (isValidString(realUserIp)) {
          val realUserIpAddressOnBase64 = realUserIp.toString
          if (StringUtils.isNotEmpty(realUserIpAddressOnBase64)) {
            val realUserIpAddress = new String(Base64.decodeBase64(realUserIpAddressOnBase64))
            //            if (InetAddressValidator.getInstance.isValid(realUserIpAddress))
            //              realUserIpAddress
            //            else
            //              print("***")
          }
        } else
          convertedUserIp
      println(finalUserIp)
    }
  }

  private def extractIp(ip: String, xip: String): String = {
    if (ip.startsWith("10.")) {
      if (StringUtils.isEmpty(xip) || xip.contains("-") || xip.contains("unknown"))
        ip
      else {
        if (xip.contains(","))
          xip.split(",")(0)
        else
          xip
      }
    } else
      ip
  }

  def parseFullUrl(fullUrl: String, fieldMap: util.LinkedHashMap[String, Any]) = {
    val paramStartIndex = fullUrl.indexOf('?')
    if (paramStartIndex > 0 && paramStartIndex != fullUrl.length - 1) {
      val queryString = fullUrl.substring(paramStartIndex + 1)
      queryString.split("&").foreach(
        kvPair => {
          val splitIndex = kvPair.indexOf("=")
          //只有参数名，没有=，也没有值
          if (splitIndex == -1)
            fieldMap.put(kvPair, "")
          //有参数名，有=，也没有值
          else if (splitIndex == kvPair.length - 1)
            fieldMap.put(kvPair.substring(0, splitIndex), "")
          else
          //有参数名，有=，有值
            fieldMap.put(kvPair.substring(0, splitIndex), kvPair.substring(splitIndex + 1))
        }
      )
    }
    println(fieldMap.toString)
  }

  var baseUrl: String = null
  var urlParams: util.LinkedHashMap[String, Any] = null

  /**
   * 解析url?k1=v1&k2=v2
   */
  private def parseFullUrl1(fullUrl: String): Unit = {
    val paramStartIndex = fullUrl.indexOf('?')
    if (paramStartIndex < 0) { // 没有GET参数
      baseUrl = fullUrl
      urlParams = new util.LinkedHashMap[String, Any]
    }
    else if (paramStartIndex == fullUrl.length - 1) { // 末尾有?，但是没有参数
      baseUrl = fullUrl.substring(0, paramStartIndex)
      urlParams = new util.LinkedHashMap[String, Any]
    }
    else {
      baseUrl = fullUrl.substring(0, paramStartIndex)
      urlParams = parseUrlParam(fullUrl.substring(paramStartIndex + 1))
    }
    println("baseUrl:" + baseUrl)
    println("urlParams:" + urlParams)
  }

  /**
   * 解析k1=v1&k2=v2
   */
  private def parseUrlParam(queryString: String) = {
    val res = new util.LinkedHashMap[String, Any]
    val kvPairs = queryString.split("&")
    for (kvPair <- kvPairs) {
      val splitIndex = kvPair.indexOf('=')
      if (splitIndex == -1) { // 只有参数名，没有=，也没有值
        res.put(kvPair, "")
      }
      else if (splitIndex == kvPair.length - 1) { // 有参数名和=，没有参数值
        res.put(kvPair.substring(0, splitIndex), "")
      }
      else res.put(kvPair.substring(0, splitIndex), kvPair.substring(splitIndex + 1))
    }
    res
  }

  def main(args: Array[String]): Unit = {
    val realUserIp = "dW5rbm93bg=="
    val realUserIpAddress = "113.140.31.114"
    val url = "113.140.31.114 - - [15/Jul/2019:04:57:17 +0800] \"GET /cp2.gif?p=i&fi=&t=s&di=&ri=11%3An1%3A1000000012516%3A1000000000674%3B&x=&oi=11%3An1%3A1000000012516%3A1000000000674%3B&rid=5e821681272564cb2f29148d9452bb15&vv=10.6.6&ps=0&vft=0&nw=1&uvpt=0&ol=0&mk=8e48946f144759d86a50075555fd5862&mvpt=0&a=055C3D2B-ABF1-4577-A663-09BE5C1DACF4&b=239388601&c=6&d=4089&e=ba72f3375f17b8e3aef390d8bbc82834&vfst=0&g=0&ept=0&h=0&i=055C3D2B-ABF1-4577-A663-09BE5C1DACF4&l=dW5rbm93bg==&m=02:00:00:00:00:00&o=da1e737504a1cac3f5868233254005eb1f4fae4c&s=1563137837162&u=055C3D2B-ABF1-4577-A663-09BE5C1DACF4&av=5.47.006&v=3334840000&y=qc_100001_100070&tpt=0 HTTP/1.1\" 200 25 \"-\" \"Cupid/5.47.006;NetType/wifi;QTP/1.10.12.6\" \"unknown\""
    val nourl = "183.150.14.184 - - [15/Jul/2019:04:00:01 +0800] \"GET /cp2.gif?p=s&rd=70&rc=-1&t=s&e=d1590dcbb2f25375f645b45716bb0350&y=qc_100001_100012&u=9e55e01672ddd1426e85c5e168969f8e&a=312cae69ae33e4b53beff11040ef9a21&av=mars-7.0.15&vv=5.0.5&g=1&d=1402&rid=b15a4e423417e400b69bc624227d22f7&s=1563134401002&b=202783001&c=4&h=0&l=MTgzLjE1MC4xNC4xODQ%3D&qxlc=86330802&ps=0&tpt=1&uvpt=1&mvpt=0&v=385110700&ept=0 HTTP/2.0\" 200 0 \"https://www.iqiyi.com/v_19rrok15fk.html?vfm=2008_aldbd\" \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36\" \"-\" \"\" 0.000"
    val pingbackR = """(.*) - (.*) \[(.*)\] \"(.*) (.*) (.*)\" ([0-9]+) ([0-9]+) \"(.*)\" \"(.*)\" \"(.*)\" \"(.*)\" ([0-9]+.[0-9]+).*""".r
    val pcPingbackR = """(.*) - (.*) \[(.*)\] \"(.*) (.*) (.*)\" ([0-9]+) ([0-9]+) \"([^\t\r\n\f\"]*)\" \"([^\t\r\n\f\"]*)\" \"(.*)\".*""".r
    val lpFormR = """(.*) - \[(.*)\] \"(.*) (.*) (.*)\" \"(.*)\" ([0-9]+) ([0-9]+) \"(.*)\" \"(.*)\".*""".r
    val lpPingbackR = """(.*) - \[(.*)\] \"(.*) (.*) (.*)\" ([0-9]+) ([0-9]+) \"(.*)\" \"(.*)\".*""".r
    val file = "D:\\log.txt"
    val ipR = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}".r
    val timeR = "\\[(.*)\\]".r
    val re_timeR = "\\d{1,3}.\\d{1,3}".r //\"(.*)\" \"(.*)\" \d{1,3}\.\d{1,3}
    val ppR = """(.*) - (.*) \[(.*)\] \"(GET|POST|HEAD) (.*) (.*)\" (\d+) (\d+) \"([^\t\r\n\f\"]*)\" \"([^\t\r\n\f\"]*)\" \"(.*)\".*""".r
    //    println(ppR.findFirstMatchIn(url))
    //    println(re_timeR.findFirstMatchIn(url))
    val line = IOUtils.readLines(new FileInputStream(file), "UTF-8")
    //    val linkedHashMap=new util.LinkedHashMap[String,Any]()
    //    parseFullUrl(url,urlParams)
    //    parseFullUrl(nourl,urlParams)
    //    println(urlParams)
    //      parseFullUrl(url)
    //    parseFullUrl(nourl)
    val fieldMap = new util.LinkedHashMap[String, Any]()
    for (i <- 0 to line.size() - 1) {
      ppR.findFirstMatchIn(line.get(i)) match {
        case Some(_) => println(i + 1 + ":Log OK")
        case None => println(i + 1 + ":Log match fail")
      }
      //      parseUrlParam(fieldMap.get(""))
      parseFullUrl(line.get(i), fieldMap)
    }
    //    println(InetAddressValidator.getInstance.isValid("unknow"))
    //    val calSecurityCode = DigestUtils.md5Hex(""+"41402")
    //    println(calSecurityCode)
    //    println(realUserIp.substring(10))
    //    println(realUserIp.indexOf("="))


    //    println(s.toString)
    //    if (InetAddressValidator.getInstance.isValid(realUserIp))
    //      println(realUserIp)
    //    else
    //      println(null)
    //    merge(realUserIpAddress,null,realUserIp)

    //    println(InetAddressValidator.getInstance.isValid(realUserIp))
    //    println(extractIp("null",realUserIpAddress))


  }

}
