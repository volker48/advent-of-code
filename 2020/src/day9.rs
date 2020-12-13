use itertools::Itertools;
use std::fs;

pub fn part1(path: &str) -> usize {
    let input = fs::read_to_string(path).unwrap();
    let input_iter = input
        .split('\n')
        .map(str::trim)
        .map(|line| line.parse::<usize>().unwrap());
    let mut preamble: Vec<usize> = input_iter.clone().take(25).collect();
    for item in input_iter.skip(25) {
        let sums: Vec<usize> = preamble
            .iter()
            .tuple_combinations()
            .map(|(a, b)| a + b)
            .collect();
        if !sums.contains(&item) {
            return item;
        }
        preamble.push(item);
        preamble = preamble[1..].to_vec();
    }
    unreachable!()
}

pub fn part2(path: &str) -> usize {
    let target: usize = 41682220;
    let input = fs::read_to_string(path).unwrap();
    let nums: Vec<usize> = input
        .split('\n')
        .map(str::trim)
        .filter(|s| *s != "")
        .map(|line| line.parse::<usize>().unwrap())
        .collect();
    for i in 0..nums.len() {
        let mut sum: usize = nums[i];
        for j in i + 1..nums.len() {
            sum += nums[j];
            if sum == target {
                return nums[i..j].iter().min().unwrap() + nums[i..j].iter().max().unwrap();
            }
        }
    }
    unreachable!();
}
