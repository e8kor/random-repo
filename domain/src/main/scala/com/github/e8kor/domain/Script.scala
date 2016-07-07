package com.github.e8kor.domain

import cats.data.Ior
import cats.data.Ior.{Both, Left, Right}
import com.github.e8kor.domain.dsl.ServiceAction._
import com.github.e8kor.infrastructure._
import com.github.e8kor.model._

trait Script {

  def findRecords(keywords: Iterable[String]): Action[Iterable[RecordWithKey]] = {
    keywords map {
      keyword =>
        findCountry(keyword) flatMap {
          countries =>
            countries map collectAirportsAndRunaways
        } map {
          records =>
            keyword -> records
        }
    }
  }

  def getTop10HighestAmountOfAirports: Action[Iterable[Record]] = {
    top10HighestNumberOfAirports flatMap {
      countries =>
        countries map collectAirportsAndRunaways
    }
  }

  def getTop10LeastAmountOfAirports: Action[Iterable[Record]] = {
    top10LeastNumberOfAirports flatMap {
      countries =>
        countries map collectAirportsAndRunaways
    }
  }

  def collectAirportsAndRunaways(country: Country): Action[Record] = {
    findAirports(country) flatMap {
      airports =>
        airports map {
          airport =>
            findRunway(airport) map {
              runaways =>
                airport -> runaways
            }
        }
    } map {
      airportsAndRunaways =>
        country -> airportsAndRunaways
    }
  }

  def searchSimilar(precise: Int, keywords: Iterable[String]): Action[Iterable[Country]] = {
    searchSimilarCountries(precise, keywords.toSeq: _*)
  }

  def validate(records: Iterable[RecordWithKey]): Action[Ior[Iterable[String], Iterable[RecordWithKey]]] = {
    validateResults(records)
  }

  def showSimilarCountries(result: Iterable[Country]): Action[Unit] = {
    printSimilarCountries(result)
  }

  def showReport(isHighestAmountOfAirports: Boolean, result: Iterable[Record]): Action[Unit] = {
    printReport(isHighestAmountOfAirports, result)
  }

  def showResult(result: Ior[Iterable[String], Iterable[RecordWithKey]]): Action[Unit] = {
    result match {
      case Left(invalids) => printErrors(invalids)
      case Right(valids) => printResult(valids)
      case Both(invalids, valids) =>
        for {
          _ <- printErrors(invalids)
          _ <- printResult(valids)
        } yield ()
    }

  }

}

object Script extends Script
