package com.github.e8kor.model

import com.github.e8kor.model.common.BooleanState

case class Runaway(
                    id: Long,
                    airportRef: Long,
                    iataCode: String,
                    length: Option[Long],
                    width: Option[Long],
                    surface: Option[String],
                    lighted: BooleanState,
                    closed: BooleanState,
                    leavingLocation: Option[LeavingLocation],
                    headingLocation: Option[HeadingLocation]
                  )