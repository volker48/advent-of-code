#[derive(Debug)]
struct Dice {
    state: usize,
    rolls: usize,
}

impl Dice {
    fn new() -> Self {
        Dice { state: 1, rolls: 0 }
    }

    fn roll(&mut self) -> [usize; 3] {
        let mut rolls = [0; 3];
        for i in 0..3 {
            self.rolls += 1;
            rolls[i] = self.state;
            if self.state < 100 {
                self.state += 1;
            } else {
                self.state = 1;
            }
        }
        rolls
    }
}

#[derive(Debug)]
struct Player {
    position: usize,
    score: usize,
}

impl Player {
    fn new(position: usize) -> Self {
        Player { position, score: 0 }
    }
    fn play(&mut self, d: &mut Dice) -> bool {
        let rolls = d.roll();
        self.position += rolls.iter().sum::<usize>();
        self.position %= 10;
        if self.position == 0 {
            self.position = 10;
        }
        self.score += self.position;
        self.score >= 1000
    }
}

pub fn part1() -> usize {
    let mut players = vec![Player::new(9), Player::new(3)];
    let mut dice = Dice::new();
    let mut finished = false;
    while !finished {
        for player in &mut *players {
            if let true = player.play(&mut dice) {
                finished = true;
                break;
            }
        }
    }
    let loser: Vec<&Player> = players.iter().filter(|p| p.score < 1000).collect();
    loser[0].score * dice.rolls
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_simple() {
        let mut players = vec![Player::new(4), Player::new(8)];
        let mut dice = Dice::new();
        let mut finished = false;
        while !finished {
            for player in &mut *players {
                if let true = player.play(&mut dice) {
                    finished = true;
                    break;
                }
            }
        }
        let loser: Vec<&Player> = players.iter().filter(|p| p.score < 1000).collect();
        println!("player 1 {:?}", players[0]);
        println!("player 2 {:?}", players[1]);
        println!("dice {:?}", dice);
        assert_eq!(739785, loser[0].score * dice.rolls)
    }
    #[test]
    fn test_roll() {
        let mut d = Dice::new();
        assert_eq!([1, 2, 3], d.roll());
        assert_eq!([4, 5, 6], d.roll());
    }

    #[test]
    fn test_roll_wrap() {
        let mut expected = (1..=100).collect::<Vec<usize>>();
        expected.extend(vec![1, 2].iter());
        let mut result: Vec<usize> = Vec::new();
        let mut d = Dice::new();
        for _ in 0..34 {
            result.extend(d.roll().iter());
        }
        assert_eq!(expected, result);
    }
}
