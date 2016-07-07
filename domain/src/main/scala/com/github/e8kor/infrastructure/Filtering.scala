package com.github.e8kor.infrastructure

import com.github.e8kor.infrastructure.LevenshtainOrdering._
import com.github.e8kor.model.{Airport, Country, Runaway}

import scala.language.{higherKinds, postfixOps}

trait Function2[A, B, R] extends (A => (B => R))

sealed trait FuzzyFilter[A, B] extends Function2[A, B, Int]

sealed trait Filter[A, B] extends Function2[A, B, Boolean]

object FuzzyCountryFilterByISOCountryCode extends FuzzyFilter[String, Country] {
  override def apply(v1: String): FuzzyCountryFilter = {
    v2: Country =>
      (v2 isoCountryCode) levenshtainDistance v1
  }
}

object FuzzyCountryFilterByName extends FuzzyFilter[String, Country] {
  override def apply(v1: String): FuzzyCountryFilter = {
    v2: Country =>
      (v2 name) levenshtainDistance v1
  }
}

object CountrySearchByISOCountryCode extends Filter[String, Country] {
  override def apply(v1: String): CountryFilter = {
    v2: Country =>
      (v2 isoCountryCode) equals v1
  }
}

object CountrySearchByName extends Filter[String, Country] {
  override def apply(v1: String): CountryFilter = {
    v2: Country =>
      (v2 name) equals v1
  }
}

object SearchAirportByCountry extends Filter[Country, Airport] {
  override def apply(v1: Country): AirportFilter = {
    v2: Airport =>
      (v2 isoCountryCode) equals (v1 isoCountryCode)
  }
}

object SearchRunawayByAirport extends Filter[Airport, Runaway] {
  override def apply(v1: Airport): RunawayFilter = {
    v2: Runaway =>
      (v2 airportRef) equals (v1 id)
  }
}

object Filter {

  implicit class FuzzyToStrongFilter[A, B](companion: FuzzyFilter[A, B]) {

    def withPrecise(precise: Int) = {
      new Filter[A, B] {
        override def apply(v1: A): (B) => Boolean = {
          v2: B =>
            val distance = companion(v1)(v2)
            if (distance == 0) { // not interested in exact matches
              false
            } else if (precise >= distance) { // words are enough similar
              true
            } else { // not interested if distance is bigger than precise
              false
            }
        }
      }
    }
  }

}