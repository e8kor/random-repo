package com.github.e8kor.model.generator

import com.github.e8kor.model.{HeadingLocation, LeavingLocation, Location}
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen._

object LocationGen {
  val randomLocation = for {
    v <- oneOf(bigAndAbnormalCities ++ randomCities)
  } yield {
    v
  }

  val seededLocation = for {
    seedLat <- choose(-0.4d, 0.4d) // Distort lat and lon little bit
    seedLon <- choose(-0.8d, 0.8d)
    (lat, lng) <- randomLocation
  } yield {
    (lat + seedLat) -> (lng + seedLon)
  }

  val elevationOpt = for {
    opt <- option(choose(-100d, 100d))
  } yield {
    opt
  }

  val ident = for {
    v1 <- numChar
    v2 <- numChar
    v3 <- option(alphaChar)
  } yield {
    v3 map (v => s"$v1$v2$v") getOrElse s"$v1$v2"
  }

  val identOpt = option(ident)

  val displacedThresholdOpt = for {
    opt <- option(choose(0, 10000000))
  } yield {
    opt
  }

  val headingOpt = for {
    opt <- option(choose(0.0f, 1000000.0f))
  } yield {
    opt
  }

  val gen: Gen[Location] = {
    for {
      indent <- identOpt
      (lng, lat) <- seededLocation
      elevation <- elevationOpt
    } yield {
      new Location(indent, lng, lat, elevation)
    }
  }

  implicit val arbitrary: Arbitrary[Location] = Arbitrary(gen)

}

object LeavingLocationGen {

  import LocationGen.{gen => _, _}

  val gen: Gen[LeavingLocation] = {
    for {
      indent <- identOpt
      (lng, lat) <- seededLocation
      heading <- headingOpt
      elevation <- elevationOpt
      displacedThreshold <- displacedThresholdOpt
    } yield {
      new LeavingLocation(indent, lng, lat, elevation, heading, displacedThreshold)
    }
  }

  implicit val arbitrary: Arbitrary[LeavingLocation] = Arbitrary(gen)

}

object HeadingLocationGen {

  import LocationGen.{gen => _, _}

  val gen: Gen[HeadingLocation] = {
    for {
      indent <- identOpt
      (lng, lat) <- seededLocation
      elevation <- elevationOpt
      heading <- headingOpt

      displacedThreshold <- displacedThresholdOpt

    } yield {
      new HeadingLocation(indent, lng, lat, elevation, heading, displacedThreshold)
    }
  }

  implicit val arbitrary: Arbitrary[HeadingLocation] = Arbitrary(gen)

}