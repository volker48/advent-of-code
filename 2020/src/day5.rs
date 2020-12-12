use std::fs;
struct Seat {
    row: usize,
    column: usize,
}

impl Seat {
    fn seat_id(&self) -> usize {
        8 * self.row + self.column
    }
}

pub fn get_seat(rules: &str) -> usize {
    let row_rules = &rules[..7];
    let col_rules = &rules[7..];
    let row_seats: &Vec<usize> = &(0..128).collect();
    let col_seats: &Vec<usize> = &(0..8).collect();
    fn go(rules: &str, seats: &[usize]) -> usize {
        let l = seats.len() / 2;
        let new_seats = match &rules[0..1] {
            "F" | "L" => &seats[..l],
            "B" | "R" => &seats[l..],
            _ => unreachable!(),
        };
        return match new_seats {
            [single] => *single,
            _ => go(&rules[1..], new_seats),
        };
    }
    let row = go(row_rules, row_seats);
    let col = go(col_rules, col_seats);
    let seat = Seat {
        row: row,
        column: col,
    };
    seat.seat_id()
}

pub fn part1(path: &str) -> usize {
    let input = fs::read_to_string(path).expect("error reading file");
    input.split_ascii_whitespace().map(get_seat).max().unwrap()
}
