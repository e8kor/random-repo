package com.github.e8kor.domain.dsl

import cats.data.Ior
import cats.free.Free._
import com.github.e8kor.domain._
import com.github.e8kor.model.{Airport, Country, Record, Runaway}

import scala.language.postfixOps

sealed trait ServiceAction[+Next]

object ServiceAction {

  case class FindCountries(keywords: Iterable[String]) extends ServiceAction[Iterable[Country]]

  case class SearchSimilarCountries(precise: Int, keywords: Iterable[String]) extends ServiceAction[Iterable[Country]]

  case class FindAirports(country: Country) extends ServiceAction[Iterable[Airport]]

  case class FindRunaways(airport: Airport) extends ServiceAction[Iterable[Runaway]]

  case class ValidateRecords(collected: Iterable[Record]) extends ServiceAction[Ior[Iterable[String], Iterable[Record]]]

  case class PrintSimilarCountries(collected: Iterable[Country]) extends ServiceAction[Unit]

  case class PrintResult(collected: Iterable[Record]) extends ServiceAction[Unit]

  case class PrintErrors(collected: Iterable[String]) extends ServiceAction[Unit]

  def searchSimilarCountries(precise: Int, keywords: String*): Action[Iterable[Country]] = {
    if (keywords isEmpty) {
      pure((Iterable empty)[Country])
    } else {
      liftF(SearchSimilarCountries(precise, keywords))
    }
  }

  def findCountry(keywords: String*): Action[Iterable[Country]] = {
    if (keywords isEmpty) {
      pure((Iterable empty)[Country])
    } else {
      liftF(FindCountries(keywords))
    }
  }

  def findAirports(country: Country): Action[Iterable[Airport]] = {
    liftF(FindAirports(country))
  }

  def findRunway(airport: Airport): Action[Iterable[Runaway]] = {
    liftF(FindRunaways(airport))
  }

  def validateResults(collected: Iterable[Record]): Action[Ior[Iterable[String], Iterable[Record]]] = {
    liftF(ValidateRecords(collected))
  }

  def printResult(collected: Iterable[Record]): Action[Unit] = {
    liftF(PrintResult(collected))
  }

  def printErrors(collected: Iterable[String]): Action[Unit] = {
    liftF(PrintErrors(collected))
  }

  def printSimilarCountries(collected: Iterable[Country]): Action[Unit] = {
    liftF(PrintSimilarCountries(collected))
  }
}
