package com.github.e8kor.infrastructure

import java.net.URL

import com.github.e8kor.model._
import com.github.e8kor.model.common.{AirportType, BooleanState, ScheduledService}
import simulacrum.{op, typeclass}

import scala.language.postfixOps

trait Deserializer[A, B] {

  def deserializeOpt(raw: A): Option[B]

  def deserialize(raw: A): B = {
    deserializeOpt(raw).get
  }

}

@typeclass trait DeserializerFromMap[T] extends Deserializer[Map[String, String], T] {

  @op("?<-") override def deserializeOpt(raw: Map[String, String]): Option[T]

  @op("!<-") override def deserialize(raw: Map[String, String]): T = {
    deserializeOpt(raw).get
  }
}

object Deserializer {

  private def filterOutEmpty[T](dictionary: Map[T, String]): Map[T, String] = {
    dictionary.filterNot {
      case (l, r) =>
        r isEmpty
    }
  }

  implicit class MapDeserializableOps(raw: Map[String, String]) {

    def deserialize[A: DeserializerFromMap]: A = {
      val ev = implicitly[DeserializerFromMap[A]]
      ev.deserialize(raw)
    }

    def deserializeOpt[A: DeserializerFromMap]: Option[A] = {
      val ev = implicitly[DeserializerFromMap[A]]
      ev.deserializeOpt(raw)
    }

  }

  implicit object HeadingLocationDeserializer extends DeserializerFromMap[HeadingLocation] {

    override def deserializeOpt(raw: Map[String, String]): Option[HeadingLocation] = {
      val dictionary = filterOutEmpty(raw)
      for {
        he_latitude_deg <- dictionary.get("he_latitude_deg").map(_.toDouble)
        he_longitude_deg <- dictionary.get("he_longitude_deg").map(_.toDouble)
      } yield {
        val (he_ident) = dictionary.get("he_ident")
        val (he_elevation_ft) = dictionary.get("he_elevation_ft").map(_.toDouble)
        val (he_heading_degT) = dictionary.get("he_heading_degT").map(_.toFloat)
        val (he_displaced_threshold_ft) = dictionary.get("he_displaced_threshold_ft").map(_.toInt)

        HeadingLocation(
          he_ident,
          he_latitude_deg,
          he_longitude_deg, he_elevation_ft, he_heading_degT, he_displaced_threshold_ft)
      }
    }

  }

  implicit object LeavingLocationDeserializer extends DeserializerFromMap[LeavingLocation] {

    override def deserializeOpt(raw: Map[String, String]): Option[LeavingLocation] = {
      val dictionary = filterOutEmpty(raw)
      for {
        le_latitude_deg <- dictionary.get("le_latitude_deg").map(_.toDouble)
        le_longitude_deg <- dictionary.get("le_longitude_deg").map(_.toDouble)
      } yield {
        val (le_ident) = dictionary.get("le_ident")
        val (le_elevation_ft) = dictionary.get("le_elevation_ft").map(_.toDouble)
        val (le_heading_degT) = dictionary.get("le_heading_degT").map(_.toFloat)
        val (le_displaced_threshold_ft) = dictionary.get("le_displaced_threshold_ft").map(_.toInt)

        LeavingLocation(le_ident, le_latitude_deg, le_longitude_deg, le_elevation_ft, le_heading_degT, le_displaced_threshold_ft)
      }
    }

  }

  implicit object LocationDeserializer extends DeserializerFromMap[Location] {

    override def deserializeOpt(raw: Map[String, String]): Option[Location] = {
      val dictionary = filterOutEmpty(raw)
      for {
        latitudeDeg <- dictionary.get("latitude_deg").map(_.toDouble)
        longitudeDeg <- dictionary.get("longitude_deg").map(_.toDouble)
      } yield {
        val indent = dictionary.get("indent")
        val elevationFt = dictionary.get("elevation_ft").map(_.toDouble)

        Location(indent, latitudeDeg, longitudeDeg, elevationFt)
      }
    }

  }

  implicit object AirportDeserializer extends DeserializerFromMap[Airport] {

    override def deserializeOpt(raw: Map[String, String]): Option[Airport] = {
      val dictionary = filterOutEmpty(raw)
      for {
        id <- dictionary.get("id").map(_.toLong)
        tpe <- dictionary.get("type").map(AirportType.apply)
        name <- dictionary.get("name")
        continent <- dictionary.get("continent")
        isoCountry <- dictionary.get("iso_country")
        isoRegion <- dictionary.get("iso_region")
        scheduledService <- dictionary.get("scheduled_service").map(ScheduledService.apply)
        location <- raw.deserializeOpt[Location]
      } yield {
        val municipality = dictionary.get("municipality")
        val gpsCode = dictionary.get("gps_code")
        val iataCode = dictionary.get("iata_code")
        val localCode = dictionary.get("local_code")
        val homeLink = dictionary.get("home_link").map(new URL(_))
        val wikipediaLink = dictionary.get("wikipedia_link").map(new URL(_))
        val keywords = dictionary.get("keywords").map(v => v.split(",").map(_.trim).toSet[String]).getOrElse(Set.empty[String])

        Airport(
          id,
          location,
          tpe,
          name,
          continent,
          isoCountry,
          isoRegion,
          municipality,
          scheduledService,
          gpsCode,
          iataCode,
          localCode,
          homeLink,
          wikipediaLink,
          keywords
        )
      }
    }

  }

  implicit object RunawayDeserializer extends DeserializerFromMap[Runaway] {

    override def deserializeOpt(raw: Map[String, String]): Option[Runaway] = {
      val dictionary = filterOutEmpty(raw)
      for {
        id <- dictionary.get("id").map(_.toLong)
        airport_ref <- dictionary.get("airport_ref").map(_.toLong)
        airport_ident <- dictionary.get("airport_ident")
        lighted <- dictionary.get("lighted").map(_.toInt).map(BooleanState.apply)
        closed <- dictionary.get("closed").map(_.toInt).map(BooleanState.apply)
      } yield {
        val (length_ft) = dictionary.get("length_ft").map(_.toLong)
        val (width_ft) = dictionary.get("width_ft").map(_.toLong)
        val (surface) = dictionary.get("surface")
        val leavingLocation = raw.deserializeOpt[LeavingLocation]
        val headingLocation = raw.deserializeOpt[HeadingLocation]

        Runaway(
          id,
          airport_ref,
          airport_ident,
          length_ft,
          width_ft,
          surface,
          lighted,
          closed,
          leavingLocation,
          headingLocation
        )
      }
    }

  }

  implicit object CountryDeserializer extends DeserializerFromMap[Country] {

    override def deserializeOpt(raw: Map[String, String]): Option[Country] = {
      val dictionary = filterOutEmpty(raw)

      for {
        id <- dictionary.get("id").map(_.toLong)
        code <- dictionary.get("code")
        name <- dictionary.get("name")
        continent <- dictionary.get("continent")
      } yield {
        val wikipedia_link = dictionary.get("wikipedia_link").map(new URL(_))
        val keywords = dictionary.get("keywords").map(v => v.split(",").toSet).getOrElse(Set.empty[String])

        Country(id, code, name, continent, wikipedia_link, keywords)
      }
    }

  }

}