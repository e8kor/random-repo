package com.github.e8kor.model

sealed trait Locatable {

  def ident: Option[String]

  def latitude: Double

  def longitude: Double

  def elevation: Option[Double]

}

case class Location(
                     ident: Option[String],
                     latitude: Double,
                     longitude: Double,
                     elevation: Option[Double]
                   ) extends Locatable

case class LeavingLocation(

                            ident: Option[String],
                            latitude: Double,
                            longitude: Double,
                            elevation: Option[Double],
                            heading: Option[Float],
                            displacedThreshold: Option[Int]
                          ) extends Locatable

case class HeadingLocation(

                            ident: Option[String],
                            latitude: Double,
                            longitude: Double,
                            elevation: Option[Double],
                            heading: Option[Float],
                            displacedThreshold: Option[Int]
                          ) extends Locatable