package com.cheonjaeung.compose.grid

import kotlin.math.max

/**
 * Returns a new [MutableList] with specified [size] filled with `null`.
 */
@Suppress("NOTHING_TO_INLINE")
internal inline fun <T> mutableListOfNulls(size: Int): MutableList<T?> {
    return MutableList(size) { null }
}

/**
 * Returns the largest element or 0 if there are no elements.
 */
internal fun IntArray.maxOrZero(): Int {
    if (this.isEmpty()) {
        return 0
    }
    var maxValue = this[0]
    for (i in 1 until this.size) {
        maxValue = max(maxValue, this[i])
    }
    return maxValue
}

/**
 * Returns the sum of the all values produced by [selector] function applied to each element.
 */
internal inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Int): Int {
    var sum = 0
    for ((index, element) in this.withIndex()) {
        sum += selector(index, element)
    }
    return sum
}
