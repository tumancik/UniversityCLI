package entities;

import enums.Grade;

import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {

    private static int nextId = 1;

    private final int id;
    private Student student;
    private Course course;
    private String semester;
    private Grade grade;
    private boolean paid;
    private final LocalDate enrolledAt;

    public Enrollment(Student student, Course course, String semester) {
        this.id = nextId++;
        setStudent(student);
        setCourse(course);
        setSemester(semester);

        this.grade = Grade.NA;
        this.paid = false;
        this.enrolledAt = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }

        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        if (semester == null || semester.isBlank()) {
            throw new IllegalArgumentException("Semester cannot be empty.");
        }

        this.semester = semester;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null.");
        }

        this.grade = grade;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markAsPaid() {
        this.paid = true;
    }

    public void markAsUnpaid() {
        this.paid = false;
    }

    public LocalDate getEnrolledAt() {
        return enrolledAt;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", student=" + student.getName() +
                ", course=" + course.getName() +
                ", semester='" + semester + '\'' +
                ", grade=" + grade +
                ", paid=" + paid +
                ", enrolledAt=" + enrolledAt +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Enrollment enrollment)) {
            return false;
        }

        return id == enrollment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
