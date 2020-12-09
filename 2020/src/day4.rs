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
#[derive(Debug)]
struct Numeric<'a> {
    re: &'a Regex,
    lower: i32,
    upper: i32,
}

struct Height<'a> {
    re: &'a Regex,
}

impl Validator for Height<'_> {
    fn is_valid(&self, text: &str) -> bool {
        let caps = match self.re.captures(text) {
            Some(c) => c,
            None => return false,
        };
        let units = caps.get(2).expect("missing in/cm").as_str();
        let lower: i32;
        let upper: i32;
        if units == "cm" {
            lower = 150;
            upper = 193;
        } else {
            lower = 59;
            upper = 76;
        }
        caps.get(1).map_or(false, |numerals| {
            let y = numerals.as_str().parse::<i32>().unwrap();
            y >= lower && y <= upper
        })
    }
}

impl Validator for Regex {
    fn is_valid(&self, text: &str) -> bool {
        self.is_match(text)
    }
}

impl Validator for Numeric<'_> {
    fn is_valid(&self, text: &str) -> bool {
        let caps = match self.re.captures(text) {
            Some(c) => c,
            None => return false,
        };
        caps.get(1).map_or(false, |numerals| {
            let y = numerals.as_str().parse::<i32>().unwrap();
            y >= self.lower && y <= self.upper
        })
    }
}

trait Validator {
    fn is_valid(&self, text: &str) -> bool;
}

pub fn part2(path: &str) -> usize {
    let required_keys = &vec!["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"];
    let raw_input = fs::read_to_string(path).expect("error reading file");
    let re = Regex::new(r"(?m)^\s*$").expect("invalid regex");
    let year_re = &Regex::new(r"^(\d{4})$").expect("invalid year regex");
    let hcl_re = Regex::new(r"^#[a-f0-9]{6}").expect("invalid hcl regex");
    let ecl_re = Regex::new(r"^(amb|blu|brn|gry|grn|hzl|oth)$").expect("invalid ecl regex");
    let hgt_re = &Regex::new(r"(\d{2,3})(in|cm)$").expect("invalid hgt regex");
    let pid_re = Regex::new(r"^\d{9}$").expect("invalid pid re");
    let cid_re = Regex::new(r".*").expect("invalid cid re");
    let mut num_valid = 0;
    let mut validators: HashMap<String, Box<dyn Validator>> = HashMap::new();
    validators.insert(
        String::from("byr"),
        Box::new(Numeric {
            re: year_re,
            lower: 1920,
            upper: 2002,
        }),
    );
    validators.insert(
        String::from("iyr"),
        Box::new(Numeric {
            re: year_re,
            lower: 2010,
            upper: 2020,
        }),
    );
    validators.insert(
        String::from("eyr"),
        Box::new(Numeric {
            re: year_re,
            lower: 2020,
            upper: 2030,
        }),
    );
    validators.insert(String::from("hgt"), Box::new(Height { re: hgt_re }));
    validators.insert(String::from("hcl"), Box::new(hcl_re));
    validators.insert(String::from("ecl"), Box::new(ecl_re));
    validators.insert(String::from("pid"), Box::new(pid_re));
    validators.insert(String::from("cid"), Box::new(cid_re));
    for raw_passport in re.split(&raw_input) {
        let raw_fields = raw_passport.trim().split_ascii_whitespace();
        let passport = fields_to_map(raw_fields);
        let has_keys = required_keys
            .into_iter()
            .all(|key| passport.contains_key(&key.to_string()));
        if !has_keys {
            continue;
        }
        let valid = passport.into_iter().all(|(k, v)| {
            let validator = validators.get(&k).expect(&format!("missing key {}", k));
            validator.is_valid(&v)
        });
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
