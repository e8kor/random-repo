package com.github.e8kor.model.generator

import com.github.e8kor.model.Runaway
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen._

object RunawayGen {
  val ident = for {
    v1 <- numChar
    v2 <- numChar
    v3 <- option(alphaChar)
  } yield {
    v3 map (v => s"$v1$v2$v") getOrElse s"$v1$v2"
  }

  val surfaceOpt = for {
    opt <- option(oneOf(surfaces))
  } yield {
    opt
  }

  val gen: Gen[Runaway] = for {
    id <- id
    airportId <- id
    airportIdent <- ident
    length <- option(choose(0l, 100l))
    width <- option(choose(0l, 50l))
    surface <- surfaceOpt
    lighted <- BooleanStateGen.gen
    closed <- BooleanStateGen.gen
    leavingLocation <- option(LeavingLocationGen.gen)
    headingLocation <- option(HeadingLocationGen.gen)
  } yield {
    new Runaway(
      id,
      airportId,
      airportIdent,
      length,
      width,
      surface,
      lighted,
      closed,
      leavingLocation,
      headingLocation
    )
  }

  implicit val arbitrary: Arbitrary[Runaway] = Arbitrary(gen)

}
