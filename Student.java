import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String studentID;
    private String department;
    private double feesPaid;
    private List<Course> courses;

    public Student() {
        this.courses = new ArrayList<>();
        this.feesPaid = 0.0;
    }

    public Student(String name, String email, String contactNumber, String studentID, String department) {
        super(name, email, contactNumber);
        this.studentID = studentID;
        this.department = department;
        this.feesPaid = 0.0;
        this.courses = new ArrayList<>();
    }

    // Getters & Setters
    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getFeesPaid() { return feesPaid; }
    public void setFeesPaid(double feesPaid) { this.feesPaid = feesPaid; }

    public List<Course> getCourses() { return courses; }

    // Compile-time polymorphism (method overloading) for registering courses:
    public boolean registerCourse(Course course) {
        if (courses.size() >= 3) return false;
        courses.add(course);
        return true;
    }

    public boolean registerCourse(String name, String code, int creditHours) {
        if (courses.size() >= 3) return false;
        Course c = new Course(name, code, creditHours);
        courses.add(c);
        return true;
    }

    public double calculateTotalFees() {
        int totalCredits = 0;
        for (Course c : courses) totalCredits += c.getCreditHours();
        // Example fee calculation: 1000 per credit hour
        return totalCredits * 1000.0;
    }

    public void addPayment(double amount) {
        this.feesPaid += amount;
    }

    public String getPaymentStatus() {
        double total = calculateTotalFees();
        if (feesPaid >= total && total > 0) return "PAID";
        if (feesPaid > 0 && feesPaid < total) return "PARTIALLY PAID";
        return "NOT PAID";
    }

    public void displayStudentDetails() {
        System.out.println("----- Student Details -----");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getStudentID());
        System.out.println("Department: " + getDepartment());
        System.out.println("Email: " + getEmail());
        System.out.println("Contact: " + getContactNumber());
        System.out.println("Fees Paid: GHS " + getFeesPaid());
        System.out.println("Payment Status: " + getPaymentStatus());
    }

    public void displayCourses() {
        System.out.println("----- Registered Courses -----");
        if (courses.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    // Method to modify student details
    public void modifyStudentDetails(String name, String email, String contactNumber, String department) {
        if (name != null && !name.isEmpty()) setName(name);
        if (email != null && !email.isEmpty()) setEmail(email);
        if (contactNumber != null && !contactNumber.isEmpty()) setContactNumber(contactNumber);
        if (department != null && !department.isEmpty()) setDepartment(department);
    }

    // Method to remove a course by code
    public boolean removeCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(courseCode)) {
                courses.remove(course);
                return true;
            }
        }
        return false;
    }

    // Method to clear all courses
    public void clearAllCourses() {
        courses.clear();
    }
}