// Module class represents a module in the student management system
public class Module {
    // Private fields to store module name and marks
    private String moduleName;
    private int marks;

    // Constructor to initialize Module with moduleName and marks
    public Module(String moduleName, int marks) {
        this.moduleName = moduleName;
        this.marks = marks;
    }

    // Getter for moduleName
    public String getModuleName() {
        return moduleName;
    }

    // Setter for moduleName
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    // Getter for marks
    public int getMarks() {
        return marks;
    }

    // Setter for marks
    public void setMarks(int marks) {
        this.marks = marks;
    }
}
