package com.hoc.learn_arrow.chapter1

import arrow.core.*
import arrow.core.extensions.eq
import arrow.core.extensions.option.eq.eq
import arrow.typeclasses.Eq
import com.hoc.learn_arrow.chapter1.EqInstances.anyEq
import com.hoc.learn_arrow.chapter1.EqInstances.catEq
import com.hoc.learn_arrow.chapter1.EqInstances.dateEq
import com.hoc.learn_arrow.chapter1.EqInstances.intEq
import com.hoc.learn_arrow.chapter1.EqInstances.optionCatEq
import com.hoc.learn_arrow.chapter1.EqInstances.optionIntEq
import java.util.*

object EqInstances {
  private val stringEq = String.eq()
  private val longEq = Long.eq()

  val intEq = Int.eq()

  val anyEq = Eq.any()

  val dateEq = Eq { date1: Date, date2: Date ->
    longEq.run { date1.time.eqv(date2.time) }
  }

  val catEq = Eq { cat1: Cat, cat2: Cat ->
    stringEq.run { cat1.name.eqv(cat2.name) } &&
        intEq.run { cat1.age.eqv(cat2.age) } &&
        stringEq.run { cat1.color.eqv(cat2.color) }
  }

  val optionIntEq = Option.eq(intEq)

  val optionCatEq = Option.eq(catEq)
}

fun main() {
  println("Int eq...")
  println(intEq.run { 1.eqv(2) }) // 1 === 2 -> false
  println(intEq.run { 2.eqv(2) }) // 2 === 2 -> true
  println(intEq.run { 123.neqv(123) }) // 123 =!= 123 -> false
  println(intEq.run { 123.neqv(321) }) // 123 =!= 321 -> true
  // compile error
  // println(eqInt.run { 2.eqv("2") })
  println("-----------")

  println("Any eq...")
  // Option is a data class with a single value
  println(anyEq.run { Some(1).eqv(Option.just(1)) }) // true
  // Fails because the wrapped function is not evaluated for comparison
  println(anyEq.run { Eval.later { 1 }.eqv(Eval.later { 1 }) }) // false
  println("-----------")

  println("Option eq...")
  println(optionIntEq.run { Some(1).eqv(None) }) // false
  println(optionIntEq.run { 1.some().eqv(None) }) // false
  println(optionIntEq.run { Option.just(1).eqv(Option.empty()) }) // false
  println(optionIntEq.run { Some(1).eqv(Option.just(1)) }) // true
  println("-----------")

  println("Date eq...")

  val x = Date() // now
  Thread.sleep(100)
  val y = Date() // a bit later than now
  println(dateEq.run { x.eqv(x) }) // true
  println(dateEq.run { x.eqv(y) }) // false
  println("-----------")

  println("Cate + Option<Cat> eq...")
  val cat1 = Cat("Garfield", 38, "orange and black")
  val cat2 = Cat("Heathcliff", 33, "orange and black")
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty<Cat>()

  println(catEq.run { cat1.eqv(cat2) }) // false
  println(catEq.run { cat1.neqv(cat2) }) // true

  println(optionCatEq.run { optionCat1.eqv(optionCat1) }) // true
  println(optionCatEq.run { optionCat1.eqv(optionCat2) }) // false
  println(optionCatEq.run { optionCat1.neqv(optionCat2) }) // true
}