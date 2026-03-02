# Extended Cell Strategy

`ExtendedGridCells` provides advanced cell strategies like `Responsive` and `Track`.
These strategies are available for `BoxGrid`, `VerticalGrid` and `HorizontalGrid`.
And also available for standard Compose grids like `LazyGrid` and `LazyStaggeredGrid`.

!!! example "Experimental"
    This cell strategies are currently **experimental**. You should use it with `@ExperimentalGridApi` annotation to opt-in.

## Responsive

`Responsive` is a cell strategy that switches between other cell strategies based on the available size.

This strategy has a factory lambda parameter.
It determines which cell strategy to use based on the available size.

For example, the following code shows that how to show 3 columns on a small screen and adaptive columns on a large screen:

```kotlin
VerticalGrid(
    columns = ExtendedGridCells.SimpleGridCells.Responsive { availableWidth ->
        if (availableWidth < 600.dp) {
            SimpleGridCells.Fixed(3)
        } else {
            SimpleGridCells.Adaptive(150.dp)
        }
    },
    modifier = Modifier.fillMaxWidth()
) { /* content */ }
```

This strategy is also available for standard Compose grids:

- For `LazyVerticalGrid` and `LazyHorizontalGrid`, use `ExtendedGridCells.GridCells.Responsive`.
- For `LazyVerticalStaggeredGrid` and `LazyHorizontalStaggeredGrid`, use `ExtendedGridCells.StaggeredGridCells.Responsive`.

## Track

`Track` is a cell strategy for specific tracks with fixed or weighted sizes.
It allows you to define each row or column's size individually, similar to CSS Grid's `grid-template-columns`.

You can define tracks using `GridTrack.Fixed` for absolute sizes and `GridTrack.Weight` for proportional sizes of the remaining space.

For example, a vertical grid has 400dp width and you want to have 3 columns where:

2. The first column is fixed to 100dp.
2. The second column takes 2/3 of the remaining space.
3. The third column takes 1/3 of the remaining space.

```kotlin
VerticalGrid(
    columns = ExtendedGridCells.SimpleGridCells.Track(
        GridTrack.Fixed(100.dp),
        GridTrack.Weight(2f),
        GridTrack.Weight(1f)
    ),
    modifier = Modifier.width(400.dp)
) { /* content */ }
```

In this case, the first column will have 100dp. The remaining 300dp will be divided by the total weight (2 + 1 = 3), so the second column will have 200dp and the third column will have 100dp.

This strategy is also available for standard Compose grids:

- For `LazyVerticalGrid` and `LazyHorizontalGrid`, use `ExtendedGridCells.GridCells.Track`.
- For `LazyVerticalStaggeredGrid` and `LazyHorizontalStaggeredGrid`, use `ExtendedGridCells.StaggeredGridCells.Track`.
