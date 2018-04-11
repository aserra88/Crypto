package it.findomestic.spark.util

import java.security.{MessageDigest, SecureRandom}
import javax.crypto.{Cipher, Mac, SecretKeyFactory}
import javax.crypto.spec.{IvParameterSpec, PBEKeySpec, SecretKeySpec}

import org.apache.commons.codec.binary.Base64




class JavaCryptoEncryption(algorithm: String) {

  private def decodeBase64(string: String) = Base64.decodeBase64(string)


  private def cipher(b64key: String, mode:Int): Cipher = {

    val encipher = Cipher.getInstance(algorithm +"/CTR/NoPadding")


    val spec = new PBEKeySpec(b64key.toCharArray,Constants.ivBytesAES, 65536, 256)
    val f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
    val keyByte = f.generateSecret(spec).getEncoded

    val secretKey = new SecretKeySpec(keyByte,algorithm)//decodeBase64(b64key), algorithm)
    val ivSpec = new IvParameterSpec(Constants.ivBytesAES)

    encipher.init(mode,secretKey,ivSpec)
    encipher
  }

  private def cipherSHA(b64key: String): Mac ={

    val encipher =  Mac.getInstance(algorithm)
    encipher.init(new SecretKeySpec(decodeBase64(b64key),algorithm))
    encipher

  }


  def encrypt(bytes: Array[Byte], b64secret: String): Array[Byte] = {
    val encoder = cipher(b64secret,Cipher.ENCRYPT_MODE)
    encoder.doFinal(bytes)

  }

  def decrypt(bytes: Array[Byte], b64secret: String): Array[Byte] = {
    val decoder = cipher(b64secret, Cipher.DECRYPT_MODE)
    decoder.doFinal(bytes)
  }


  def pseudoAnonim(bytes: Array[Byte], b64secret: String): Array[Byte] = {
    val encoder = cipherSHA(b64secret)
    encoder.doFinal(bytes)
  }



}