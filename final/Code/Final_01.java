/*
// HX: 20 points for Final_01
// A word consists of a sequence of
// letters ([a-z]+[A-Z]) plus aprostrophe (')
// And words are separated by non-letters-aprostrophe
// (such as blanks, punctuations, etc.) in pg2701.txt.
*/

import FnList.*;
import LnStrm.*;

public class Final_01 {
	static LnStrm<FnList<Character>> pg2701_word$strmize() {
		// HX-2025-12-16:
		// Please construct a stream of words contained in the
		// file Data/pg2701.txt
		// Note that a word is represented as a list of characters
		// Also every upper case letter in the original text should
		// be turned into its corresponding lower case.
		// This stream should be built on top of pg2701_char$strmize
		// which is already implemented in Final_00.
		// In particular, you should NOT use Java library function
		// for processing files!

		LnStrm<Character> charStream = Final_00.pg2701_char$strmize();
		return buildWordStream(charStream);
	}

	// Build word stream from character stream
	private static LnStrm<FnList<Character>> buildWordStream(LnStrm<Character> chars) {
		return new LnStrm<FnList<Character>>(
				() -> {
					LnStcn<Character> firstCons = skipNonWordChars(chars);
					if (firstCons.nilq()) {
						return new LnStcn<FnList<Character>>();
					}

					WordResult result = extractWord(firstCons);
					return new LnStcn<FnList<Character>>(
							result.word,
							buildWordStream(result.remaining));
				});
	}

	// Helper class to return both word and remaining stream
	private static class WordResult {
		FnList<Character> word;
		LnStrm<Character> remaining;

		WordResult(FnList<Character> w, LnStrm<Character> r) {
			word = w;
			remaining = r;
		}
	}

	// Skip non-word characters
	private static LnStcn<Character> skipNonWordChars(LnStrm<Character> chars) {
		LnStcn<Character> cons = chars.eval0();
		if (cons.nilq()) {
			return new LnStcn<Character>();
		}

		char ch = cons.hd();
		if (isWordChar(ch)) {
			return cons;
		} else {
			return skipNonWordChars(cons.tl());
		}
	}

	// Extract one word and return both word and remaining stream
	private static WordResult extractWord(LnStcn<Character> startCons) {
		FnList<Character> word = FnListSUtil.nil();
		LnStcn<Character> current = startCons;
		while (!current.nilq()) {
			char ch = current.hd();
			if (isWordChar(ch)) {
				char lower = toLowerCase(ch);
				word = FnListSUtil.cons(lower, word);
				LnStrm<Character> nextStrm = current.tl();
				current = nextStrm.eval0();
			} else {
				break;
			}
		}
		// remaining starts from current
		LnStcn<Character> finalCurrent = current;
		LnStrm<Character> remaining = new LnStrm<>(() -> finalCurrent);
		return new WordResult(FnListSUtil.reverse(word), remaining);
	}

	// Check if character is part of a word (letter or apostrophe)
	private static boolean isWordChar(char ch) {
		return (ch >= 'a' && ch <= 'z') ||
				(ch >= 'A' && ch <= 'Z') ||
				(ch == '\'');
	}

	// Convert character to lowercase
	private static char toLowerCase(char ch) {
		if (ch >= 'A' && ch <= 'Z') {
			return (char) (ch + ('a' - 'A'));
		}
		return ch;
	}

	public static void main(String[] args) {
		// HX-2025-12-16:
		// Please write minimal testing code for pg2701_word$strmize()

		System.out.println("Testing pg2701_word$strmize()...\n");

		LnStrm<FnList<Character>> wordStream = pg2701_word$strmize();

		// Print first 30 words
		System.out.println("First 30 words:");
		int count = 0;
		while (count < 30) {
			LnStcn<FnList<Character>> cons = wordStream.eval0();
			if (cons.nilq()) {
				System.out.println("\n(End of stream at word " + count + ")");
				break;
			}

			FnList<Character> word = cons.hd();
			System.out.print((count + 1) + ": \"");
			printWord(word);
			System.out.println("\"");

			wordStream = cons.tl();
			count++;
		}

		System.out.println("\nTest complete!");
		return /* void */;
	}

	// Helper to print a word (FnList<Character>)
	private static void printWord(FnList<Character> word) {
		word.foritm(ch -> System.out.print(ch));
	}
}