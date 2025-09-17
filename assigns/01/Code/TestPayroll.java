
public class TestPayroll {
    /* your code */

    public static void main(String[] args) {

        Payroll roll1 = new Payroll();
        Payroll roll2 = new Payroll();
        Employee x = new Employee("Jasper", 1, 25.0);
        Employee y = new Employee("Jeremiah", 2, 20.0);
        Employee z = new Employee("John", 3, 15);

        roll1.add_employee(x);
        roll1.add_employee(y);
        roll1.add_employee(z);
        roll1.printMember();
        System.out.println();

        Employee a = new Employee("Andrew", 11, 10);
        Employee b = new Employee("Andy", 12, 11);
        Employee c = new Employee("Andrea", 13, 15);

        roll2.add_employee(a);
        roll2.add_employee(b);
        roll2.add_employee(c);
        System.out.println("roll2");
        roll2.printMember();
        System.out.println();

        try {
            roll1.remove_employee(0);
        } catch (Exception e) {

        }
        roll1.printMember();

        try {
            System.out.println("Found at index: " + roll1.find_employee("Jeremiah"));
        } catch (Exception e) {
            System.out.println("not found");
        }

        System.out.println();
        roll1.add_payroll(roll2);
        roll1.printMember();

        System.out.println();

        roll1.copy_payroll(roll2);
        roll1.printMember();

    }

}
