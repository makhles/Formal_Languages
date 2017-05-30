package model;

import java.util.ArrayDeque;
import java.util.Deque;

public class BinaryTree {

	private Node root;
	private Node current;
	private Deque<Node> leftOff = new ArrayDeque<>();

	public BinaryTree() {
		root = null;
	}

	public void insert(Character symbol) {
		if (root == null) {
			root = new Node(symbol);
			current = root;
		} else {
			Node node = new Node(symbol);
			Character currentSymbol = current.symbol();
			if (currentSymbol.equals('*') || currentSymbol.equals('?')) {
				current.insertAtLeft(node);
			} else if (currentSymbol.equals('.') || currentSymbol.equals('|')) {
				current.insertAtRight(node);
				leftOff.push(current);
			} else {
				if (!leftOff.isEmpty()) {
					current = leftOff.pop();
					current.insertAtLeft(node);
				}
			}
			current = node;
		}
	}

	@Override
	public String toString() {
		visit(root);
		return "";
	}

	private void visit(Node node) {
		if (node.left() != null) {
			visit(node.left());
			System.out.print("(" + node.symbol() + ")  ");
		}
		if (node.right() != null) {
			visit(node.right());
		}
		System.out.print(node.symbol());
	}
}
