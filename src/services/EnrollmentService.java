package services;

import entities.Course;
import entities.Enrollment;
import entities.Student;
import enums.Grade;

public class EnrollmentService {

    private final Enrollment[] enrollments = new Enrollment[200];
    private int enrollmentCount = 0;

    public void enrollStudent(Student student,
                              Course course,
                              String semester) {

        if (enrollmentCount >= enrollments.length) {
            throw new IllegalStateException("Enrollment storage is full.");
        }

        enrollments[enrollmentCount++] =
                new Enrollment(student, course, semester);
    }

    public Enrollment[] getAllEnrollments() {

        Enrollment[] result = new Enrollment[enrollmentCount];

        for (int i = 0; i < enrollmentCount; i++) {
            result[i] = enrollments[i];
        }

        return result;
    }

    public Enrollment findById(int id) {

        for (int i = 0; i < enrollmentCount; i++) {

            if (enrollments[i].getId() == id) {
                return enrollments[i];
            }

        }

        return null;
    }

    public void setGrade(int enrollmentId, Grade grade) {

        Enrollment enrollment = findById(enrollmentId);

        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment not found.");
        }

        enrollment.setGrade(grade);
    }

    public void markPaid(int enrollmentId) {

        Enrollment enrollment = findById(enrollmentId);

        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment not found.");
        }

        enrollment.markAsPaid();
    }

    public Enrollment[] getStudentEnrollments(Student student) {

        Enrollment[] temp = new Enrollment[enrollmentCount];
        int count = 0;

        for (int i = 0; i < enrollmentCount; i++) {

            if (enrollments[i].getStudent().equals(student)) {
                temp[count++] = enrollments[i];
            }

        }

        Enrollment[] result = new Enrollment[count];

        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }

        return result;
    }

    public Enrollment[] getCourseEnrollments(Course course) {

        Enrollment[] temp = new Enrollment[enrollmentCount];
        int count = 0;

        for (int i = 0; i < enrollmentCount; i++) {

            if (enrollments[i].getCourse().equals(course)) {
                temp[count++] = enrollments[i];
            }

        }

        Enrollment[] result = new Enrollment[count];

        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }

        return result;
    }

    public boolean deleteEnrollment(int id) {

        for (int i = 0; i < enrollmentCount; i++) {

            if (enrollments[i].getId() == id) {

                for (int j = i; j < enrollmentCount - 1; j++) {
                    enrollments[j] = enrollments[j + 1];
                }

                enrollments[enrollmentCount - 1] = null;
                enrollmentCount--;

                return true;
            }

        }

        return false;
    }

    public int getEnrollmentCount() {
        return enrollmentCount;
    }

}
