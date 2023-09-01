<h1 align="center">Grid Layout for Compose</h1>
<p align="center"><i>Missing grid layout for Jetpack Compose and Compose Multiplatform</i></p>

GridLayout for Compose is a library that provides simple grid layout composables for Jetpack Compose and Compose Multiplatform.

Jetpack Compose doesn't contains non-lazy grid layout, there are only lazy grid layout.
There are some alternatives, combining rows and colums or flow layout.
But these alternatives are not for just grid and they need size or position calculating.
This library provides grid layout with similar API to lazy grid (but for non-lazy).
It helps to implement simple grid UI like implementing lazy grid.

## Installation

To download this library, add dependency to your gradle:

```groovy
dependencies {
    implementation("io.woong.compose.grid:grid:<version>")
}
```

## Usage

![usage-example](docs/images/overview-grid.png)

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

For more information, please visit [documentation](https://cheonjaewoong.github.io/gridlayout-compose/) site.

## Changelog

Please see [changelog](./CHANGELOG.md) file.

## License

GridLayout for Compose is licensed under Apache License 2.0. See [license file](./LICENSE.txt) for more details.
