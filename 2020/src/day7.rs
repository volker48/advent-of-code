use regex::Regex;
use std::collections::HashMap;
use std::fs;

pub fn part1(path: &str) -> usize {
    let input = fs::read_to_string(path).unwrap();
    let raw_bags = &mut HashMap::new();
    let remove = Regex::new(r" ?bags?\.? ?").unwrap();
    for s in input.split('\n') {
        let replaced = remove.replace_all(s, "");
        let parts: Vec<String> = replaced.splitn(2, "contain").map(String::from).collect();
        println!("parts: {:?}", parts);
        let parent = parts[0].clone();
        let children: Vec<String> = parts[1]
            .split(",")
            .map(str::trim)
            .map(|s| s[2..].to_string())
            .collect();
        raw_bags.insert(parent, children);
        // println!("parent: {} children: {:?}", parts[0], children);
    }
    println!("{:?}", raw_bags);
    let mut can_contain = 0;
    for children in raw_bags.values() {
        let res = blah(raw_bags, children);
        if res > 0 {
            can_contain += 1;
        }
    }
    can_contain
}

fn blah(bags: &HashMap<String, Vec<String>>, children: &Vec<String>) -> usize {
    let mut result: usize = 0;
    for child in children {
        result += match child.as_str() {
            " other" => 0,
            "shiny gold" => 1,
            color => blah(bags, bags.get(color).unwrap()),
        }
    }
    result
}
