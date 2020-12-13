use std::{collections::HashSet, convert::TryInto, fs};

enum OpCode {
    Nop,
    Acc,
    Jmp,
}

fn parse_line(line: &str) -> (OpCode, i32) {
    let parts: Vec<&str> = line.splitn(2, ' ').map(str::trim).collect();
    println!("parts {:?}", parts);
    let op = parts[0];
    let val: i32 = parts[1].parse().unwrap();
    match op {
        "acc" => (OpCode::Acc, val),
        "nop" => (OpCode::Nop, val),
        "jmp" => (OpCode::Jmp, val),
        _ => unreachable!(),
    }
}

pub fn part1(path: &str) {
    let input = fs::read_to_string(path).unwrap();
    let mut executed: HashSet<usize> = HashSet::new();
    let mut instruction_ptr: usize = 0;
    let mut acc = 0;
    let mut program: Vec<(OpCode, i32)> = Vec::new();

    for line in input.split('\n').map(str::trim).filter(|l| *l != "") {
        let (opcode, val) = parse_line(line);
        program.push((opcode, val));
    }
    while !executed.contains(&instruction_ptr) {
        let (opcode, val) = &program[instruction_ptr];
        executed.insert(instruction_ptr);
        match opcode {
            OpCode::Nop => instruction_ptr += 1,
            OpCode::Acc => {
                instruction_ptr += 1;
                acc += val;
            }
            OpCode::Jmp => instruction_ptr = (instruction_ptr as i32 + val).try_into().unwrap(),
        }
    }
    println!("acc {}", acc);
}
