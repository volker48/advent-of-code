struct Cell {
    value: i32,
    marked: bool,
}
struct Board {
    rows: Vec<Cell>,
    columns: Vec<Cell>,
}

impl Cell {
    pub fn new(value: i32) -> Cell {
        Cell {
            value,
            marked: false,
        }
    }
}

pub fn part1() -> () {}
