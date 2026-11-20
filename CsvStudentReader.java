
package com.datheon.studentgrades;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class CsvStudentReader {

public static List<Student> loadStudentsFromCsv() throws IOException {
    String filePath = "C:\\Users\\USER\\Downloads\\student_grades.csv";

    Map<Integer, Student> studentsMap = new LinkedHashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        reader.readLine(); // Skip header

        String line;

        while ((line = reader.readLine()) != null) {
        	String[] data = line.split(",", -1);

            int id = Integer.parseInt(data[0].trim());
            String name = data[1].trim();

String course = data[2].trim();

Student student = studentsMap.get(id);

if (student == null) {
    student = new Student(id, name);
    studentsMap.put(id, student);
}

if (course.isEmpty() || data[3].trim().isEmpty()) {
    continue;
}

double score = Double.parseDouble(data[3].trim());
student.addGrade(course, score);


           
        }
    }

    return new ArrayList<>(studentsMap.values());
}


    public static void main(String[] args) {

    	String filePath = "C:\\Users\\USER\\Downloads\\sample_student_grades.csv";

        List<Student> students = new ArrayList<>();

        try (BufferedReader reader =
                 new BufferedReader(new FileReader(filePath))) {

            String line;

            // Skip the header row
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                int studentId = Integer.parseInt(data[0].trim());
                String name = data[1].trim();

                int math = Integer.parseInt(data[2].trim());
                int science = Integer.parseInt(data[3].trim());
                int english = Integer.parseInt(data[4].trim());
                int computer = Integer.parseInt(data[5].trim());

                Student student = new Student(studentId, name);

                student.addGrade("Math", math);
                student.addGrade("Science", science);
                student.addGrade("English", english);
                student.addGrade("Computer", computer);

                students.add(student);

                double average =
                    (math + science + english + computer) / 4.0;

                String grade = calculateGrade(average);

                System.out.println("Student ID: " + studentId);
                System.out.println("Name: " + name);
                System.out.println("Math: " + math);
                System.out.println("Science: " + science);
                System.out.println("English: " + english);
                System.out.println("Computer: " + computer);
                System.out.println("Average: " + average);
                System.out.println("Grade: " + grade);
                System.out.println("--------------------");
            }

            // Display total number of students
            System.out.println("Total students: " + students.size());

            // Search for a student
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter student ID: ");
            int searchId = scanner.nextInt();

            boolean found = false;

            for (Student s : students) {
                if (s.getId() == searchId) {
                    System.out.println(
                        "Student found: ID = " + s.getId()
                        + ", Name = " + s.getName()
                    );
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student not found.");
            }

            // Test grade calculation
            System.out.println("Grade Tests:");
            System.out.println(calculateGrade(95));
            System.out.println(calculateGrade(85));
            System.out.println(calculateGrade(75));
            System.out.println(calculateGrade(65));
            System.out.println(calculateGrade(55));

        } catch (IOException e) {
            System.out.println("Could not read the CSV file.");
            e.printStackTrace();
        }
    }


public static void saveStudentsToCsv(List<Student> students)
        throws IOException {

    String filePath = "C:\\Users\\USER\\Downloads\\student_grades.csv";

    try (BufferedWriter writer =
            new BufferedWriter(new FileWriter(filePath))) {

        writer.write("student_id,name,course,score");
        writer.newLine();

        for (Student student : students) {

            Map<String, Double> grades = student.getGrades();

            if (grades.isEmpty()) {
                writer.write(
                    student.getId() + ","
                    + student.getName() + ",,"
                );
                writer.newLine();
                continue;
            }

            for (Map.Entry<String, Double> entry : grades.entrySet()) {
                writer.write(
                    student.getId() + ","
                    + student.getName() + ","
                    + entry.getKey() + ","
                    + entry.getValue()
                );
                writer.newLine();
            }
        }
    }
}



    public static String calculateGrade(double average) {

        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}
