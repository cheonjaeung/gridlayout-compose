# Spanning Cells

## Sequential Grid

`HorizontalGrid` and `VerticalGrid` are also available to set span size.
The `content` composable lambda of grid layout extends `GridScope`.
To apply span size, you can set `span` modifier in the `GridScope`.
Like `BoxGridScope`'s `rowSpan` and `columnSpan`, the `span` modifier takes a lambda to calculate span.

!!! warning
    If returned span size is bigger than maximum cell count of the axis, the cell will be undisplayed.

```kotlin
HorizontalGrid(rows = SimpleGridCells.Fixed(3)) {
    Item()
    Item(Modifier.span { 2 })
    Item(Modifier.span { 2 })
    Item()
    Item()
}

VerticalGrid(columns = SimpleGridCells.Fixed(3)) {
    Item()
    Item(Modifier.span { 2 })
    Item(Modifier.span { 2 })
    Item()
    Item()
}
```

![span-graphics2](images/span-graphics2.png)

You can access to `GridItemSpanScope` to get information of current line span.

The `maxLineSpan` is the same to cross axis cell count.
For example, if a vertical grid has 4 columns, the `maxLineSpan` is 4.

And the `maxCurrentLineSpan` is the current usable cell count.
For example, if a vertical grid has 4 columns and already one cell is filled, the `maxCurrentLineSpan` is 3.

```kotlin
VerticalGrid(columns = SimpleGridCells.Adaptive(30.dp)) {
    for (i in 0 until itemCount) {
        Item(
            modifier = Modifier.span {
                if (maxLineSpan == maxCurrentLineSpan) {
                    maxLineSpan - i
                } else {
                    maxCurrentLineSpan
                }
            }
        )
    }
}
```

## Box Grid

The `content` composable lambda of grid layout extends `BoxGridScope`.
In the `BoxGridScope`, you can use `rowSpan` and `columnSpan` modifier to set span size of cell.

The `span` modifiers take a lambda to calculate spans.
The lambda returns a `BoxGridItemSpan` which represents row and column span size.
Or you can just pass `null` instead of lambda to use default span size, which is 1.

```kotlin
BoxGrid(
    rows = SimpleGridCells.Fixed(3),
    columns = SimpleGridCells.Fixed(3)
) {
    Item(modifier = Modifier.span { BoxGridItemSpan(row = 2) })
    Item(modifier = Modifier.column(2))
    Item(modifier = Modifier.position(row = 1, column = 1).span { BoxGridItemSpan(column = 2) })
    Item(modifier = Modifier.row(2)span { BoxGridItemSpan(column = 2) })
}
```

![span-graphics1](images/span-graphics.png)

You can access to `BoxGridItemSpanScope` to get information of current row or column span.

The `maxRowSpan` and the `maxColumnSpan` is the same to cell count.
And the `maxCurrentRowSpan` and the `maxCurrentColumnSpan` is the current usable cell count.

```kotlin
BoxGrid(
    rows = SimpleGridCells.Adaptive(30.dp),
    columns = SimpleGridCells.Adaptive(30.dp)
) {
    for (i in 0 until itemCount) {
        Item(
            modifier = Modifier.span {
                val rowSpan = if (maxRowSpan == maxCurrentRowSpan) {
                    maxRowSpan - i
                } else {
                    maxCurrentRowSpan
                }
                val columnSpan = if (maxColumnSpan == maxCurrentColumnSpan) {
                    maxColumnSpan - i
                } else {
                    maxCurrentColumnSpan
                }
                BoxGridItemSpan(
                    row = rowSpan,
                    column = columnSpan
                )
            }
        )
    }
}
```
