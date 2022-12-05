# %%
from typing import Tuple

# %%


def find_common_item(sack1: str, sack2: str, sack3: str) -> str:
    intersection = set(sack1) & set(sack2) & set(sack3)
    if len(intersection) != 1:
        raise Exception("too many items in common")
    return intersection.pop()


# %%
def get_priority(item: str) -> int:
    val = ord(item)
    if val > 90:
        return val - ord("a") + 1
    else:
        return val - ord("A") + 27


# %%
with open("input.txt", "r") as fp:
    puzzle = fp.read()
split = puzzle.split("\n")
end = len(split)
end
# %%
indices = zip(range(0, end, 3), range(3, end + 3, 3))
# %%
groups = [split[i:j] for i, j in indices]
common = [find_common_item(*group) for group in groups]
priorities = [get_priority(item) for item in common]
sum(priorities)

# %%
