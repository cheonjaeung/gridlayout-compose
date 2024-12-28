# Changelog

## 2.2.0

_2024_12_28_

## Changed

- `HorizontalGrid` and `VerticalGrid` now have default alignment parameter.

## 2.1.0

_2024.10.19_

## Changed

- New `Modifier.span` is added that has a lambda parameter to calculate span size.
In this lambda, you can access to `GridItemSpanScope` to get max span and remaining span.

### Dependencies

Project dependencies are updated.

- Kotlin: 1.9.22 -> 1.9.25
- Android Gradle Plugin: 8.2.0 -> 8.6.1
- Compose Multiplatform: 1.6.0 -> 1.7.0
- Compose Android: 1.6.0 -> 1.7.0
- Compose Android Compiler Plugin: 1.5.10 -> 1.5.15

## 2.0.0

_2024.04.13_

**Library's group id and package name is changed from `io.woong.compose.grid` to `com.cheonjaeung.compose.grid`.**

## 1.2.2

_2024.03.01_

### Dependencies

Project dependencies are updated.

- Kotlin: 1.9.0 -> 1.9.22
- Android Gradle Plugin: 8.1.1 -> 8.2.0
- Compose Compiler Plugin: 1.5.2 -> 1.5.10
- Compose Android: 1.5.0 -> 1.6.0
- Compose Multiplatform: 1.5.1 -> 1.6.0

## 1.2.1

_2024.02.13_

### Fixed

- Fixed incorrect layout size when grid should have smaller size than constraints max size.
- Fixed incorrect spacing before the last item.
- Fixed crash when measuring invisible item composable constraints.

## 1.2.0

_2024.01.06_

### Added

- Start supporting Compose Multiplatform for iOS.
- New `align` modifier is added on `GridScope` to set alignment of specific item composable in the cell.
  The `align` modifier takes an `Alignement` parameter like `BoxScope`'s `align`.

### Improved

- Performance for item composable measuring and placing is improved.

### Changed

- Artifact names are changed. If you use library dependency for desktop platform with
  `io.woong.compose.grid:grid-desktop`, you must change artifact name to `io.woong.compose.grid:grid-jvm`
  or just `io.woong.compose.grid:grid`. If you already add dependency as `io.woong.compose.grid:grid`,
  you can ignore it.

### Compose Dependencies

There is no dependency changes since previous version.

- Android: Jetpack Compose 1.5.0
- Multiplatform: Compose Multiplatform 1.5.2

## 1.1.0

_2023.12.09_

### Added

- `content` lambda of grid now extends `GridScope`.
- New `span` modifier is added. `span` is can applied to item composable in the `GridScope`.
- New optional parameter `fill` is added for `SimpleGridCells.Fixed` and `SimpleGridCells.Adaptive`.

### Compose Dependencies

There is no dependency changes since previous version.

- Android: Jetpack Compose 1.5.0
- Multiplatform: Compose Multiplatform 1.5.2

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
