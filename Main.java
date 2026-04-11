import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static Student currentStudent = null;
    private static Staff currentStaff = null;
    private static List<Student> allStudents = new ArrayList<>();
    private static List<Staff> allStaff = new ArrayList<>();

    public static void main(String[] args) {
        boolean start = false;
        System.out.println("Welcome to Smart Campus Management System ");

        while (!start) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Student");
            System.out.println("2. Staff");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    studentMenu();
                    break;
                case "2":
                    staffMenu();
                    break;
                case "3":
                    start = true;
                    System.out.println("Exiting Smart Campus Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }

        // Close scanner before exit
        scanner.close();
    }

    private static void studentMenu() {
        boolean menu = false;
        while (!menu) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. Register Student");
            System.out.println("2. Register Course");
            System.out.println("3. Display Registered Courses");
            System.out.println("4. Pay Fees");
            System.out.println("5. View Report");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose (1-6): ");
            String opt = scanner.nextLine();

            switch (opt) {
                case "1":
                    registerStudent();
                    break;
                case "2":
                    registerCourseForStudent();
                    break;
                case "3":
                    if (ensureStudent()) currentStudent.displayCourses();
                    break;
                case "4":
                    if (ensureStudent()) processPaymentForStudent();
                    break;
                case "5":
                    if (ensureStudent()) displayReport();
                    break;
                case "6":
                    menu = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void staffMenu() {
        boolean detail = false;
        while (!detail) {
            System.out.println("\nStaff Menu:");
            System.out.println("1. Register Staff");
            System.out.println("2. Display Staff Details");
            System.out.println("3. Student Management");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose (1-4): ");
            String opt = scanner.nextLine();

            switch (opt) {
                case "1":
                    registerStaff();
                    break;
                case "2":
                    if (currentStaff != null) currentStaff.displayStaffDetails();
                    else System.out.println("No staff registered yet.");
                    break;
                case "3":
                    studentManagement();
                    break;
                case "4":
                    detail = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void studentManagement() {
        boolean manager = false;
        while (!manager) {
            System.out.println("\nStudent Management:");
            System.out.println("1. Search Student by ID");
            System.out.println("2. List All Students");
            System.out.println("3. Modify Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Set Current Student by ID");
            System.out.println("6. Back to Staff Menu");
            System.out.print("Choose (1-6): ");
            String opt = scanner.nextLine();

            switch (opt) {
                case "1":
                    searchStudentById();
                    break;
                case "2":
                    listAllStudents();
                    break;
                case "3":
                    modifyStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    setCurrentStudentById();
                    break;
                case "6":
                    manager = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void searchStudentById() {
        System.out.print("Enter student ID to search: ");
        String id = scanner.nextLine();

        Student found = findStudentById(id);
        if (found != null) {
            System.out.println(" Student found:");
            found.displayStudentDetails();
            found.displayCourses();
        } else {
            System.out.println(" Student with ID '" + id + "' not found.");
        }
    }

    private static void listAllStudents() {
        if (allStudents.isEmpty()) {
            System.out.println("No students registered yet.");
            return;
        }

        System.out.println("\n===== ALL STUDENTS =====");
        for (int i = 0; i < allStudents.size(); i++) {
            Student s = allStudents.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " (" + s.getStudentID() + ") - " + s.getDepartment());
        }
        System.out.println("Total students: " + allStudents.size());
    }

    private static void modifyStudent() {
        System.out.print("Enter student ID to modify: ");
        String id = scanner.nextLine();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Current details:");
        student.displayStudentDetails();

        System.out.println("\nEnter new details (press Enter to keep current value):");

        System.out.print("Name [" + student.getName() + "]: ");
        String name = scanner.nextLine();

        System.out.print("Email [" + student.getEmail() + "]: ");
        String email = scanner.nextLine();

        System.out.print("Contact [" + student.getContactNumber() + "]: ");
        String contact = scanner.nextLine();

        System.out.print("Department [" + student.getDepartment() + "]: ");
        String dept = scanner.nextLine();

        student.modifyStudentDetails(
                name.isEmpty() ? null : name,
                email.isEmpty() ? null : email,
                contact.isEmpty() ? null : contact,
                dept.isEmpty() ? null : dept
        );

        System.out.println(" Student details updated successfully.");
        student.displayStudentDetails();
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("You are about to delete:");
        student.displayStudentDetails();

        System.out.print("Are you sure? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            allStudents.remove(student);
            if (currentStudent == student) {
                currentStudent = null;
            }
            System.out.println(" Student deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void setCurrentStudentById() {
        System.out.print("Enter student ID to set as current: ");
        String id = scanner.nextLine();

        Student student = findStudentById(id);
        if (student != null) {
            currentStudent = student;
            System.out.println(" Current student set to: " + student.getName() + " (" + student.getStudentID() + ")");
        } else {
            System.out.println(" Student not found.");
        }
    }

    private static Student findStudentById(String id) {
        for (Student student : allStudents) {
            if (student.getStudentID().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }

    private static void registerStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter contact number: ");
        String contact = scanner.nextLine();

        // Check if student ID already exists
        if (findStudentById(id) != null) {
            System.out.println(" Student with ID '" + id + "' already exists.");
            return;
        }

        currentStudent = new Student(name, email, contact, id, dept);
        allStudents.add(currentStudent);
        System.out.println(" Student registered successfully.");
        currentStudent.displayStudentDetails();
    }

    private static void registerCourseForStudent() {
        if (!ensureStudent()) return;

        if (currentStudent.getCourses().size() >= 3) {
            System.out.println("Student already has 3 courses registered (limit reached).");
            return;
        }

        System.out.println("Register Course (you can register up to 3 courses total).");
        System.out.print("Enter course name: ");
        String cname = scanner.nextLine();
        System.out.print("Enter course code: ");
        String ccode = scanner.nextLine();
        int credits = 0;
        while (true) {
            try {
                System.out.print("Enter credit hours (integer): ");
                credits = Integer.parseInt(scanner.nextLine());
                if (credits <= 0) {
                    System.out.println("Credit hours must be positive.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }

        // Use overloaded method (compile-time polymorphism)
        boolean success = currentStudent.registerCourse(cname, ccode, credits);
        if (success) System.out.println("Course registered.");
        else System.out.println("Unable to register course (limit reached).");
    }

    private static void processPaymentForStudent() {
        double totalFees = currentStudent.calculateTotalFees();
        double paid = currentStudent.getFeesPaid();
        double due = totalFees - paid;
        if (totalFees == 0) {
            System.out.println("No courses registered -> no fees to pay.");
            return;
        }

        System.out.println("Total Fees: GHS " + totalFees);
        System.out.println("Already Paid: GHS " + paid);
        System.out.println("Amount Due: GHS " + due);
        System.out.println("Choose payment type:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. Mobile Money");
        System.out.print("Your choice (1-3): ");
        String pchoice = scanner.nextLine();

        Payment payment = null;
        double amountToPay = 0.0;
        while (true) {
            try {
                System.out.print("Enter amount to pay now: ");
                amountToPay = Double.parseDouble(scanner.nextLine());
                if (amountToPay <= 0) {
                    System.out.println("Amount must be positive.");
                    continue;
                }
                if (amountToPay > due) {
                    System.out.println("Amount cannot exceed due (" + due + "). Enter a lower amount or equal.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Try again.");
            }
        }

        switch (pchoice) {
            case "1":
                payment = new CashPayment(amountToPay, currentStudent.getName());
                break;
            case "2":
                payment = new CardPayment(amountToPay, currentStudent.getName());
                break;
            case "3":
                payment = new MobileMoneyPayment(amountToPay, currentStudent.getName());
                break;
            default:
                System.out.println("Invalid payment type.");
                return;
        }

        // Runtime polymorphism: processPayment() implemented differently in subclasses
        boolean success = payment.processPayment();
        if (success) {
            currentStudent.addPayment(amountToPay);
            System.out.println("Payment of GHS " + amountToPay + " recorded.");
            System.out.println("Payment status: " + currentStudent.getPaymentStatus());
        } else {
            System.out.println("Payment failed.");
        }
    }

    private static void displayReport() {
        System.out.println("\n===== Student Summary Report =====");
        currentStudent.displayStudentDetails();
        currentStudent.displayCourses();
        double total = currentStudent.calculateTotalFees();
        System.out.println("Total Fees: GHS " + total);
        System.out.println("Fees Paid: GHS " + currentStudent.getFeesPaid());
        System.out.println("Payment Status: " + currentStudent.getPaymentStatus());
        System.out.println("==================================");
    }

    private static void registerStaff() {
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        System.out.print("Enter staff ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        System.out.print("Enter department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter contact number: ");
        String contact = scanner.nextLine();

        currentStaff = new Staff(name, email, contact, id, role, dept);
        allStaff.add(currentStaff);
        System.out.println("Staff registered successfully.");
    }

    private static boolean ensureStudent() {
        if (currentStudent == null) {
            System.out.println("No student registered. Please register a student first.");
            return false;
        }
        return true;
    }
}