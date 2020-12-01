mod day1;

fn main() {
    let (x, y) = day1::part1();
    println!("part 1 {}", x*y);
    let (x, y, z) = day1::part2();
    println!("part 2 {}", x*y*z);
}
