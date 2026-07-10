package services;

import entities.Course;
import entities.Teacher;

public class CourseService {

    private final Course[] courses = new Course[100];
    private int courseCount = 0;

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (courseCount >= courses.length) {
            throw new IllegalStateException("Course storage is full.");
        }

        courses[courseCount++] = course;
    }

    public Course[] getAllCourses() {
        Course[] result = new Course[courseCount];

        for (int i = 0; i < courseCount; i++) {
            result[i] = courses[i];
        }

        return result;
    }

    public Course findById(int id) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getId() == id) {
                return courses[i];
            }
        }

        return null;
    }

    public void updateCourse(int id,
                             String name,
                             int credits,
                             Teacher teacher,
                             double price) {

        Course course = findById(id);

        if (course == null) {
            throw new IllegalArgumentException("Course not found.");
        }

        course.setName(name);
        course.setCredits(credits);
        course.setTeacher(teacher);
        course.setPrice(price);
    }

    public boolean deleteCourse(int id) {

        for (int i = 0; i < courseCount; i++) {

            if (courses[i].getId() == id) {

                for (int j = i; j < courseCount - 1; j++) {
                    courses[j] = courses[j + 1];
                }

                courses[courseCount - 1] = null;
                courseCount--;

                return true;
            }
        }

        return false;
    }

    public Course[] filterByCredits(int credits) {

        Course[] temp = new Course[courseCount];
        int count = 0;

        for (int i = 0; i < courseCount; i++) {

            if (courses[i].getCredits() == credits) {
                temp[count++] = courses[i];
            }

        }

        Course[] result = new Course[count];

        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }

        return result;
    }

    public Course[] filterByTeacher(Teacher teacher) {

        Course[] temp = new Course[courseCount];
        int count = 0;

        for (int i = 0; i < courseCount; i++) {

            if (courses[i].getTeacher().equals(teacher)) {
                temp[count++] = courses[i];
            }

        }

        Course[] result = new Course[count];

        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }

        return result;
    }

    public int getCourseCount() {
        return courseCount;
    }

}
