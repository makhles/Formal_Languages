package model;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RegexParser {

	private static final RegexParser INSTANCE = new RegexParser();
	private static final char ASTERISK = '*';
	private static final char PLUS = '+';
	private static final char LEFT_PARENTHESIS = '(';
	private static final char RIGHT_PARENTHESIS = ')';
	private static final char QUESTION = '?';
	private static final char PIPE = '|';
	private static final char DOT = '.';
	private static final char ZERO = '0';
	private static final char NINE = '9';
	private static final char LETTER_A = 'a';
	private static final char LETTER_Z = 'z';
	private static final Map<Character, Integer> PRECEDENCE;
	static {
		Map<Character, Integer> m = new HashMap<>();
		m.put('|', 1);
		m.put('.', 2);
		m.put('*', 3);
		m.put('?', 3);
		m.put('+', 3);
		PRECEDENCE = Collections.unmodifiableMap(m);
	}

	private RegexParser() {
	}

	public static RegexParser instance() {
		return INSTANCE;
	}

	private boolean hasLoweOrEqualPrecedence(Character a, Character b) {
		return PRECEDENCE.get(a) <= PRECEDENCE.get(b);
	}

	// a*(b?c|d)*)
	public Deque<Character> parse(final String input) {
		Deque<Character> output = new ArrayDeque<>();
		Deque<Character> stack = new ArrayDeque<>();
		boolean lastTokenWasAlphanumeric = false;
		boolean lastTokenWasParenthesis = false;
		boolean lastTokenWasUnaryOperator = false;  // *+?

		for (int i = 0; i < input.length(); i++) {
			char token = input.charAt(i);
			if ((token >= ZERO && token <= NINE) || (token >= LETTER_A && token <= LETTER_Z)) {
				if (lastTokenWasAlphanumeric || lastTokenWasUnaryOperator) {
					output.push(new Character(DOT));
				}
				output.push(new Character(token));
				lastTokenWasUnaryOperator = false;
			} else if (token == ASTERISK || token == PLUS || token == PIPE || token == QUESTION || token == DOT) {
				Character charAtTheTop = stack.peek();
				while (charAtTheTop != null && charAtTheTop != LEFT_PARENTHESIS && charAtTheTop != RIGHT_PARENTHESIS
						&& hasLoweOrEqualPrecedence(token, charAtTheTop)) {
					output.push(stack.pop());
					charAtTheTop = stack.peek();
				}
				stack.push(token);
				if (token == ASTERISK || token == PLUS || token == PIPE) {
					lastTokenWasUnaryOperator = true;
				}
			} else if (token == LEFT_PARENTHESIS) {
				stack.push(token);
			} else if (token == RIGHT_PARENTHESIS) {
				boolean parenthesisNotFound = true;
				while (!stack.isEmpty() && parenthesisNotFound) {
					Character charAtTheTop = stack.peek();
					if (charAtTheTop == LEFT_PARENTHESIS) {
						stack.pop();
						parenthesisNotFound = false;
					} else {
						output.push(stack.pop());
						charAtTheTop = stack.peek();
					}
				}
				if (parenthesisNotFound) {
					System.err.println("ERROR: Mismatching parentheses!");
					System.exit(1);
				}
			}
		}
		while (!stack.isEmpty()) {
			Character charAtTheTop = stack.peek();
			if (charAtTheTop == LEFT_PARENTHESIS || charAtTheTop == RIGHT_PARENTHESIS) {
				System.err.println("ERROR: Mismatching parentheses!");
				System.exit(1);
			} else {
				output.push(stack.pop());
			}
		}
		return output;
	}
}
