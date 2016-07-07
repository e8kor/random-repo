package com.github.e8kor.infrastructure.spec

import com.github.e8kor.model.Airport

class AirportSerializerSpec extends PropertySpec {

  import com.github.e8kor.model.generator.AirportGen._

  property("serialize", props) {
    forAll {
      entity: Airport =>
        import com.github.e8kor.infrastructure.Serializer._
        import com.github.e8kor.infrastructure.SerializerToMap.ops._

        val dictionary = entity ->!
        val testificant = Map(
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

        dictionary should equal(testificant)
    }
  }

}
