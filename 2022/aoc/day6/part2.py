# %%
from collections import deque

with open("input.txt", "r") as fp:
    puzzle = fp.read()
d = deque(puzzle[:14])
recvd_count = 14
if len(set(d)) == 14:
    print(recvd_count)
else:
    for c in puzzle[14:]:
        recvd_count += 1
        d.popleft()
        d.append(c)
        if len(set(d)) == 14:
            print(recvd_count)
            break

# %%
