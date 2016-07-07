package com.github.e8kor.infrastructure.experimental

import com.github.e8kor.model._
import simulacrum._

trait Ser[A, B] {

  def serialize(entity: A): B

}

@typeclass trait SerToMap[T] extends Ser[T, Map[String, String]] {

  @op("""->!""") def serialize(entity: T): Map[String, String]

}

object SerToMap {

  import SerToMap.ops._

  implicit object HeadingLocationSer extends SerToMap[HeadingLocation] {

    override def serialize(entity: HeadingLocation): Map[String, String] = {
      Map(
        "he_la->!titude_deg" -> entity.latitude.toString,
        "he_longitude_deg" -> entity.longitude.toString,
        "he_ident" -> entity.ident.getOrElse(""),
        "he_elevation_ft" -> entity.elevation.map(_.toString).getOrElse(""),
        "he_heading_degT" -> entity.heading.map(_.toString).getOrElse(""),
        "he_displaced_threshold_ft" -> entity.displacedThreshold.map(_.toString).getOrElse("")
      )
    }

  }

  implicit def optionSer[T: SerToMap]: SerToMap[Option[T]] = {
    new SerToMap[Option[T]] {

      override def serialize(entity: Option[T]): Map[String, String] = {
        val companion = implicitly[SerToMap[T]]
        entity.map(companion.serialize).getOrElse(Map.empty[String, String])
      }

    }
  }

  implicit object LeavingLocationSer extends SerToMap[LeavingLocation] {

    override def serialize(entity: LeavingLocation): Map[String, String] = {
      Map(
        "le_latitude_deg" -> entity.latitude.toString,
        "le_longitude_deg" -> entity.longitude.toString,
        "le_ident" -> entity.ident.getOrElse(""),
        "le_elevation_ft" -> entity.elevation.map(_.toString).getOrElse(""),
        "le_heading_degT" -> entity.heading.map(_.toString).getOrElse(""),
        "le_displaced_threshold_ft" -> entity.displacedThreshold.map(_.toString).getOrElse("")
      )
    }

  }

  implicit object LocationSer extends SerToMap[Location] {

    override def serialize(entity: Location): Map[String, String] = {
      Map(
        "latitude_deg" -> entity.latitude.toString,
        "longitude_deg" -> entity.longitude.toString,
        "indent" -> entity.ident.getOrElse(""),
        "elevation_ft" -> entity.elevation.map(_.toString).getOrElse("")
      )
    }

  }

  implicit object AirportSer extends SerToMap[Airport] {

    override def serialize(entity: Airport): Map[String, String] = {
      Map(
        "id" -> entity.id.toString,
        "type" -> entity.`type`.code,
        "name" -> entity.name,
        "continent" -> entity.isoContinentCode,
        "iso_country" -> entity.isoCountryCode,
        "iso_region" -> entity.isoRegionCode,
        "scheduled_service" -> entity.scheduledService.code,
        "municipality" -> entity.municipality.getOrElse(""),
        "gps_code" -> entity.gpsCode.getOrElse(""),
        "iata_code" -> entity.iataCode.getOrElse(""),
        "local_code" -> entity.localCode.getOrElse(""),
        "home_link" -> entity.homeLink.map(_.toExternalForm).getOrElse(""),
        "wikipedia_link" -> entity.wikipediaLink.map(_.toExternalForm).getOrElse(""),
        "keywords" -> entity.keywords.mkString(",")
      ) ++ (entity.location ->!)
    }

  }

  implicit object RunawaySer extends SerToMap[Runaway] {

    override def serialize(entity: Runaway): Map[String, String] = {

      (entity.headingLocation ->!) ++ (entity.leavingLocation ->!) ++ Map(
        "airport_ref" -> entity.airportRef.toString,
        "airport_ident" -> entity.iataCode,
        "lighted" -> entity.lighted.code.toString,
        "closed" -> entity.closed.code.toString,
        "length_ft" -> entity.length.map(_.toString).getOrElse(""),
        "width_ft" -> entity.width.map(_.toString).getOrElse(""),
        "surface" -> entity.surface.getOrElse(""),
        "id" -> entity.id.toString
      )

    }

  }

  implicit object CountrySer extends SerToMap[Country] {

    override def serialize(entity: Country): Map[String, String] = {
      Map(
        "id" -> entity.id.toString,
        "code" -> entity.isoCountryCode.toString,
        "name" -> entity.name,
        "continent" -> entity.isoContinentCode,
        "wikipedia_link" -> entity.wikipediaLink.map(_.toExternalForm).getOrElse(""),
        "keywords" -> entity.keywords.mkString(",")
      )
    }

  }

}