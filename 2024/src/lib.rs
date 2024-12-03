use std::fs::File;
use std::io::{BufRead, BufReader};

pub fn get_lines(file: File) -> impl Iterator<Item = std::string::String> {
    BufReader::new(file).lines().map(|l| l.unwrap())
}
