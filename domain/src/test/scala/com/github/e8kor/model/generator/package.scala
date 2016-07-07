package com.github.e8kor.model

//import org.scalacheck.Gen.Choose._
import java.net.URL

import org.scalacheck.Gen._

package object generator {

  private[generator] val homeLinks = List(
    "http://www.wsdot.wa.gov/aviation/AllStateAirports/Colfax_LowerGraniteState.htm",
    "https://www.oceanreef.com/community/private-airport-1345.html",
    "http://www.boreal.org/airport/",
    "http://www.edairinc.com/index.html",
    "http://www.burgaero.com/",
    "http://www.silvercreekgliderclub.com/",
    "http://www.adelantoairport.com/index.html",
    "http://www.timberon.org/community/timberon-airport-52nm/",
    "http://thesalmonfarm.org/blog/p/2704",
    "http://www.aviationcadet.com/index.html",
    "http://www.leroyairport.com/"
  ).map(new URL(_))

  private[generator] val wikipediaLinks = List(
    "http://en.wikipedia.org/wiki/McKenzie_Bridge_State_Airport",
    "https://en.wikipedia.org/wiki/Grant_Airport",
    "https://en.wikipedia.org/wiki/Utirik_Airport",
    "http://en.wikipedia.org/wiki/Sandy_River_Airport",
    "http://en.wikipedia.org/wiki/Vernonia_Airfield",
    "http://en.wikipedia.org/wiki/North_Palm_Beach_County_General_Aviation_Airport",
    "https://en.wikipedia.org/wiki/Ocean_Reef_Club_Airport",
    "http://en.wikipedia.org/wiki/Mid-Columbia_Medical_Center_Heliport",
    "https://en.wikipedia.org/wiki/Pilot_Station_Airport",
    "http://en.wikipedia.org/wiki/Warren-Sugarbush_Airport",
    "http://en.wikipedia.org/wiki/Grand_Marais/Cook_County_Seaplane_Base",
    "http://en.wikipedia.org/wiki/Lida_Junction_Airport",
    "http://en.wikipedia.org/wiki/Goldfield_Airport",
    "http://en.wikipedia.org/wiki/Corning_Municipal_Airport_(California)",
    "http://en.wikipedia.org/wiki/Providence_Hospital_Heliport",
    "http://en.wikipedia.org/wiki/Shingletown_Airport"
  ).map(new URL(_))

  private[generator] val municipalities = List(
    "Bensalem", "Anchor Point", "Harvest", "Newport", "Cordes",
    "Barstow", "Briggsdale", "Bushnell", "Riverview", "Okeechobee",
    "Lithonia", "Hiram", "Kailua/Kona", "Clark Fork", "Chesterton",
    "Polo", "Hobart", "Kings", "Gardner", "Stanford", "Gonzales",
    "Chatham", "Esterwood", "Federalsburg", "Ludington", "Battle Lake",
    "Alba", "Havre", "Bridgeton", "Louisburg", "New Brunswick", "Grant",
    "West Bloomfield", "Wauseon", "Dayton", "Salem", "Coatesville",
    "Mercer", "Loysville", "Mc Kenzie Bridge", "Sumter", "Fort Worth",
    "Fort Worth", "Manchester", "Everman", "Baytown", "Kanab", "Alton",
    "Hillsville", "Colfax", "Longbranch", "Waupaca", "Asotin", "Beverly",
    "O'Donnell", "Purkeypile", "Seward", "Clanton", "De Queen", "Camp Verde"
  )

  private[generator] val surfaces = List(
    "grass/concrete",
    "grass/earth",
    "grav",
    "gravel",
    "ground",
    "hard",
    "ice",
    "packed dirt",
    "paved",
    "paving",
    "sand",
    "sand/grass",
    "sealed",
    "tar",
    "tar old",
    "turf",
    "unsealed",
    "water"
  )

  private[generator] val keywordList = List(
    "5K8", "0WA0", "Formerly 00E", "Formerly 92W", "UT23",
    "Air Force Plant 78 Airfield", "08IN", "Formerly 82T", "0CA1",
    "Devil Track Resort", "Rockville", "University of Kansas Medical Center Heliport",
    "Formerly 12AR", "Formerly MI96", "Formerly 38B", "Formerly 16XS", "6Q6",
    "Formerly 9WI7", "1IN7", "27XS", "E21", "Moved marker to satellite view of pad",
    "H-22", "23AK", "Formerly 7CA0", "2ID7", " S74", "Formerly KOTN", " Formerly OTN",
    " 2IG4", "Formerly 2E4", "1W5", "Sackett Farms Airstrip", "Hinkles Ferry",
    "Formerly 7IA2", "8F4", "CMH", "3XS7", "U40", "3MO", "Formerly 3N7",
    "Formerly 3TX6", "Formerly 8W8", "41LA", "Transamerica Center Heliport",
    "4LA3", " Jackson Airport", "00Y", "Formerly N76", "4U8", " Lord Howe Atoll",
    "Bunch's Half Acre Airport", "50E", "2L9", "52E", "WI50", "Formerly 55TS",
    "5IN7", "5IN8", "Formerly 44I", "5NC3", "formerly S26", "II93",
    "Morris Army Airfield", "66GA", "67TX", "Formerly 8J2", "6IN7", "6IN9",
    "6F2", "24726.011*A", "Formerly 9C2", "5II0", "Formerly 02FD", "Formerly 4K2",
    "9KS8", "81IN", "6F4", "Formerly 8A9", "IN03", "02V", "8NK3", "Air George Heliport",
    "Lytleville Orchard Airport", "Chandler Field", "FD51", "AK63", "Uruzgan", " Urozgan",
    "Santa Isabel Islands", "Henderson Field", "Mbambanakira Airport", "Luangiua"
  )

  // TODO replace with coodinates from res files
  private[generator] val bigAndAbnormalCities = Vector(
    (28.666668d, 77.21667d),
    (55.75222d, 37.615555d),
    (43.10562d, 131.87354d),
    (56.00972d, 92.79167d),
    (69.3535d, 88.2027d),
    (40.4165d, -3.70256d),
    (35.91979d, -88.75895d),
    (22.28552d, 114.15769d),
    (-23.5475d, -46.63611d),
    (-1.28333d, 36.81667d),
    (42.98339d, -81.23304d),
    (43.2d, -80.38333d),
    (35.6895d, 139.69171d),
    (37.085152d, 15.273000d),
    (39.213039d, -106.937820d),
    (42.869999d, 74.589996d),
    (50.000000d, 8.271110d)
  )

  private[generator] val randomCities = Vector(
    (33.59278d, -7.61916d),
    (14.35d, 108.0d),
    (26.866667d, 81.2d),
    (12.65d, -8.0d),
    (-3.945d, 122.49889d),
    (31.21564d, 29.95527d),
    (42.89427d, 24.71589d),
    (42.43333d, 23.81667d),
    (13.68935d, -89.18718d),
    (7.88481d, 98.40008d),
    (47.93595d, 13.48306d),
    (39.59611d, 27.02444d),
    (6.12104d, 100.36014d),
    (52.5396d, 31.9275d),
    (8.122222d, -63.54972d),
    (30.609444d, 34.80111d),
    (30.609444d, 34.80111d),
    (36.72564d, 9.18169d),
    (18.00191d, -66.10822d),
    (10.31672d, 123.89071d),
    (42.88052d, -8.54569d),
    (25.05d, 61.74167d),
    (-38.13874d, 176.24516d),
    (-22.68333d, 14.53333d),
    (4.58333d, 13.68333d),
    (5.47366d, 10.41786d),
    (38.71418d, -93.99133d),
    (46.55472d, 15.64667d),
    (46.55d, 26.95d),
    (-24.65451d, 25.90859d),
    (52.70389d, -8.86417d),
    (34.60712d, 43.67822d),
    (28.233334d, 83.98333d),
    (51.0159d, 4.20173d),
    (-33.83333d, 151.13333d),
    (-6.82349d, 39.26951d),
    (-34.83346d, -56.16735d),
    (21.51694d, 39.21917d),
    (-17.82935d, 31.05389d),
    (35.01361d, 69.17139d),
    (-20.16194d, 57.49889d),
    (47.39489d, 18.9136d),
    (-16.5d, -68.15d),
    (60.86667d, 26.7d),
    (67.25883d, 15.39181d),
    (50.63945d, 20.30454d),
    (46.91035d, 7.47096d),
    (9.17583d, 7.18083d),
    (35.1d, 33.41667d),
    (24.46667d, 54.36667d),
    (50.75667d, 78.54d),
    (-36.89272d, -60.32254d),
    (51.40606d, -0.4137d),
    (-4.21528d, -69.94056d),
    (35.88972d, 14.4425d),
    (35.53722d, 129.31667d),
    (48.666668d, 26.566668d),
    (-16.47083d, -54.63556d),
    (39.31009d, 16.3399d),
    (19.56378d, -70.87582d),
    (4.88447d, -1.75536d),
    (-29.61678d, 30.39278d),
    (54.09833d, 28.3325d),
    (-4.05466d, 39.66359d),
    (31.1d, -107.98333d),
    (48.73946d, 19.15349d),
    (35.69111d, -0.64167d),
    (25.286667d, 51.533333d),
    (55.86066d, 9.85034d),
    (9.90467d, -83.68352d)
  )

  val keyword = for {
    v <- oneOf(keywordList)
  } yield {
    v
  }

  val keywords = for {
    v1 <- option(keyword)
    v2 <- option(keyword)
    v3 <- option(keyword)
  } yield {
    List(v1, v2, v3).flatten.toSet
  }

  val name = for {
    v <- alphaStr.filter(_.length <= 10)
  } yield {
    v
  }

  val id = for {
    v <- choose(1, 10000)
  } yield {
    v
  }
}
