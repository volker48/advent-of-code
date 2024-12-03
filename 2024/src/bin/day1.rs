use aoc2024::get_lines;
use std::cmp::Reverse;
use std::collections::BinaryHeap;
use std::fs::File;
use std::io::Error;

fn main() -> Result<(), Error> {
    let mut heap_left = BinaryHeap::new();
    let mut heap_right = BinaryHeap::new();
    let input = File::open("input/day1.txt")?;
    let lines = get_lines(input);
    lines
        .map(|line| {
            let mut split = line.split_whitespace();
            (
                split.next().unwrap().parse::<usize>().unwrap(),
                split.next().unwrap().parse::<usize>().unwrap(),
            )
        })
        .for_each(|(left, right)| {
            heap_left.push(Reverse(left));
            heap_right.push(Reverse(right));
        });

    let mut total_distance = 0;
    assert_eq!(heap_left.len(), heap_right.len());
    for _ in 0..heap_left.len() {
        let left_val = heap_left.pop().unwrap();
        let right_val = heap_right.pop().unwrap();

        total_distance += left_val.0.abs_diff(right_val.0);
    }

    println!("total distance {}", total_distance);
    Ok(())
}
