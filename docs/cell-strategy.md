# Cell Strategy

Both grid layout composables take a cell strategy parameter called `SimpleGridCells`.
`SimpleGridCells` defines the number of cells and the size of each cell.

There are 2 types of cell strategy, `Fixed` and `Adaptive`.
They are similar to LazyGrid's `Fixed` and `Adaptive`.

## Fixed

`SimpleGridCells.Fixed` is a cell strategy for exact count of rows or columns.
And each cell will have 1/n of the grid size.

The API of `Fixed` looks like this:

```kotlin
class Fixed(private val count: Int) : SimpleGridCells
```

There is a parameter called `count`. This is the maximum number of cells the grid should have on
each line. The `count` must be a positive number. If 0 or negative number is provided, it occurs
an exception.

For example, a grid has 400dp width or height, and `Fixed(4)` is applied.

```kotlin
HorizontalGrid(
    rows = SimpleGridCells.Fixed(4),
    modifier = Modifier.height(400.dp)
) { /* content */ }

VerticalGrid(
    columns = SimpleGridCells.Fixed(4),
    modifier = Modifier.width(400.dp)
) { /* content */ }
```

![fixed-example](./images/fixed-example.png)

The grid will have 4 cells on each line.
If the grid is horizontal, it will have 4 rows and if vertical, it will have 4 columns.
And each cells should have 1/n of the grid size.
In this example, each cells will have 100dp width or height.

## Adaptive

`SimpleGridCells.Adaptive` is a cell strategy for as many cells as possible.
And each cell will have at least minimum size.

The API of `Adaptive` looks like this:

```kotlin
class Adaptive(private val minSize: Dp) : SimpleGridCells
```

There is a parameter called `minSize`. This is the minimum size of each cell should have.
The `minSize` must be a positive size. If the size is 0 or below, it occurs an exception.
The grid layout with `Adaptive` calculates the maximum number of cells possible while keeping
the `minSize` restriction.

For example, a grid has 400dp width or height and `Adaptive(120.dp)` is applied.

```kotlin
HorizontalGrid(
    rows = SimpleGridCells.Adaptive(120.dp),
    modifier = Modifier.height(400.dp)
) { /* content */ }

VerticalGrid(
    columns = SimpleGridCells.Adaptive(120.dp),
    modifier = Modifier.width(400.dp)
) { /* content */ }
```

![adaptive-example](./images/adaptive-example.png)

The grid will calculate how many cells should have.
In this case, the grid will have 3 cells on each line.
Because, there is no way to have 4 or more cells while keeping `minSize` restriction.
And each cells will have 1/3 of 400dp (about 133.333dp) width or height.

If the grid size is expanded to 600dp, the number of cells on each line will be changed to 5
and each cell's size will be 120dp.
