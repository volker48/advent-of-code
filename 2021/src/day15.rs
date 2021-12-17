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
    u: Node,
    v: Node,
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
            risk: risk,
            predecessor: Vertex::Nil,
            estimate: i32::MAX,
            node_id: node_id,
        }
    }
}

fn relax(nodes: HashMap<NodeID, Node>, u_id: NodeID, v_id: NodeID) {
    let u = nodes.get(&u_id).unwrap();
    let v = nodes.get(&v_id).unwrap();
    if v.estimate > u.estimate + u.risk {
        v.estimate = u.estimate + u.risk;
        v.predecessor = Vertex::NodeID(u.node_id);
    }
}

pub fn blah() {
    let raw = helpers::get_input("src/day15_test.txt");
    let columns: usize = raw[0].len();
    let rows: usize = raw.len();
    let mut graph: Vec<Vec<Node>> = Vec::new();
    let mut nodes: HashMap<NodeID, Node> = HashMap::new();
    for (y, line) in raw.into_iter().enumerate() {
        let mut row: Vec<Node> = Vec::new();
        for (x, char) in line.chars().enumerate() {
            let risk = char.to_digit(10).unwrap();
            let node_id = (x, y);
            let v = Node::new(risk as i32, node_id);
            row.push(v);
            nodes[&node_id] = v;
        }
        graph.push(row);
    }
    graph[0][0].estimate = 0;
    let mut edges: Vec<Edge> = Vec::new();
    for y in 0..rows {
        for x in 0..columns {
            let u = &graph[y][x];
            if x + 1 < columns {
                edges.push(Edge {
                    u: Box::new(u),
                    v: &graph[y][x + 1],
                });
            }
            if columns > 0 {
                edges.push(Edge {
                    u: Box::new(u),
                    v: &graph[y][x - 1],
                });
            }
            if y + 1 < rows {
                edges.push(Edge {
                    u: Box::new(u),
                    v: &graph[y + 1][x],
                });
            }
            if y > 0 {
                edges.push(Edge {
                    u: Box::new(u),
                    v: &graph[y - 1][x],
                })
            }
        }
    }
    for edge in edges {}
}
