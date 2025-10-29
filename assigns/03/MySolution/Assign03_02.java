import Library.MyStackArray;

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
			String ch = text.substring(i, i + 1);

			// If it's an opening bracket, push it
			if (ch.equals("(") || ch.equals("{") || ch.equals("[")) {
				stack.push$raw(ch);
			}
			// If it's a closing bracket, check if it matches
			else if (ch.equals(")") || ch.equals("}") || ch.equals("]")) {
				// If stack is empty, there's no matching opening bracket
				if (stack.isEmpty()) {
					return false;
				}

				String top = stack.pop$raw();

				// Check if the brackets match
				if (ch.equals(")") && !top.equals("(")) {
					return false;
				}
				if (ch.equals("}") && !top.equals("{")) {
					return false;
				}
				if (ch.equals("]") && !top.equals("[")) {
					return false;
				}
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
