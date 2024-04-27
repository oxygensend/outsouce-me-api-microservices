package com.oxygensend.opinions.context.utils

import kotlin.reflect.KProperty

interface PathRequest {
    val changes: Set<KProperty<*>>

    fun hasChanged(property: KProperty<*>) = property in changes
}

class PathChanges<R, T>(private val changes: MutableSet<KProperty<*>>) {
    private var v: T? = null
    operator fun getValue(thisRef: R, property: KProperty<*>) = v
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        changes += property
        v = value
    }
}

class Undefined<V>(
    val value: V,
    val isDefined: Boolean
)

inline fun updateIfDefined(value: Undefined<*>, update: () -> Unit) {
    if (value.isDefined) {
        update()
    }
}
