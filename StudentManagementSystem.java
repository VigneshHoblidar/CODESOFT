import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadData();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveData();
        System.out.println("Student added successfully.");
    }

    public void removeStudent(int rollNumber) {
        boolean removed = students.removeIf(student -> student.getRollNumber() == rollNumber);
        if (removed) {
            saveData();
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}


import java.util.Scanner;

public class StudentManagementSystemApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent(sms, scanner);
                    break;
                case 2:
                    removeStudent(sms, scanner);
                    break;
                case 3:
                    searchStudent(sms, scanner);
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private static void addStudent(StudentManagementSystem sms, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        int rollNumber = -1;
        while (rollNumber < 0) {
            System.out.print("Enter roll number: ");
            if (scanner.hasNextInt()) {
                rollNumber = scanner.nextInt();
                if (rollNumber < 0) {
                    System.out.println("Roll number must be positive.");
                }
            } else {
                System.out.println("Invalid roll number. Please enter a valid integer.");
                scanner.next();
            }
        }

        scanner.nextLine();
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();

        Student student = new Student(name, rollNumber, grade);
        sms.addStudent(student);
    }

    private static void removeStudent(StudentManagementSystem sms, Scanner scanner) {
        System.out.print("Enter roll number of student to remove: ");
        int rollNumber = scanner.nextInt();
        sms.removeStudent(rollNumber);
    }

    private static void searchStudent(StudentManagementSystem sms, Scanner scanner) {
        System.out.print("Enter roll number of student to search: ");
        int rollNumber = scanner.nextInt();
        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found: " + student);
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }
}

