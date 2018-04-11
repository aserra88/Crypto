package it.findomestic.spark

import java.nio.charset.StandardCharsets

import org.apache.commons.codec.binary.Base64
import javax.crypto.KeyGenerator

import grizzled.slf4j.Logger
import it.findomestic.spark.util.HDFS
import it.findomestic.spark.util.Constants._
import it.findomestic.spark.util.{Constants, JavaCryptoEncryption}
import org.apache.hadoop.fs.Path

import scala.xml.XML



object Main {

  object logging extends Serializable {
    @transient lazy val log = Logger(getClass.getName)
  }

  def main(args: Array[String]) {



    try {
      val chiave = args(0)


      val xmlFile = XML.loadFile("/home/wce/clsadmin/test/Spark/Crypto/prova.xml")


      val pathIn = (xmlFile \ "Input" \ "pathInput").text
      val pathOut = (xmlFile \ "Output" \"pathOutput").text

      val campiPseudo = (xmlFile \ "Fields" \ "Number").toArray.map(p => p.text.toInt)

      println("campiPseudo:")
      campiPseudo.foreach(println(_))
      println(pathOut)

      logging.log.info("inizio processo")
      val rdd = util.Spark.sc.textFile(pathIn).map(p => p.split(Constants.separatore))
      //val campiPseudo = Array(1,3)

      val rddAnon = rdd.map(p => {
        val a = Array.fill[String](p.length)("")

        for(i <- p.indices) {

         if(campiPseudo.contains(i))
           a(i) = Crypto.pseudoEncrypt(chiave, p(i), Constants.SHA)
         else
           a(i) = p(i)

        }
        a
      })
      logging.log.info("fine processo")

      if(HDFS.dfs.exists(new Path(pathOut)))
        HDFS.dfs.delete(new Path(pathOut),true)

      rddAnon.map(_.mkString(Constants.separatore)).saveAsTextFile(pathOut)



    }catch{
      case ex: Exception => logging.log.error(idLog + s"Errore nel metodo main", ex)
        logging.log.error(ex)
        throw ex
    }


  }
}