use std::ops::RangeInclusive;

use crate::helpers;

pub fn part1() -> i32 {
    let positions = helpers::get_input("src/day7_input.txt")[0]
        .split(",")
        .flat_map(|s| s.parse::<i32>())
        .collect();
    get_min_fuel(&positions)
}

fn get_min_fuel(positions: &Vec<i32>) -> i32 {
    let range = get_range(positions);
    range
        .map(|test_pos| {
            positions
                .iter()
                .map(|p| fuel_used(*p, test_pos))
                .sum::<i32>()
        })
        .min()
        .unwrap()
}

fn fuel_used(position: i32, test_pos: i32) -> i32 {
    if position >= test_pos {
        position - test_pos
    } else {
        test_pos - position
    }
}

fn get_range(positions: &Vec<i32>) -> RangeInclusive<i32> {
    let min = positions.iter().min().unwrap();
    let max = positions.iter().max().unwrap();
    RangeInclusive::new(*min, *max)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_min_max() {
        let test_data = vec![16, 1, 2, 0, 4, 2, 7, 1, 2, 14];
        assert_eq!(0..=16, get_range(&test_data));
    }

    #[test]
    fn test_get_min_fuel() {
        let test_data = vec![16, 1, 2, 0, 4, 2, 7, 1, 2, 14];
        assert_eq!(get_min_fuel(&test_data), 37);
    }
}
