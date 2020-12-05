pub fn part1() -> usize {
    let raw_input = include_str!("day2_input.txt");
    return raw_input
        .lines()
        .map(|line| Password::from_line(line))
        .filter(|password| password.is_valid())
        .count();
}

pub fn part2() -> usize {
    let raw_input = include_str!("day2_input.txt");
    return raw_input
        .lines()
        .map(|line| Password::from_line(line))
        .filter(|password| password.is_valid2())
        .count();
}

// 1-3 a: abcde
// 1-3 b: cdefg
// 2-9 c: ccccccccc

#[derive(PartialEq, Debug)]
struct Password {
    lower_bound: usize,
    upper_bound: usize,
    letter: char,
    password: String,
}

impl Password {
    fn is_valid(&self) -> bool {
        let count = self.password.chars().filter(|c| *c == self.letter).count();
        self.lower_bound <= count && count <= self.upper_bound
    }

    fn is_valid2(&self) -> bool {
        let chars: Vec<char> = self.password.chars().collect();
        return (chars[self.lower_bound - 1] == self.letter
            && chars[self.upper_bound - 1] != self.letter)
            || (chars[self.upper_bound - 1] == self.letter
                && chars[self.lower_bound - 1] != self.letter);
    }

    fn from_line(line: &str) -> Password {
        let parts: Vec<&str> = line.split_whitespace().collect();
        let range: Vec<usize> = parts[0].split('-').map(|x| x.parse().unwrap()).collect();
        let letter = parts[1];
        let password = parts[2];
        Password {
            lower_bound: range[0],
            upper_bound: range[1],
            letter: letter.chars().next().unwrap(),
            password: password.to_string(),
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_is_valid() {
        assert!(Password {
            lower_bound: 1,
            upper_bound: 3,
            letter: 'a',
            password: String::from("abcde")
        }
        .is_valid());
        assert!(!Password {
            lower_bound: 1,
            upper_bound: 3,
            letter: 'b',
            password: String::from("cdefg")
        }
        .is_valid());
        assert!(Password {
            lower_bound: 2,
            upper_bound: 9,
            letter: 'c',
            password: String::from("ccccccccc")
        }
        .is_valid());
    }
    #[test]
    fn test_from_line() {
        // 2-9 c: ccccccccc
        let expected = Password {
            lower_bound: 2,
            upper_bound: 9,
            letter: 'c',
            password: String::from("ccccccccc"),
        };
        let result = Password::from_line("2-9 c: ccccccccc");
        assert_eq!(expected, result);
    }
}
