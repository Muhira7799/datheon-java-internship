
package com.datheon.studentgrades;

import java.util.HashMap;
import java.util.Map;

public class Student {

    private int id;
    private String name;
    private Map<String, Double> grades = new HashMap<>();

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public Map<String, Double> getGrades() {
        return new HashMap<>(grades);
    }

    public void addGrade(String course, double score) {
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty.");
        }

        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }

        grades.put(course, score);
    }

public double calculateAverage() {
    if (grades.isEmpty()) {
        return 0;
    }

    double total = 0;

    for (double score : grades.values()) {
        total += score;
    }

    return total / grades.size();
}

public double calculateGPA() {
    if (grades.isEmpty()) {
        return 0.0;
    }

    double totalPoints = 0.0;

    for (double score : grades.values()) {
        if (score >= 90) {
            totalPoints += 4.0;
        } else if (score >= 80) {
            totalPoints += 3.0;
        } else if (score >= 70) {
            totalPoints += 2.0;
        } else if (score >= 60) {
            totalPoints += 1.0;
        } else {
            totalPoints += 0.0;
        }
    }

    return totalPoints / grades.size();
}

}
