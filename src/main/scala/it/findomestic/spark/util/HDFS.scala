package it.findomestic.spark.util

import org.apache.hadoop.conf.Configuration

object HDFS {
  val confHDFS = new Configuration()
  val dfs = org.apache.hadoop.fs.FileSystem.get(confHDFS)
}

