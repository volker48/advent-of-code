use std::fs;

use itertools::Itertools;

pub fn get_input(path: &str) -> Vec<String> {
    let text = fs::read_to_string(path).expect("error reading file");
    text.lines()
        .map(|line| line.trim())
        .map(str::to_string)
        .collect()
}
