package com.github.e8kor.app

import com.github.e8kor.domain.Source
import com.github.e8kor.model.{Airport, Country, Runaway}
import com.github.tototoshi.csv.CSVReader

import scala.io.{Source => SSource}

object Repository extends Source {

  import com.github.e8kor.infrastructure.Deserializer._

  private def fromResources(path: String): Stream[Map[String, String]] = {
    val lines = SSource.fromInputStream(this.getClass.getResourceAsStream(path))

    CSVReader.open(lines).toStreamWithHeaders
  }

  override lazy val countries: Iterable[Country] = fromResources("/countries.csv").flatMap(v => v.deserializeOpt[Country])

  override lazy val airports: Iterable[Airport] = fromResources("/airports.csv").flatMap(v => v.deserializeOpt[Airport])

  override lazy val runaways: Iterable[Runaway] = fromResources("/runaways.csv").flatMap(v => v.deserializeOpt[Runaway])

}
