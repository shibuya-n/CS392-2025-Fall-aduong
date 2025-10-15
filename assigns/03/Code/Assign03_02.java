import MyLibrary.MyStackArray.*;

public class Assign03_02 {
	public static boolean balencedq(String text) {
		//
		// There are only '(', ')', '[', ']', '{', and '}'
		// appearing in [text]. This method should return
		// true if and only if the parentheses/brackets/braces
		// in [text] are balenced.
		// Your solution must make proper use of MyStack!
		//

		MyStackArray<String> stack = new MyStackArray<String>(text.length());

		for (int i = 0; i < text.length(); i++) {

			stack.push$raw(text.substring(i, i + 1));

		}

		MyStackArray<String> matchParen = new MyStackArray<String>(text.length());
		for (int i = 0; i < text.length(); i++) {
			String x = stack.pop$raw();

			switch (x) {
				case "(":
					matchParen.push$raw("(");
				case "{":
					matchParen.push$raw("{");
				case "[":
					matchParen.push$raw("[");

				case ")":
					matchParen.pop$raw();
				case "}":
					matchParen.pop$raw();
				case "]":
					matchParen.pop$raw();
			}

		}

		return stack.isEmpty();
	}

	// two stacks -> one stack with everything
	// another stack

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
