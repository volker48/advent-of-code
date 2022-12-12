from dataclasses import dataclass
from enum import Enum
# %%
puzzle = """$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k"""

class Op(Enum):
    LS = "ls"
    CD = "cd"

@dataclass
class Cmd:
    op: Op
    arg: str | None = None


def parse_line(line: str):
    match line.split():
        case ["$", *rest]:
            return None
        case ["dir", dirname]:
            pass
        case []