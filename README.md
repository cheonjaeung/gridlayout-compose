# GridLayout for Compose

[![ci-latest-status](https://github.com/cheonjaeung/gridlayout-compose/actions/workflows/ci.yml/badge.svg)](https://github.com/cheonjaeung/gridlayout-compose/actions/workflows/ci.yml)
![maven-central](https://img.shields.io/maven-central/v/com.cheonjaeung.compose.grid/grid)
[![Static Badge](https://img.shields.io/badge/License-Apache%202.0-Green)](https://github.com/cheonjaeung/gridlayout-compose/blob/main/LICENSE.txt)

GridLayout for Compose is a library that provides missing non-lazy grid layout composables for Compose Multiplatform.

![grid-examples](docs/images/grid-overview-examples.png)

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

## Installation

To download this library, add dependency to your gradle:

```kotlin
dependencies {
    implementation("com.cheonjaeung.compose.grid:grid:<version>")
}
```

If you want to download before 2.0.0, use following:

```kotlin
dependencies {
    implementation("io.woong.compose.grid:grid:<version>")
}
```

## Getting Started

### Box Grid

`BoxGrid` is a grid layout composable that allows you to place items in grid format.
Items in `BoxGrid` can be placed in any position.

![boxgrid-layout](docs/images/boxgrid-layout.png)

```kotlin
BoxGrid(
    modifier = Modifier.fillMaxSize(),
    rows = SimpleGridCells.Fixed(3),
    columns = SimpleGridCells.Fixed(3),
) {
    // It will be placed at 0, 0.
    Item()
    // It will be placed at 0, 1.
    Item(modifier = Modifier.row(1))
    // It will be placed at 1, 2.
    Item(modifier = Modifier.position(row = 2, column = 1))
    // It will be placed at 2, 0.
    Item(modifier = Modifier.column(2))
}
```

### Sequential Grid

`VerticalGrid` and `HorizontalGrid` are sequential grid layout composables.
They place items sequentially in a grid format.

![sequentialgrid-layout](docs/images/grid-increasing.png)

```kotlin
HorizontalGrid(
    rows = SimpleGridCells.Fixed(3),
    modifier = Modifier.fillMaxHeight()
) {
    Item() // It will be placed at 0, 0.
    Item() // It will be placed at 0, 1.
    Item() // It will be placed at 0, 2.
    Item() // It will be placed at 1, 0.
}

VerticalGrid(
    columns = SimpleGridCells.Fixed(3),
    modifier = Modifier.fillMaxWidth()
) {
    Item() // It will be placed at 0, 0.
    Item() // It will be placed at 1, 0.
    Item() // It will be placed at 2, 0.
    Item() // It will be placed at 0, 1.
}
```

For more information, please visit [documentation](https://cheonjaeung.github.io/gridlayout-compose/) site.

## Performance

Benchmarks measure first-composition cost across three grid implementations.

> [!NOTE]
> Measured on Galaxy S23 (SM-S911N), Android 16, CPU locked at 3.36 GHz.
>
> The measured library version is 2.7.2

### First Composition Time (Median)

|                             | 9 items        | 30 items       | 60 items       |
|-----------------------------|----------------|----------------|----------------|
| **VerticalGrid (Baseline)** | 33.4 ms        | 33.6 ms        | 41.4 ms        |
| **RowColumn**               | 33.4 ms (+0%)  | 33.4 ms (−1%)  | 41.5 ms (+0%)  |
| **LazyVerticalGrid**        | 46.7 ms (+40%) | 43.8 ms (+30%) | 49.8 ms (+20%) |

### Allocation Count (Median)

|                             | 9 items      | 30 items      | 60 items      |
|-----------------------------|--------------|---------------|---------------|
| **VerticalGrid (Baseline)** | 3,079        | 3,735         | 5,287         |
| **RowColumn**               | 3,499 (1.1×) | 4,512 (1.2×)  | 7,058 (1.3×)  |
| **LazyVerticalGrid**        | 6,682 (2.2×) | 16,522 (4.4×) | 19,125 (3.6×) |

- **VerticalGrid ≈ RowColumn**: No performance overhead over manually written `Row`+`Column` code, meaning the simpler API comes for free.
- **VerticalGrid is faster than LazyVerticalGrid**: 20 ~ 40% faster on first composition for fully-visible grids, meaning this library is more efficient than LazyGrid for simple grids.
- **VerticalGrid allocates less than LazyVerticalGrid**: 2 ~ 4 times fewer allocations than LazyVerticalGrid, meaning lower GC pressure in allocation-sensitive scenarios.

> [!NOTE]
> This benchmark shows only the first composition cost.
> If the dataset is large, `LazyVerticalGrid` is the right choice.

## Building

This project is kotlin multiplatform project.
It's recommended to setup kotlin multiplatform developement environment.

### Samples

This project contains sample app project in the `samples` directory.
You can run sample app both Andorid and iOS (iOS sample app can run only on the macOS).

### Testing

This project has unit tests and snapshot tests.
Run following command to run all tests:

```shell
./gradlew :grid:test
```

To run only snapshot tests, run following command:

```shell
./gradlew :grid:verifyPaparazziDebug
```

If you add or edit snapshot tests, run following command to create new snapshots:

```shell
./gradlew :grid:recordPaparazziDebug
```

If test is failed, you can check what is wrong from diff images.
The diff images are generated in `grid/build/paparazzi/failures/` with `delta-*` name.

### Binary Compatibility

This project runs public binary API compatibility validation on CI.
If you change public APIs, run following command before commit:

```shell
./gradlew apiDump
```

And you should commit api files with your code changes.

## Contributing

Welcome to contribute to this project!
Feel free to create pull requests or issues.

- **Patching**: If you want to change code, make a pull request.
- **Bug Reporting**: If you think something is wrong, make an issue and add the **bug** label.
- **Feature Requesting**: If you want to request some features, make an issue and add the **enhancement** label.
- **Questions**: If you have some questions, make an issue and add the **question** label.

## Changelog

Please see [changelog](./CHANGELOG.md) file.

## License

```
Copyright 2023 Cheon Jaeung

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

GridLayout for Compose is licensed under Apache License 2.0. See [license file](./LICENSE.txt) for more details.
