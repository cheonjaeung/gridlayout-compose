# GridLayout for Compose

![overview-examples](images/grid-overview-examples.png)

GridLayout for Compose is a library that provides missing non-lazy grid layout composables for Compose Multiplatform.

Jetpack Compose doesn't offer non-lazy grid layouts, providing only lazy grids and alternatives (e.g., flow layouts).
However, sometimes we need a simple grid layout for less complexity and more intuitive layout code.
This library offers sequential grid, and box grid as simpler solutions for small grid UIs.

There are benefits of this library:

- **Similar API to LazyGrid**: The GridLayout's APIs are designed to provide similar development experience to LazyGrid.
- **Easy to implement various grid**: There are _"Fixed"_, _"Adaptive"_ and _"FixedSize"_ for grid layout management like LazyGrid.
  Like LazyGrid, it eliminates dealing with different screen sizes.
- **Simple to use as a part of LazyList**: The GridLayout is not lazy layout. It can be simply placed in lazy layouts.
  If only a portion of the full layout is grid, No need to use LazyGrid with span size for full layout.
- **Efficient for small datasets**: LazyGrid has complex logics for large datasets. But when datasets are small, it can be inefficient.
  The GridLayout is just a simple layout. It can be more efficient for smaller datasets.
- **Flexible item placement**: This library provide box grid layout to allow placing items at the specific row and column position.

## Overview

GridLayout for Compose provides a simple layout composables for building grid UI.

This library provides 2 types of grid, **box grid** and **sequential grid**.

`BoxGrid` is a grid layout that allows you to place items in grid format.
Items in `BoxGrid` can be placed in any position, and multiple items can placed in the same cell like `Box` composable.

Sequential grid is a layout that arranges items in a grid format, either horizontally or vertically.
There are 2 composables for each direction: `HorizontalGrid` and `VerticalGrid`.
Its API is similar to `LazyGrid`.
For example to draw grid like this:

![usage-example](images/usage-example.png)

You can write code like this:

```kotlin
VerticalGrid(
    columns = SimpleGridCells.Fixed(3),
    modifier = Modifier.fillMaxWidth(),
) {
    for ((index, color) in colors.withIndex()) {
        ColorBox(
            color = color,
            text = (index + 1).toString(),
        )
    }
}
```

To read more detail documentation, please read [documentation](./installation.md).

## License

This library is licensed under the Apache License, Version 2.0.
