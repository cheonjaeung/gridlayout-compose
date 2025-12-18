# Changelog

## 2.6.0

_2025.12.18_

This release introduces a new experimental modifier, `fillMaxMainAxisSize`.
It makes the item have the same size to the maximum of the main axis sizes.

## Changed

- `BoxGrid` is now stable API.
- New experimental modifier `fillMaxMainAxisSize` is added.
- All grids now skip item measurement and placement when there is no available space or calculated cell size is invalid.

## 2.5.2

_2025.11.04_

### Changed

- Fix crash when an item composable has infinity constraints size in `HorizontalGrid` or `VerticalGrid`.
- Fix incorrect min/max size validation when creating `OrientationIndependentConstraints`.

## 2.5.1

_2025.11.02_

### Changed

- Optimize measurement performance of `HorizontalGrid` and `VerticalGrid` via reducing object memory allocation.
- Compose dependencies are replaced with Compose Multiplatform plugin.
- `androidx.core` dependency is removed from Android artifact.

## 2.5.0

_2025.10.16_

This release introduces a new cell strategy, `SimpleGridCells.FixedSize`.
`FixedSize` is a cell strategy class that make each cell to have exact size.
This class is now experimental feature.
You should opt-in to use it.

### Changed

- New experimental cell strategy `SimpleGridCells.FixedSize` is added.
- Optimize arrangement performance of `BoxGrid`.
- Fix crash when `minSize` of `Adaptive` grid cells is equal to negative spacing.
- Spanned cell size calculation was incorrect when using `horizontalSpacing` and `verticalSpacing` with spanning in `BoxGrid`.

### Dependencies

Project dependencies are updated.

- Kotlin 2.1.0 -> 2.2.20
- Android Gradle Plugin 8.10.1 -> 8.13.0
- Compose 1.8.0 -> 1.9.0
- Target SDK (Android Only) 35 -> 36

## 2.4.0

_2025.08.01_

This release contains **breaking changes** for the experimental feature, `BoxGrid`.
`BoxGridScope`'s `rowSpan` and `columnSpan` modifier is replaced to `span` modifier.
And also `span` modifier's parameter now receive `BoxGridItemSpanScope` instead of `GridItemSpanScope`.
It contains more information for calculating box grid item span.

### Changed

- Add `Modifier.position` to `BoxGridScope` for setting row/column cell position at the same time.
- Add `Modifier.span` to `BoxGridScope` for setting row/column span at the same time.
- Add `BoxGridItemSpanScope` for `span` modifier of `BoxGridScope`.
- Add `BoxGridItemSpan`. It is a container represents row/column span size.
- Remove `Modifier.rowSpan` and `Modifier.columnSpan` from `BoxGridScope`.
- Remove deprecated `HorizontalGrid` and `VerticalGrid` with `alignment` parameter.
  Replace it to composable with `contentAlignment` parameter.
- Fix remaining cell size distribution of `SimpleGridCell` more properly.

## 2.3.2

### Changed

_2025.07.05_

- Fix `contentAlignment` of `BoxGrid` not working as expected.
- Fix incorrect remaining span calculation of `BoxGrid`.

## 2.3.1

_2025.06.02_

### Changed

- Fix `BoxGrid` crashing when applying span size to items with unspecified row/column position.

## 2.3.0

_2025.05.31_

### Changed

- New experimental layout composable `BoxGrid` is added.
  `BoxGrid` is a layout that allows to place items freely in grid cells.
  This layout is now experimental. Use it with `@ExperimentalGridApi` annotation to opt-in.
  For more information, see the documentation.
- `HorizontalGrid` and `VerticalGrid`'s default alignment parameter `alignment` is renamed to `contentAlignment`.
- Deprecated API `GridScope.span(Int)` is now removed. Use `GridScope.span(Lambda)` instead.

### Dependencies

Project dependencies are updated.

- JVM Target 8 -> 11
- Kotlin 1.9.25 -> 2.1.0
- Android Gradle Plugin 8.6.1 -> 8.10.1
- Compose 1.7.0 -> 1.8.0
- Target SDK (Android Only) 34 -> 35

## 2.2.1

_2025.02.08_

### Changed

- `Modifier.span` with single integer parameter is deprecated. Replace it to modifier with lambda parameter.
- Modifiers of `GridScope` now have inspector info.

## 2.2.0

_2024.12.28_

### Changed

- `HorizontalGrid` and `VerticalGrid` now have default alignment parameter.

## 2.1.0

_2024.10.19_

### Changed

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
