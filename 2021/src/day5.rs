use std::{collections::HashMap, ops::RangeInclusive};

use itertools::Itertools;

use crate::helpers;
pub fn part1() -> usize {
    let raw = helpers::get_input("src/day5_input.txt");
    let lines: Vec<Line> = raw
        .into_iter()
        .map(Line::new)
        .filter(|l| l.is_not_diag())
        .collect();
    let mut map: HashMap<Point, i32> = HashMap::new();
    for line in lines {
        for p in line.points_on_line() {
            let v = map.entry(p).or_insert(0);
            *v += 1;
        }
    }
    map.values().filter(|v| **v > 1).count()
}

#[derive(Clone, Debug, PartialEq, Eq, Hash)]
struct Point {
    x: i32,
    y: i32,
}
#[derive(Debug)]
struct Line {
    start: Point,
    end: Point,
}

impl Line {
    fn new(line: String) -> Line {
        let points: Vec<Point> = line.split(" -> ").map(|pair| Point::new(pair)).collect();
        Line {
            start: points[0].clone(),
            end: points[1].clone(),
        }
    }

    fn is_not_diag(&self) -> bool {
        self.start.x == self.end.x || self.start.y == self.end.y
    }

    fn points_on_line(self) -> Vec<Point> {
        let (start_x, end_x) = if self.start.x <= self.end.x {
            (self.start.x, self.end.x)
        } else {
            (self.end.x, self.start.x)
        };
        let (start_y, end_y) = if self.start.y <= self.end.y {
            (self.start.y, self.end.y)
        } else {
            (self.end.y, self.start.y)
        };

        if start_x == end_x {
            RangeInclusive::new(start_y, end_y)
                .map(|y| Point { x: start_x, y })
                .collect()
        } else {
            RangeInclusive::new(start_x, end_x)
                .map(|x| Point { x, y: start_y })
                .collect()
        }
    }
}

impl Point {
    fn new(pair: &str) -> Point {
        let points: Vec<i32> = pair.split(",").flat_map(|s| s.parse::<i32>()).collect();
        Point {
            x: points[0],
            y: points[1],
        }
    }
}

#[cfg(test)]
mod tests {

    use std::{collections::HashMap, iter::Map};

    use super::*;

    #[test]
    fn test_parse() {
        let raw = helpers::get_input("src/day5_test.txt");
        let lines: Vec<Line> = raw.into_iter().map(Line::new).collect();
        println!("{:?}", lines);
        let mut map: HashMap<Point, i32> = HashMap::new();
    }
    #[test]
    fn test_points_on_line() {
        let l = Line {
            start: Point { x: 1, y: 1 },
            end: Point { x: 1, y: 3 },
        };
        assert_eq!(
            l.points_on_line(),
            vec![
                Point { x: 1, y: 1 },
                Point { x: 1, y: 2 },
                Point { x: 1, y: 3 }
            ]
        )
    }
}
