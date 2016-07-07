package com.github.e8kor

import com.github.e8kor.model.{Airport, Country, Runaway}

package object infrastructure {

  type FuzzyCountryFilter = Country => Int
  type CountryFilter = Country => Boolean
  type AirportFilter = Airport => Boolean
  type RunawayFilter = Runaway => Boolean

}
