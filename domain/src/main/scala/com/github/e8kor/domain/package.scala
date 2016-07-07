package com.github.e8kor

import cats.free.Free
import cats.free.Free.pure
import cats.~>

import scala.language.{higherKinds, implicitConversions, postfixOps}

package object domain {

  type Action[A] = Free[ServiceAction, A]

  type Interpreter[M[_]] = ServiceAction ~> M

  implicit def flattenIterableOfFreeIterable[A[_], B](traversable: Iterable[Free[A, Iterable[B]]]): Free[A, Iterable[B]] = {
    val pureF = pure[A, Iterable[B]]((Iterable empty)[B])
    (pureF /: traversable) {
      case (accum, action) =>
        accum.flatMap {
          existing =>
            action.map {
              fresh =>
                existing ++ fresh
            }
        }
    }
  }

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
