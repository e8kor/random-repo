package com.github.e8kor

import cats.free.Free
import cats.~>
import com.github.e8kor.domain.dsl.ServiceAction

import scala.language.{higherKinds, implicitConversions, postfixOps}

package object domain {

  type Action[A] = Free[ServiceAction, A]
  type Interpreter[M[_]] = ServiceAction ~> M

  val NoCountriesFounded = "Seems like no countries founded for %s"

}
