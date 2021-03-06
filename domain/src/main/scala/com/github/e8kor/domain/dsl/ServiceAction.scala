package com.github.e8kor.domain.dsl

import cats.data.Ior
import cats.free.Free._
import com.github.e8kor.domain._
import com.github.e8kor.model._

import scala.language.postfixOps

/**
  * @tparam Next
  */

sealed trait ServiceAction[+Next]

object ServiceAction {

  case object Top10HighestNumberOfAirports extends ServiceAction[Iterable[Country]]

  case object Top10LeastNumberOfAirports extends ServiceAction[Iterable[Country]]

  case class FindCountries(keywords: Iterable[String]) extends ServiceAction[Iterable[Country]]

  case class SearchSimilarCountries(precise: Int, keywords: Iterable[String]) extends ServiceAction[Iterable[Country]]

  case class FindAirports(country: Country) extends ServiceAction[Iterable[Airport]]

  case class FindRunaways(airport: Airport) extends ServiceAction[Iterable[Runaway]]

  case class ValidateRecords(collected: Iterable[RecordWithKey]) extends ServiceAction[Ior[Iterable[String], Iterable[RecordWithKey]]]

  case class PrintSimilarCountries(collected: Iterable[Country]) extends ServiceAction[Unit]

  case class PrintResult(collected: Iterable[RecordWithKey]) extends ServiceAction[Unit]

  case class PrintReport(isHighestAmountOfAirports: Boolean, collected: Iterable[Record]) extends ServiceAction[Unit]

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

  def validateResults(collected: Iterable[RecordWithKey]): Action[Ior[Iterable[String], Iterable[RecordWithKey]]] = {
    liftF(ValidateRecords(collected))
  }

  def printResult(collected: Iterable[RecordWithKey]): Action[Unit] = {
    liftF(PrintResult(collected))
  }

  def printReport(isHighestAmountOfAirports: Boolean, collected: Iterable[Record]): Action[Unit] = {
    liftF(PrintReport(isHighestAmountOfAirports, collected))
  }

  def printErrors(collected: Iterable[String]): Action[Unit] = {
    liftF(PrintErrors(collected))
  }

  def printSimilarCountries(collected: Iterable[Country]): Action[Unit] = {
    liftF(PrintSimilarCountries(collected))
  }

  def top10HighestNumberOfAirports: Action[Iterable[Country]] = {
    liftF(Top10HighestNumberOfAirports)
  }

  def top10LeastNumberOfAirports: Action[Iterable[Country]] = {
    liftF(Top10LeastNumberOfAirports)
  }
}
