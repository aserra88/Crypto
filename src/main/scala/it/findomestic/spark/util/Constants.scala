package it.findomestic.spark.util

import grizzled.slf4j.Logger
import ibm.bigdata.util.configurations.logging._


object Constants {

  val idLog = "***** Crypto *****"

  val AES = "AES"
  //val SHA = "HmacSHA512"
  val SHA = "HmacSHA256"

  val separatore = "\u001F"

  val ENCRYPT = "Encrypt"
  val DECRYPT = "Decrypt"
  val PSEUDOANONIM = "PseudoAnonim"



  val ivBytesAES = Array[Byte](0x63.toByte, 0xd2.toByte, 0xa6.toByte, 0x45.toByte, 0x5c.toByte, 0x74.toByte, 0xa4.toByte, 0x75.toByte, 0x4f.toByte, 0xf8.toByte, 0x53.toByte, 0xc8.toByte, 0x73.toByte, 0x68.toByte, 0x2f.toByte, 0xa4.toByte)
  val ivBytesNull = Array[Byte](0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte, 0x00.toByte)



  object logging extends Serializable {
    @transient lazy val logger = Logger(getClass.getName)
  }

}
