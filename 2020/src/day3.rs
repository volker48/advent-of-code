pub fn part1(down: usize, right: usize) -> usize {
    let raw_input = include_str!("day3_input.txt");
    return traverse(raw_input, down, right);
}

fn traverse(raw_input: &str, down: usize, right: usize) -> usize {
    let mut tree_count = 0;
    let path = &mut raw_input.lines().map(|line| line.chars().cycle()).skip(1);
    let mut i = 0;
    while let Some(line) = path.skip(down - 1).next() {
        let skip_count = (i + 1) * right;
        match line.skip(skip_count).next() {
            Some('#') => tree_count += 1,
            _ => (),
        }
        i += 1;
    }
    return tree_count;
}

#[cfg(test)]
mod tests {
    use std::vec;

    use super::*;

    #[test]
    fn test_from_line() {
        let test_input = include_str!("test_input.txt");
        let params: Vec<(usize, usize, usize)> =
            vec![(1, 1, 2), (1, 3, 7), (1, 5, 3), (1, 7, 4), (2, 1, 2)];
        for (down, right, expected) in params {
            let actual = traverse(test_input, down, right);
            println!(
                "down {}, right {}, expected {}, actual {}",
                down, right, expected, actual
            );
            assert!(expected == actual);
        }
    }
}
