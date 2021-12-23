# AOC 2021 solutions

## Day 21

Had a bug here where my code for handling the wrap around case was incorrect. For some
reason I was resetting position to 1 after doing % 10 if the result was 0. This should
have been 10 instead.
