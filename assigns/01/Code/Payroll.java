
public class Payroll {
    public static final int INITIAL_MAXIMUM_SIZE = 1024;

    private Employee people[];
    private int maximum_size;
    private int current_size;

    public Payroll() {
        /* your code */
        people = new Employee[maximum_size];
        maximum_size = INITIAL_MAXIMUM_SIZE;
        current_size = 0;

    }

    public void add_employee(Employee newbie) {
        if (current_size >= maximum_size) {

        }

        people[current_size] = newbie;
        current_size++;

    }

    public void remove_employee(int i) throws EmployeeIndexException {
        /* your code */
        if (people[i] == null) {
            throw new EmployeeIndexException();
        } else {
            people[i] = null;

            for (int j = i; j < people.length; j++) {
                Employee temp = people[j + 1];
                people[j + 1] = people[j];
                people[j] = temp;
            }
        }
    }

    public int find_employee(String name) throws EmployeeNotFoundException {
        /* your code */

        for (int i = 0; i < people.length; i++) {
            if (name.equals(people[i].name)) {
                return i;
            }
        }
        throw new EmployeeNotFoundException();
    }

    public void add_payroll(Payroll source) {
        /* your code */
        maximum_size = maximum_size * 2;
        Employee[] temp = new Employee[maximum_size];
    }

    public void copy_payroll(Payroll source) {
        /* your code */
    }

    public int getSize() {
        return current_size;
    }

    public void printMember() {
        for (int i = 0; i < current_size; i++) {
            System.out.println(people[i]);
        }
    }

}
