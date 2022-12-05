# %%
with open("input.txt") as fp:
    data = fp.read()
elves = data.split("\n\n")
elves

# %%
def elf_data_to_ints(data: list[str]) -> list[list[int]]:
    split = [s.split("\n") for s in data]
    return [[int(cal.strip()) for cal in elf if cal] for elf in split]


# %%
elf_totals = [sum(elf) for elf in elf_data_to_ints(elves)]
elf_totals
# %%
part1 = max(elf_totals)

# %%
elf_totals.sort()
part2 = sum(elf_totals[-3:])
part2

# %%
