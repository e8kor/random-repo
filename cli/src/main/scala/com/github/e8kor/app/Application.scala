package com.github.e8kor.app

import com.github.e8kor.domain.{Action, AppInterpreter, Script}

import scala.language.postfixOps

object Application extends App {

  implicit val _ = Repository

  lazy val keywords: Iterable[String] = args toIterable

  lazy val interpreter = AppInterpreter()

  lazy val script = Script

  lazy val printFree: Action[Unit] = {
    script findRecords keywords flatMap {
      records =>
        val countries = records flatMap {
          case (_, items) =>
            items
        }
        if (countries isEmpty) {
          script searchSimilar(2, keywords) flatMap (script print)
        } else {
          script validate records flatMap (script print)
        }
    }
  }

  printFree foldMap interpreter
}
