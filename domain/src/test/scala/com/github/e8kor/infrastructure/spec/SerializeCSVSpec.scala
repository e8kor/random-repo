package com.github.e8kor.infrastructure.spec

import java.io.{StringReader, StringWriter}
import java.nio.charset.Charset

import com.github.e8kor.model.{Airport, Country, Runaway}
import com.github.tototoshi.csv.{CSVReader, CSVWriter}

import scala.io.{Codec, Source}

class SerializeCSVSpec extends PropertySpec {

  import com.github.e8kor.infrastructure.Deserializable._
  import com.github.e8kor.infrastructure.Serializable._

  implicit val codec = Codec(Charset.defaultCharset())

  property("airports serialization round", props) {
    val lines = Source.fromInputStream(this.getClass.getResourceAsStream("/airports.csv")).getLines().toSeq
    val reader = new StringReader(lines.mkString("\n"))
    val list = CSVReader.open(reader).toStreamWithHeaders
    val deserialized1 = list.flatMap(v => v.deserializeOpt[Airport])

    val (headers, body) = {
      val tmp = deserialized1.map(v => v.serialize)
      val v1 = tmp.headOption.map(v => v.keys.toSeq).toSeq
      val v2 = tmp.map(v => v.values.toSeq)
      v1 -> v2
    }

    val writer = new StringWriter()
    CSVWriter.open(writer).writeAll(headers ++ body)
    val deserialized2 = CSVReader
      .open(Source.fromString(writer.toString))
      .toStreamWithHeaders
      .force
      .toList
      .flatMap(v => v.deserializeOpt[Airport])

    deserialized1 should have size 13
    deserialized2 should have size 13
    deserialized1 should contain theSameElementsAs deserialized2

  }

  property("countries serialization round", props) {
    val lines = Source.fromInputStream(this.getClass.getResourceAsStream("/countries.csv")).getLines().toSeq
    val reader = new StringReader(lines.mkString("\n"))
    val list = CSVReader.open(reader).toStreamWithHeaders
    val deserialized1 = list.flatMap(v => v.deserializeOpt[Country])

    val (headers, body) = {
      val tmp = deserialized1.map(v => v.serialize)
      val v1 = tmp.headOption.map(v => v.keys.toSeq).toSeq
      val v2 = tmp.map(v => v.values.toSeq)
      v1 -> v2
    }

    val writer = new StringWriter()
    CSVWriter.open(writer).writeAll(headers ++ body)
    val deserialized2 = CSVReader
      .open(Source.fromString(writer.toString))
      .toStreamWithHeaders
      .force
      .toList
      .flatMap(v => v.deserializeOpt[Country])

    deserialized1 should have size 14
    deserialized2 should have size 14
    deserialized1 should contain theSameElementsAs deserialized2
  }

  property("runaways serialization round", props) {
    val lines = Source.fromInputStream(this.getClass.getResourceAsStream("/runaways.csv")).getLines().toSeq
    val reader = new StringReader(lines.mkString("\n"))
    val list = CSVReader.open(reader).toStreamWithHeaders
    val deserialized1 = list.flatMap(v => v.deserializeOpt[Runaway])

    val (headers, body) = {
      val tmp = deserialized1.map(v => v.serialize)
      val v1 = tmp.headOption.map(v => v.keys.toSeq).toSeq
      val v2 = tmp.map(v => v.values.toSeq)
      v1 -> v2
    }

    val writer = new StringWriter()
    CSVWriter.open(writer).writeAll(headers ++ body)
    val tmp = CSVReader
      .open(Source.fromString(writer.toString))
      .toStreamWithHeaders
      .force
      .toList

    val deserialized2 = tmp.flatMap(v => v.deserializeOpt[Runaway])

    deserialized1 should equal(deserialized2)

    deserialized1 should have size 9
    deserialized2 should have size 9
  }
}
