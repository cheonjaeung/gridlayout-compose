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
- **Flexible item placement**: This library provide `BoxGrid` layout to allow placing items at the specific row and column position.

## Features

- **Non-Lazy Grid Layouts**: Lightweight grid layouts optimized for small datasets, avoiding the overhead of lazy-loading logic.
  - **Box Grid**: Place items at specific row and column positions.
  - **Sequential Grids**: `VerticalGrid` and `HorizontalGrid` for simple sequential item placement.
  - **Cell Management Support**: Support for standard `Fixed`, `Adaptive`, and `FixedSize` cell management.
  - **Spanning**: Allow items to span across multiple rows or columns in both Box and Sequential grids.
  - **RTL Support**: Full support for Right-to-Left layout directions.
- **Extended Cell Management**: Extended cell management options for more advanced use cases. Available for both non-lazy and lazy grids.
  - **Responsive**: Dynamically switch cell management strategy based on available layout size.
  - **Track**: Define each row or column's size individually using fixed and weighted sizes.
- **Multiplatform**: Compatible with Compose Multiplatform.

## Overview

GridLayout for Compose provides a simple layout composables for building grid UI.

This library provides 2 types of grid, **Box Grid** and **Sequential Grid**.

`BoxGrid` is a grid layout that allows you to place items in grid format.
Items in `BoxGrid` can be placed in any position, and multiple items can placed in the same cell like `Box` composable.

Sequential grid is a layout that arranges items in a grid format, either horizontally or vertically.
There are 2 composables for each direction: `HorizontalGrid` and `VerticalGrid`.
Its API is designed to be familiar to anyone who has used `LazyGrid`.

## License

This library is licensed under the Apache License, Version 2.0.
