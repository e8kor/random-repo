package com.github.e8kor.domain

import cats.Id
import com.github.e8kor.domain.ServiceAction._
import com.github.e8kor.infrastructure._
import com.github.e8kor.model._

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
        println(s"founded: ${countries.mkString(",")}")
        countries
      case FindAirports(country) =>
        val filters: AirportFilter = SearchAirportByCountry(country)
        val airports: Iterable[Airport] = source filterAirports filters
        println(s"founded: ${airports.mkString(",")}")
        airports
      case FindRunaways(airport) =>
        val filters: RunawayFilter = SearchRunawayByAirport(airport)
        val runaways: Iterable[Runaway] = source filterRunaways filters
        println(s"founded: ${runaways.mkString(",")}")
        runaways
      case PrintResult(collected: Iterable[Record]) =>
        println("We have exact match")
        ()
    }
  }
}
