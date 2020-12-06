use regex::Regex;
use std::collections::HashMap;
use std::{fs, str::SplitAsciiWhitespace};

pub fn part1(path: &str) -> usize {
    let required_keys = &vec!["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"];
    let raw_input = fs::read_to_string(path).expect("error reading file");
    let re = Regex::new(r"(?m)^\s*$").expect("invalid regex");
    let mut num_valid = 0;
    for raw_passport in re.split(&raw_input) {
        let raw_fields = raw_passport.trim().split_ascii_whitespace();
        let passport = fields_to_map(raw_fields);
        let valid = required_keys
            .into_iter()
            .all(|key| passport.contains_key(&key.to_string()));
        if valid {
            num_valid += 1;
        }
    }
    return num_valid;
}

fn fields_to_map(raw_fields: SplitAsciiWhitespace<'_>) -> HashMap<String, String> {
    let mut map: HashMap<String, String> = HashMap::new();
    for raw_field in raw_fields {
        let fields: Vec<&str> = raw_field.split(':').collect();
        map.insert(fields[0].to_string(), fields[1].to_string());
    }
    return map;
}
