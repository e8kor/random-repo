package com.github.e8kor.domain

import cats.data.Ior
import cats.data.Ior.{Both, Left, Right}
import com.github.e8kor.domain.dsl.ServiceAction._
import com.github.e8kor.infrastructure._
import com.github.e8kor.model._

trait Script {

  def findRecords(keywords: Iterable[String]): Action[Iterable[Record]] = {
    keywords map {
      keyword =>
        findCountry(keyword) flatMap {
          countries =>
            countries map {
              country =>
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
        } map {
          records =>
            keyword -> records
        }
    }
  }

  def searchSimilar(precise: Int, keywords: Iterable[String]): Action[Iterable[Country]] = {
    searchSimilarCountries(precise, keywords.toSeq: _*)
  }

  def validate(records: Iterable[Record]): Action[Ior[Iterable[String], Iterable[Record]]] = {
    validateResults(records)
  }

  def print(result: Iterable[Country]): Action[Unit] = {
    printSimilarCountries(result)
  }

  def print(result: Ior[Iterable[String], Iterable[Record]]): Action[Unit] = {
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
