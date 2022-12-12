# %%
from dataclasses import dataclass
from collections import deque


@dataclass
class Instr:
    val: int
    execution_cycle: int


class CPU:
    def __init__(self):
        self.x = 1
        self.cycle = 0
        self.interesting = {20: 0, 60: 0, 100: 0, 140: 0, 180: 0, 220: 0}

    def tick(self):
        self.cycle += 1
        if self.cycle in self.interesting:
            self.interesting[self.cycle] = self.x * self.cycle

    def process(self, instruction: str):
        match instruction.split():
            case ["noop"]:
                self.tick()
            case ["addx", num]:
                val = int(num)
                self.tick()
                self.tick()
                self.x += val

    def __str__(self) -> str:
        return f"CPU<x {self.x} cycle {self.cycle}"

    def __repr__(self) -> str:
        return self.__str__()


with open("input.txt", "r") as fp:
    puzzle = fp.read()

cpu = CPU()

for item in puzzle.split("\n"):
    cpu.process(item)
cpu.interesting


# %%
sum(cpu.interesting.values())
# %%
