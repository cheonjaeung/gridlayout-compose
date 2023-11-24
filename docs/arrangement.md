# Arrangement

`HorizontalGrid` and `VerticalGrid` have arrangement parameters called `horizontalArrangement` and `verticalArrangement`.
`Arrangement` determines how the grid cells are arranged or the spacing between cells.
`horizontalArrangement` arrange the cells in the horizontal axis direction, and `verticalArrangement` arrange in vertical axis direction.

GridLayout supports all of built in `Arrangement` types, `Start`, `Center`, `End`, `Top`, `Bottom`,
`SpaceAround`, `SpaceEvenly`, `SpaceBetween`, `spacedBy` and all other types including custom `Arrangement`.

## Basic Arrangements

Following graphics show differences of arrangement types.
Graphics are based on LTR (Left to Right) direction.
If the layout direction is RTL (Right to Left), the `Arrangement.Start` and `Arrangement.End` will be reversed.

### Start

![arrangement-start](images/arrangement-start-top-graphic.png)

`Arrangement.Start` is available for `horizontalArrangement`.
It places cells to the beginning of the horizontal axis.

### Top

![arrangement-top](images/arrangement-start-top-graphic.png)

`Arrangement.Top` is available for `verticalArrangement`.
It places cells to the top of the vertical axis.

### Center

![arrangement-center](images/arrangement-center-graphic.png)

`Arrangement.Center` is available for both `horizontalArrangement` and `verticalArrangement`.
It places cells to the middle of axis.

### End

![arrangement-end](images/arrangement-end-graphic.png)

`Arrangement.End` is available for `horizontalArrangement`.
It places cells to the end of horizontal axis.

### Bottom

![arrangement-bottom](images/arrangement-bottom-graphic.png)

`Arrangement.End` is available for `verticalArrangement`.
It places cells to the bottom of vertical axis.

## Spacing Between Cells

Following graphics show some arrangement types for spacing between cells.

### SpaceAround

![arrangement-spacearound](images/arrangement-spacearound-graphic.png)

`Arrangement.SpaceAround` is available for both `horizontalArrangement` and `verticalArrangement`.
It places cells with spacing.
The spacing between two cells have same amount.
But the spacing before the first cell and after the last cell have a half of spacing between two cells.

### SpaceBetween

![arrangement-spacebetween](images/arrangement-spacebetween-graphic.png)

`Arrangement.SpaceBetween` is available for both `horizontalArrangement` and `verticalArrangement`.
It places cells with same spacing excluding before the first cell and after the last cell.

### SpaceEvenly

![arrangement-spaceevenly](images/arrangement-spaceevenly-graphic.png)

`Arrangement.SpaceEvenly` is available for both `horizontalArrangement` and `verticalArrangement`.
It places cells with spacing.
It places cells with same spacing including before the first cell and after the last cell.

### SpacedBy

![arrangement-spacedby](images/arrangement-spacedby-graphic.png)

`Arrangement.spacedBy()` is available for both `horizontalArrangement` and `verticalArrangement`.
It places cells with fixed amount of spacing.
The spacing is not applied to the before the first cell and after the last cell.
