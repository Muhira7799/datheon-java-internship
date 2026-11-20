
package com.datheon.studentgrades;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class GradeManager {
	private List<Student> students = new ArrayList<>();
	
	public void addStudent(Student student) {
	    students.add(student);
	}
	public boolean addNewStudent(int id, String name) {

	    if (id <= 0) {
	        System.out.println("Student ID must be positive.");
	        return false;
	    }

	    if (findStudentById(id) != null) {
	        System.out.println("Student ID already exists.");
	        return false;
	    }

	    if (name == null || name.trim().isEmpty()) {
	        System.out.println("Student name cannot be empty.");
	        return false;
	    }

Student newStudent = new Student(id, name);
students.add(newStudent);

try {
    CsvStudentReader.saveStudentsToCsv(students);
    System.out.println("Student added successfully and saved to CSV.");
    return true;
} catch (IOException e) {
    students.remove(newStudent);
    System.out.println("Could not save student to CSV.");
    return false;
}

	}
	public boolean addGradeToStudent(int id, String course, double score) {
	    Student student = findStudentById(id);

	    if (student == null) {
	        System.out.println("Student not found.");
	        return false;
	    }


try {
    student.addGrade(course, score);

    CsvStudentReader.saveStudentsToCsv(students);

    System.out.println("Grade added successfully and saved to CSV.");
    return true;

} catch (IllegalArgumentException e) {
    System.out.println("Invalid grade: " + e.getMessage());
    return false;

} catch (IOException e) {
    System.out.println("Grade was added, but could not be saved to CSV.");
    return false;
}

	}
	public Student findStudentById(int id) {
	    for (Student student : students) {
	        if (student.getId() == id) {
	            return student;
	        }
	    }
	    return null;
	}
	public List<Student> getAllStudents() {
	    return new ArrayList<>(students);
	}
	public double calculateAverage(Student student) {
	    return student.calculateAverage();
	}
	public double calculateClassAverage() {
	    if (students.isEmpty()) {
	        return 0;
	    }

	    double total = 0;

	    for (Student student : students) {
	        total += calculateAverage(student);
	    }

	    return total / students.size();
	}
	public double calculateMedian() {
	    if (students.isEmpty()) {
	        return 0;
	    }

	    List<Double> averages = new ArrayList<>();

	    for (Student student : students) {
	        averages.add(calculateAverage(student));
	    }

	    averages.sort(Double::compareTo);

	    int middle = averages.size() / 2;

	    if (averages.size() % 2 == 0) {
	        return (averages.get(middle - 1)
	                + averages.get(middle)) / 2.0;
	    }

	    return averages.get(middle);
	}
	public String generateReport(Student student) {
	    StringBuilder report = new StringBuilder();

	    report.append("Student ID: ")
	          .append(student.getId()).append("\n");

	    report.append("Student Name: ")
	          .append(student.getName()).append("\n");

	    report.append("Grades: ")
	          .append(student.getGrades()).append("\n");

	    report.append(String.format(
	        "Average: %.2f%n", calculateAverage(student)));
	    report.append(String.format("GPA: %.2f / 4.0%n", student.calculateGPA()));

	    return report.toString();
	
	}

public void displayAllStudents() {
    System.out.println(String.format(
            "%-12s %-20s %-45s %10s %10s",
            "Student ID", "Student Name", "Grades", "Average", "GPA"));
    System.out.println("----------------------------------------------------------------------------------------------");

    for (Student student : students) {
        System.out.println(String.format(
                "%-12d %-20s %-45s %10.2f %10.2f",
                student.getId(),
                student.getName(),
                student.getGrades(),
                calculateAverage(student),
                student.calculateGPA()));
    }
}

	public void displayStudentById(int id) {
	    Student student = findStudentById(id);

	    if (student != null) {
	        System.out.println(generateReport(student));
	    } else {
	        System.out.println("Student not found.");
	    }
	}
	public void displayMenu() {
	    System.out.println("\n===== Student Grade Management System =====");
	    System.out.println("1. Display all students");
	    System.out.println("2. Search student by ID");
	    System.out.println("3. Calculate class average");
	    System.out.println("4. Calculate median");
	    System.out.println("5. Add new student");
	    System.out.println("6. Add grade to student");
	    System.out.println("7. Exit");
	    System.out.print("Enter your choice: ");
	}
	

    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	GradeManager manager = new GradeManager();

    	try {
    	    for (Student student : CsvStudentReader.loadStudentsFromCsv()) {
    	        manager.addStudent(student);
    	    }
    	} catch (IOException e) {
    	    System.out.println("Could not load CSV file.");
    	    return;
    	}
    	
    	int choice = 0;

    	while (choice != 7) {
    	    manager.displayMenu();
    	    if (scanner.hasNextInt()) {
    	        choice = scanner.nextInt();
    	    } else {
    	        System.out.println("Invalid input. Please enter a number.");
    	        scanner.next();
    	        continue;
    	    }    	
    	
    	
    	    switch (choice) {
    	    case 1:
    	        manager.displayAllStudents();
    	        break;

    	    case 2:
    	        System.out.print("Enter student ID: ");
    	        int id = scanner.nextInt();
    	        manager.displayStudentById(id);
    	        break;

    	    case 3:
    	        System.out.printf(
    	            "Class Average: %.2f%n",
    	            manager.calculateClassAverage()
    	        );
    	        break;

    	    case 4:
    	        System.out.printf(
    	            "Class Median: %.2f%n",
    	            manager.calculateMedian()
    	        );
    	        break;
    	        
    	    case 5:
    	        System.out.print("Enter new student ID: ");
    	        int newId = scanner.nextInt();
    	        scanner.nextLine();

    	        System.out.print("Enter student name: ");
    	        String newName = scanner.nextLine();

    	        manager.addNewStudent(newId, newName);
    	        break;

    	    case 6:
    	        System.out.print("Enter student ID: ");
    	        int gradeStudentId = scanner.nextInt();
    	        scanner.nextLine();

    	        System.out.print("Enter course name: ");
    	        String course = scanner.nextLine();

    	        System.out.print("Enter score (0-100): ");
    	        double score = scanner.nextDouble();

    	        manager.addGradeToStudent(
    	            gradeStudentId, course, score
    	        );
    	        break;
    	    case 7:
    	        System.out.println("Goodbye!");
    	        break;

    	    default:
    	        System.out.println("Invalid choice. Please try again.");
    	}
    	}

    }
}
