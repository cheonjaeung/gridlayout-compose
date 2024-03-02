# GridLayout for Compose

![overview-examples](images/grid-overview-examples.png)

**GridLayout for Compose** is a library for missing grid layout in Jetpack Compose and Compose Multiplatform.
This library helps to implement simple grid UI.

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
