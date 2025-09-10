
public class TestPayroll {
    /* your code */

    public static void main(String[] args) {

        Payroll roll1 = new Payroll();
        Payroll roll2 = new Payroll();
        Employee x = new Employee("Jasper", 1, 25.0);
        Employee y = new Employee("Jeremiah", 2, 20.0);

        roll1.add_employee(x);
        roll1.add_employee(y);
        roll1.printMember();

        try {
            roll1.remove_employee(0);
            roll1.printMember();
        } catch (Exception e) {
            System.out.println("no members found");
        }

        try {
            roll1.find_employee("Jasper");
        } catch (Exception e) {
            System.out.println("could not find member");
        }
        // System.out.println(test.find_employee("Jasper"));
        // test.remove_employee(0);

    }

}
