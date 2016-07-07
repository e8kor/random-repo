package com.github.e8kor.app

import cats.Id
import cats.data.Validated
import cats.free.Free
import com.github.e8kor.domain.experimental.free.ServiceAction._
import com.github.e8kor.domain.experimental.free.{Action, ServiceAction, ServiceInterpreter}
import com.github.e8kor.model.{Country, Ooops}

object Application extends App {

  val repository = Repository

  val interpereter = ServiceInterpreter(repository)

  val a: Id[Validated[Iterable[Country], Iterable[Country]]] = findCountry(args).foldMap(interpereter)

  //ap(findCountry(args))
  //  val result = for {
  //    countries <- findCountry(args)
  //    country <- countries
  //    airports <- findAirports(country)
  //    airport <- airports
  //    runaways <- findRunway(airport)
  //    _ <- printSuccessDescription(country, airport, runaways)
  //  } yield ()
//  val result: Free[ServiceAction, Validated[Iterable[Country], Iterable[Free[ServiceAction, Validated[Ooops, Iterable[Free[ServiceAction, Validated[Ooops, Action[Unit]]]]]]]]] = findCountry(args).map(
//    value =>
//      value.map(
//        v =>
//          v.map(
//            v1 =>
//              findAirports(v1).map(
//                v2 =>
//                  v2.map(
//                    v3 =>
//                      v3.map(
//                        v4 =>
//                          findRunway(v4).map(
//                            v5 =>
//                              v5.map(
//                                v6 =>
//                                  printSuccessDescription(v1, v4, v6)
//                              )
//                          )
//                      )
//                  )
//              )
//          )
//      )
//  )
//  val folded = result foldMap ServiceInterpreter(repository)
//  result.
}
