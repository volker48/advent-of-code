use std::collections::HashMap;
use std::fs::File;
use std::io::Error;

use aoc2024::get_lines;

fn main() -> Result<(), Error> {
    let mut right_values = HashMap::new();
    let mut left_values = Vec::new();
    let input = File::open("input/day1.txt")?;
    get_lines(input)
        .map(|line| {
            let mut split = line.split_whitespace();
            (
                split.next().unwrap().parse::<usize>().unwrap(),
                split.next().unwrap().parse::<usize>().unwrap(),
            )
        })
        .for_each(|(left, right)| {
            left_values.push(left);
            *right_values.entry(right).or_insert(0) += 1;
        });
    let similarity_score: usize = left_values
        .into_iter()
        .map(|value| value * right_values.get(&value).unwrap_or(&0))
        .sum();

    println!("similarity score {}", similarity_score);
    Ok(())
}
