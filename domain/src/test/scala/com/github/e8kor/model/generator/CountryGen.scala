package com.github.e8kor.model.generator

import com.github.e8kor.model.Country
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen._

object CountryGen {

  val countryCode = for {
    v1 <- numChar
    v2 <- numChar
    v3 <- numChar
    v4 <- numChar
    v5 <- numChar
    v6 <- numChar
  } yield {
    s"$v1$v2$v3$v4$v5$v6"
  }

  val wikipediaLinkOpt = for {
    opt <- option(oneOf(wikipediaLinks))
  } yield {
    opt
  }

  val continentCode = for {
    v1 <- alphaUpperChar
    v2 <- alphaUpperChar
  } yield {
    s"$v1$v2"
  }

  val gen: Gen[Country] = for {
    id <- id
    name <- name
    code <- countryCode
    continent <- continentCode
    wikipediaLink <- wikipediaLinkOpt
    keywords <- keywords
  } yield {
    new Country(
      id,
      code,
      name,
      continent,
      wikipediaLink,
      keywords
    )
  }

  implicit val arbitrary: Arbitrary[Country] = Arbitrary(gen)

}
