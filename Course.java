public class Course {
    private String name;
    private String code;
    private int creditHours;

    public Course() {}

    public Course(String name, String code, int creditHours) {
        this.name = name;
        this.code = code;
        this.creditHours = creditHours;
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    @Override
    public String toString() {
        return String.format("Course: %s | Code: %s | Credits: %d", name, code, creditHours);
    }
}
