package com.jordi9.skeleton

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property delegate that fails with a descriptive error when read before being set.
 * Use instead of `lateinit var` in test contexts — works with all types
 * and produces clearer error messages that include the property name.
 */
class RequiredDelegate<T : Any> : ReadWriteProperty<Any?, T> {
  private var value: T? = null

  override fun getValue(thisRef: Any?, property: KProperty<*>): T =
    value ?: error("${property.name} has not been set in test context")

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    this.value = value
  }
}

fun <T : Any> required() = RequiredDelegate<T>()
