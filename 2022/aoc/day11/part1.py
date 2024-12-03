# %%
from typing import Callable
from collections import deque


class Monkey:
    def __init__(
        self,
        monkey_id: int,
        items: list[int],
        op: Callable[[int], int],
        test: Callable[[int], bool],
        true_monkey: int,
        false_monkey: int,
        monkeys: dict[int, "Monkey"],
    ):
        self.monkey_id = monkey_id
        self.items = deque(items)
        self.op = op
        self.test = test
        self.true_monkey = true_monkey
        self.false_monkey = false_monkey
        self.monkeys = monkeys
        self.inspected = 0

    def catch(self, item):
        self.items.append(item)

    def turn(self):
        while self.items:
            self.inspected += 1
            item = self.items.popleft()
            new = self.op(item)
            # new //= 3
            if self.test(new):
                self.monkeys[self.true_monkey].catch(new)
            else:
                self.monkeys[self.false_monkey].catch(new)

    def __repr__(self):
        return f"Monkey {self.monkey_id} inspected: {self.inspected}: {self.items}"


# %%
monkeys: dict[int, Monkey] = {}
monkeys[0] = Monkey(0, [74, 73, 57, 77, 74], lambda x: x * 11, lambda x: x % 19 == 0, 6, 7, monkeys)
monkeys[1] = Monkey(1, [99, 77, 79], lambda x: x + 8, lambda x: x % 2 == 0, 6, 0, monkeys)
monkeys[2] = Monkey(2, [64, 67, 50, 96, 89, 82, 82], lambda x: x + 1, lambda x: x % 3 == 0, 5, 3, monkeys)
monkeys[3] = Monkey(3, [88], lambda x: x * 7, lambda x: x % 17 == 0, 5, 4, monkeys)
monkeys[4] = Monkey(4, [80, 66, 98, 83, 70, 63, 57, 66], lambda x: x + 4, lambda x: x % 13 == 0, 0, 1, monkeys)
monkeys[5] = Monkey(5, [81, 93, 90, 61, 62, 64], lambda x: x + 7, lambda x: x % 7 == 0, 1, 4, monkeys)
monkeys[6] = Monkey(6, [69, 97, 88, 93], lambda x: x * x, lambda x: x % 5 == 0, 7, 2, monkeys)
monkeys[7] = Monkey(7, [59, 80], lambda x: x + 6, lambda x: x % 11 == 0, 2, 3, monkeys)

# %%
for _ in range(10000):
    for monkey in monkeys.values():
        monkey.turn()

monkeys

# %%
sorted(monkeys.values(), key=lambda monkey: monkey.inspected)[-2:]

# %%
258 * 271

# %%
