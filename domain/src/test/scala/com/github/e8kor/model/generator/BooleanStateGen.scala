package com.github.e8kor.model.generator

import com.github.e8kor.model.common.BooleanState
import org.scalacheck.Gen._
import org.scalacheck.{Arbitrary, Gen}

object BooleanStateGen {

  val gen: Gen[BooleanState] = for {
    v <- oneOf(BooleanState.values.toList)
  } yield {
    v
  }

  implicit val arbitrary: Arbitrary[BooleanState] = Arbitrary(gen)

}
