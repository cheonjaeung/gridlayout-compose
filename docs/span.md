# Spanning Cells

The `content` composable lambda of grid layout extends `GridScope`.
To apply span size, you can set `span` modifier in the `GridScope`.

The following code and graphic show a example of span.

```kotlin
HorizontalGrid(rows = SimpleGridCells.Fixed(3)) {
    Item()
    Item(Modifier.span(2))
    Item(Modifier.span(2))
    Item()
    Item()
}

VerticalGrid(columns = SimpleGridCells.Fixed(3)) {
    Item()
    Item(Modifier.span(2))
    Item(Modifier.span(2))
    Item()
    Item()
}
```

![span-graphic](images/span-graphic.png)

!!! warning
    If given span size is bigger than maximum cell count of the axis, the cell will be undisplayed.
