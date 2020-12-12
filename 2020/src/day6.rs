use regex::Regex;
use std::collections::HashSet;
use std::fs;

pub fn part1(path: &str) {
    let input = fs::read_to_string(path).unwrap();
    let re = Regex::new(r"(?m)^\s*$").unwrap();
    let size: usize = re
        .split(&input)
        .map(|group| {
            let mut s: HashSet<char> = HashSet::new();
            s.extend(group.chars().filter(|c| !c.is_ascii_whitespace()));
            s.len()
        })
        .sum();
    println!("{}", size);
}

pub fn part2(path: &str) {
    let input = fs::read_to_string(path).unwrap();
    let re = Regex::new(r"(?m)^\s*$").unwrap();
    let size: usize = re
        .split(&input)
        .map(str::trim)
        .map(|group| {
            let mut person_itr = group.split('\n').map(|person| {
                let mut s: HashSet<char> = HashSet::new();
                s.extend(person.chars().filter(|c| !c.is_ascii_whitespace()));
                s
            });
            let mut acc = person_itr.next().unwrap();
            for person in person_itr {
                acc = acc.intersection(&person).map(|c| *c).collect();
            }
            acc.len()
        })
        .sum();
    println!("{}", size);
}
