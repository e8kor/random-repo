package com.github.e8kor.infrastructure.spec

import com.github.e8kor.model.HeadingLocation

class HeadingLocationSerializerSpec extends PropertySpec {

  import com.github.e8kor.model.generator.HeadingLocationGen._

  property("serialize", props) {
    forAll {
      entity: HeadingLocation =>
        import com.github.e8kor.infrastructure.Serializer._
        import com.github.e8kor.infrastructure.SerializerToMap.ops._

        val dictionary = entity ->!
        val testificant = Map(
          "he_latitude_deg" -> entity.latitude.toString,
          "he_longitude_deg" -> entity.longitude.toString,
          "he_ident" -> entity.ident.getOrElse(""),
          "he_elevation_ft" -> entity.elevation.map(_.toString).getOrElse(""),
          "he_heading_degT" -> entity.heading.map(_.toString).getOrElse(""),
          "he_displaced_threshold_ft" -> entity.displacedThreshold.map(_.toString).getOrElse("")
        )

        dictionary should equal(testificant)
    }
  }

}
