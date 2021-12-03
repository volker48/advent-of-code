use crate::helpers;

pub fn part1() -> i32 {
    struct Position {
        x: i32,
        y: i32,
    }
    impl Position {
        fn forward(self, magnitude: i32) -> Position {
            Position {
                x: self.x + magnitude,
                ..self
            }
        }
        fn backwards(self, magnitude: i32) -> Position {
            Position {
                x: self.x - magnitude,
                ..self
            }
        }
        fn up(self, magnitude: i32) -> Position {
            Position {
                y: self.y - magnitude,
                ..self
            }
        }
        fn down(self, magnitude: i32) -> Position {
            Position {
                y: self.y + magnitude,
                ..self
            }
        }
    }
    let lines = helpers::get_input("src/day2_input.txt");
    let final_position = lines.iter().map(|line| line.split_whitespace()).fold(
        Position { x: 0, y: 0 },
        |acc: Position, mut itr| {
            let direction = itr.next().unwrap();
            let magnitude = itr.next().unwrap().parse::<i32>().unwrap();
            match direction {
                "forward" => acc.forward(magnitude),
                "up" => acc.up(magnitude),
                "down" => acc.down(magnitude),
                _ => panic!("unhandled pattern!"),
            }
        },
    );
    final_position.x * final_position.y
}

pub fn part2() -> i32 {
    struct Position {
        horizontal: i32,
        depth: i32,
        aim: i32,
    }
    impl Position {
        fn forward(self, magnitude: i32) -> Position {
            Position {
                horizontal: self.horizontal + magnitude,
                depth: self.depth + (self.aim * magnitude),
                ..self
            }
        }
        fn up(self, magnitude: i32) -> Position {
            Position {
                aim: self.aim - magnitude,
                ..self
            }
        }
        fn down(self, magnitude: i32) -> Position {
            Position {
                aim: self.aim + magnitude,
                ..self
            }
        }
    }
    let lines = helpers::get_input("src/day2_input.txt");
    let final_position = lines.iter().map(|line| line.split_whitespace()).fold(
        Position {
            horizontal: 0,
            depth: 0,
            aim: 0,
        },
        |acc: Position, mut itr| {
            let direction = itr.next().unwrap();
            let magnitude = itr.next().unwrap().parse::<i32>().unwrap();
            match direction {
                "forward" => acc.forward(magnitude),
                "up" => acc.up(magnitude),
                "down" => acc.down(magnitude),
                _ => panic!("unhandled pattern!"),
            }
        },
    );
    final_position.horizontal * final_position.depth
}
