package com.hoc.learn_arrow.chapter1

import arrow.core.extensions.show
import arrow.typeclasses.Show
import java.util.*

fun main() {
  val showInt = Int.show()
  val showString = String.show()

  println(showInt.run { 123.show() })
  println(showInt.run { (-28).show() })

  println(showString.run { "Hello world".show() })
  println(showString.run { "It's working".show() })

  Show<Date> { "${this.time}ms since the epoch." }
    .run { Date().show() }
    .let(::println)

  val showCat = Show<Cat> {
    val name = showString.run { this@Show.name.show() }
    val age = showInt.run { this@Show.age.show() }
    val color = showString.run { this@Show.color.show() }
    "$name is a $age year-old $color cat"
  }

  println(showCat.run { Cat("Betty", 6, "grey").show() })
}