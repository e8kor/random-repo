package com.github.e8kor.model.generator

import com.github.e8kor.model.common.ScheduledService
import org.scalacheck.Gen._
import org.scalacheck.{Arbitrary, Gen}

object ScheduledServiceGen {

  val gen: Gen[ScheduledService] = for {
    v <- oneOf(ScheduledService.values.toList)
  } yield {
    v
  }

  implicit val arbitrary: Arbitrary[ScheduledService] = Arbitrary(gen)

}
