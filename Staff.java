public class Staff extends Person {
    private String staffID;
    private String role;
    private String department;

    public Staff() {}

    public Staff(String name, String email, String contactNumber, String staffID, String role, String department) {
        super(name, email, contactNumber);
        this.staffID = staffID;
        this.role = role;
        this.department = department;
    }

    // Getters & Setters
    public String getStaffID() { return staffID; }
    public void setStaffID(String staffID) { this.staffID = staffID; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public void displayStaffDetails() {
        System.out.println("----- Staff Details -----");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getStaffID());
        System.out.println("Role: " + getRole());
        System.out.println("Department: " + getDepartment());
        System.out.println("Email: " + getEmail());
        System.out.println("Contact: " + getContactNumber());
    }
}
