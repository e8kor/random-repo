package com.github.e8kor.model.common

sealed trait AirportType {

  def code: String

  override def toString: String = code
}

object AirportType {

  val values = Set(
    Heliport,
    SmallAirport,
    Closed,
    SeaplaneBase,
    Balloonport,
    MediumAirport,
    LargeAirport
  )

  private val types = values.map(_.code)

  def apply(`type`: String): AirportType = {
    require(types contains `type`, s"in not a type of airport :${`type`}")
    if (`type` equals Heliport.code) {
      Heliport
    } else if (`type` equals SmallAirport.code) {
      SmallAirport
    } else if (`type` equals Closed.code) {
      Closed
    } else if (`type` equals SeaplaneBase.code) {
      SeaplaneBase
    } else if (`type` equals Balloonport.code) {
      Balloonport
    } else if (`type` equals MediumAirport.code) {
      MediumAirport
    } else {
      LargeAirport
    }
  }
}

case object Heliport extends AirportType {
  override def code = "heliport"
}

case object Balloonport extends AirportType {
  override def code = "balloonport"
}

case object SeaplaneBase extends AirportType {
  override def code = "seaplane_base"
}

case object SmallAirport extends AirportType {
  override def code = "small_airport"
}

case object MediumAirport extends AirportType {
  override def code = "medium_airport"
}

case object LargeAirport extends AirportType {
  override def code = "large_airport"
}

case object Closed extends AirportType {
  override def code = "closed"
}

