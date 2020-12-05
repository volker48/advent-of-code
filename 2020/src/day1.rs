use itertools::Itertools;

pub fn part1() -> (i32, i32) {
    let raw_input = include_str!("day1_input.txt");
    let expenses: Vec<i32> = raw_input
        .lines()
        .map(|x| x.trim().parse::<i32>().unwrap())
        .collect();
    let result = expenses
        .into_iter()
        .tuple_combinations()
        .map(|(x, y)| (x + y, x, y))
        .find(|(sum, _, _)| *sum == 2020)
        .unwrap();
    (result.1, result.2)
}

pub fn part2() -> (i32, i32, i32) {
    let raw_input = include_str!("day1_input.txt");
    let expenses: Vec<i32> = raw_input
        .lines()
        .map(|x| x.trim().parse::<i32>().unwrap())
        .collect();
    let result = expenses
        .into_iter()
        .tuple_combinations()
        .map(|(x, y, z)| (x + y + z, x, y, z))
        .find(|(sum, _, _, _)| *sum == 2020)
        .unwrap();
    (result.1, result.2, result.3)
}

pub fn blah() -> Option<i32> {
    let raw_input = include_str!("day1_input.txt");
    for (x, y) in raw_input
        .lines()
        .map(|x| x.trim().parse::<i32>().unwrap())
        .tuple_combinations()
    {
        let sum = x + y;
        if sum == 2020 {
            return Some(x * y);
        }
    }
    return None;
}
