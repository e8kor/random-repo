package com.github.e8kor.app

import com.github.e8kor.domain.{Action, AppInterpreter, Script}

import scala.language.postfixOps

object Application extends App {

  implicit val _ = Repository

  val keywords: Iterable[String] = args toIterable

  val interpreter = AppInterpreter()

  val script = Script

  if (keywords nonEmpty) {
    val printFree: Action[Unit] = {
      script findRecords keywords flatMap {
        records =>
          val countries = records flatMap {
            case (_, items) =>
              items
          }
          if (countries isEmpty) {
            script searchSimilar(2, keywords) flatMap (script showSimilarCountries)
          } else {
            script validate records flatMap (script showResult)
          }
      }
    }

    printFree foldMap interpreter
  } else {
    val printFree: Action[Unit] = for {
      highest <- (script getTop10HighestAmountOfAirports)
      _ <- script showReport(true, highest)
      least <- (script getTop10LeastAmountOfAirports)
      _ <- script showReport(false, least)
    } yield ()

    printFree foldMap interpreter
  }
}
