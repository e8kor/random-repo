package com.github.e8kor.model

import java.net.URL

import com.github.e8kor.model.common.{AirportType, ScheduledService}

case class Airport(
                    id: Long,
                    location: Location,
                    `type`: AirportType,
                    name: String,
                    isoContinentCode: String,
                    isoCountryCode: String,
                    isoRegionCode: String,
                    municipality: Option[String],
                    scheduledService: ScheduledService,
                    gpsCode: Option[String],
                    iataCode: Option[String],
                    localCode: Option[String],
                    homeLink: Option[URL],
                    wikipediaLink: Option[URL],
                    keywords: Set[String]
                  )