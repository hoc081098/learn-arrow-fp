package com.hoc.learn_arrow.chapter2

import arrow.core.*
import arrow.core.extensions.list.foldable.foldMap
import arrow.core.extensions.monoid
import arrow.core.extensions.option.monoid.monoid
import arrow.typeclasses.Monoid

fun main() {
  val monoidString = String.monoid()
  println(monoidString.run { empty() })
  println(monoidString.run { "123".combine("345") })
  println("-".repeat(20))

  val strings = listOf("Λ", "R", "R", "O", "W")
  println(monoidString.run { strings.combineAll() })
  println(monoidString.combineAll(strings))
  println("-".repeat(20))

  println(monoidString.run { "hello".maybeCombine(null) })
  println(monoidString.run { "hello".maybeCombine(" world") })
  println("-".repeat(20))

  val monoidOptionInt: Monoid<Option<Int>> = Option.monoid(Int.monoid())
  monoidOptionInt.run { None.combine(1.some()) }.let(::println) // Some(1)
  monoidOptionInt.run { 2.some().combine(1.some()) }.let(::println) // Some(3)
  monoidOptionInt.run { 2.some() + 1.some() }.let(::println) // Some(3)
  monoidOptionInt.run { None.combine(Option.empty()) }.let(::println)// None
  println("-".repeat(20))

  monoidOptionInt.run {
    listOf(None, 2.some(), 3.some()).combineAll()
  }.let(::println) // Some(5)
  monoidOptionInt.run {
    listOf(4.some(), 2.some(), 3.some()).combineAll()
  }.let(::println) // Some(9)
  println("-".repeat(20))

  val ints = (1..10).toList().k()
  val sum = ints.foldMap(Int.monoid(), ::identity)
  println("Sum=$sum")
  val joined = ints.foldMap(String.monoid()) { it.toString() }
  println("Joined=$joined")
  println("-".repeat(20))

}