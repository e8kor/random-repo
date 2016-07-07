package com.github.e8kor.model

import java.net.URL

case class Country(
                    id: Long,
                    isoCountryCode: String,
                    name: String,
                    isoContinentCode: String,
                    wikipediaLink: Option[URL],
                    keywords: Set[String]
                  )