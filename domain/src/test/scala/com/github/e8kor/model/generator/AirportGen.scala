package com.github.e8kor.model.generator

import com.github.e8kor.model.Airport
import com.github.e8kor.model.common.AirportType
import org.scalacheck.Gen._
import org.scalacheck.{Arbitrary, Gen}

object AirportGen {

  val homeLinkOpt = for {
    opt <- option(oneOf(homeLinks))
  } yield {
    opt
  }

  val wikipediaLinkOpt = for {
    opt <- option(oneOf(wikipediaLinks))
  } yield {
    opt
  }

  val municipalityOpt = for {
    opt <- option(oneOf(municipalities))
  } yield {
    opt
  }

  val twoAlphaISOCode = for {
    v1 <- alphaUpperChar
    v2 <- alphaUpperChar
  } yield {
    s"$v1$v2"
  }

  val continentCode = for {
    v1 <- alphaUpperChar
    v2 <- alphaUpperChar
  } yield {
    s"$v1$v2"
  }

  val IATACode = for {
    v1 <- alphaUpperChar
    v2 <- alphaUpperChar
    v3 <- alphaUpperChar
  } yield {
    s"$v1$v2$v3"
  }

  val IATACodeOpt = for {
    opt <- option(IATACode)
  } yield {
    opt
  }

  val ISORegionCode = for {
    v1 <- alphaUpperChar
    v2 <- alphaUpperChar
    v3 <- alphaUpperChar
    v4 <- alphaUpperChar
  } yield {
    s"$v1$v2-$v3$v4"
  }

  val gpsCode = for {
    v1 <- alphaUpperChar
    v2 <- alphaUpperChar
    v3 <- numChar
    v4 <- alphaUpperChar
  } yield {
    s"$v1$v2$v3$v4"
  }

  val gpsCodeOpt = for {
    opt <- option(gpsCode)
  } yield {
    opt
  }

  val localCode = for {
    v1 <- option(alphaUpperChar)
    v2 <- alphaUpperChar
    v3 <- alphaNumChar.map(_.toUpper)
    v4 <- numChar
  } yield {
    v1.map(v => s"$v1$v2$v3$v4").getOrElse(s"$v2$v3$v4")
  }

  val localCodeOpt = for {
    opt <- option(localCode)
  } yield {
    opt
  }

  val displacedThresholdOpt = for {
    opt <- option(choose(0, 10000000))
  } yield {
    opt
  }

  val airportType = for {
    v <- oneOf(AirportType.values.toList)
  } yield {
    v
  }

  val gen: Gen[Airport] = for {
    id <- id
    name <- name
    location <- LocationGen.gen
    tpe <- airportType
    isoCountry <- twoAlphaISOCode
    isoRegion <- ISORegionCode
    scheduledService <- ScheduledServiceGen.gen
    municipality <- municipalityOpt
    continent <- continentCode
    gpsCode <- gpsCodeOpt
    localCode <- localCodeOpt
    iataCode <- IATACodeOpt
    homeLink <- homeLinkOpt
    wikipediaLink <- wikipediaLinkOpt
    keywords <- keywords
  } yield {
    new Airport(
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

  implicit val arbitrary: Arbitrary[Airport] = Arbitrary(gen)
}
