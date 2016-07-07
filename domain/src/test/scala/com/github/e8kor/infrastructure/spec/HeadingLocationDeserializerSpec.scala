package com.github.e8kor.infrastructure.spec

import com.github.e8kor.model.HeadingLocation

class HeadingLocationDeserializerSpec extends PropertySpec {

  property("deserialize", props) {
    import com.github.e8kor.infrastructure.Deserializer._

    val dictionary = Map(
      "he_ident" -> "81",
      "he_longitude_deg" -> "78.40756935916102",
      "he_displaced_threshold_ft" -> "5301368",
      "he_heading_degT" -> "",
      "he_elevation_ft" -> "",
      "he_latitude_deg" -> "50.51216448690032"
    )
    val entity = dictionary.deserialize[HeadingLocation]

    entity.ident should equal(Some("81"))
    entity.longitude should equal(78.40756935916102d)
    entity.latitude should equal(50.51216448690032d)
    entity.displacedThreshold should equal(Some(5301368))
    entity.heading should equal(None)
    entity.elevation should equal(None)
  }

}
