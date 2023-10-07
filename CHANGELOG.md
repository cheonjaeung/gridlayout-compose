# Changelog

## 1.0.0

_2023.10.07_

GridLayout is now stable version.
This update contains removing deprecated and dependency updates.
Grid's features have no changes.

### Removed

- Deprecated `HorizontalGrid` and `VerticalGrid` composable functions are now removed.

### Dependency Updates

Project dependencies are updated.

For Android, the compile SDK version is updated from 33 to 34.

- Kotlin: 1.8.20 -> 1.9.0
- Android Gradle Plugin: 7.3.0 -> 8.1.1
- Compose Compiler Plugin: 1.4.6 -> 1.5.2
- Compose Android: 1.4.3 -> 1.5.0
- Compose Multiplatform: 1.4.1 -> 1.5.1

### Compose Dependencies

Now 1.0.0 version depends on following compose versions.

- Android: Jetpack Compose 1.5.0
- Multiplatform: Compose Multiplatform 1.5.2

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
