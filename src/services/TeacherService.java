package services;

import entities.Teacher;
import enums.TeacherPosition;

public class TeacherService {

    private final Teacher[] teachers = new Teacher[100];
    private int teacherCount = 0;

    public void addTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null.");
        }

        if (teacherCount >= teachers.length) {
            throw new IllegalStateException("Teacher storage is full.");
        }

        teachers[teacherCount++] = teacher;
    }

    public Teacher[] getAllTeachers() {
        Teacher[] result = new Teacher[teacherCount];

        for (int i = 0; i < teacherCount; i++) {
            result[i] = teachers[i];
        }

        return result;
    }

    public Teacher findById(String teacherId) {
        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i].getTeacherId().equalsIgnoreCase(teacherId)) {
                return teachers[i];
            }
        }

        return null;
    }

    public void updateTeacher(
            String teacherId,
            String name,
            String email,
            String phoneNumber,
            TeacherPosition position
    ) {
        Teacher teacher = findById(teacherId);

        if (teacher == null) {
            throw new IllegalArgumentException("Teacher not found.");
        }

        teacher.setName(name);
        teacher.setEmail(email);
        teacher.setPhoneNumber(phoneNumber);
        teacher.setPosition(position);
    }

    public boolean deleteTeacher(String teacherId) {
        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i].getTeacherId().equalsIgnoreCase(teacherId)) {

                for (int j = i; j < teacherCount - 1; j++) {
                    teachers[j] = teachers[j + 1];
                }

                teachers[teacherCount - 1] = null;
                teacherCount--;

                return true;
            }
        }

        return false;
    }

    public Teacher[] filterByPosition(TeacherPosition position) {
        Teacher[] temporary = new Teacher[teacherCount];
        int resultCount = 0;

        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i].getPosition() == position) {
                temporary[resultCount++] = teachers[i];
            }
        }

        Teacher[] result = new Teacher[resultCount];

        for (int i = 0; i < resultCount; i++) {
            result[i] = temporary[i];
        }

        return result;
    }

    public int getTeacherCount() {
        return teacherCount;
    }
}
