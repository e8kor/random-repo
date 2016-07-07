package com.github.e8kor.infrastructure

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, PropSpec, Tag}

package object spec {

  val props = Tag("props")

  private [spec] trait PropertySpec extends PropSpec with GeneratorDrivenPropertyChecks with Matchers

}
