package model;

import java.util.ArrayDeque;
import java.util.Deque;

public class TestBinaryTree {

	public static void main(String[] args) {
		Deque<Character> regex = readRegex();
		BinaryTree tree = new BinaryTree();
		while (!regex.isEmpty()) {
			tree.insert(regex.pop());
		}
		System.out.println(tree);
	}

	private static Deque<Character> readRegex() {
		Deque<Character> regex = new ArrayDeque<>();
		regex.push(new Character('a'));
		regex.push(new Character('*'));
		regex.push(new Character('b'));
		regex.push(new Character('?'));
		regex.push(new Character('c'));
		regex.push(new Character('.'));
		regex.push(new Character('d'));
		regex.push(new Character('|'));
		regex.push(new Character('*'));
		regex.push(new Character('.'));
		return regex;
	}

}
