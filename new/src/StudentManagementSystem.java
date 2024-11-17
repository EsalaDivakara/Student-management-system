import java.io.*;
import java.util.Scanner;

public class StudentManagementSystem {
    // Maximum number of students the system can handle
    private static final int MAX_CAPACITY = 100;
    // Array to store student objects
    private static Student[] students = new Student[MAX_CAPACITY];
    // Counter to keep track of the number of students
    private static int studentCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Main program loop
        do {
            showMenu();
            // Input validation for menu choice
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // clear invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            // Switch statement to handle menu choices
            switch (choice) {
                case 1:
                    checkAvailableSeats();
                    break;
                case 2:
                    registerStudent(scanner);
                    break;
                case 3:
                    deleteStudent(scanner);
                    break;
                case 4:
                    findStudent(scanner);
                    break;
                case 5:
                    storeStudentDetails();
                    break;
                case 6:
                    loadStudentDetails();
                    break;
                case 7:
                    viewStudentsByName();
                    break;
                case 8:
                    showSubMenu(scanner);
                    break;
                case 0:
                    System.out.println("Closing the program");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // Display the main menu options
    private static void showMenu() {
        System.out.println("\nUniversity Management System Menu");
        System.out.println("1. Check available seats");
        System.out.println("2. Register student (with ID)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student (with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from the file");
        System.out.println("7. View the list of students based on their names");
        System.out.println("8. Additional controls");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // Check and display the number of available seats
    private static void checkAvailableSeats() {
        System.out.println("Available seats: " + (MAX_CAPACITY - studentCount));
    }

    // Register a new student
    private static void registerStudent(Scanner scanner) {
        if (studentCount >= MAX_CAPACITY) {
            System.out.println("No more seats available.");
            return;
        }

        // Input and validate student ID
        System.out.print("Enter Student ID (length 8, e.g., w1234567): ");
        String studentID = scanner.nextLine();
        if (!studentID.matches("w\\d{7}")) {
            System.out.println("Invalid Student ID. Must be 'w' followed by 7 digits.");
            return;
        }

        // Input and validate student name
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();
        if (studentName.trim().isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return;
        }

        // Create and populate module array
        Module[] modules = new Module[3];
        // Initialize modules with default names
        modules[0] = new Module("Module 1", 0);
        modules[1] = new Module("Module 2", 0);
        modules[2] = new Module("Module 3", 0);

        // Input and validate module marks
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter Module " + (i + 1) + " Marks (0-100): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 0 and 100.");
                scanner.next(); // clear invalid input
            }
            int marks = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (marks < 0 || marks > 100) {
                System.out.println("Invalid mark. Please enter a number between 0 and 100.");
                return;
            }
            modules[i].setMarks(marks);
        }

        // Create new student object and add to array
        students[studentCount++] = new Student(studentID, studentName, modules);
        System.out.println("Student registered successfully.");
    }

    // Delete a student by ID
    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        String studentID = scanner.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(studentID)) {
                students[i] = students[--studentCount]; // replace with last student
                students[studentCount] = null;
                System.out.println("Student deleted successfully.");
                return;
            }
        }

        System.out.println("Student ID not found.");
    }

    // Find and display student details by ID
    private static void findStudent(Scanner scanner) {
        System.out.print("Enter Student ID to find: ");
        String studentID = scanner.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(studentID)) {
                System.out.println("Student ID: " + students[i].getStudentID());
                System.out.println("Student Name: " + students[i].getStudentName());
                for (int j = 0; j < 3; j++) {
                    System.out.println("Module " + (j + 1) + " Name: " + students[i].getModules()[j].getModuleName());
                    System.out.println("Module " + (j + 1) + " Marks: " + students[i].getModules()[j].getMarks());
                }
                System.out.println("Average: " + students[i].getAverage());
                System.out.println("Grade: " + students[i].getGrade());
                return;
            }
        }

        System.out.println("Student ID not found.");
    }

    // Store student details to a file
    private static void storeStudentDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("studentDetails.txt"))) {
            for (int i = 0; i < studentCount; i++) {
                writer.write(students[i].getStudentID() + "," + students[i].getStudentName());
                for (int j = 0; j < 3; j++) {
                    writer.write("," + students[i].getModules()[j].getModuleName() + "," + students[i].getModules()[j].getMarks());
                }
                writer.write("\n");
            }
            System.out.println("Student details stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing student details: " + e.getMessage());
        }
    }


    // Load student details from a file
    private static void loadStudentDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader("studentDetails.txt"))) {
            String line;
            studentCount = 0;
            while ((line = reader.readLine()) != null && studentCount < MAX_CAPACITY) {
                String[] details = line.split(",");
                String studentID = details[0];
                String studentName = details[1];
                Module[] modules = new Module[3];
                modules[0] = new Module(details[2], Integer.parseInt(details[3]));
                modules[1] = new Module(details[4], Integer.parseInt(details[5]));
                modules[2] = new Module(details[6], Integer.parseInt(details[7]));
                students[studentCount++] = new Student(studentID, studentName, modules);

                // Print student details to the console
                System.out.println("Student ID: " + studentID);
                System.out.println("Student Name: " + studentName);
                for (int i = 0; i < 3; i++) {
                    System.out.println("Module " + (i + 1) + " Name: " + modules[i].getModuleName());
                    System.out.println("Module " + (i + 1) + " Marks: " + modules[i].getMarks());
                }
                System.out.println();
            }
            System.out.println("Student details loaded successfully.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }


    // View students sorted by name

    private static void viewStudentsByName() {
        loadStudentDetails(); // Load student details from the file

        // Bubble sort students by name
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = 0; j < studentCount - 1 - i; j++) {
                if (students[j].getStudentName().compareTo(students[j + 1].getStudentName()) > 0) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }

        // Display sorted student details
        System.out.println("Students sorted by name:");
        for (int i = 0; i < studentCount; i++) {
            System.out.println("Student ID: " + students[i].getStudentID());
            System.out.println("Student Name: " + students[i].getStudentName());
            for (int j = 0; j < 3; j++) {
                System.out.println("Module " + (j + 1) + " Name: " + students[i].getModules()[j].getModuleName());
                System.out.println("Module " + (j + 1) + " Marks: " + students[i].getModules()[j].getMarks());
            }
            System.out.println("Average: " + students[i].getAverage());
            System.out.println("Grade: " + students[i].getGrade());
            System.out.println();
        }
    }

    // Display and handle sub-menu options
    private static void showSubMenu(Scanner scanner) {
        boolean validChoice = false;
        do {
            // Display sub-menu options
            System.out.println("a. Add student name");
            System.out.println("b. Module marks 1, 2, and 3");
            System.out.println("c. Generate a summary of the system");
            System.out.println("d. Generate complete report");
            System.out.println("0. Return to main menu");
            System.out.print("Enter your choice: ");
            String subChoice = scanner.nextLine();

            // Handle sub-menu choices
            switch (subChoice) {
                case "a":
                    addStudentName(scanner);
                    validChoice = true;
                    break;
                case "b":
                    addModuleMarks(scanner);
                    validChoice = true;
                    break;
                case "c":
                    generateSummary();
                    validChoice = true;
                    break;
                case "d":
                    generateCompleteReport();
                    validChoice = true;
                    break;
                case "0":
                    System.out.println("Returning to main menu.");
                    validChoice = true; // exit loop to return to main menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!validChoice);
    }

    // Add or update student name
    private static void addStudentName(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(studentID)) {
                System.out.print("Enter new Student Name: ");
                students[i].setStudentName(scanner.nextLine());
                System.out.println("Student name updated successfully.");
                return;
            }
        }

        System.out.println("Student ID not found.");
    }

    // Add or update module marks for a student
    private static void addModuleMarks(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(studentID)) {
                for (int j = 0; j < 3; j++) {
                    System.out.print("Enter new Module " + (j + 1) + " Marks (0-100): ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number between 0 and 100.");
                        scanner.next(); // clear invalid input
                    }
                    students[i].getModules()[j].setMarks(scanner.nextInt());
                    scanner.nextLine(); // consume newline
                    if (students[i].getModules()[j].getMarks() < 0 || students[i].getModules()[j].getMarks() > 100) {
                        System.out.println("Invalid mark. Please enter a number between 0 and 100.");
                        return;
                    }
                }
                students[i].calculateGrade();
                System.out.println("Module marks updated successfully.");
                return;
            }
        }

        System.out.println("Student ID not found.");
    }

    // Generate a summary of the system
    private static void generateSummary() {
        System.out.println("Total student registrations: " + studentCount);
        int passedModule1 = 0, passedModule2 = 0, passedModule3 = 0;
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getModules()[0].getMarks() > 40) passedModule1++;
            if (students[i].getModules()[1].getMarks() > 40) passedModule2++;
            if (students[i].getModules()[2].getMarks() > 40) passedModule3++;
        }
        System.out.println("Total students scored more than 40 marks in Module 1: " + passedModule1);
        System.out.println("Total students scored more than 40 marks in Module 2: " + passedModule2);
        System.out.println("Total students scored more than 40 marks in Module 3: " + passedModule3);
    }

    // Generate a complete report of all students
    private static void generateCompleteReport() {
        // Bubble sort students by average marks (highest to lowest)
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = 0; j < studentCount - 1 - i; j++) {
                if (students[j].getAverage() < students[j + 1].getAverage()) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }

        // Display sorted student details
        System.out.println("Complete Report:");
        for (int i = 0; i < studentCount; i++) {
            System.out.println("Student ID: " + students[i].getStudentID());
            System.out.println("Student Name: " + students[i].getStudentName());
            for (int j = 0; j < 3; j++) {
                System.out.println("Module " + (j + 1) + " Name: " + students[i].getModules()[j].getModuleName());
                System.out.println("Module " + (j + 1) + " Marks: " + students[i].getModules()[j].getMarks());
            }
            int total = students[i].getModules()[0].getMarks() + students[i].getModules()[1].getMarks() + students[i].getModules()[2].getMarks();
            System.out.println("Total: " + total);
            System.out.println("Average: " + students[i].getAverage());
            System.out.println("Grade: " + students[i].getGrade());
            System.out.println();
        }
    }
}