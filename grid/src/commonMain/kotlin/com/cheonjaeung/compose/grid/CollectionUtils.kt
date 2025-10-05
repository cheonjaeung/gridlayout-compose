package com.cheonjaeung.compose.grid

import kotlin.math.max

/**
 * Returns a new [IntArray] with specified [size] filled with 0.
 */
@Suppress("NOTHING_TO_INLINE")
internal inline fun intArrayOfZeros(size: Int): IntArray {
    return IntArray(size) { 0 }
}

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
