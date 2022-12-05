# %%
from typing import Tuple
from dataclasses import dataclass

# %%
@dataclass
class Range:
    start: int
    end: int

    @classmethod
    def parse_str(cls, s: str) -> "Range":
        start, end = s.split("-")
        return Range(int(start), int(end))

    def fully_contains(self, other: "Range") -> bool:
        return other.start >= self.start and other.end <= self.end

    def partial_contains(self, other: "Range") -> bool:
        if self.fully_contains(other):
            return True
        return self.start <= other.start <= self.end or self.start <= other.end <= self.end


def line_to_ranges(line: str) -> Tuple[Range, Range]:
    r1, r2 = line.split(",")
    return Range.parse_str(r1), Range.parse_str(r2)


def either_contains(r1: Range, r2: Range) -> bool:
    return r1.fully_contains(r2) or r2.fully_contains(r1)


def either_partial_contains(r1: Range, r2: Range) -> bool:
    return r1.partial_contains(r2) or r2.partial_contains(r1)


with open("input.txt", "r") as fp:
    puzzle = fp.read()


lines = puzzle.split("\n")
ranges = [line_to_ranges(line) for line in lines]
fully_contains = [either_contains(*pair) for pair in ranges]

# %%
part1 = sum(fully_contains)
print(f"part1 {part1}")
# %%
partial_contains = [either_partial_contains(*pair) for pair in ranges]
part2 = sum(partial_contains)
print(f"part2 {part2}")

# %%
