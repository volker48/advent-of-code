# %%
from dataclasses import dataclass
from collections import deque

stacks = {
    1: deque("RNFVLJSM"),
    2: deque("PNDZFJWH"),
    3: deque("WRCDG"),
    4: deque("NBS"),
    5: deque("MZWPCBFN"),
    6: deque("PRMW"),
    7: deque("RTNGLSW"),
    8: deque("QTHFNBV"),
    9: deque("LMHZNF"),
}
# %%
@dataclass
class Op:
    qty: int
    src: int
    dest: int

    def execute(self, stacks: dict[int, deque[str]]):
        intermediate = deque()
        for _ in range(self.qty):
            item = stacks[self.src].pop()
            intermediate.append(item)
        while intermediate:
            stacks[self.dest].append(intermediate.pop())

def parse_line(line: str) -> "Op":
    _, qty, _, src, _, dest = line.split(" ")
    return Op(int(qty), int(src), int(dest))


with open("input.txt", "r") as fp:
    puzzle = fp.read()

ops = [parse_line(line) for line in puzzle.split("\n")]

for op in ops:
    op.execute(stacks)

final = [s[-1] for s in stacks.values()]
# %%
"".join(final)

# %%
