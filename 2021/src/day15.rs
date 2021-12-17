use std::{collections::HashMap, hash::Hash};

use crate::helpers;

type NodeID = (usize, usize);

#[derive(Clone, Copy)]
enum Vertex {
    NodeID(NodeID),
    Nil,
}

#[derive(Clone, Copy)]
struct Edge {
    u: NodeID,
    v: NodeID,
}

#[derive(Clone, Copy)]
struct Node {
    risk: i32,
    predecessor: Vertex,
    estimate: i32,
    node_id: NodeID,
}

impl Node {
    fn new(risk: i32, node_id: NodeID) -> Node {
        Node {
            risk,
            predecessor: Vertex::Nil,
            estimate: i32::MAX,
            node_id,
        }
    }
}

fn relax(nodes: &mut HashMap<NodeID, Node>, u_id: NodeID, v_id: NodeID) -> () {
    let v = nodes.get(&v_id).unwrap();
    let u = nodes.get(&u_id).unwrap();
    if v.estimate > u.estimate + u.risk {
        let mut new = Node::new(v.risk, v_id);
        new.estimate = u.estimate + u.risk;
        new.predecessor = Vertex::NodeID(u.node_id);
        nodes.insert(v_id, new);
    }
}

pub fn part1() -> i32 {
    let raw = helpers::get_input("src/day15_input.txt");
    let columns: usize = raw[0].len();
    let rows: usize = raw.len();
    println!("rows {} columns {}", rows, columns);
    let mut nodes: HashMap<NodeID, Node> = HashMap::new();
    for (y, line) in raw.into_iter().enumerate() {
        for (x, char) in line.chars().enumerate() {
            let risk = char.to_digit(10).unwrap();
            let node_id = (x, y);
            let v = Node::new(risk as i32, node_id);
            nodes.insert(node_id, v);
        }
    }
    println!("Nodes length {}", nodes.len());
    nodes.get_mut(&(0, 0)).unwrap().estimate = 0;
    let mut edges: Vec<Edge> = Vec::new();
    for y in 0..rows {
        for x in 0..columns {
            if x + 1 < columns {
                edges.push(Edge {
                    u: (x, y),
                    v: (x + 1, y),
                });
            }
            if x > 0 {
                edges.push(Edge {
                    u: (x, y),
                    v: (x - 1, y),
                });
            }
            if y + 1 < rows {
                edges.push(Edge {
                    u: (x, y),
                    v: (x, y + 1),
                });
            }
            if y > 0 {
                edges.push(Edge {
                    u: (x, y),
                    v: (x, y - 1),
                })
            }
        }
    }
    for _ in 0..nodes.len() {
        for edge in &edges {
            relax(&mut nodes, edge.u, edge.v);
        }
    }

    let mut pi = nodes.get(&(columns - 1, rows - 1)).unwrap().predecessor;
    let mut risk = 0;
    while let Vertex::NodeID(n) = pi {
        let node = nodes.get(&n).unwrap();
        risk += node.risk;
        pi = node.predecessor;
    }
    risk
}
