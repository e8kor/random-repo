package com.github.e8kor.model.common

sealed trait BooleanState {

  def code: Int

  override def toString: String = code.toString

}

object True extends BooleanState {

  override val code: Int = 1

}

object False extends BooleanState {

  override val code: Int = 0

}

object BooleanState {

  val values = Set(True, False)

  private val types = values.map(_.code)

  def apply(code: Int): BooleanState = {
    require(types.contains(code), s"cannot parse value - $code")
    if (code.equals(True.code)) {
      True
    } else {
      False
    }
  }
}
