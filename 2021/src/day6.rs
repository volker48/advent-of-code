use crate::helpers;

enum FishUpdate {
    OneFish(Fish),
    TwoFish(Fish, Fish),
}
#[derive(Debug, Clone, Copy)]
struct Fish {
    timer: i32,
}

impl Fish {
    fn new(timer: i32) -> Fish {
        Fish { timer }
    }

    fn update(self) -> FishUpdate {
        match self.timer {
            x if x > 0 => FishUpdate::OneFish(Fish {
                timer: self.timer - 1,
            }),
            _ => FishUpdate::TwoFish(Fish { timer: 6 }, Fish { timer: 8 }),
        }
    }
}

pub fn part1() -> usize {
    let raw = helpers::get_input("src/day6_input.txt");
    let inital_state: Vec<Fish> = raw
        .iter()
        .flat_map(|s| s.split(","))
        .map(|s| s.parse::<i32>().unwrap())
        .map(Fish::new)
        .collect();
    let mut new_fish: Vec<Fish> = inital_state
        .iter()
        .flat_map(|f| match f.update() {
            FishUpdate::OneFish(old_fish) => vec![old_fish],
            FishUpdate::TwoFish(old_fish, new_fish) => vec![old_fish, new_fish],
        })
        .collect();
    for _ in 1..256 {
        new_fish = new_fish
            .iter()
            .flat_map(|f| match f.update() {
                FishUpdate::OneFish(old_fish) => vec![old_fish],
                FishUpdate::TwoFish(old_fish, new_fish) => vec![old_fish, new_fish],
            })
            .collect();
    }
    new_fish.iter().count()
}
