use crate::helpers;
use std::collections::HashMap;

pub fn part1() -> i32 {
    let lines = helpers::get_input("src/day10_input.txt");
    lines.into_iter().map(score_line).sum()
}

fn score_line(line: String) -> i32 {
    let scores: HashMap<char, i32> = vec![')', ']', '}', '>']
        .into_iter()
        .zip(vec![3, 57, 1197, 25137])
        .collect();
    let mut stack: Vec<char> = Vec::new();
    let mut bad_char: Option<char> = None;
    for char in line.chars() {
        match char {
            opener @ ('(' | '[' | '{' | '<') => stack.push(opener),
            closer @ (')' | ']' | '}' | '>') => {
                if is_valid(stack.last().unwrap_or(&'*'), closer) {
                    stack.pop();
                } else {
                    bad_char = Some(closer);
                    break;
                }
            }
            _ => panic!("{} invalid character", char),
        }
    }
    match bad_char {
        Some(closer) => scores.get(&closer).unwrap().to_owned(),
        None => 0,
    }
}

fn is_valid(opener: &char, closer: char) -> bool {
    match (opener, closer) {
        ('(', ')') | ('[', ']') | ('{', '}') | ('<', '>') => true,
        _ => false,
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_is_valid() {
        assert_eq!(false, is_valid(&'(', ']'));
        assert_eq!(true, is_valid(&'(', ')'));
        assert_eq!(true, is_valid(&'<', '>'));
    }

    #[test]
    fn test_score_line() {
        assert_eq!(0, score_line(String::from("()")));
        assert_eq!(0, score_line(String::from("(()")));
        assert_eq!(3, score_line(String::from("[())")));
        assert_eq!(1197, score_line(String::from("{([(<{}[<>[]}>{[]{[(<()>")));
        assert_eq!(57, score_line(String::from("[{[{({}]{}}([{[{{{}}([]")));
        assert_eq!(3, score_line(String::from("[<(<(<(<{}))><([]([]()")));
        assert_eq!(25137, score_line(String::from("<{([([[(<>()){}]>(<<{{")));
    }

    #[test]
    fn test_total_score() {
        let lines = helpers::get_input("src/day10_test.txt");
        let total: i32 = lines.into_iter().map(score_line).sum();
        assert_eq!(26397, total);
    }
}
