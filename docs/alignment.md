# Alignment

The `content` lambda of `HorizontalGrid` and `VerticalGrid` is `GridScope`.
In the `GridScope`, users can apply `align` modifier to item composable.

The `align` modifier set the item composable's alignment in the cell.
`align` takes an `Alignment` parameter likes `BoxScope`'s one.
For example, you can set an item composable at the bottom-center of the cell with following code:

```Kotlin
VerticalGrid(
    columns = SimpleGridCells.Fixed(2),
    modifier = Modifier.fillMaxSize()
) {
    Item(Modifier.size(100.dp)
    
    Item(
        modifier = Modifier
            .size(50.dp)
            .align(Alignment.BottomCenter)
    )
    
    Item(Modifier.size(100.dp)
}
```

![alignment](images/alignment-graphic.png)
