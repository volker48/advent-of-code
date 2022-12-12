# %%
from collections import deque
with open("input.txt", "r") as fp:
    puzzle = fp.read()
d = deque(puzzle[:4])
recvd_count = 4
if len(set(d)) == 4:
    print("4")
else:
    for c in puzzle[4:]:
        recvd_count += 1
        d.popleft()
        d.append(c)
        if len(set(d)) == 4:
            print(recvd_count)
            break

# %%

