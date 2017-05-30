package model;

public class Node {

	private Character symbol;
	private Node left;
	private Node right;

	public Node(Character symbol) {
		this.symbol = symbol;
		left = null;
		right = null;
	}

	public Node left() {
		return left;
	}

	public Node right() {
		return right;
	}

	public Character symbol() {
		return symbol;
	}

	public void insertAtLeft(Node node) {
		left = node;
	}

	public void insertAtRight(Node node) {
		right = node;
	}
}
