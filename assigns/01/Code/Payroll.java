
public class Payroll {
    public static final int INITIAL_MAXIMUM_SIZE = 1024;

    public Employee people[];
    private int maximum_size;
    private int current_size;

    public Payroll() {
        /* your code */
        maximum_size = INITIAL_MAXIMUM_SIZE;
        people = new Employee[maximum_size];

        current_size = 0;

    }

    public void add_employee(Employee newbie) {
        if (current_size >= maximum_size) {
            maximum_size = maximum_size * 2;
            Employee[] temp = new Employee[maximum_size];

            int count = 0;
            while ((temp != null) && (count <= temp.length)) {
                temp[count] = people[count];
                count++;

            }
            people = temp;

            people[current_size] = newbie;
            current_size++;
        }

        else {
            people[current_size] = newbie;
            current_size++;
        }

    }

    public void remove_employee(int i) throws EmployeeIndexException {
        /* your code */

        if (people[i] == null) {
            throw new EmployeeIndexException();
        } else {
            current_size = current_size - 1;
            people[i] = null;

        }

        for (int j = i; j < people.length; j++) {
            Employee temp = people[j + 1];
            people[j + 1] = people[j];
            people[j] = temp;
        }

    }

    public int find_employee(String name) throws EmployeeNotFoundException {
        /* your code */
        for (int i = 0; i < people.length; i++) {
            if ((name.equals(people[i].name)) && (people[i] != null)) {
                return i;

            }
        }
        throw new EmployeeNotFoundException();
    }

    public void add_payroll(Payroll source) {

        int i = 0;

        while (i < source.current_size) {
            people[current_size] = source.people[i];

            current_size += 1;
            i += 1;

        }

    }

    public void copy_payroll(Payroll source) {
        /* your code */
        Employee[] temp = source.people;
        int sourceMaxSize = source.maximum_size;
        int sourceCurrentSize = source.current_size;

        people = temp;
        maximum_size = sourceMaxSize;
        current_size = sourceCurrentSize;
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
