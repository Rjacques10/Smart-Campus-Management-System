public class Department {
    private String name;
    private String head;

    public Department() {}

    public Department(String name, String head) {
        this.name = name;
        this.head = head;
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHead() { return head; }
    public void setHead(String head) { this.head = head; }

    public void displayDepartment() {
        System.out.println("Department: " + name + " | Head: " + head);
    }
}
