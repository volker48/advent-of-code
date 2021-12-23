use std::fs;

struct Board {
    grid: Vec<Vec<i32>>,
}

pub fn part1() -> () {
    let text = fs::read_to_string("src/day3_test.txt").expect("error reading file");
    let draw: Vec<i32> = text
        .lines()
        .next()
        .unwrap()
        .split(",")
        .map(|s| s.parse().unwrap())
        .collect();
    println!("{:#?}", text.split("\n\n").collect::<Vec<&str>>());
    // let raw_boards = text
    //     .lines()
    //     .skip(2)
    //     .fold(String::new(), |acc, val| {
    //         acc.push_str(val);
    //         acc
    //     })
    //     .collect::<String>();
}
impl Board {
    fn new(
        row1: Vec<i32>,
        row2: Vec<i32>,
        row3: Vec<i32>,
        row4: Vec<i32>,
        row5: Vec<i32>,
    ) -> Board {
        Board {
            grid: vec![row1, row2, row3, row4, row5],
        }
    }
}
