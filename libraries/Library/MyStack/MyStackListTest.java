package Library.MyStack;

import MyStackList;

public class MyStackListTest {
	public static void main(String[] args) {
		MyStackList<Integer> itms = new MyStackList<Integer>();
		itms.push$exn(1);
		itms.push$exn(2);
		itms.push$exn(3);
		itms.foritm(itm -> System.out.println(itm)); // important!!
		itms.pop$exn();
		itms.pop$exn();
		itms.push$exn(4);
		itms.push$exn(5);
		itms.System$out$print();
		System.out.println();
	}
}
