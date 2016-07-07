package com.github.e8kor.domain.experimental

import cats.free.Free

package object free {

  type Action[A] = Free[ServiceAction, A]
}
