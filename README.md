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
    implementation("io.woong.compose.grid:grid:${version}")
}
```

## Usage

![usage-example](docs/images/overview-grid.png)

```kotlin
@Composable
fun ExampleGrid(
    colors: List<Color>,
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    VerticalGrid(
        columns = SimpleGridCells.Fixed(3),
        modifier = modifier,
    ) {
        for ((index, color) in colors.withIndex()) {
            ColorBox(
                modifier = Modifier,
                color = color,
                text = (index + 1).toString(),
            )
        }
    }
}
```

The `VerticalGrid` and `HorizontalGrid` composables places items in multiple rows and columns.
A `VerticalGrid` displays items in vertically across multiple columns.
On the other hand, A `HorizontalGrid` displays items in horizonatlly across multiple rows.

### SimpleGridCells

The `VerticalGrid` and `HorizontalGrid` composables take a class called `SimpleGridCells`.
The `SimpleGridCells` defines how many cells should exist.

There are 2 options for `SimpleGridCells`, `Fixed` and `Adaptive`.

`Fixed` is for exact count of rows or columns.
If you set `Fixed(3)` for `VerticalGrid`, the grid will have exact 3 columns.

`Adaptive` is for flexible count of rows or columns.
An `Adaptive` class have `minSize` parameter that is the minimum size of each rows or columns.
If you set `Adaptive(80.dp)` for `VerticalGrid`, the grid will have as many columns as possible.
When the grid have 300dp width, there will be 3 columns and 100dp width each.
But when the grid have 340dp width, there will be 4 columns and width will be 85dp.

The `Adaptive` is useful to support multiple device sizes like bar style phone and foldable or phone and tablet.
But if UI requires fixed count of row or columns, `Fixed` will be useful.

### Arrangement

The grid layouts have `horizontalArrangement` and `verticalArrangement` parameter that controls the way to place children and determine space between children.
The default horizontal arrangement is `Arrangement.Start` and the default vertical arrangement is `Arrangement.Top`.

## Sample App

This project contains sample app for Android and Desktop platform. You can see how the grid layout
actually works with options. Checkout `sample-android` and `sample-desktop` directory.

## License

Compose Grid is licensed under Apache License 2.0. See [license file](./LICENSE.txt) for more details.
