package com.github.e8kor.infrastructure.spec

import java.nio.charset.Charset

import com.github.e8kor.infrastructure.Deserializer._
import com.github.e8kor.model.{Airport, Country, Runaway}
import com.github.tototoshi.csv.CSVReader

import scala.io.{Codec, Source}

class DeserializeCSVSpec extends PropertySpec {

  implicit val codec = Codec(Charset.defaultCharset())

  property("deserialize airports", props) {
    val stream = Source.fromInputStream(this.getClass.getResourceAsStream("/airports.csv"))
    val reader = CSVReader.open(stream)
    val list = reader.toStreamWithHeaders.force.toList
    val deserialized = list.flatMap(v => v.deserializeOpt[Airport])

    list should have size (deserialized size)
  }

  property("deserialize countries", props) {
    val stream = Source.fromInputStream(this.getClass.getResourceAsStream("/countries.csv"))
    val reader = CSVReader.open(stream)
    val list = reader.toStreamWithHeaders.force.toList
    val deserialized = list.flatMap(v => v.deserializeOpt[Country])

    list should have size (deserialized size)
  }

  property("deserialize runaways", props) {
    val stream = Source.fromInputStream(this.getClass.getResourceAsStream("/runaways.csv"))
    val reader = CSVReader.open(stream)
    val list = reader.toStreamWithHeaders.force.toList
    val deserialized = list.flatMap(v => v.deserializeOpt[Runaway])

    list should have size (deserialized size)
  }
}
