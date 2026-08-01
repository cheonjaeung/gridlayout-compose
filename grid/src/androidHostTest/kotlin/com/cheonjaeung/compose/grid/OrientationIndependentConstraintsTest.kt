package com.cheonjaeung.compose.grid

import androidx.compose.ui.unit.Constraints
import org.junit.Assert.assertEquals
import org.junit.Test

class OrientationIndependentConstraintsTest {
    @Test
    fun testFromConstraintsHorizontal() {
        val source = Constraints(
            minWidth = 100,
            maxWidth = 1920,
            minHeight = 50,
            maxHeight = 1080,
        )

        val c = OrientationIndependentConstraints(LayoutOrientation.Horizontal, source)

        assertEquals(100, c.mainAxisMinSize)
        assertEquals(1920, c.mainAxisMaxSize)
        assertEquals(50, c.crossAxisMinSize)
        assertEquals(1080, c.crossAxisMaxSize)
    }

    @Test
    fun testFromConstraintsVertical() {
        val source = Constraints(
            minWidth = 50,
            maxWidth = 1080,
            minHeight = 100,
            maxHeight = 1920,
        )

        val c = OrientationIndependentConstraints(LayoutOrientation.Vertical, source)

        assertEquals(100, c.mainAxisMinSize)
        assertEquals(1920, c.mainAxisMaxSize)
        assertEquals(50, c.crossAxisMinSize)
        assertEquals(1080, c.crossAxisMaxSize)
    }

    @Test
    fun testZeroConstraints() {
        val c = OrientationIndependentConstraints(
            mainAxisMinSize = 0,
            mainAxisMaxSize = 0,
            crossAxisMinSize = 0,
            crossAxisMaxSize = 0,
        )

        assertEquals(0, c.mainAxisMinSize)
        assertEquals(0, c.mainAxisMaxSize)
        assertEquals(0, c.crossAxisMinSize)
        assertEquals(0, c.crossAxisMaxSize)
    }

    @Test
    fun testSameMinMaxConstraints() {
        val c = OrientationIndependentConstraints(
            mainAxisMinSize = 1920,
            mainAxisMaxSize = 1920,
            crossAxisMinSize = 1080,
            crossAxisMaxSize = 1080,
        )

        assertEquals(1920, c.mainAxisMinSize)
        assertEquals(1920, c.mainAxisMaxSize)
        assertEquals(1080, c.crossAxisMinSize)
        assertEquals(1080, c.crossAxisMaxSize)
    }

    @Test
    fun testCreateWithInfinityMainAxisMaxSize() {
        val c = OrientationIndependentConstraints(
            mainAxisMinSize = 0,
            mainAxisMaxSize = Constraints.Infinity,
            crossAxisMinSize = 0,
            crossAxisMaxSize = 1080,
        )

        val converted = c.toConstraints(LayoutOrientation.Vertical)
        assertEquals(0, converted.minHeight)
        assertEquals(Constraints.Infinity, converted.maxHeight)
        assertEquals(0, converted.minWidth)
        assertEquals(1080, converted.maxWidth)
    }

    @Test
    fun testCreateWithInfinityCrossAxisMaxSize() {
        val c = OrientationIndependentConstraints(
            mainAxisMinSize = 0,
            mainAxisMaxSize = 1920,
            crossAxisMinSize = 0,
            crossAxisMaxSize = Constraints.Infinity,
        )

        val converted = c.toConstraints(LayoutOrientation.Vertical)
        assertEquals(0, converted.minHeight)
        assertEquals(1920, converted.maxHeight)
        assertEquals(0, converted.minWidth)
        assertEquals(Constraints.Infinity, converted.maxWidth)
    }

    @Test
    fun testFromConstraintsWithInfiniteWidth() {
        val source = Constraints(
            minWidth = 0,
            maxWidth = Constraints.Infinity,
            minHeight = 0,
            maxHeight = 1920,
        )

        val c = OrientationIndependentConstraints(LayoutOrientation.Horizontal, source)

        val converted = c.toConstraints(LayoutOrientation.Horizontal)
        assertEquals(source.minWidth, converted.minWidth)
        assertEquals(source.maxWidth, converted.maxWidth)
        assertEquals(source.minHeight, converted.minHeight)
        assertEquals(source.maxHeight, converted.maxHeight)
    }

    @Test
    fun testFromConstraintsWithInfiniteHeight() {
        val source = Constraints(
            minWidth = 0,
            maxWidth = 1080,
            minHeight = 0,
            maxHeight = Constraints.Infinity,
        )

        val c = OrientationIndependentConstraints(LayoutOrientation.Vertical, source)

        val converted = c.toConstraints(LayoutOrientation.Vertical)
        assertEquals(source.minWidth, converted.minWidth)
        assertEquals(source.maxWidth, converted.maxWidth)
        assertEquals(source.minHeight, converted.minHeight)
        assertEquals(source.maxHeight, converted.maxHeight)
    }

    @Test
    fun testCreateWithMaxFiniteSize() {
        val c = OrientationIndependentConstraints(
            mainAxisMinSize = 0,
            mainAxisMaxSize = 65534,
            crossAxisMinSize = 0,
            crossAxisMaxSize = 65534,
        )

        assertEquals(65534, c.mainAxisMaxSize)
        assertEquals(65534, c.crossAxisMaxSize)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCreateWithMinSizeGreaterThanMaxSize() {
        OrientationIndependentConstraints(
            mainAxisMinSize = 200,
            mainAxisMaxSize = 100,
            crossAxisMinSize = 0,
            crossAxisMaxSize = 100,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCreateWithTooLargeMainAxisSize() {
        OrientationIndependentConstraints(
            mainAxisMinSize = 0,
            mainAxisMaxSize = 65535,
            crossAxisMinSize = 0,
            crossAxisMaxSize = 1080,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCreateWithTooLargeCrossAxisSize() {
        OrientationIndependentConstraints(
            mainAxisMinSize = 0,
            mainAxisMaxSize = 1080,
            crossAxisMinSize = 0,
            crossAxisMaxSize = 65535,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCreateWithNegativeSize() {
        OrientationIndependentConstraints(
            mainAxisMinSize = -1,
            mainAxisMaxSize = 100,
            crossAxisMinSize = 0,
            crossAxisMaxSize = 100,
        )
    }
}
