# Changelog

## 0.2.0

_2023.08.15_

This update include a new type of cell count strategy.
The new cell count strategy is for similar API to Jetpack Compose Foundation's lazy grid.
Therefore, old grid layout composables is deprecated and added new composables.
Please migrate layout to new API.

### Added

- New `SimpleGridCells` class that defines how many cells should exist.
- New `HorizontalGrid` and `VerticalGrid` composables take `SimpleGridCells` parameter.

### Deprecated

- `rowCount` and `columnCount` parameter of `HorizontalGrid` and `VerticalGrid` is deprecated.
These deprecated composables can be remove in the future version.
Migrate to grid composable with `row` and `columns`.

### Compose Dependencies

This library is built on these Compose libraries.

- Android: Jetpack Compose 1.4.3
- Multiplatform: Compose Multiplatform 1.4.1

## 0.1.0

_2023.07.22_

Initial public release of this library.

### Compose Dependencies

This library is built on these Compose libraries.

- Android: Jetpack Compose 1.4.3
- Multiplatform: Compose Multiplatform 1.4.1
