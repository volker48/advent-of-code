use std::collections::VecDeque;
pub fn part1(path: &str) -> usize {
    let player1 = "28
    50
    9
    11
    4
    45
    19
    26
    42
    43
    31
    46
    21
    40
    33
    20
    7
    6
    17
    44
    5
    39
    35
    27
    10";
    let player2 = "18
    16
    29
    41
    14
    12
    30
    37
    36
    24
    48
    38
    47
    34
    15
    8
    49
    23
    1
    3
    32
    25
    22
    13
    2";
    let mut p1deck: VecDeque<usize> = player1
        .split('\n')
        .map(str::trim)
        .map(|s| s.parse().unwrap())
        .collect();
    let mut p2deck: VecDeque<usize> = player2
        .split('\n')
        .map(str::trim)
        .map(|s| s.parse().unwrap())
        .collect();
    println!("p1: {:?}, p2: {:?}", p1deck, p2deck);

    while p1deck.len() > 0 && p2deck.len() > 0 {
        let p1card = p1deck.pop_front().unwrap();
        let p2card = p2deck.pop_front().unwrap();
        if p1card > p2card {
            p1deck.push_back(p1card);
            p1deck.push_back(p2card);
        } else {
            p2deck.push_back(p2card);
            p2deck.push_back(p1card);
        }
    }

    println!("p1: {:?}, p2: {:?}", p1deck, p2deck);
    let winner = if p1deck.len() > 0 { p1deck } else { p2deck };
    winner
        .into_iter()
        .rev()
        .enumerate()
        .map(|(i, val)| (1 + i) * val)
        .sum()
}
