package com.github.e8kor.infrastructure

import scala.language.postfixOps

trait LevenshtainOrdering[A] extends Ordering[A] {

  def levenshtainDistance(v1: A, v2: A): Int

  override def compare(v1: A, v2: A): Int = {
    levenshtainDistance(v1, v2)
  }

}

object LevenshtainOrdering {

  implicit class LevenshtainComparable[A: LevenshtainOrdering](entity: A) {

    def levenshtainDistance(that: A): Int = {
      val comparator = implicitly[LevenshtainOrdering[A]]
      comparator.levenshtainDistance(entity, that)
    }

  }

  object LevenshtainDistance extends ((Iterable[Char], Iterable[Char]) => Int) {

    import scala.math.min

    override def apply(v1: Iterable[Char], v2: Iterable[Char]): Int = {
      ((0 to v2.size).toList /: v1) ((prev, x) =>
        (prev zip prev.tail zip v2).scanLeft(prev.head + 1) {
          case (h, ((d, v), y)) => min(min(h + 1, v + 1), d + (if (x == y) 0 else 1))
        }) last
    }

  }

  implicit object LevenshtainCharsOrdering extends LevenshtainOrdering[Iterable[Char]] {

    override def levenshtainDistance(x: Iterable[Char], y: Iterable[Char]): Int = {
      LevenshtainDistance(x, y)
    }

  }

  implicit object LevenshtainStringOrdering extends LevenshtainOrdering[String] {

    override def levenshtainDistance(x: String, y: String): Int = {
      LevenshtainCharsOrdering.compare(x.toCharArray, y.toCharArray)
    }

  }

}