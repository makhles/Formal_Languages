package test;

import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Test;

import model.RegexParser;

public class TestRegexParser {

	private Deque<Character> stack(String input) {
		Deque<Character> output = new ArrayDeque<>();
		for (int i = 0; i < input.length(); i++) {
			output.push(new Character(input.charAt(i)));
		}
		return output;
	}

	@Test
	public void testInputSequence1() throws Exception {
		Deque<Character> expected = stack("a*b?c.d|*.");
		assertEquals(expected , RegexParser.instance().parse("a*(b?c|d)*"));
	}
}
