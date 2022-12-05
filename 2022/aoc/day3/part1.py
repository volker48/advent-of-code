# %%
from typing import Tuple

# %%
def split_rucksack(sack: str) -> Tuple[str, str]:
    half = len(sack) // 2
    return sack[:half], sack[half:]


def find_common_item(sack1: str, sack2: str) -> str:
    union = set(sack1) & set(sack2)
    if len(union) != 1:
        raise Exception("too many items in common")
    return union.pop()


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
sacks = puzzle.split("\n")
split_sacks = [split_rucksack(sack) for sack in sacks]
# %%
common_items = [find_common_item(*sacks) for sacks in split_sacks]
common_items
# %%
priorities = [get_priority(item) for item in common_items]
priorities
# %%
sum(priorities)

# %%
