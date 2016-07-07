package com.github.e8kor.app

import cats.free.Free
import com.github.e8kor.domain.ServiceAction._
import com.github.e8kor.domain.{AppInterpreter, ServiceAction}
import com.github.e8kor.model.Record

import scala.language.postfixOps

object Application extends App {

  implicit val _ = Repository

  lazy val keywords: Iterable[String] = args toIterable

  lazy val interpreter = AppInterpreter()

  lazy val recordsFree: Free[ServiceAction, Iterable[Record]] = {
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
                  tuples =>
                    country -> tuples
                }
            }
        } map {
          records =>
            keyword -> records
        }
    }
  }

  lazy val printFree: Free[ServiceAction, Unit] = for {
    recods <- recordsFree
    nothing <- printResult(recods)
  } yield nothing

  printFree foldMap interpreter
}
