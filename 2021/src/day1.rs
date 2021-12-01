use itertools::{izip, Itertools};
use std::fs;

pub fn part1() -> i32 {
    let raw_input = fs::read_to_string("src/day1_input.txt").expect("error reading file");
    let depths: Vec<i32> = raw_input
        .lines()
        .map(|x| x.trim().parse::<i32>().unwrap())
        .collect();
    let i1 = depths.iter();
    let mut i2 = depths.iter();
    i2.next();
    i1.zip(i2).fold(0, |acc, pair| {
        let (first, second) = pair;
        if second > first {
            acc + 1
        } else {
            acc
        }
    })
}

pub fn part2() -> i32 {
    let raw_input = fs::read_to_string("src/day1_input.txt").expect("error reading file");
    let depths: Vec<i32> = raw_input
        .lines()
        .map(|x| x.trim().parse::<i32>().unwrap())
        .collect();
    let window1 = depths.clone().into_iter().tuple_windows();
    let mut iter = depths.into_iter();
    iter.next();
    let window2 = iter.tuple_windows();
    izip!(window1, window2).fold(0, |acc, windows| {
        let ((a1, a2, a3), (b1, b2, b3)) = windows;
        if (a1 + a2 + a3) < (b1 + b2 + b3) {
            acc + 1
        } else {
            acc
        }
    })
}
