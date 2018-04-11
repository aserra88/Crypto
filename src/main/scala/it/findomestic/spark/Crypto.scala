package it.findomestic.spark

import java.nio.charset.StandardCharsets
import javax.crypto.KeyGenerator

import grizzled.slf4j.Logger
import it.findomestic.spark.util.Constants._
import it.findomestic.spark.util.{Constants, JavaCryptoEncryption}
import org.apache.commons.codec.binary.{Base64, Hex}




object Crypto {

  object logging extends Serializable {
    @transient lazy val log = Logger(getClass.getName)
  }

  object AES extends JavaCryptoEncryption(Constants.AES)
  object SHA extends JavaCryptoEncryption(Constants.SHA)


  def encodeBase64(bytes: Array[Byte]) = Base64.encodeBase64String(bytes)

  def getKey64(keyString:String) : String = encodeBase64(keyString.getBytes("UTF-8"))

  def generateKey(algorithm: String, size: Int): Array[Byte] = {
    val generator = KeyGenerator.getInstance(algorithm)
    generator.init(size)
    generator.generateKey().getEncoded
  }


  def encrypt(keyString: String, word: String, cryptoType: String): String ={
    try {
      val key = getKey64(keyString)

      var wordCrypted = Array[Byte]()
      if (cryptoType == Constants.AES)
        wordCrypted = AES.encrypt(word.getBytes("UTF-8"), key)
      else
        throw new Exception(s"Tipo sconosciuto per la cryptazione: [$cryptoType]")

      encodeBase64(wordCrypted)

    }catch{
      case ex: Exception => logging.log.error(idLog + s"Errore nel metodo encrypt", ex)
        logging.log.error(ex)
        throw ex
    }
  }


  def decrypt(keyString: String, word: String, cryptoType: String): String ={
    try {
      val key = getKey64(keyString)
      var wordDecrypted = Array[Byte]()
      if (cryptoType == Constants.AES)
        wordDecrypted = AES.decrypt(Base64.decodeBase64(word), key)
      else
        throw new Exception(s"Tipo sconosciuto per la decryptazione: [$cryptoType]")

      new String(wordDecrypted, "UTF-8")

    }catch{
      case ex: Exception => logging.log.error(idLog + s"Errore nel metodo decrypt", ex)
        logging.log.error(ex)
        throw ex
    }
  }

  def pseudoEncrypt(keyString: String, word: String, cryptoType: String): String ={
    try {
      val key = getKey64(keyString)
      var wordPseudo = Array[Byte]()

      if (cryptoType == Constants.SHA)
        wordPseudo = SHA.pseudoAnonim(word.getBytes(StandardCharsets.ISO_8859_1),key)//"UTF-8"), key)

      else
        throw new Exception(s"Tipo sconosciuto per la pseudoanonimizzazione: [$cryptoType]")

      //Base64.encodeBase64String(wordPseudo)

      Hex.encodeHex(wordPseudo).mkString("")

    }catch{
      case ex: Exception => logging.log.error(idLog + s"Errore nel metodo pseudoEncrypt", ex)
        logging.log.error(ex)
        throw ex
    }


  }

  def string2hex(str: String): String = {
    str.toList.map(_.toInt.toHexString).mkString
  }




}
