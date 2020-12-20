use std::fs;

pub fn part1(path: &str) -> usize {
    let raw_input = fs::read_to_string(path).unwrap();
    let (depart_time, schedule): (usize, &str) =
        match raw_input.splitn(2, '\n').collect::<Vec<&str>>()[..] {
            [time, schedule] => (time.parse().unwrap(), schedule),
            _ => unreachable!(),
        };
    let ids: Vec<usize> = schedule
        .split(',')
        .filter_map(|s| if s == "x" { None } else { s.parse().ok() })
        .collect();
    println!("ids: {:?}", ids);
    let result = ids
        .iter()
        .map(|id| {
            let t = 1 + (depart_time / id);
            println!("t: {}", t);
            let wait_time = (t * id) - depart_time;
            (id, wait_time)
        })
        .min_by(|x, y| x.1.cmp(&y.1))
        .unwrap();
    result.0 * result.1
}
