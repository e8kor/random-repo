package com.github.e8kor.infrastructure

import com.github.e8kor.model._
import simulacrum._

trait Serializer[A, B] {

  def serialize(entity: A): B

}

@typeclass trait SerializerToMap[T] extends Serializer[T, Map[String, String]] {

  @op("""->!""") def serialize(entity: T): Map[String, String]

}

object Serializer {

  import SerializerToMap.ops._

  implicit def optionSer[T: SerializerToMap]: SerializerToMap[Option[T]] = {
    new SerializerToMap[Option[T]] {

      override def serialize(entity: Option[T]): Map[String, String] = {
        val companion = implicitly[SerializerToMap[T]]
        entity.map(companion.serialize).getOrElse(Map.empty[String, String])
      }

    }
  }

  implicit object HeadingLocationSer extends SerializerToMap[HeadingLocation] {

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


  implicit object LeavingLocationSer extends SerializerToMap[LeavingLocation] {

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

  implicit object LocationSer extends SerializerToMap[Location] {

    override def serialize(entity: Location): Map[String, String] = {
      Map(
        "latitude_deg" -> entity.latitude.toString,
        "longitude_deg" -> entity.longitude.toString,
        "indent" -> entity.ident.getOrElse(""),
        "elevation_ft" -> entity.elevation.map(_.toString).getOrElse("")
      )
    }

  }

  implicit object AirportSer extends SerializerToMap[Airport] {

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

  implicit object RunawaySer extends SerializerToMap[Runaway] {

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

  implicit object CountrySer extends SerializerToMap[Country] {

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