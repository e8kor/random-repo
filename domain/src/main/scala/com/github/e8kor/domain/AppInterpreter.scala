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
      case ValidateRecords(collected: Iterable[Record]) =>

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

        val ior: Ior[Iterable[String], Iterable[Record]] = if (invalids.nonEmpty && valids.nonEmpty) {
          Both(invalids, valids)
        } else if (invalids.nonEmpty) {
          Left(invalids)
        } else {
          Right(valids)
        }

        (ior asInstanceOf)[A]
      case PrintResult(collected: Iterable[Record]) =>
        collected foreach {
          case (keyword, countries) =>
            println(s"for keyword $keyword we found ${countries size} countries")
            countries foreach {
              case (country, airports) =>
                println(s" ${airports size} airports in ${country name}:${country isoCountryCode}")
                airports foreach {
                  case (airport, runaways) =>
                    println(s" ${airports size} runaways in ${airport `type`} ${airport name}")
                    val surfaces = runaways flatMap (_ surface)
                    if (surfaces nonEmpty) {
                      println(s" runaways ground type are ${surfaces mkString ", "}")
                    }
                }

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
