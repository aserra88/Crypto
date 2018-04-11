package it.findomestic.spark.util

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

object Spark extends Serializable {
  val conf = new SparkConf().setAppName("CaringBU")
  conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
  val sc = new SparkContext(conf)
  val hiveContext = new HiveContext(sc)
}
