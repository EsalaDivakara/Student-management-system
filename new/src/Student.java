// Student class represents a student in the student management system
public class Student {

    // Private fields to store student details
    private String studentID;        // Student ID
    private String studentName;      // Student Name
    private Module[] modules = new Module[3]; // Modules associated with the student
    private String grade;            // Grade of the student
    private double average;          // Average marks of the student

    // Constructor to initialize Student with studentID, studentName, and modules
    public Student(String studentID, String studentName, Module[] modules) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.modules = modules;
        calculateGrade();  // Calculate grade based on module marks
    }

    // Getter for studentID
    public String getStudentID() {
        return studentID;
    }

    // Setter for studentID
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    // Getter for studentName
    public String getStudentName() {
        return studentName;
    }

    // Setter for studentName
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Getter for modules
    public Module[] getModules() {
        return modules;
    }

    // Setter for modules
    public void setModules(Module[] modules) {
        this.modules = modules;
        calculateGrade();  // Recalculate grade when modules are set
    }

    // Getter for grade
    public String getGrade() {
        return grade;
    }

    // Getter for average
    public double getAverage() {
        return average;
    }

    // Method to calculate the grade based on module marks
    public void calculateGrade() {
        int total = 0;
        for (Module module : modules) {
            total += module.getMarks();
        }
        this.average = total / 3.0;  // Calculate average marks
        // Determine grade based on average marks
        if (average >= 80) {
            this.grade = "Distinction";
        } else if (average >= 70) {
            this.grade = "Merit";
        } else if (average >= 40) {
            this.grade = "Pass";
        } else {
            this.grade = "Fail";
        }
    }
}
