package com.github.e8kor.domain.experimental.free

import cats.data.Validated
import cats.data.Validated._
import cats.~>
import com.github.e8kor.domain.Source
import com.github.e8kor.domain.experimental.free.ServiceAction._
import com.github.e8kor.infrastructure.Filter.FuzzyToStrongFilter
import com.github.e8kor.infrastructure._
import com.github.e8kor.model.{Airport, Country, Ooops, Runaway}

import scala.language.{postfixOps, reflectiveCalls}

case class ServiceInterpreter(source: Source) extends (
  ServiceAction ~> ({type V[A] = Validated[Ooops, A]})#V
  ) {

  override def apply[A](fa: ServiceAction[A]): Validated[Ooops, A] = {
    fa match {
      case FindCountries(keywords) =>
        val exactFilters: Seq[CountryFilter] = keywords flatMap (
          keyword =>
            List(
              CountrySearchByName(keyword),
              CountrySearchByISOCountryCode(keyword)
            )
          ) toSeq

        val exactCountries: Iterable[Country] = source filterCountries (exactFilters: _*)

        val result = if (exactCountries isEmpty) {
          val fuzzyFilters: Seq[CountryFilter] = keywords flatMap (
            keyword =>
              List(
                (FuzzyCountryFilterByName withPrecise 5) (keyword),
                (FuzzyCountryFilterByISOCountryCode withPrecise 5) (keyword)
              )
            ) toSeq

          val fuzzyCountries: Iterable[Country] = source filterCountries (fuzzyFilters: _*)
          if (fuzzyCountries.isEmpty) {
            invalid(Ooops("Oops! There is no similar countries, sorry about that."))
          } else {
            invalid(Ooops(
              s"""There is no countries with name:
                  |${keywords mkString ","}
                  |but you may me interested in:
                  |${fuzzyCountries map (_ name)}""".stripMargin))
          }
        } else {
          valid(exactCountries)
        }

        result
      case FindAirports(country) =>
        val filters: AirportFilter = SearchAirportByCountry(country)
        val airports: Iterable[Airport] = source filterAirports filters
        val result = if (airports isEmpty) {
          invalid(Ooops(s"No airports founded for ${country name}"))
        } else {
          valid(airports)
        }
        result
      case FindRunaways(airport) =>
        val filters: RunawayFilter = SearchRunawayByAirport(airport)
        val runaways: Iterable[Runaway] = source filterRunaways filters
        val result = if (runaways isEmpty) {
          invalid(Ooops(s"No runaways founded for ${airport.name}"))
        } else {
          valid(runaways)
        }
        result
      case PrintSuccessDescription(country: Country, airport: Airport, runaways: Iterable[Runaway]) =>
        println("We have exact match")
        ()

      case PrintFuzzySuccessDescription(countries: Iterable[Country]) =>
        println("We have fuzzy match")
        ()
      case PrintErrorDescription(ooops: Ooops) =>
        println("We have error")
        ()
    }
  }
}
