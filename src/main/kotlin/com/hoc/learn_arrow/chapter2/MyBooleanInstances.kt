package com.hoc.learn_arrow.chapter2

import arrow.typeclasses.Monoid
import com.hoc.learn_arrow.chapter2.MyBooleanInstances.booleanAndMonoid
import com.hoc.learn_arrow.chapter2.MyBooleanInstances.booleanEitherMonoid
import com.hoc.learn_arrow.chapter2.MyBooleanInstances.booleanOrMonoid
import com.hoc.learn_arrow.chapter2.MyBooleanInstances.booleanXnorMonoid

object MyBooleanInstances {
  @JvmStatic
  val booleanAndMonoid = object : Monoid<Boolean> {
    override fun empty() = true
    override fun Boolean.combine(b: Boolean) = this && b
  }

  @JvmStatic
  val booleanOrMonoid = object : Monoid<Boolean> {
    override fun empty() = false
    override fun Boolean.combine(b: Boolean) = this || b
  }

  @JvmStatic
  val booleanEitherMonoid = object : Monoid<Boolean> {
    override fun empty() = false
    override fun Boolean.combine(b: Boolean) = this xor b
  }

  @JvmStatic
  val booleanXnorMonoid = object : Monoid<Boolean> {
    override fun empty() = false
    override fun Boolean.combine(b: Boolean) = !(this xor b)
  }

}

private inline fun showResult(f: () -> Boolean) = println(if (f()) "TRUE" else "FALSE")

fun main() {
  showResult { booleanAndMonoid.run { true + true } == true }
  showResult { booleanAndMonoid.run { true + false } == false }
  showResult { booleanAndMonoid.run { false + false } == false }
  showResult { booleanAndMonoid.run { false + true } == false }
  println("-".repeat(20))

  showResult { booleanOrMonoid.run { true + true } == true }
  showResult { booleanOrMonoid.run { true + false } == true }
  showResult { booleanOrMonoid.run { false + false } == false }
  showResult { booleanOrMonoid.run { false + true } == true }
  println("-".repeat(20))

  showResult { booleanEitherMonoid.run { true + true } == false }
  showResult { booleanEitherMonoid.run { true + false } == true }
  showResult { booleanEitherMonoid.run { false + false } == false }
  showResult { booleanEitherMonoid.run { false + true } == true }
  println("-".repeat(20))

  showResult { booleanXnorMonoid.run { true + true } == true }
  showResult { booleanXnorMonoid.run { true + false } == false }
  showResult { booleanXnorMonoid.run { false + false } == true }
  showResult { booleanXnorMonoid.run { false + true } == false }
  println("-".repeat(20))
}