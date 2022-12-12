# %%
# puzzle = """30373
# 25512
# 65332
# 33549
# 35390"""
with open("input.txt", "r") as fp:
    puzzle = fp.read()

ints = [list(map(int, list(line))) for line in puzzle.split("\n")]
ints
# %%
# %%
def on_edge(max_rows: int, max_cols: int, row: int, col: int) -> bool:
    if row == 0:
        return True
    if col == 0:
        return True
    if row == max_rows - 1:
        return True
    if col == max_cols - 1:
        return True
    return False


def is_visible(row: int, col: int, data: list[list[int]]) -> bool:
    if on_edge(len(data), len(data[0]), row, col):
        return True
    tree_height = data[row][col]
    max_right = max(data[row][col + 1 :])
    max_left = max(data[row][:col])
    return (
        tree_height > max_above(row, col, data)
        or tree_height > max_below(row, col, data)
        or tree_height > max_left
        or tree_height > max_right
    )


def max_above(row: int, col: int, data: list[list[int]]) -> int:
    trees = []
    for i in range(row - 1, -1, -1):
        tree = data[i][col]
        trees.append(tree)
    return max(trees)


def max_below(row: int, col: int, data: list[list[int]]) -> int:
    trees = []
    for i in range(row + 1, len(data), 1):
        tree = data[i][col]
        trees.append(tree)
    return max(trees)


visible = 0
for row in range(len(ints)):
    for col in range(len(ints[0])):
        if is_visible(row, col, ints):
            visible += 1
visible

# %%
