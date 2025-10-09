
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

		MyQueueList open = new MyQueueList<>();
		MyStackArray closed = new MyStackArray<>(text.length());
		while (!stack.isEmpty()) {
			char x = (char) stack.pop$raw();

			if ((x == '(') || (x == '{') || (x == '[')) {
				System.out.println(x);
				open.enque$raw(x);
			}

			else if ((x == ')') || (x == '}') || (x == ']')) {
				System.out.println(x);
				closed.push$raw(x);
			}

		}

		if (open.isEmpty() && closed.isEmpty()) {
			return false;
		}
		boolean flagCurl = true;
		boolean flagBrack = true;
		boolean flagParen = true;

		if (temp.length == 0 || (open.size() != closed.size())) {
			return false;
		} else {
			while (!open.isEmpty()) {
				System.out.println();
				char x = (char) open.deque$raw();
				char y = (char) closed.pop$raw();

				switch (x) {
					case '{':
						if (y != '}') {
							System.out.println(x);
							System.out.println(y);
							flagCurl = false;
						}
						break;

					case '(':
						if (y != ')') {
							flagParen = false;
						}
						break;
					case '[':
						if (y != ']') {
							flagBrack = false;
						}
						break;
				}
			}
			return flagCurl && flagBrack && flagParen;
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
