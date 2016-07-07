package com.github.e8kor.domain

import com.github.e8kor.infrastructure.{AirportFilter, CountryFilter, RunawayFilter}
import com.github.e8kor.model.{Airport, Country, Runaway}

trait Source {

  def countries: Iterable[Country]

  def airports: Iterable[Airport]

  def runaways: Iterable[Runaway]

  def filterCountries(filters: CountryFilter*): Iterable[Country] = countries filter (v => filters.exists(f => f(v)))

  def filterAirports(filters: AirportFilter*): Iterable[Airport] = airports filter (v => filters.exists(f => f(v)))

  def filterRunaways(filters: RunawayFilter*): Iterable[Runaway] = runaways filter (v => filters.exists(f => f(v)))

}

