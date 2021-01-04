pub fn part1(pub_key1: usize, pub_key2: usize) -> usize {
    let loop_size = find_loop_size(pub_key1);
    calc_encryption_key(loop_size, pub_key2)
}

pub fn calc_encryption_key(loop_size: usize, pub_key: usize) -> usize {
    let mut value: usize = 1;
    for _ in 0..loop_size {
        value = value * pub_key;
        value = value % 20201227;
    }
    value
}

pub fn find_loop_size(pub_key: usize) -> usize {
    let mut loop_size = 1;
    let mut value: usize = 1;
    loop {
        value = value * 7;
        value = value % 20201227;
        if value == pub_key {
            return loop_size;
        }
        loop_size += 1;
    }
}
