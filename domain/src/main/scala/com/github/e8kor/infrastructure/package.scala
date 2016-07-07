package com.github.e8kor

import cats.free.Free
import cats.free.Free._
import com.github.e8kor.model.{Airport, Country, Runaway}

import scala.language.{higherKinds, implicitConversions, postfixOps}

package object infrastructure {

  type FuzzyCountryFilter = Country => Int
  type CountryFilter = Country => Boolean
  type AirportFilter = Airport => Boolean
  type RunawayFilter = Runaway => Boolean

//  implicit def flattenIterableOfFreeIterable[A[_], B](traversable: Iterable[Free[A, Iterable[B]]]): Free[A, Iterable[B]] = {
//    val pureF = pure[A, Iterable[B]]((Iterable empty)[B])
//    (pureF /: traversable) {
//      case (accum, action) =>
//        accum.flatMap {
//          existing =>
//            action.map {
//              fresh =>
//                existing ++ fresh
//            }
//        }
//    }
//  }

  implicit def flattenIterableOfFree[A[_], B](traversable: Iterable[Free[A, B]]): Free[A, Iterable[B]] = {
    val pureF = pure[A, Iterable[B]]((Iterable empty)[B])
    (pureF /: traversable) {
      case (accum, action) =>
        accum flatMap {
          existing =>
            action map {
              fresh =>
                existing ++ Iterable(fresh)
            }
        }
    }
  }

}
