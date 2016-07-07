package com.github.e8kor.domain

import cats.Id
import cats.data.Ior
import cats.data.Ior._
import com.github.e8kor.domain.dsl.ServiceAction
import com.github.e8kor.domain.dsl.ServiceAction._
import com.github.e8kor.infrastructure.Filter._
import com.github.e8kor.infrastructure._
import com.github.e8kor.model._

import scala.language.postfixOps

case class AppInterpreter()(implicit source: Source) extends Interpreter[Id] {

  override def apply[A](fa: ServiceAction[A]): Id[A] = {
    fa match {
      case Top10HighestNumberOfAirports =>
        val countries: Iterable[Country] = source.countries.map {
          country =>
            val airports = source.airports.filter {
              airport =>
                airport.isoCountryCode == country.isoCountryCode
            }
            country -> airports.size
        }.toList.sortBy {
          case (_, amountOfAirports) => amountOfAirports
        }.reverse.take(10).map {
          case (country, _) => country
        }

        countries
      case Top10LeastNumberOfAirports =>
        val countries: Iterable[Country] = source.countries.map {
          country =>
            val airports = source.airports.filter {
              airport =>
                airport.isoCountryCode == country.isoCountryCode
            }
            country -> airports.size
        }.toList.sortBy {
          case (_, amountOfAirports) => amountOfAirports
        }.take(10).map {
          case (country, _) => country
        }
        countries
      case FindCountries(keywords) =>
        val exactFilters: Seq[CountryFilter] = keywords flatMap (
          keyword =>
            List(
              CountrySearchByName(keyword),
              CountrySearchByISOCountryCode(keyword)
            )
          ) toSeq

        val countries: Iterable[Country] = source filterCountries (exactFilters: _*)
        countries
      case SearchSimilarCountries(precise, keywords) =>
        val exactFilters: Seq[CountryFilter] = keywords flatMap (
          keyword =>
            List(
              // TODO Need split search filters
              // (FuzzyCountryFilterByISOCountryCode withPrecise precise) (keyword),
              (FuzzyCountryFilterByName withPrecise precise) (keyword)
            )
          ) toSeq

        val countries: Iterable[Country] = source filterCountries (exactFilters: _*)
        countries
      case FindAirports(country) =>
        val filters: AirportFilter = SearchAirportByCountry(country)
        val airports: Iterable[Airport] = source filterAirports filters
        airports
      case FindRunaways(airport) =>
        val filters: RunawayFilter = SearchRunawayByAirport(airport)
        val runaways: Iterable[Runaway] = source filterRunaways filters
        runaways
      case ValidateRecords(collected: Iterable[RecordWithKey]) =>

        val validated = collected map {
          case (keyword, Nil) =>
            Left(NoCountriesFounded format keyword)
          case other =>
            Right(other)
        }

        val invalids = validated collect {
          case Left(message) => message
        }

        val valids = validated collect {
          case Right(item) => item
        }

        val ior: Ior[Iterable[String], Iterable[RecordWithKey]] = if (invalids.nonEmpty && valids.nonEmpty) {
          Both(invalids, valids)
        } else if (invalids.nonEmpty) {
          Left(invalids)
        } else {
          Right(valids)
        }

        (ior asInstanceOf)[A]
      case PrintResult(collected: Iterable[RecordWithKey]) =>
        collected foreach {
          case (keyword, countries) =>
            println(s"for keyword $keyword we found ${countries size} countries")
            countries foreach {
              case (country, airports) =>
                println(s"${airports size} airports in ${country name}:${country isoCountryCode}")
                airports foreach {
                  case (airport, runaways) =>
                    val ids = runaways map (_ id) mkString ", "
                    println(s"${airports size} runaways in ${airport `type`} ${airport name}")
                    println(s"runaway ids are : $ids")
                }
            }
        }
        ()
      case PrintReport(isHighestAmountOfAirports, collected: Iterable[Record]) =>
        val kind = if (isHighestAmountOfAirports) {
          "highest"
        } else {
          "least"
        }
        println(s"top 10 countries with $kind amount of airports")
        collected foreach {
          case (country, airports) =>
            println(s" ${airports size} airports in ${country name}:${country isoCountryCode}")
            val runaways = airports flatMap {
              case (_, items) =>
                items
            }
            val surfaces = runaways flatMap (_ surface)

            val idents = runaways flatMap (_ leavingLocation) flatMap (_ ident)
            if (surfaces nonEmpty) {
              println(s"runaways ground type are ${surfaces mkString ", "}")
            }
            if (idents nonEmpty) {
              println(s"idents are ${idents mkString ", "}")
            }
        }
        ()
      case PrintErrors(collected: Iterable[String]) =>
        val message = collected mkString "\n"
        println(
          s"""Ooops we have some problems with your request like:
              |$message
          """.stripMargin)
        ()
      case PrintSimilarCountries(collected: Iterable[Country]) =>
        val message = collected map (_ name) mkString "\n"
        println(
          s"""Here is countries with similar names:
              |$message
          """.stripMargin)
        ()
    }
  }
}
