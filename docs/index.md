# GridLayout for Compose

![overview-examples](images/grid-overview-examples.png)

**GridLayout for Compose** is a library for missing grid layout in Compose Multiplatform.

Jetpack Compose doesn't offer non lazy grid layout, there are only lazy grid layout and alternatives (for example, flow layout).
But sometimes, we need to use grid layout for less complexity and more intuitive layout code.
This library can be simpler solution for small grid UI.

There are benefits of this library:

- **Similar API to LazyGrid**: The GridLayout's APIs are designed to provide similar development experience to LazyGrid.
- **Easy to implement adaptive grid**: There are _"Fixed"_ and _"Adaptive"_ for grid layout management like LazyGrid.
  Like LazyGrid, it eliminates dealing with different screen sizes.
- **Simple to use as a part of LazyList**: The GridLayout is not lazy layout. It can be simply placed in lazy layouts.
  If only a portion of the full layout is grid, No need to use LazyGrid with span size for full layout.
- **Efficient for small datasets**: LazyGrid has complex logics for large datasets. But when datasets are small, it can be inefficient.
  The GridLayout is just a simple layout. It can be more efficient for smaller datasets.

## Overview

GridLayout for Compose provides a simple layout composables for building grid UI.
Below list is the core features of this library:

- 2 layout composables (`HorizontalGrid` and `VerticalGrid`) for building grid.
- Cell strategy called `SimpleGridCells` to define how many cells should exist.
- Horizontal and vertical arrangement.

The grid layout composables have simple API to implement grid.
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
