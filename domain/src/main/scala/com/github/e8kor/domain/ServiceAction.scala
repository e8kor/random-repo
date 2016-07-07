package com.github.e8kor.domain

import cats.data.Validated
import cats.free.Free._
import com.github.e8kor.model.{Airport, Country, Record, Runaway}

import scala.language.postfixOps

sealed trait ServiceAction[+Next]

object ServiceAction {

  case class FindCountries(keywords: Iterable[String]) extends ServiceAction[Iterable[Country]]

  case class FindAirports(country: Country) extends ServiceAction[Iterable[Airport]]

  case class FindRunaways(airport: Airport) extends ServiceAction[Iterable[Runaway]]

  case class ValidateResult(collected: Iterable[Record]) extends ServiceAction[Validated[String, Iterable[Record]]]

  case class PrintResult(collected: Iterable[Record]) extends ServiceAction[Unit]

  def findCountry(keywords: String*): Action[Iterable[Country]] = {
    val result: Action[Iterable[Country]] = if (keywords isEmpty) {
      val default = (Iterable empty)[Country]
      pure(default)
    } else {
      liftF(FindCountries(keywords))
    }
    result
  }

  def findAirports(country: Country): Action[Iterable[Airport]] = {
    liftF(FindAirports(country))
  }

  def findRunway(airport: Airport): Action[Iterable[Runaway]] = {
    liftF(FindRunaways(airport))
  }

  def printResult(collected: Iterable[Record]): Action[Unit] = {
    liftF(PrintResult(collected))
  }
}
