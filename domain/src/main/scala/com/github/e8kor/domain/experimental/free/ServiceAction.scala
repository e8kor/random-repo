package com.github.e8kor.domain.experimental.free

import cats.data.Validated
import cats.data.Validated._
import cats.free.Free._
import com.github.e8kor.model.{Airport, Country, Ooops, Runaway}

import scala.language.postfixOps

sealed trait ServiceAction[+Next]

object ServiceAction {

  case class FindCountries(keywords: Iterable[String]) extends ServiceAction[Validated[Ooops, Iterable[Country]]]

  case class FindAirports(country: Country) extends ServiceAction[Validated[Ooops, Iterable[Airport]]]

  case class FindRunaways(airport: Airport) extends ServiceAction[Validated[Ooops, Iterable[Runaway]]]

  case class PrintSuccessDescription(country: Country, airport: Airport, runaways: Iterable[Runaway]) extends ServiceAction[Unit]

  case class PrintFuzzySuccessDescription(countries: Iterable[Country]) extends ServiceAction[Unit]

  case class PrintErrorDescription(ooops: Ooops) extends ServiceAction[Unit]

  def findCountry(keywords: Iterable[String]): Action[Validated[Ooops, Iterable[Country]]] = {
    val result: Action[Validated[Ooops, Iterable[Country]]] = if (keywords.isEmpty) {
      val default = (Iterable empty)[Country]
      pure(valid(default))
    } else {
      liftF(FindCountries(keywords))
    }
    result
  }

  def findAirports(country: Country): Action[Validated[Ooops, (Country, Iterable[Airport])]] = {
    liftF(FindAirports(country)) map (v => v map (v1 => country -> v1))
  }

  def findRunway(airport: Airport): Action[Validated[Ooops, (Airport, Iterable[Runaway])]] = {
    liftF(FindRunaways(airport)) map (v => v map (v1 => airport -> v1))
  }

  def printSuccessDescription(country: Country, airport: Airport, runaways: Iterable[Runaway]): Action[Unit] = {
    liftF(PrintSuccessDescription(country, airport, runaways))
  }

  def printFuzzySuccessDescription(countries: Iterable[Country]): Action[Unit] = {
    liftF(PrintFuzzySuccessDescription(countries))
  }

  def printErrorDescription(ooops: Ooops): Action[Unit] = {
    liftF(PrintErrorDescription(ooops))
  }
}
