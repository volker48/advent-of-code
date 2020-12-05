mod day3;
fn main() {
    let total = day3::part1(1, 1)
        * day3::part1(1, 3)
        * day3::part1(1, 5)
        * day3::part1(1, 7)
        * day3::part1(2, 1);
    println!("trees {}", total);
}
