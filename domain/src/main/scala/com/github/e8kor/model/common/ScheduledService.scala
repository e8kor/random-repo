package com.github.e8kor.model.common

sealed trait ScheduledService {

  def code: String

  override def toString: String = code

}

object No extends ScheduledService {

  override val code: String = "no"

}

object Yes extends ScheduledService {

  override val code: String = "yes"

}

object ScheduledService {

  val values = Set(Yes, No)

  private val types = values.map(_.code)

  def apply(state: String): ScheduledService = {
    require(types.contains(state), s"cannot parse value - $state")
    if (state.equals(Yes.code)) {
      Yes
    } else {
      No
    }
  }

}
