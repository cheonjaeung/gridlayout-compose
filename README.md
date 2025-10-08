# GridLayout for Compose

[![ci-latest-status](https://github.com/cheonjaeung/gridlayout-compose/actions/workflows/ci.yml/badge.svg)](https://github.com/cheonjaeung/gridlayout-compose/actions/workflows/ci.yml)
![maven-central](https://img.shields.io/maven-central/v/com.cheonjaeung.compose.grid/grid)
[![Static Badge](https://img.shields.io/badge/License-Apache%202.0-Green)](https://github.com/cheonjaeung/gridlayout-compose/blob/main/LICENSE.txt)

GridLayout for Compose is a library that provides missing non lazy grid layout composables for Compose Multiplatform.

![grid-examples](docs/images/grid-overview-examples.png)

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

## Installation

To download this library, add dependency to your gradle:

```groovy
dependencies {
    implementation("com.cheonjaeung.compose.grid:grid:<version>")
}
```

**After the 2.0.0 version, the group id and package name is changed from**
**`io.woong.compose.grid` to `com.cheonjaeung.compose.grid`.**

## Usage

![usage-example](docs/images/usage-example.png)

```kotlin
VerticalGrid(
    columns = SimpleGridCells.Fixed(3),
    modifier = Modifier.fillMaxWidth(),
) {
    for ((index, color) in colors.withIndex()) {
        ColorBox(
            modifier = Modifier,
            color = color,
            text = (index + 1).toString(),
        )
    }
}
```

For more information, please visit [documentation](https://cheonjaeung.github.io/gridlayout-compose/) site.

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
