import Library.MyStackArray.*;

public class Assign03_02 {
	public static boolean balencedq(String text) {
		//
		// There are only '(', ')', '[', ']', '{', and '}'
		// appearing in [text]. This method should return
		// true if and only if the parentheses/brackets/braces
		// in [text] are balenced.
		// Your solution must make proper use of MyStack!
		//
		char[] temp = new char[text.length()];
		MyStackArray stack = new MyStackArray<>(text.length());

		for (int i = 0; i < text.length(); i++) {
			temp[i] = text.charAt(i);
			stack.push$raw(temp[i]);

		}

		MyStack
		for (int i = 0; i < text.length(); i++){
			
		}
	}

	// two stacks -> one stack with everything
	// another stack :d

	// stack in (push) is one end of the bracket
	// stack out (pop) is another end of the bracket

	// if the stack is empty then it is true

	public static void main(String[] argv) {
		// Please write some testing code for your 'balencedq"

		System.out.println(balencedq("{yes"));
		System.out.println(balencedq("{yes}"));
		System.out.println(balencedq("{(yes)}"));
		System.out.println(balencedq("(){}"));
	}
}
