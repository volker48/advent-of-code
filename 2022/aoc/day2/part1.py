# %%
def round_score(opponent: str, mine: str) -> int:
    total_score = shape_score(mine) + outcome_score(opponent, mine)
    return total_score


def outcome_score(opponent: str, mine: str) -> int:
    match (opponent, mine):
        case ("A", "Y"):
            return 6
        case ("B", "Z"):
            return 6
        case ("C", "X"):
            return 6
        case ("A", "X"):
            return 3
        case ("B", "Y"):
            return 3
        case ("C", "Z"):
            return 3
        case _:
            return 0


def choose_shape(opponent: str, strat: str) -> str:
    match strat:
        case "X":
            match opponent:
                case "A":
                    return "Z"
                case "B":
                    return "X"
                case "C":
                    return "Y"
                case _:
                    raise Exception()
        case "Y":
            match opponent:
                case "A":
                    return "X"
                case "B":
                    return "Y"
                case "C":
                    return "Z"
                case _:
                    raise Exception()
        case "Z":
            match opponent:
                case "A":
                    return "Y"
                case "B":
                    return "Z"
                case "C":
                    return "X"
                case _:
                    raise Exception()
        case _:
            raise Exception()


def shape_score(shape: str) -> int:
    match shape:
        case "X":
            return 1
        case "Y":
            return 2
        case "Z":
            return 3
        case _:
            raise Exception("unknown shape " + shape)


# %%
with open("input.txt") as fp:
    puzzle = fp.read()

rounds = puzzle.split("\n")
rounds
# %%
round_scores = [round_score(opponent, mine) for opponent, mine in [round.split(" ") for round in rounds]]
round_scores

# %%
part1 = sum(round_scores)

# %%
rounds_fixed = [(opponent, choose_shape(opponent, strat)) for opponent, strat in [round.split(" ") for round in rounds]]
scores = [round_score(opponent, mine) for opponent, mine in rounds_fixed]
part2 = sum(scores)
print(f"part2 {part2}")
